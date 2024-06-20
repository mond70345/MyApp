package com.mchen2.myapp.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/kafka-producer/")
@RestController
@RequiredArgsConstructor
public class KafkaProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/send")
    public void sendNormalMessage(@RequestParam("topic") String topic, @RequestParam("key") String key,
                                  @RequestParam("value") String value) {
        kafkaTemplate.send(topic, key, value);
    }

}
