package com.server.service;

import com.server.util.ChatRoom;

import java.util.*;

public class ChatService {

    private static ChatService instance = null;

    private static Set<ChatRoom> chatRooms;

    private ChatService() {
        chatRooms = new HashSet<>();
    }

    public ChatService getInstance() {
        if (instance == null) {
            return new ChatService();
        }
        return instance;
    }

    public static String joinChatroom(String chatroomName) {
        ChatRoom newChatRoom = new ChatRoom(chatroomName);
        if (!chatRooms.contains(newChatRoom)) {
            chatRooms.add(newChatRoom);
        }
        return "chat room " + newChatRoom.getRoomName() + " joined";
    }
}
