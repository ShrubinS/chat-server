package com.server.thread;

import com.server.protocol.ChatServerProtocol;
import com.server.protocol.KnockKnockProtocol;
import com.server.service.ChatService;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

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
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(
                                socket.getInputStream())
        ) {
            String fromUser, outputLine;
            int inputLength;
            ChatServerProtocol csp = ChatServerProtocol.getInstance();
            outputLine = "Connected";
            out.writeInt(outputLine.length());
            out.write(outputLine.getBytes());

//            in.lines().forEach(System.out::println);

//            List<String> inp = in.lines().collect(Collectors.toList());

            while ((inputLength = in.readInt()) != 0) {
                byte[] message = new byte[inputLength];
                in.readFully(message, 0, message.length); // read the message
                fromUser = new String(message);
                System.out.println(fromUser);

                outputLine = "line \nline 2";
                byte[] outputBytes = outputLine.getBytes();
                out.writeInt(outputBytes.length);
                out.write(outputBytes);
            }
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
