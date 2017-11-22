package com.server.service;

import com.server.util.Client;

public class Action {
    private static final Action instance = new Action();

    private Action(){
    }

    public static Action getInstance() {
        return instance;
    }

    public String joinChatRoom(String chatroomName, String clientName, String iP, String port) {
        ChatService chatService = ChatService.getInstance();
        Client client = new Client(iP, clientName);
        String output = chatService.joinChatRoom(chatroomName, client);
        return output;
    }
}
