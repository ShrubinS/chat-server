package com.server.thread;

import com.server.protocol.ChatServerProtocol;
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
//                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//                DataInputStream in = new DataInputStream(
//                                socket.getInputStream())
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()))
        ) {
            String fromUser, outputLine;
            int inputLength;
            ChatServerProtocol csp = ChatServerProtocol.getInstance();
            outputLine = "Connected";
            out.println(outputLine);
//            out.writeInt(outputLine.length());
//            out.write(outputLine.getBytes());


            while (true) {
                if (in.ready()) {
//                Read into byte array and write to String
//                byte[] message = new byte[inputLength];
//                in.readFully(message, 0, message.length); // read the message
//                fromUser = new String(message);
                    StringBuilder sb = new StringBuilder();
                    char[] c = new char[] { 1024 };
                    while (in.ready()) {
                        in.read(c);
                        sb.append(c);
                    }
                    fromUser = sb.toString();
                    System.out.println("Client: " + fromUser);



                    outputLine = "line \nline 2";

//                Write to bye array with length
//                byte[] outputBytes = outputLine.getBytes();
//                out.writeInt(outputBytes.length);
//                out.write(outputBytes);

                    out.println(outputLine);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
