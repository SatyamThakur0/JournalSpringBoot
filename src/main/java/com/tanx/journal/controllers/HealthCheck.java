package com.tanx.journal.controllers;

//import com.tanx.journal.kafka.JsonKafkaProducer;
import com.tanx.journal.pojo.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
//    private final JsonKafkaProducer jsonKafkaProducer;
//    public HealthCheck(JsonKafkaProducer jsonKafkaProducer){
//        this.jsonKafkaProducer = jsonKafkaProducer;
//    }

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(){
        User user = new User(1, "Satyam", "Thakur");
//        this.jsonKafkaProducer.sendMessage(user);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
