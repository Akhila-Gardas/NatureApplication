package com.nature.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NatureController {

    @GetMapping("/")
    public String home() {
        return "🌿 Welcome to Nature App! 🌳 Protect Nature, Protect Life! 🌍";
    }
}
