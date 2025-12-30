package com.tanx.journal.Repository;

import com.tanx.journal.Entity.ConfigPropsEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigPropsRepository extends MongoRepository <ConfigPropsEntity,String>{
}
