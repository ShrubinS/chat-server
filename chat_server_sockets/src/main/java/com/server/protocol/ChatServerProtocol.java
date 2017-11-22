package com.server.protocol;

import com.server.service.Action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatServerProtocol {
    private static final ChatServerProtocol instance = new ChatServerProtocol();
    private Action action;

    private ChatServerProtocol() {
        action = Action.getInstance();
    }

    public static ChatServerProtocol getInstance() {
        return instance;
    }

    public String processRequest(String request, String port, String iP) {
        String output = "nothing happened";
        if(request.contains("JOIN_CHATROOM")) {
            String[] joinRequest = request.split("\n");
            String chatroomName = joinRequest[0].split(":")[1].trim();
            String clientIP = joinRequest[1].split(":")[1].trim();
            String clientPort = joinRequest[2].split(":")[1].trim();
            String clientName = joinRequest[3].split(":")[1].trim();

            output = action.joinChatRoom(chatroomName, clientName, iP, port);
        }

        return output;
    }



}
