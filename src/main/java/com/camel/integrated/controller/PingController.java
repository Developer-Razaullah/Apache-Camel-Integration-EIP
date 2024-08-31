package com.camel.integrated.controller;

import com.camel.integrated.config.UrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("")
public class PingController {

    @Autowired
    private UrlConfig urlConfig;

    @GetMapping("/status")
    public String getMessage() {
        return "Up";
    }

    @GetMapping("/technologies")
    public ResponseEntity<String> getTechnology() {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = urlConfig.getTechnologyEndpoint();
        return restTemplate.getForEntity(fooResourceUrl, String.class);
    }
}
