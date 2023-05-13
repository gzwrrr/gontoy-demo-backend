package com.gontoy.spring.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SomeService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void someMethod() {
        logger.info("--- someMethod invoke ---");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
