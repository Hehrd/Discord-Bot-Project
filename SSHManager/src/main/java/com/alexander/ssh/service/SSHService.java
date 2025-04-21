package com.alexander.ssh.service;

import com.alexander.ssh.client.SSHClient;
import com.jcraft.jsch.JSchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Service
public class SSHService {
    @Autowired
    private SSHClient sshClient;

    public String executeCmd(String cmd) throws JSchException, IOException, InterruptedException {
        return sshClient.executeRemoteCmd(cmd);
    }

    public String executeCmds(List<String> cmds) throws JSchException, IOException, InterruptedException {
        return sshClient.executeRemoteShellCommands(cmds);
    }
}
