package com.example.SpringBootMongoAtlas.controller;

import com.example.SpringBootMongoAtlas.entity.User;
import com.example.SpringBootMongoAtlas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){
        try {
            Optional<User> optionalUser = userService.findByUserName(user.getUsername());
            if(optionalUser.isEmpty())
                return new ResponseEntity<>("User Not found", HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>("Log in successfully",HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try {
            User saved = userService.saveUser(user);
            return new ResponseEntity<>(saved,HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
