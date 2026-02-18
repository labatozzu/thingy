package com.myproject;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ItemController {

    private final GarmentRepository garmentRepository;
    private final UserRepository userRepository;

    public ItemController(GarmentRepository garmentRepository, UserRepository userRepository) {
        this.garmentRepository = garmentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/items")
    public ResponseEntity<List<Map<String, Object>>> items(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        List<Garment> garments = garmentRepository.findByOwner_IdOrderByIdDesc(user.getId());
        List<Map<String, Object>> items = garments.stream()
                .map(g -> {
                    var map = new java.util.HashMap<String, Object>();
                    map.put("id", g.getId());
                    map.put("title", g.getTitle());
                    map.put("photoUrl", g.getPhotoUrl());
                    map.put("waistMin", g.getWaistMin());
                    map.put("waistMax", g.getWaistMax());
                    map.put("lengthIn", g.getLengthIn());
                    map.put("fitEstimate", g.getFitEstimate().name());
                    map.put("state", g.getState().name());
                    return map;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }
}
