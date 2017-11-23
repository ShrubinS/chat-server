package com.server.service;

import com.google.common.collect.BiMap;
import com.google.common.eventbus.EventBus;
import com.server.thread.MultiServerThread;
import com.server.util.ChatRoom;
import com.server.util.Client;
import com.server.util.Sequence;

import java.util.*;

// Using a singleton instead of a static class
public class ChatService {

    private ChatService instance = null;
    private Map<String, ChatRoom> chatRooms;

    private ChatService() {
        chatRooms = Collections.synchronizedMap(new HashMap<>());
    }

//    Initialization-on-demand holder idiom
//    Lazily initialized
    private static class LazyHolder {
        static final ChatService INSTANCE = new ChatService();
    }

    public static ChatService getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String joinChatRoom(MultiServerThread thread, String chatroomName, Client client) {
        ChatRoom chatRoom = new ChatRoom(chatroomName);
        if (chatRooms.containsKey(chatroomName)) {
            chatRoom = chatRooms.get(chatroomName);
        } else {
            chatRooms.put(chatroomName, chatRoom);
        }
        EventBus chatRoomChannel = chatRoom.getChannel();
        chatRoomChannel.register(thread);

        Integer id;
        BiMap<Integer, Client> connectedClients = chatRoom.getConnectedClients();
        if (connectedClients.containsValue(client)) {
            id = connectedClients.inverse().get(client);
        } else {
            id = Sequence.nextValue();
            connectedClients.put(id, client);
        }
//                "JOINED_CHATROOM: [chatroom name]\n" +
//                "SERVER_IP: [IP address of chat room]\n" +
//                "PORT: [port number of chat room]\n" +
//                "ROOM_REF: [integer that uniquely identifies chat room on server]\n" +
//                "JOIN_ID: [integer that uniquely identifies client joining]"
        chatRoomChannel.post(client.getName() + " joined!!");
        return "client " + client.getName() + " joined chat room " + chatRoom.getRoomName() + " with id " + id;
    }

    public String leaveChatRoom(String chatRoomName, Client client) {
        ChatRoom chatRoom = chatRooms.get(chatRoomName);
        chatRoom.getConnectedClients().remove(client);
        return "client " + client.getName() + " left the chat room " + chatRoom.getRoomName();
    }

    public String sayStuff(String stuff, Client client) {
        return "";
    }
}
