package com.alexander.ssh.controller;

import com.alexander.ssh.service.SSHService;
import com.jcraft.jsch.JSchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping(value = "/ssh")
public class SSHCommandController {
    @Autowired
    private SSHService sshService;

    @RequestMapping(value = "/execcmd/sql", method = RequestMethod.POST, consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> executeCommand(@RequestBody String command) throws JSchException, IOException, InterruptedException {
        String output = sshService.executeCmd(command);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(output);
    }
}
