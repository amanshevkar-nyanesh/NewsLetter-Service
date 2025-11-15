package com.newsletter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DatabaseConfig {

    /**
     * Converts Render's postgresql:// URL format to jdbc:postgresql:// format
     * Render provides: postgresql://user:password@host:port/dbname
     * Spring Boot needs: jdbc:postgresql://user:password@host:port/dbname
     */
    @Bean
    @Primary
    public DataSource dataSource(@Value("${DATABASE_URL:}") String databaseUrl) {
        String jdbcUrl;
        String driverClassName = null;
        
        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            // Convert postgresql:// to jdbc:postgresql://
            if (databaseUrl.startsWith("postgresql://")) {
                jdbcUrl = "jdbc:" + databaseUrl;
                driverClassName = "org.postgresql.Driver";
                log.info("Converted DATABASE_URL from postgresql:// to jdbc:postgresql:// format");
            } else if (databaseUrl.startsWith("jdbc:postgresql://")) {
                // Already in correct format
                jdbcUrl = databaseUrl;
                driverClassName = "org.postgresql.Driver";
                log.info("DATABASE_URL already in jdbc:postgresql:// format");
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
        
        return builder.build();
    }
}

