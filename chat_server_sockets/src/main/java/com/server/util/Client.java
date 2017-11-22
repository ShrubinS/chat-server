package com.server.util;

public class Client {

    private String address;
    private String port;
    private String name;

    public Client(){

    }

    public Client(String address, String port, String name) {
        this.address = address;
        this.port = port;
        this.name = name;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!address.equals(client.address)) return false;
        if (!port.equals(client.port)) return false;
        return name.equals(client.name);
    }

    @Override
    public int hashCode() {
        int result = address.hashCode();
        result = 31 * result + port.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
