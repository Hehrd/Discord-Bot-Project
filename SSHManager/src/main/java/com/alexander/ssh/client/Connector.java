package com.alexander.ssh.client;

import lombok.Data;

@Data
public class Connector {
    private String host;
    private int port;
    private String username;
    private String password;

    public Connector(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }
    public int getPort() {
        return port;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
