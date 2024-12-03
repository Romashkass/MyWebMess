package com.example.MyWebMess.controller;

import com.example.MyWebMess.dto.UsersDto;
import com.example.MyWebMess.model.Users;
import com.example.MyWebMess.service.UsersService;
import com.example.MyWebMess.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UsersController {

    private final UsersService usersService;
    private final MappingUtils mappingUtils;

    @PostMapping("/register")
    public UsersDto register(@RequestBody UsersDto user) {
        return mappingUtils.mapToUsersDto(usersService.register(mappingUtils.mapToUsers(user)));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsersDto user) {
        return usersService.verify(mappingUtils.mapToUsers(user));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UsersDto>> getUsersList() {
        List<Users> users = usersService.getUsersList();
        return new ResponseEntity<>(mappingUtils.mapToUsersDto(users), HttpStatus.OK);
    }

}
