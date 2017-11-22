package com.server.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

import java.util.*;

public class ChatRoom {
    private String roomName;
    private BiMap<Integer, Client> connectedClients;

    public ChatRoom(String roomName) {
//        this.connectedClients = Collections.synchronizedMap(new HashMap<>());
        this.connectedClients = Maps.synchronizedBiMap(HashBiMap.create());
        this.roomName = roomName.toLowerCase();
    }

    public String getRoomName() {
        return roomName;
    }

    public BiMap<Integer, Client> getConnectedClients() {
        return connectedClients;
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
