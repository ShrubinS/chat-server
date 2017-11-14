package com.server.protocol;

public class ChatServerProtocol {
    private static final int NOTJOINED = 0;
    private static final int

    private int state = WAITING;

    public String processInput(String input) {
        if (state == WAITING) {
            return "Welcome to chat-server 1337";

        } else {
            return "0";
        }
    }
}
