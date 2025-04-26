package com.alexander.ssh;

import com.alexander.ssh.client.SSHClient;
import com.jcraft.jsch.JSchException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws JSchException, IOException, InterruptedException {
//        SSHClient sshClient = new SSHClient("localhost",
//                                            22,
//                                            "alexander",
//                                                "Tys0n_Boksior1961?");
//        String cmd = "/usr/local/bin/docker exec test_aleks " +
//                        "psql -U postgres -h localhost -p 5432 -d db " +
//                        "-c \"SELECT * FROM students;\"";
//        String s = sshClient.executeRemoteCmd(cmd);
//        System.out.println(s);
        SpringApplication.run(Main.class, args);
    }
}