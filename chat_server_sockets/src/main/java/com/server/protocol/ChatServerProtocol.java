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

//    public String getAction()



}
