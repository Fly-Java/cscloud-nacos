package com.zzp.first.controller;

import com.zzp.common.entity.User;
import com.zzp.first.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/getUserList")
    public List<User> getUserList(){
        return userService.getUser();
    }

}
