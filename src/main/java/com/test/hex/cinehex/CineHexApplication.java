package com.test.hex.cinehex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CineHexApplication {

    public static void main(String[] args) {
        SpringApplication.run(CineHexApplication.class, args);
    }

}
