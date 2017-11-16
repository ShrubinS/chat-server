package com.server.service;

import com.server.util.ChatRoom;
import com.server.util.Client;

import java.util.*;

// Using a singleton instead of a static class
public class ChatService {

    private ChatService instance = null;
    private Map<String, ChatRoom> chatRooms;

    private ChatService() {
        chatRooms = new HashMap<>();
    }

//    Initialization-on-demand holder idiom
//    Lazily initialized
    private static class LazyHolder {
        static final ChatService INSTANCE = new ChatService();
    }

    public static ChatService getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String joinChatRoom(String chatroomName, Client client) {
        ChatRoom chatRoom = new ChatRoom(chatroomName);
        if (chatRooms.containsKey(chatroomName)) {
            chatRoom = chatRooms.get(chatroomName);
        } else {
            chatRooms.put(chatroomName, chatRoom);
        }

        chatRoom.getConnectedClients().add(client);
        return "client " + client.getName() + " joined chat room " + chatRoom.getRoomName();
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
