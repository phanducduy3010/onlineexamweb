package com.ngovangiang.onlineexam.controller;

import com.ngovangiang.onlineexam.dto.request.LoginInfo;
import com.ngovangiang.onlineexam.entity.User;
import com.ngovangiang.onlineexam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> checkUser(@RequestBody LoginInfo loginInfo) {
        User user = userService.checkUser(loginInfo.getEmail(), loginInfo.getPassword());
        if (user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }
}
