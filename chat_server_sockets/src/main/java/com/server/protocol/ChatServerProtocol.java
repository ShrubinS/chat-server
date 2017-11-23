package com.server.protocol;

import com.server.service.ChatService;
import com.server.thread.MultiServerThread;
import com.server.util.Client;
import com.server.util.ServerInfo;

public class ChatServerProtocol {
    private static final ChatServerProtocol instance = new ChatServerProtocol();
    private ChatService chatService;

    private ChatServerProtocol() {
        chatService = ChatService.getInstance();
    }

    public static ChatServerProtocol getInstance() {
        return instance;
    }

    public String processRequest(MultiServerThread thread, String request, ServerInfo serverInfo) {
        String output = "";
        String[] joinRequest = request.split("\n");
        if (request.contains("JOIN_CHATROOM")) {
            String chatRoomName = ChatServerProtocol.getValue(joinRequest, 0);
            String clientIP = ChatServerProtocol.getValue(joinRequest, 1);
            String clientPort = ChatServerProtocol.getValue(joinRequest, 2);
            String clientName = ChatServerProtocol.getValue(joinRequest, 3);

            Client client = new Client(clientIP, clientPort, clientName);

            output = chatService.joinChatRoom(thread, chatRoomName, client, serverInfo);
        } else if (request.contains("LEAVE_CHATROOM")) {
            String chatRoomRef = ChatServerProtocol.getValue(joinRequest, 0);
            String joinId = ChatServerProtocol.getValue(joinRequest, 1);
            String clientName = ChatServerProtocol.getValue(joinRequest, 2);

            output = chatService.leaveChatRoom(thread, Integer.parseInt(chatRoomRef), Integer.parseInt(joinId), clientName);
        } else if (request.contains("DISCONNECT")) {
            String clientIP = ChatServerProtocol.getValue(joinRequest, 0);
            String clientPort = ChatServerProtocol.getValue(joinRequest, 1);
            String clientName = ChatServerProtocol.getValue(joinRequest, 2);

            Client client = new Client(clientIP, clientPort, clientName);

            output = chatService.leaveAllChatRooms(thread, client);
        } else if (request.contains("CHAT")) {
            String chatRoomRef = ChatServerProtocol.getValue(joinRequest, 0);
            String joinId = ChatServerProtocol.getValue(joinRequest, 1);
            String clientName = ChatServerProtocol.getValue(joinRequest, 2);
            String clientMessage = request.split("MESSAGE:")[1].trim();

            chatService.chat(Integer.parseInt(chatRoomRef), Integer.parseInt(joinId), clientName, clientMessage);
        } else if (request.contains("HELO")) {
            output = "HELO BASE_TEST";
        }

        return output;
    }


    private static String getValue(String[] joinRequest, int index) throws IndexOutOfBoundsException {
        return joinRequest[index].split(":")[1].trim();
    }



}
