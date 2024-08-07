package com.hubo.gillajabi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableScheduling
@EnableAsync
public class HuboApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuboApplication.class, args);
    }

}
