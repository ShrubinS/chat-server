package com.server.service;

public class Action {
    private static final Action instance = new Action();

    private Action(){
    }

    public static Action getInstance() {
        return instance;
    }

    public String joinChatRoom(String chatroomName, String clientName, String iP, String port) {
        
    }
}
