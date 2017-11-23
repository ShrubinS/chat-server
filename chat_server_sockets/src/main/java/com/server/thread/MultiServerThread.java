package com.server.thread;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.server.protocol.ChatServerProtocol;
import com.server.service.ChatService;
import com.server.util.ChannelMessage;
import com.server.util.Output;
import com.server.util.ServerInfo;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class MultiServerThread extends Thread {
    private Socket socket = null;
    private ServerInfo serverInfo;
    private BufferedReader in;
    private PrintWriter out;

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
        serverInfo = new ServerInfo(String.valueOf(socket.getInetAddress()).replace("/", ""), String.valueOf(socket.getLocalPort()));
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Subscribe
    public void recieveMessage(ChannelMessage message) {
        // Figure out the intent of message
        if (message.getType().equals("message")) {
            if (out != null && message.getMessage() != null) {
                out.println(message.getMessage());
            }
        }
    }

    @Override
    public void run() {

        try {

            String fromUser, outputLine;
            ChatServerProtocol csp = ChatServerProtocol.getInstance();


            while (true) {
                if (in.ready()) {

                    StringBuilder sb = new StringBuilder();
                    char[] c = new char[] { 1024 };
                    while (in.ready()) {
                        in.read(c);
                        sb.append(c);
                    }

                    fromUser = sb.toString();
                    System.out.println("Client: " + fromUser);


                    Output output = csp.processRequest(this, fromUser, serverInfo);

                    if (fromUser.contains("KILL_SERVICE")) {
                        System.exit(0);
                    }

                    if (output != null) {
                        if (output.getOutputMessage() != null && !output.getOutputMessage().isEmpty()) {
                            out.println(output.getOutputMessage());
                        }

                        if (output.getChannel() != null && output.getChannelMessage() != null) {
                            output.getChannel().post(output.getChannelMessage());
                        }
                    }

                    if (fromUser.contains("DISCONNECT")) {
                        break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
