package com.form.builder.configservice.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig implements CachingConfigurer {
	private final Logger log = LoggerFactory.getLogger(CacheConfig.class);
	public final static String RESOURCE_MESSAGE_CACHE = "RESOURCE_MESSAGE_CACHE";
    public final static String SYSTEM_LANGUAGE_CACHE = "SYSTEM_LANGUAGE_CACHE";
	
	@Override
	public CacheManager cacheManager() {
		log.info("Initializing simple Guava Cache manager.");
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        
        /*GuavaCache cache1 = new GuavaCache(RESOURCE_MESSAGE_CACHE, CacheBuilder.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .build());

        GuavaCache cache2 = new GuavaCache(CACHE_TWO, CacheBuilder.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .build());

        cacheManager.setCaches(Arrays.asList(cache1,cache2));*/

        return cacheManager;
	}

	@Override
	public CacheResolver cacheResolver() {
		return null;
	}

	@Override
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

	@Override
	public CacheErrorHandler errorHandler() {
		return null;
	}
	
}
