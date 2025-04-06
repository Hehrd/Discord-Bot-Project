package com.alexander.bot.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SSHClient {
    public String executeRemoteCmd(Connector connector, String cmd) throws JSchException, IOException, InterruptedException {
        StringBuilder output = new StringBuilder();
        Session session;
        ChannelExec channel;

        session = createSession(
                connector.getHost(),
                connector.getPort(),
                connector.getUsername(),
                connector.getPassword());

        channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(cmd);
        channel.setInputStream(null);
        channel.setErrStream(System.err);
        channel.connect();
        InputStream input = channel.getInputStream();
        channel.connect();
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
