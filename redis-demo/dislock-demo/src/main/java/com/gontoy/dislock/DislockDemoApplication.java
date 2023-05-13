package com.gontoy.dislock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class DislockDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DislockDemoApplication.class, args);
    }

}
