package com.tanx.journal.kafka;

import com.tanx.journal.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaJsonConsumer {
    @KafkaListener(topics = "java_guides", groupId = "myGroup")
    public void kafkaJsonConsumer(User user){
        log.info("JsonMessage received : {}", user.toString());
    }
}
