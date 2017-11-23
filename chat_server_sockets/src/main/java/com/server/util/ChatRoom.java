package com.server.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.eventbus.EventBus;

import java.util.*;

public class ChatRoom {
    private String roomName;
    private BiMap<Integer, Client> connectedClients;
    private EventBus channel;

    public ChatRoom(String roomName) {
//        this.connectedClients = Collections.synchronizedMap(new HashMap<>());
        this.connectedClients = Maps.synchronizedBiMap(HashBiMap.create());
        this.roomName = roomName.toLowerCase();
        this.channel = new EventBus();
    }

    public String getRoomName() {
        return roomName;
    }

    public BiMap<Integer, Client> getConnectedClients() {
        return connectedClients;
    }

    public EventBus getChannel() {
        return channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatRoom chatRoom = (ChatRoom) o;

        return roomName.equals(chatRoom.roomName);
    }

    @Override
    public int hashCode() {
        return roomName.hashCode();
    }
}
