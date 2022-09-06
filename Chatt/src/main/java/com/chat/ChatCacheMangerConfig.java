package com.chat;

import java.time.Duration;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.spi.CachingProvider;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class ChatCacheMangerConfig {
	@Bean
	public CacheManager ehcacheManger() {
		CachingProvider provider = Caching.getCachingProvider();
		CacheManager cacheManager = provider.getCacheManager();
		MutableConfiguration<Long, String> configuration = new MutableConfiguration<Long, String>()
				.setTypes(Long.class, String.class)
				.setStoreByValue(false)
				.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(javax.cache.expiry.Duration.ONE_MINUTE));
		Cache<Long, String> cache = cacheManager.createCache("customerCache", configuration);
		return cacheManager;
		
	}
}
