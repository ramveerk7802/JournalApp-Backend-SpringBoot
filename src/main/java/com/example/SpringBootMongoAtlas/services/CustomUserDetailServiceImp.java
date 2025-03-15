package com.example.SpringBootMongoAtlas.services;

import com.example.SpringBootMongoAtlas.CustomUserDetail;
import com.example.SpringBootMongoAtlas.entity.User;
import com.example.SpringBootMongoAtlas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CustomUserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Optional<User> optionalUser =repository.findByUsername(username);
         if(optionalUser.isEmpty()){
             throw new UsernameNotFoundException("user not found exception");
         }
         User user = optionalUser.get();
        return new CustomUserDetail(user);
    }
}
