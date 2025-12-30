package com.tanx.journal.Repository;

import com.tanx.journal.Entity.UserEntry;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserEntryRepository extends MongoRepository<UserEntry, String> {
    public UserEntry findUserByUsername(String username);
}
