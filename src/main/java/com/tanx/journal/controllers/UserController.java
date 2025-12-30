package com.tanx.journal.controllers;

import com.tanx.journal.Entity.UserEntry;
import com.tanx.journal.Services.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserEntryService userEntryService;
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserByIdController(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserEntry userEntry = userEntryService.getUserByUserNameService(authentication.getName());
            userEntryService.deleteUserByIdService(userEntry.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<?> updateUserByUserNameController(@RequestBody UserEntry user){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserEntry userEntry = userEntryService.getUserByUserNameService(authentication.getName());
            if(userEntry != null){
                userEntry.setUsername(user.getUsername());
                userEntry.setPassword(user.getPassword());
                userEntryService.createUserService(userEntry);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
