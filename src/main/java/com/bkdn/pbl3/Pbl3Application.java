package com.bkdn.pbl3;

import com.bkdn.pbl3.config.StorageProperties;
import com.bkdn.pbl3.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Pbl3Application {

    public static void main(String[] args) {
        SpringApplication.run(Pbl3Application.class, args);

    }
    @Bean
    CommandLineRunner init(StorageService storageService){
        return (args -> {
            storageService.init();
        });
    }
}
