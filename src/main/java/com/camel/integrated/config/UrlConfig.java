package com.camel.integrated.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("url")
public class UrlConfig {

    private String baseEndpoint;
    private String technology;
    private String messages;
    private String message;

}
