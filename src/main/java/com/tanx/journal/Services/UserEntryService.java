package com.tanx.journal.Services;

import com.tanx.journal.Entity.CustomUserDetails;
import com.tanx.journal.Entity.UserEntry;
import com.tanx.journal.Repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserEntryService implements UserDetailsService {
    @Autowired
    private UserEntryRepository userEntryRepository;

    public CustomUserDetails loadUserByUsername(String username){
        UserEntry user = userEntryRepository.findUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found : " + username);
        }
        return new CustomUserDetails(user);
    }

    public UserEntry createUserService(UserEntry userEntry){
        return userEntryRepository.save(userEntry);
    }

    public List<UserEntry> getAllUsersService(){
        return userEntryRepository.findAll();
    }

    public Optional<UserEntry> getUserByIdService(String id){
        return userEntryRepository.findById(id);
    }

    public void deleteUserByIdService(String id){
        userEntryRepository.deleteById(id);
    }

    public UserEntry getUserByUserNameService(String username){
        return userEntryRepository.findUserByUsername(username);
    }




}
