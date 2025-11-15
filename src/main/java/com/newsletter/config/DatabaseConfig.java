package com.newsletter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class DatabaseConfig {

    /**
     * Converts Render's postgresql:// URL format to jdbc:postgresql:// format
     * Render provides: postgresql://user:password@host:port/dbname
     * Spring Boot needs: jdbc:postgresql://host:port/dbname with separate username/password
     */
    @Bean
    @Primary
    public DataSource dataSource(@Value("${DATABASE_URL:}") String databaseUrl) {
        String jdbcUrl;
        String driverClassName = null;
        String username = null;
        String password = null;
        
        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            // Parse PostgreSQL URL format: postgresql://user:password@host:port/dbname
            if (databaseUrl.startsWith("postgresql://") || databaseUrl.startsWith("jdbc:postgresql://")) {
                try {
                    // Remove jdbc: prefix if present for parsing
                    String urlToParse = databaseUrl.startsWith("jdbc:") 
                        ? databaseUrl.substring(5) 
                        : databaseUrl;
                    
                    // Parse the URI
                    URI uri = new URI(urlToParse);
                    
                    // Extract components
                    String host = uri.getHost();
                    int port = uri.getPort() == -1 ? 5432 : uri.getPort();
                    String path = uri.getPath();
                    String dbName = path.startsWith("/") ? path.substring(1) : path;
                    
                    // Extract user info (user:password)
                    String userInfo = uri.getUserInfo();
                    if (userInfo != null && userInfo.contains(":")) {
                        String[] parts = userInfo.split(":", 2);
                        username = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
                        password = URLDecoder.decode(parts[1], StandardCharsets.UTF_8);
                    } else if (userInfo != null) {
                        username = URLDecoder.decode(userInfo, StandardCharsets.UTF_8);
                    }
                    
                    // Build JDBC URL without credentials (safer)
                    jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, dbName);
                    driverClassName = "org.postgresql.Driver";
                    
                    log.info("Parsed PostgreSQL DATABASE_URL - host: {}, port: {}, database: {}", host, port, dbName);
                } catch (Exception e) {
                    log.error("Failed to parse DATABASE_URL: {}", e.getMessage());
                    // Fallback to simple conversion
                    jdbcUrl = databaseUrl.startsWith("jdbc:") ? databaseUrl : "jdbc:" + databaseUrl;
                    driverClassName = "org.postgresql.Driver";
                }
            } else if (databaseUrl.startsWith("jdbc:h2:")) {
                // H2 database
                jdbcUrl = databaseUrl;
                driverClassName = "org.h2.Driver";
                log.info("Using H2 database");
            } else {
                // Fallback to default H2
                jdbcUrl = "jdbc:h2:mem:newsletterdb";
                driverClassName = "org.h2.Driver";
                log.warn("DATABASE_URL format not recognized, falling back to H2");
            }
        } else {
            // No DATABASE_URL set, use H2 for local development
            jdbcUrl = "jdbc:h2:mem:newsletterdb";
            driverClassName = "org.h2.Driver";
            log.info("No DATABASE_URL set, using H2 in-memory database");
        }
        
        DataSourceBuilder<?> builder = DataSourceBuilder.create()
                .url(jdbcUrl);
        
        if (driverClassName != null) {
            builder.driverClassName(driverClassName);
        }
        
        // Set username and password separately if parsed from URL
        if (username != null) {
            builder.username(username);
        }
        if (password != null) {
            builder.password(password);
        }
        
        return builder.build();
    }
}

