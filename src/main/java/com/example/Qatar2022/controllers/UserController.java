package com.example.Qatar2022.controllers;

import com.example.Qatar2022.Services.UserService;
import com.example.Qatar2022.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @GetMapping
    public List<UserEntity> getUsers(){
        return userService.getUsers();
    }
    @PostMapping
    public void registerNewStudent(@RequestBody UserEntity user){
        userService.addNewUser(user);
    }

    @PutMapping(path="{userId}")
    public void updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String tel,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob){
        userService.updateUser(userId, firstName,lastName, email,password,country,tel, dob);

    }
}


