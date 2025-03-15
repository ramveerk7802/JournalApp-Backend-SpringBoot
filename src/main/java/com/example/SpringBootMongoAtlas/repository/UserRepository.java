package com.example.SpringBootMongoAtlas.repository;

import com.example.SpringBootMongoAtlas.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface UserRepository extends MongoRepository<User,String> {

    public Optional<User> findByUsername(String username);
}
