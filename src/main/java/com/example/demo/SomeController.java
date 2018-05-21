package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RestController
public class SomeController {
    private final Config config;

    @Autowired
    public SomeController(Config config) {
        this.config = config;
    }

    @GetMapping("/test")
    public Mono<String> test() {
        return WebClient.create(config.getUrl()).get().uri("/200").retrieve().bodyToMono(String.class);
    }
}
