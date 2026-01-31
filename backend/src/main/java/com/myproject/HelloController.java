package com.myproject;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    private final MessageRepository messageRepository;

    public HelloController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/hello")
    public Map<String, String> hello() {
        String text = messageRepository.findById(1L)
                .map(Message::getText)
                .orElse("Text not found");
        return Map.of("message", text);
    }
}
