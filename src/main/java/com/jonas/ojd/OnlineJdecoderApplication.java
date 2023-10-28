package com.jonas.ojd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineJdecoderApplication {

    public static void main(String[] args) {
        JavaInstalls.load();
        SpringApplication.run(OnlineJdecoderApplication.class, args);
    }

}
