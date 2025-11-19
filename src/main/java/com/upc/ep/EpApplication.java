package com.upc.ep;

import com.upc.ep.Config.DatabaseUrlConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EpApplication {

    public static void main(String[] args) {
        // Log de variables de entorno importantes (sin valores sensibles)
        String port = System.getenv("PORT");
        String dbUrl = System.getenv("DATABASE_URL");
        System.out.println("Starting application...");
        System.out.println("PORT: " + (port != null ? port : "not set (using default 8080)"));
        System.out.println("DATABASE_URL: " + (dbUrl != null ? "set (length: " + dbUrl.length() + ")" : "not set"));
        
        SpringApplication app = new SpringApplication(EpApplication.class);
        app.addListeners(new DatabaseUrlConverter());
        app.run(args);
    }

}
