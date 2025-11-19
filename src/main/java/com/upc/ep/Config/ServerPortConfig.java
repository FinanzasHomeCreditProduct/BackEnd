package com.upc.ep.Config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerPortConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        String portEnv = System.getenv("PORT");
        int port = 8080; // Puerto por defecto
        
        if (portEnv != null && !portEnv.isEmpty()) {
            try {
                // Intentar parsear el puerto
                port = Integer.parseInt(portEnv);
                System.out.println("Using PORT from environment: " + port);
            } catch (NumberFormatException e) {
                // Si PORT no es un número válido, usar el por defecto
                System.err.println("Warning: Invalid PORT value '" + portEnv + "', using default 8080");
                port = 8080;
            }
        } else {
            System.out.println("PORT not set, using default 8080");
        }
        
        factory.setPort(port);
    }
}

