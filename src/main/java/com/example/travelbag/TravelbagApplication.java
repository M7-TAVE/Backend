package com.example.travelbag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TravelbagApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelbagApplication.class, args);
    }
}
