package com.server.util;

public class Client {

    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return address.equals(client.address);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }
}
