package com.myproject;

import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageSource messageSource;

    public MessageController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/message")
    public Map<String, String> message(
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage) {
        Locale locale = acceptLanguage != null && !acceptLanguage.isBlank()
                ? Locale.forLanguageTag(acceptLanguage.split(",")[0].trim().split("-")[0])
                : Locale.getDefault();
        String text = messageSource.getMessage("welcome.message", null, "Welcome", locale);
        return Map.of("message", text);
    }
}
