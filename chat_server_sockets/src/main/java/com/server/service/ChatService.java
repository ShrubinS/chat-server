package com.server.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;
import com.server.thread.MultiServerThread;
import com.server.util.*;

import java.text.Bidi;
import java.util.*;

// Using a singleton instead of a static class
public class ChatService {

    private ChatService instance = null;
    private BiMap<Integer, ChatRoom> chatRooms;

    private ChatService() {
        chatRooms = Maps.synchronizedBiMap(HashBiMap.create());
    }

//    Initialization-on-demand holder idiom
//    Lazily initialized
    private static class LazyHolder {
        static final ChatService INSTANCE = new ChatService();
    }

    public static ChatService getInstance() {
        return LazyHolder.INSTANCE;
    }

    public Output joinChatRoom(MultiServerThread thread, String chatroomName, Client client, ServerInfo serverInfo) {
        ChatRoom chatRoom = new ChatRoom(chatroomName);
        Integer chatRoomRef;
        if (chatRooms.inverse().containsKey(chatRoom)) {
            chatRoomRef = chatRooms.inverse().get(chatRoom);
            chatRoom = chatRooms.get(chatRoomRef);
        } else {
            chatRoomRef = Sequence.nextRoomRef();
            chatRooms.put(chatRoomRef, chatRoom);
        }
        EventBus chatRoomChannel = chatRoom.getChannel();
        chatRoomChannel.register(thread);
        Integer id;
        BiMap<Integer, Client> connectedClients = chatRoom.getConnectedClients();
        if (connectedClients.containsValue(client)) {
            id = connectedClients.inverse().get(client);
        } else {
            id = Sequence.nextJoinId();
            connectedClients.put(id, client);
        }
        String mess =    "CHAT: " + chatRoomRef + "\n" +
                "CLIENT_NAME:" + client.getName() + "\n" +
                "MESSAGE: " + client.getName() + " has joined this chatroom";
        ChannelMessage message = new ChannelMessage("message", mess);
//        chatRoomChannel.post(message);
        String retVal = "JOINED_CHATROOM: " + chatroomName +"\n" +
                "SERVER_IP: " + serverInfo.getServerIp() +"\n" +
                "PORT: " + serverInfo.getServerPort() +"\n" +
                "ROOM_REF: " + chatRoomRef +"\n" +
                "JOIN_ID: " + id;

        return new Output(retVal, chatRoomChannel, message);
    }

    public Output leaveChatRoom(MultiServerThread thread, Integer chatRoomRef, Integer joinId, String clientName) {
        ChatRoom chatRoom = chatRooms.get(chatRoomRef);
        chatRoom.getConnectedClients().remove(joinId);

        EventBus chatRoomChannel = chatRoom.getChannel();
        try {
            chatRoomChannel.unregister(thread);
        } catch (Exception e) {
            // already unregistered
        }
        String mess =    "CHAT: " + chatRoomRef + "\n" +
                "CLIENT_NAME:" + clientName + "\n" +
                "MESSAGE: " + clientName + " has left this chatroom";
        ChannelMessage message = new ChannelMessage("message", mess);
//        chatRoomChannel.post(message);
        String retVal = "LEFT_CHATROOM: " + chatRoomRef + "\n" +
                        "JOIN_ID: " + joinId;
        return new Output(retVal, chatRoomChannel, message);
    }

    public Output leaveAllChatRooms(MultiServerThread thread, Client client) {
        chatRooms.forEach((chatRoomRef, chatRoom) -> {
            BiMap<Integer, Client> connectedClients = chatRoom.getConnectedClients();
            if (connectedClients.inverse().containsKey(client)) {
                leaveChatRoom(thread, chatRoomRef, connectedClients.inverse().get(client), client.getName());
            }
        });
        return new Output("Disconnecting...", null, null);
    }

    public Output chat(Integer chatRoomRef, Integer joinId, String clientName, String clientMessage) {
        ChatRoom chatRoom = chatRooms.get(chatRoomRef);
        ChannelMessage message = new ChannelMessage("", "");
        if (chatRoom.getConnectedClients().containsKey(joinId)) {
            String val =    "CHAT: " + chatRoomRef + "\n" +
                            "CLIENT_NAME:" + clientName + "\n" +
                            "MESSAGE: " + clientMessage;
            message = new ChannelMessage("message", val);
        }
        EventBus chatRoomChannel = chatRoom.getChannel();
        return new Output("", chatRoomChannel, message);
    }
}
