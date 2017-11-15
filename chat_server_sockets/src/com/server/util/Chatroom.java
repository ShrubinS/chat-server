package com.server.util;

import java.util.*;

public class Chatroom {
    private String roomName;
    private Set<Client> connectedClients;

    public Chatroom(String roomName) {
        this.connectedClients = Collections.synchronizedSet(new HashSet<>());
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public Set<Client> getConnectedClients() {
        return connectedClients;
    }
}
