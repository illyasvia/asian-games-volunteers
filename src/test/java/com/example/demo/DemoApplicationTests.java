package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    ConfigurableApplicationContext context;
    @Test
    void contextLoads() {

    }

}
