package com.server.util;

import java.util.*;

public class ChatRoom {
    private String roomName;
    private Set<Client> connectedClients;

    public ChatRoom(String roomName) {
        this.connectedClients = Collections.synchronizedSet(new HashSet<>());
        this.roomName = roomName.toLowerCase();
    }

    public String getRoomName() {
        return roomName;
    }

    public Set<Client> getConnectedClients() {
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
