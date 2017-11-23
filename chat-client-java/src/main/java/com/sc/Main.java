package com.sc;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket socket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

        ) {
            String fromServer;
            String fromUser;
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));


//            while ((fromServerLength = in.readInt()) != 0) {
//                byte[] message = new byte[fromServerLength];
//                in.readFully(message, 0, message.length); // read the message
//                fromServer = new String(message);
//                System.out.println("Server: " + fromServer);
//
//                fromUser = stdIn.readLine();
//                if (fromUser.equals("exit"))
//                    break;
//                if(fromUser.equals("")) {
//                    fromUser = "JOIN_CHATROOM: first\n" +
//                            "CLIENT_IP: 0\n" +
//                            "PORT: 0\n" +
//                            "CLIENT_NAME: Macbook";
//                }
//
//                if (fromUser != null) {
//                    byte[] fromUserBytes = fromUser.getBytes();
//                    out.writeInt(fromUserBytes.length);
//                    out.write(fromUserBytes);
//                }
//            }

            new Thread(() -> {
                while (true) {
                    try {
                        if (in.ready()) {
                            StringBuilder sb = new StringBuilder();
                            char[] c = new char[] { 1024 };
                            while (in.ready()) {
                                in.read(c);
                                sb.append(c);
                            }
                            System.out.println("Server: " + sb.toString());

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            while (true) {

                fromUser = stdIn.readLine();
                if (fromUser.equals("exit"))
                    break;
                if(fromUser.equals("a")) {
                    fromUser = "JOIN_CHATROOM: first\n" +
                            "CLIENT_IP: 0\n" +
                            "PORT: 0\n" +
                            "CLIENT_NAME: Macbook";
                } else if (fromUser.equals("b")) {
                    fromUser = "LEAVE_CHATROOM: 0\n" +
                            "JOIN_ID: 0\n" +
                            "CLIENT_NAME: client1";
                }

                if (fromUser != null) {
                    out.println(fromUser);
//                    byte[] fromUserBytes = fromUser.getBytes();
//                    out.writeInt(fromUserBytes.length);
//                    out.write(fromUserBytes);
                }
            }


            System.out.println("Exiting...");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }
}
