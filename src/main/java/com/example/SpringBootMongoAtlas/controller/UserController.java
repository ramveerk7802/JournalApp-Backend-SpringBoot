package com.example.SpringBootMongoAtlas.controller;


import com.example.SpringBootMongoAtlas.entity.JournalEntry;
import com.example.SpringBootMongoAtlas.entity.User;
import com.example.SpringBootMongoAtlas.services.JournalService;
import com.example.SpringBootMongoAtlas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{username}")
    public ResponseEntity<?> findUserByUsername(@PathVariable String username){
        try {
            Optional<User> optionalUser = userService.findByUserName(username);
            if(optionalUser.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
            User user = optionalUser.get();
            return new ResponseEntity<>(user, HttpStatus.OK);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<User> optionalUser = userService.findByUserName(username);
            if(optionalUser.isEmpty()){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            User userDb = optionalUser.get();
            userDb.setPassword(user.getPassword());
            userDb.setUsername(user.getUsername());
            User saved = userService.saveUser(userDb);
            return new ResponseEntity<>(saved,HttpStatus.CREATED);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
