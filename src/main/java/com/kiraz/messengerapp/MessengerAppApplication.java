package com.kiraz.messengerapp;

import com.kiraz.messengerapp.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@CrossOrigin(origins = {"http://localhost:3000"})
public class MessengerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessengerAppApplication.class, args);
    }

}
