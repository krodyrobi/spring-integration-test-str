package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class Config {
    private final String url = "https://httpstat.us/";

    public String getUrl() {
        return url;
    }
}
