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
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                DataInputStream in = new DataInputStream(socket.getInputStream())

        ) {
            String fromServer;
            int fromServerLength = 0;
            String fromUser;
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));


            while ((fromServerLength = in.readInt()) != 0) {
                byte[] message = new byte[fromServerLength];
                in.readFully(message, 0, message.length); // read the message
                fromServer = new String(message);
                System.out.println("Server: " + fromServer);

                fromUser = stdIn.readLine();
                if (fromUser.equals("exit"))
                    break;
                if(fromUser.equals("")) {
                    fromUser = "JOIN_CHATROOM: first\n" +
                            "CLIENT_IP: 0\n" +
                            "PORT: 0\n" +
                            "CLIENT_NAME: Macbook";
                }

                if (fromUser != null) {
                    byte[] fromUserBytes = fromUser.getBytes();
                    out.writeInt(fromUserBytes.length);
                    out.write(fromUserBytes);
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
