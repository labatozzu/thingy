package com.myproject;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
                    map.put("waist", g.getWaist());
                    map.put("lengthIn", g.getLengthIn());
                    map.put("fitEstimate", g.getFitEstimate().name());
                    map.put("visibility", g.getVisibility().name());
                    map.put("status", g.getStatus().name());
                    return map;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<Map<String, Object>> item(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        var opt = garmentRepository.findByIdAndOwner_Id(id, user.getId());
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Garment g = opt.get();
        var map = new HashMap<String, Object>();
        map.put("id", g.getId());
        map.put("title", g.getTitle());
        map.put("photoUrl", g.getPhotoUrl());
        map.put("waist", g.getWaist());
        map.put("lengthIn", g.getLengthIn());
        map.put("fitEstimate", g.getFitEstimate().name());
        map.put("visibility", g.getVisibility().name());
        map.put("status", g.getStatus().name());
        return ResponseEntity.ok(map);
    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<Map<String, Object>> updateItem(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        var opt = garmentRepository.findByIdAndOwner_Id(id, user.getId());
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Garment g = opt.get();
        if (body.containsKey("waist")) {
            Object w = body.get("waist");
            if (w instanceof Number) {
                g.setWaist(((Number) w).intValue());
            }
        }
        if (body.containsKey("lengthIn")) {
            Object l = body.get("lengthIn");
            if (l instanceof Number) {
                g.setLengthIn(((Number) l).intValue());
            }
        }
        if (body.containsKey("fitEstimate")) {
            String f = body.get("fitEstimate") != null ? body.get("fitEstimate").toString() : null;
            if (f != null) {
                try {
                    g.setFitEstimate(FitEstimate.valueOf(f));
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
        if (body.containsKey("visibility")) {
            String v = body.get("visibility") != null ? body.get("visibility").toString() : null;
            if (v != null) {
                try {
                    g.setVisibility(ItemVisibility.valueOf(v));
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
        if (body.containsKey("status")) {
            String s = body.get("status") != null ? body.get("status").toString() : null;
            if (s != null) {
                try {
                    g.setStatus(ItemStatus.valueOf(s));
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
        garmentRepository.save(g);
        var map = new HashMap<String, Object>();
        map.put("id", g.getId());
        map.put("title", g.getTitle());
        map.put("photoUrl", g.getPhotoUrl());
        map.put("waist", g.getWaist());
        map.put("lengthIn", g.getLengthIn());
        map.put("fitEstimate", g.getFitEstimate().name());
        map.put("visibility", g.getVisibility().name());
        map.put("status", g.getStatus().name());
        return ResponseEntity.ok(map);
    }
}
