package com.example.SpringBootMongoAtlas.services;


import com.example.SpringBootMongoAtlas.entity.JournalEntry;
import com.example.SpringBootMongoAtlas.entity.User;
import com.example.SpringBootMongoAtlas.repository.JournalRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userService;


    public List<JournalEntry> getAllJournalEntry(){
        return journalRepository.findAll();
    }

    public Optional<JournalEntry> getById(String id){
        return journalRepository.findById(id);
    }


    @Transactional
    public void saveEntry(String username,JournalEntry journalEntry){
        try {
            Optional<User> optionalUser = userService.findByUserName(username);

            if(optionalUser.isEmpty())
                throw new RuntimeException("Internal Server Error");

            User user = optionalUser.get();

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry newEntry = journalRepository.save(journalEntry);

            user.getJournalEntries().add(newEntry);

            userService.saveUser(user);

        }catch (Exception e){
            throw new RuntimeException("Error Saving journal entry", e);
        }
    }

    @Transactional
    public void deleteEntryById(String username,String myId){
        try {

            Optional<User> optionalUser = userService.findByUserName(username);
            if(optionalUser.isEmpty())
                throw new RuntimeException("user Not Found");
            User user = optionalUser.get();
            boolean removed = user.getJournalEntries().removeIf(journalEntry-> journalEntry.getId().equals(myId) );
            if (removed){
                journalRepository.deleteById(myId);
                user.setPassword(null);
                userService.saveUser(user);
            }
            else
                throw new RuntimeException("Internal server error");

        }catch (Exception e){
            throw new RuntimeException("Error deleting journal entry", e);
        }
    }


}
