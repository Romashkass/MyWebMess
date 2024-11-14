package com.example.MyWebMess.controller;

import com.example.MyWebMess.model.Users;
import com.example.MyWebMess.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return usersService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return usersService.verify(user);
    }

}
