package com.server.protocol;

public class ChatServerProtocol {
    private static final int JUST_JOINED = 0;
    private static final int OTHER = 1;

    private int state = JUST_JOINED;

    public String processInput(String input) {
        if (state == JUST_JOINED) {
            state = OTHER;
            return "Welcome to chat-server 1337";
        } else {

            return "0";
        }
    }
}
