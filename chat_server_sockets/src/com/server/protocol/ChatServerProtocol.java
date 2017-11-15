package com.server.protocol;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatServerProtocol {
    private static final ChatServerProtocol instance = new ChatServerProtocol();

    private ChatServerProtocol() {
    }

    public static ChatServerProtocol getInstance() {
        return instance;
    }

    private static final int JUST_JOINED = 0;
    private static final int OTHER = 1;
    private static final String CHATROOM_NAME = "JOIN_CHATROOM(.*)\\n";
    private static final String CLIENT_IP = "";
    private static final String PORT = "";
    private static final String CLIENT_NAME = "";

    private static final Pattern CHATROOM_MATCH = Pattern.compile(CHATROOM_NAME);

    public Pattern getChatRoomPattern() {
        return CHATROOM_MATCH;
    }

    private int state = JUST_JOINED;


}
