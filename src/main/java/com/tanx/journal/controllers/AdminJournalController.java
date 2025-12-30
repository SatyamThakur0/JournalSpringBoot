package com.tanx.journal.controllers;

import com.tanx.journal.Entity.JournalEntry;
import com.tanx.journal.Services.JournalEntryService;
import com.tanx.journal.Services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminJournalController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private RedisService redisService;

    @GetMapping("all-journal")
    public ResponseEntity<List<JournalEntry>> getAllJournals(){
        List<JournalEntry> entries = journalEntryService.getAllEntries();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @GetMapping("journal/id/{id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable String id){
        try {
            JournalEntry je = redisService.get(id, JournalEntry.class);
            if(je != null){
                return new ResponseEntity<>(je, HttpStatus.OK);
            }
            Optional<JournalEntry> entry = journalEntryService.getEntryById(id);
            if(entry.isPresent()){
                redisService.set(id, entry.get(), (long)20);
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            }
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @DeleteMapping("journal/id/{id}")
//    public ResponseEntity<JournalEntry> deleteJournalById(@PathVariable String id){
//        try{
//            journalEntryService.deleteEntryById(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
