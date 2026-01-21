package com.tanx.journal.controllers;

import com.tanx.journal.Entity.JournalEntry;
import com.tanx.journal.Entity.UserEntry;
import com.tanx.journal.Services.JournalEntryService;
import com.tanx.journal.Services.UserEntryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal APIs", description = "getAll, create")  // FOR SWAGGER
public class JournalController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userEntryService;


    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getAllJournalsOfUser(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserEntry userEntry = userEntryService.getUserByUserNameService(authentication.getName());
            if(userEntry == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            List<JournalEntry> journals = userEntry.getJournals();
            return new ResponseEntity<>(journals, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<JournalEntry> createJournal(@RequestBody JournalEntry journalEntry){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserEntry userEntry = userEntryService.getUserByUserNameService(authentication.getName());
            JournalEntry newEntry = journalEntryService.saveEntry(journalEntry);
            userEntry.getJournals().add(newEntry);
            userEntryService.createUserService(userEntry);
            return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Same we can use @PutMapping
}
