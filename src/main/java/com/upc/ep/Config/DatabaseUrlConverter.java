package com.upc.ep.Config;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Convierte DATABASE_URL de Render (formato postgres://) a formato JDBC
 * que Spring Boot espera (jdbc:postgresql://)
 */
public class DatabaseUrlConverter implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        String databaseUrl = environment.getProperty("DATABASE_URL");
        
        // Si DATABASE_URL está en formato postgres://, convertir a JDBC
        if (databaseUrl != null && !databaseUrl.isEmpty() && databaseUrl.startsWith("postgres://")) {
            try {
                URI dbUri = new URI(databaseUrl);
                String username = dbUri.getUserInfo() != null ? dbUri.getUserInfo().split(":")[0] : "";
                String password = dbUri.getUserInfo() != null && dbUri.getUserInfo().contains(":") 
                    ? dbUri.getUserInfo().split(":")[1] : "";
                String host = dbUri.getHost();
                int port = dbUri.getPort() > 0 ? dbUri.getPort() : 5432;
                String path = dbUri.getPath();
                
                // Construir URL JDBC
                String jdbcUrl = String.format("jdbc:postgresql://%s:%d%s", host, port, path);
                
                // Agregar propiedades al entorno (solo si no están ya definidas)
                if (!environment.containsProperty("spring.datasource.url") || 
                    environment.getProperty("spring.datasource.url").equals(databaseUrl)) {
                    Map<String, Object> properties = new HashMap<>();
                    properties.put("spring.datasource.url", jdbcUrl);
                    if (!username.isEmpty() && !environment.containsProperty("spring.datasource.username")) {
                        properties.put("spring.datasource.username", username);
                    }
                    if (!password.isEmpty() && !environment.containsProperty("spring.datasource.password")) {
                        properties.put("spring.datasource.password", password);
                    }
                    
                    environment.getPropertySources().addFirst(
                        new MapPropertySource("databaseUrlConverter", properties)
                    );
                }
            } catch (Exception e) {
                System.err.println("Warning: Could not parse DATABASE_URL: " + e.getMessage());
                // No lanzar excepción, dejar que Spring Boot use la configuración por defecto
            }
        }
    }
}

