package com.tanx.journal.controllers;

import com.tanx.journal.Entity.UserEntry;
import com.tanx.journal.Services.UserEntryService;
import com.tanx.journal.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {
    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private AppCache appCache;

    @PostMapping("create-user")
    public ResponseEntity<UserEntry> createUserController(@RequestBody UserEntry user){
        try {
            UserEntry userEntry = userEntryService.createUserService(user);
            return new ResponseEntity<>(userEntry, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("all-users")
    public ResponseEntity<List<UserEntry>> getAllUsersController(){
        List<UserEntry> users = userEntryService.getAllUsersService();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserEntry> getUserByIdController(@PathVariable String id){
        try{
            Optional<UserEntry> user = userEntryService.getUserByIdService(id);
            if(user.isPresent()){
                return new ResponseEntity<>(user.get(), HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("clear-cache")
    public ResponseEntity<HttpStatus> clearCache(){
        appCache.init();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
