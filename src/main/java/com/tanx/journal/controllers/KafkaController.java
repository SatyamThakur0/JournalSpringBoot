package com.tanx.journal.controllers;

import com.tanx.journal.kafka.JsonKafkaProducer;
import com.tanx.journal.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("kafka")
public class KafkaController {
    private JsonKafkaProducer jsonKafkaProducer;
    public KafkaController(JsonKafkaProducer jsonKafkaProducer){
        this.jsonKafkaProducer = jsonKafkaProducer;
    }
    @PostMapping("send-message")
    public ResponseEntity<String> sendJsonMessage(@RequestBody User user){
        try{
            jsonKafkaProducer.sendMessage(user);
            return new ResponseEntity<>("JsonMessage "+user.toString()+" published to the kafka server.", HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Error in kafka sendJsonMessage : {}", e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
