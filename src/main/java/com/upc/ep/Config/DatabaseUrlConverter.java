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
                
                // Agregar propiedades al entorno
                // Priorizar las variables individuales si están disponibles
                Map<String, Object> properties = new HashMap<>();
                properties.put("spring.datasource.url", jdbcUrl);
                
                // Solo usar credenciales de la URL si no hay variables individuales
                String existingUsername = environment.getProperty("DATABASE_USERNAME");
                String existingPassword = environment.getProperty("DATABASE_PASSWORD");
                
                if (existingUsername == null || existingUsername.isEmpty()) {
                    if (!username.isEmpty()) {
                        properties.put("spring.datasource.username", username);
                    }
                }
                
                if (existingPassword == null || existingPassword.isEmpty()) {
                    if (!password.isEmpty()) {
                        properties.put("spring.datasource.password", password);
                    }
                }
                
                environment.getPropertySources().addFirst(
                    new MapPropertySource("databaseUrlConverter", properties)
                );
                
                System.out.println("Converted DATABASE_URL to JDBC format: " + jdbcUrl);
            } catch (Exception e) {
                System.err.println("Warning: Could not parse DATABASE_URL: " + e.getMessage());
                // No lanzar excepción, dejar que Spring Boot use la configuración por defecto
            }
        }
    }
}

