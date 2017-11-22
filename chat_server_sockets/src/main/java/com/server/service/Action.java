package com.server.service;

import com.server.util.Client;

public class Action {
    private static final Action instance = new Action();

    private Action(){
    }

    public static Action getInstance() {
        return instance;
    }

    public String joinChatRoom(String chatroomName, Client client, String iP, String port) {
        ChatService chatService = ChatService.getInstance();
        String output = chatService.joinChatRoom(chatroomName, client);
        return output;
    }

    public String leaveChatRoom(String chatroomName, Client client, String iP, String port) {
        ChatService chatService = ChatService.getInstance();
        String output = chatService.joinChatRoom(chatroomName, client);
        return output;
    }
}
