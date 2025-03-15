package com.ashishbagdane.lib.eh;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.ashishbagdane.lib.eh")
public class TestApplication {
    // Empty configuration class just to bootstrap Spring Boot test context
}
