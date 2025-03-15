package com.example.SpringBootMongoAtlas.repository;

import com.example.SpringBootMongoAtlas.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends MongoRepository<JournalEntry,String> {
}
