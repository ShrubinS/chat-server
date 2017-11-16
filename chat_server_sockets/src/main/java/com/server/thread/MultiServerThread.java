package com.server.thread;

import com.server.protocol.ChatServerProtocol;
import com.server.protocol.KnockKnockProtocol;
import com.server.service.ChatService;
import com.sun.deploy.util.StringUtils;

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
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String input, outputLine;
            ChatServerProtocol csp = ChatServerProtocol.getInstance();
            outputLine = "Connected";
            out.println(outputLine);

//            in.lines().forEach(System.out::println);

//            List<String> inp = in.lines().collect(Collectors.toList());

            while ((input = in.readLine()) != null) {
                System.out.println(input);
                outputLine =
                out.println("Hi " + outputLine);
                if (outputLine.equals("Bye"))
                    break;
            }
//            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
