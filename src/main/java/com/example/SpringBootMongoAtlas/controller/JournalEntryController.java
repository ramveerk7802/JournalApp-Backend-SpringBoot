package com.example.SpringBootMongoAtlas.controller;


import com.example.SpringBootMongoAtlas.entity.JournalEntry;
import com.example.SpringBootMongoAtlas.entity.User;
import com.example.SpringBootMongoAtlas.services.JournalService;
import com.example.SpringBootMongoAtlas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/health-check")
    public String healthCheck(){
        return "Health okk";
    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEntryOfUser(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<User> optionalUser = userService.findByUserName(username);
            if(optionalUser.isEmpty())
                return new ResponseEntity<>("Data not Found",HttpStatus.NOT_FOUND);
            User user  = optionalUser.get();
            return new ResponseEntity<>(user.getJournalEntries(),HttpStatus.OK);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @DeleteMapping("/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable String myId){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            journalService.deleteEntryById(username,myId);

            return new ResponseEntity<>("Delete successfully",HttpStatus.OK);


        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createJournalEntry(@RequestBody JournalEntry myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalService.saveEntry(username,myEntry);
            return ResponseEntity.status(HttpStatus.CREATED).body("Create new Entry successfully");
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

//    @DeleteMapping("/{username}/id/{myId}")
//    public ResponseEntity<?> deleteEntryById(@PathVariable String username,@PathVariable String myId){
//        try {
//            journalService.deleteEntryById(username,myId);
//            return new ResponseEntity<>("Entry is deleted",HttpStatus.NO_CONTENT);
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
}
