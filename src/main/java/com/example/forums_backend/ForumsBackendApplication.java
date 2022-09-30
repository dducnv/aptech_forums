package com.example.forums_backend;

import com.example.forums_backend.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ForumsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForumsBackendApplication.class, args);
    }

}
