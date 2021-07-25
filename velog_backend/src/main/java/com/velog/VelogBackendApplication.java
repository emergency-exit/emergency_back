package com.velog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VelogBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(VelogBackendApplication.class, args);
    }

}
