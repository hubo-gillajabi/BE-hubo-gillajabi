package com.hubo.gillajabi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HuboApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuboApplication.class, args);
    }

}
