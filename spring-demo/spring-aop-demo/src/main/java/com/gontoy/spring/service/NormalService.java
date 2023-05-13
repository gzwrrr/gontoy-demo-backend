package com.gontoy.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NormalService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void someMethod() {
        logger.info("--- NormalService: someMethod invoked ---");
    }
}
