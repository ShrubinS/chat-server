package com.server.protocol;

import com.server.service.Action;
import com.server.util.Client;

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
        String[] joinRequest = request.split("\n");
        if (request.contains("JOIN_CHATROOM")) {
            String chatRoomName = ChatServerProtocol.getValue(joinRequest, 0);
            String clientIP = ChatServerProtocol.getValue(joinRequest, 1);
            String clientPort = ChatServerProtocol.getValue(joinRequest, 2);
            String clientName = ChatServerProtocol.getValue(joinRequest, 3);

            Client client = new Client(clientIP, clientPort, clientName);

            output = action.joinChatRoom(chatRoomName, client, iP, port);
        } else if (request.contains("LEAVE_CHATROOM")) {
            String chatRoomName = ChatServerProtocol.getValue(joinRequest, 0);
            String joinId = ChatServerProtocol.getValue(joinRequest, 1);
            String clientName = ChatServerProtocol.getValue(joinRequest, 2);


        }

        return output;
    }

    private static String getValue(String[] joinRequest, int index) throws IndexOutOfBoundsException {
        return joinRequest[index].split(":")[1].trim();
    }



}
