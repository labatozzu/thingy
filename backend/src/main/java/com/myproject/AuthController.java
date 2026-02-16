package com.myproject;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DeferredCsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/csrf")
    public ResponseEntity<Map<String, String>> csrf(HttpServletRequest request) {
        String tokenValue = null;
        Object attr = request.getAttribute(CsrfToken.class.getName());
        if (attr instanceof DeferredCsrfToken) {
            tokenValue = ((DeferredCsrfToken) attr).get().getToken();
        } else if (attr instanceof CsrfToken) {
            tokenValue = ((CsrfToken) attr).getToken();
        }
        if (tokenValue == null && request.getCookies() != null) {
            tokenValue = Arrays.stream(request.getCookies())
                    .filter(c -> "XSRF-TOKEN".equals(c.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        if (tokenValue == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(Map.of("token", tokenValue));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> me(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }
        User user = userRepository.findByEmail(userDetails.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        var map = new java.util.HashMap<String, Object>();
        map.put("id", user.getId());
        map.put("email", user.getEmail());
        if (user.getJeansWaistMin() != null) map.put("jeansWaistMin", user.getJeansWaistMin());
        if (user.getJeansWaistMax() != null) map.put("jeansWaistMax", user.getJeansWaistMax());
        if (user.getJeansLengthIn() != null) map.put("jeansLengthIn", user.getJeansLengthIn());
        if (user.getHeightCm() != null) map.put("heightCm", user.getHeightCm());
        return ResponseEntity.ok(map);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody Map<String, Object> body) {
        if (body == null) {
            return ResponseEntity.badRequest().build();
        }
        String email = body.get("email") != null ? body.get("email").toString().trim() : null;
        String password = body.get("password") != null ? body.get("password").toString() : null;
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Integer waistMin = parseInteger(body.get("jeansWaistMin"));
        Integer waistMax = parseInteger(body.get("jeansWaistMax"));
        Integer lengthIn = parseInteger(body.get("jeansLengthIn"));
        Integer heightCm = parseInteger(body.get("heightCm"));
        if (waistMin == null || waistMax == null || lengthIn == null || heightCm == null
                || waistMax != waistMin + 1 || heightCm < 140 || heightCm > 220) {
            return ResponseEntity.badRequest().build();
        }
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(409).build();
        }
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setJeansWaistMin(waistMin);
        user.setJeansWaistMax(waistMax);
        user.setJeansLengthIn(lengthIn);
        user.setHeightCm(heightCm);
        userRepository.save(user);
        return ResponseEntity.status(201).build();
    }

    private static Integer parseInteger(Object o) {
        if (o == null) return null;
        if (o instanceof Number) return ((Number) o).intValue();
        try {
            return Integer.parseInt(o.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
