package com.example.SpringBootMongoAtlas.services;


import com.example.SpringBootMongoAtlas.entity.User;
import com.example.SpringBootMongoAtlas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }


    public User saveUser(User user){
        if(!user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of("USER"));
        }
        return userRepository.save(user);
    }

    public Optional<User> findByUserName(String username){
         return userRepository.findByUsername(username);
    }
}
