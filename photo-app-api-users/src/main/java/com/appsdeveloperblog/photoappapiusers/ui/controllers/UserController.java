package com.appsdeveloperblog.photoappapiusers.ui.controllers;

import com.appsdeveloperblog.photoappapiusers.ui.model.UserRequest;
import com.appsdeveloperblog.photoappapiusers.ui.model.UserResponse;
import com.appsdeveloperblog.photoappapiusers.ui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RefreshScope
public class UserController {

    private Environment env;
    private UserService userService;

    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/status/check")
    public String status() {
        System.out.println(env.getProperty("my.configuration"));
        return "USER MS working on port " + env.getProperty("local.server.port") + " with token secret " + env.getProperty("token.secret");
    }

    @PostMapping(consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest newUser) {
        UserResponse userResponse = userService.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
