package com.example.librarymanagementsystem.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.caffeine.CaffeineCacheManager;

/**
 * Configuration class for caching using Caffeine.
 * This class enables caching and configures the cache manager with Caffeine.
 */
@Configuration
@EnableCaching
public class CachingConfig extends CachingConfigurerSupport {

    /**
     * Creates and configures the CacheManager bean using Caffeine.
     *
     * @return the configured CacheManager
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineCacheBuilder());
        cacheManager.setCacheNames(List.of("patrons", "books")); // Define cache names
        return cacheManager;
    }

    /**
     * Builds and configures the Caffeine cache builder.
     *
     * @return the configured Caffeine cache builder
     */
    Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES) // Set expiration time
                .maximumSize(100) // Set maximum cache size
                .recordStats(); // Enable statistics recording
    }
}
