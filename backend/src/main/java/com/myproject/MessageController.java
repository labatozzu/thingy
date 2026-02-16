package com.myproject;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/message")
    public Map<String, String> message() {
        String text = messageRepository.findById(1L)
                .map(Message::getText)
                .orElse("Text not found");
        return Map.of("message", text);
    }
}
