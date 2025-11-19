package com.upc.ep;

import com.upc.ep.Config.DatabaseUrlConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EpApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EpApplication.class);
        app.addListeners(new DatabaseUrlConverter());
        app.run(args);
    }

}
