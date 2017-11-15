package com.server.thread;

import com.server.protocol.ChatServerProtocol;
import com.server.protocol.KnockKnockProtocol;
import com.server.service.ChatService;

import java.net.*;
import java.io.*;

public class MultiServerThread extends Thread {
    private Socket socket = null;
    private volatile ChatService chatService;

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
        this.chatService = ChatService.getInstance();
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            ChatServerProtocol csp = ChatServerProtocol.getInstance();

            while ((inputLine = in.readLine()) != null) {
                outputLine = csp.getChatRoomPattern().matcher(inputLine).group(1);
                out.println("Hi " + outputLine);
                if (outputLine.equals("Bye"))
                    break;
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
