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
 * 
 * Este listener se ejecuta ANTES de que Spring Boot lea application.properties,
 * por lo que puede sobrescribir las propiedades de la base de datos.
 */
public class DatabaseUrlConverter implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        
        // Leer DATABASE_URL directamente de las variables de entorno del sistema
        String databaseUrl = System.getenv("DATABASE_URL");
        
        System.out.println("DatabaseUrlConverter: Checking DATABASE_URL...");
        
        // Si DATABASE_URL está en formato postgres://, convertir a JDBC
        if (databaseUrl != null && !databaseUrl.isEmpty() && databaseUrl.startsWith("postgres://")) {
            try {
                System.out.println("DatabaseUrlConverter: Found postgres:// URL, converting to JDBC format...");
                
                URI dbUri = new URI(databaseUrl);
                String username = dbUri.getUserInfo() != null ? dbUri.getUserInfo().split(":")[0] : "";
                String password = dbUri.getUserInfo() != null && dbUri.getUserInfo().contains(":") 
                    ? dbUri.getUserInfo().split(":")[1] : "";
                String host = dbUri.getHost();
                int port = dbUri.getPort() > 0 ? dbUri.getPort() : 5432;
                String path = dbUri.getPath();
                
                // Construir URL JDBC
                String jdbcUrl = String.format("jdbc:postgresql://%s:%d%s", host, port, path);
                
                // Agregar propiedades al entorno - usar addFirst para máxima prioridad
                Map<String, Object> properties = new HashMap<>();
                properties.put("spring.datasource.url", jdbcUrl);
                
                // Solo usar credenciales de la URL si no hay variables individuales
                String existingUsername = System.getenv("DATABASE_USERNAME");
                String existingPassword = System.getenv("DATABASE_PASSWORD");
                
                if (existingUsername == null || existingUsername.isEmpty()) {
                    if (!username.isEmpty()) {
                        properties.put("spring.datasource.username", username);
                        System.out.println("DatabaseUrlConverter: Using username from DATABASE_URL");
                    }
                } else {
                    System.out.println("DatabaseUrlConverter: Using DATABASE_USERNAME from environment");
                }
                
                if (existingPassword == null || existingPassword.isEmpty()) {
                    if (!password.isEmpty()) {
                        properties.put("spring.datasource.password", password);
                        System.out.println("DatabaseUrlConverter: Using password from DATABASE_URL");
                    }
                } else {
                    System.out.println("DatabaseUrlConverter: Using DATABASE_PASSWORD from environment");
                }
                
                // Agregar al principio para que tenga máxima prioridad
                environment.getPropertySources().addFirst(
                    new MapPropertySource("databaseUrlConverter", properties)
                );
                
                System.out.println("DatabaseUrlConverter: Successfully converted DATABASE_URL to JDBC format");
                System.out.println("DatabaseUrlConverter: JDBC URL = " + jdbcUrl.replaceAll(":[^:@]+@", ":****@"));
            } catch (Exception e) {
                System.err.println("DatabaseUrlConverter ERROR: Could not parse DATABASE_URL: " + e.getMessage());
                e.printStackTrace();
                // No lanzar excepción, dejar que Spring Boot use la configuración por defecto
            }
        } else if (databaseUrl != null && !databaseUrl.isEmpty()) {
            System.out.println("DatabaseUrlConverter: DATABASE_URL is already in JDBC format or different format");
        } else {
            System.out.println("DatabaseUrlConverter: DATABASE_URL not set, using default configuration");
        }
    }
}

