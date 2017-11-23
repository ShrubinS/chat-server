package com.server.util;

public class ServerInfo {
    private String serverIp;
    private String serverPort;

    public ServerInfo(String serverIp, String serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public String getServerIp() {
        return serverIp;
    }

    public String getServerPort() {
        return serverPort;
    }

}
