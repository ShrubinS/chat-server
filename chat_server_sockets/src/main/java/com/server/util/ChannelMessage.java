package com.server.util;


public class ChannelMessage {
    private String message;
    private String type;

    public ChannelMessage(String type, String message) {
        this.type = type;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
