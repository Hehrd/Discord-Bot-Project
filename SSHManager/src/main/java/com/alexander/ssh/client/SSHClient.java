package com.alexander.ssh.client;

import com.jcraft.jsch.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class SSHClient {
    @Value("${ssh.client.host}")
    private String host;

    @Value("${ssh.client.port}")
    private int port;

    @Value("${ssh.client.username}")
    private String username;

    @Value("${ssh.client.password}")
    private String password;

    public String executeRemoteCmd(String cmd) throws JSchException, IOException, InterruptedException {
        StringBuilder output = new StringBuilder();
        Session session;
        ChannelExec channel;
//        Connector connector;
        session = createSession(
                host,
                port,
                username,
                password);

        channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(cmd);
        channel.setInputStream(null);
        channel.setErrStream(System.err);
        channel.connect();
        InputStream input = channel.getInputStream();
        byte[] tmp = new byte[1024];
        while (true) {
            while (input.available() > 0) {
                int i = input.read(tmp, 0, 1024);
                if (i < 0) break;
                output.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                break;
            }
            Thread.sleep(100);
        }

        channel.disconnect();
        session.disconnect();
        return output.toString();
    }

    public String executeRemoteShellCommands(List<String> commands)
            throws JSchException, IOException, InterruptedException {

        StringBuilder output = new StringBuilder();
        Session session = createSession(host, port, username, password);
        ChannelShell channel = (ChannelShell) session.openChannel("shell");

        PipedInputStream pipedIn = new PipedInputStream();
        PipedOutputStream pipedOut = new PipedOutputStream(pipedIn);
        OutputStream out = channel.getOutputStream();
        InputStream in = channel.getInputStream();

        channel.setPty(true);
        channel.setInputStream(pipedIn);
        // Important for interactive shells
        channel.connect();

        // Send commands to the shell
        for (String command : commands) {
            pipedOut.write((command + "\n").getBytes());
            pipedOut.flush();
        }

        // Don't close pipedOut until we are done sending all commands
        pipedOut.flush(); // Ensure all data is flushed before moving on

        // Read the response
        byte[] tmp = new byte[1024];
        long startTime = System.currentTimeMillis();
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0) break;
                output.append(new String(tmp, 0, i));
            }

            if (channel.isClosed()) {
                break;
            }

            if (System.currentTimeMillis() - startTime > 10000) { // 10 sec timeout
                output.append("\n[Timeout waiting for command execution]\n");
                break;
            }

            Thread.sleep(100);
        }

        // Close streams and disconnect
        pipedOut.close(); // Close after reading is finished
        channel.disconnect();
        session.disconnect();

        return output.toString();
    }



    private Session createSession(String host, int port, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        Session session;
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig(config);
        session.connect();
        return session;
    }
}
