package com.server.util;

import com.google.common.eventbus.EventBus;

public class Output {
    private String outputMessage;
    private EventBus channel;
    private ChannelMessage channelMessage;

    public Output(String outputMessage, EventBus channel, ChannelMessage message) {
        this.outputMessage = outputMessage;
        this.channel = channel;
        this.channelMessage = message;
    }

    public String getOutputMessage() {
        return outputMessage;
    }

    public EventBus getChannel() {
        return channel;
    }

    public ChannelMessage getChannelMessage() {
        return channelMessage;
    }
}
