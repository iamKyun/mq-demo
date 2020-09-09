package com.example.demo;

import com.example.demo.video.VideoProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
public class DemoController {
    private final VideoProducer producer;

    public DemoController(VideoProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/")
    public String test() {
        for (int i = 0; i < 1000; i++) {
            producer.send();
        }
        return "send" + LocalTime.now();
    }
}
