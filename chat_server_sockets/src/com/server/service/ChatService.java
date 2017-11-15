package com.server.service;

import com.server.util.ChatRoom;
import com.server.util.Client;

import java.util.*;

public static volatile class ChatService {

    private static ChatService instance = null;

    private static Map<String, ChatRoom> chatRooms;

    private ChatService() {
        chatRooms = new HashMap<>();
    }

    public ChatService getInstance() {
        if (instance == null) {
            return new ChatService();
        }
        return instance;
    }

    public static String joinChatRoom(String chatroomName, Client client) {
        ChatRoom chatRoom = new ChatRoom(chatroomName);
        if (chatRooms.containsKey(chatroomName)) {
            chatRoom = chatRooms.get(chatroomName);
        } else {
            chatRooms.put(chatroomName, chatRoom);
        }

        chatRoom.getConnectedClients().add(client);
        return "client " + client.getName() + " joined chat room " + chatRoom.getRoomName();
    }

    public static String leaveChatRoom(String chatRoomName, Client client) {
        ChatRoom chatRoom = chatRooms.get(chatRoomName);
        chatRoom.getConnectedClients().remove(client);
        return "client " + client.getName() + " left the chat room " + chatRoom.getRoomName();
    }

    public static String sayStuff(String stuff, Client client) {
        return "";
    }
}
