package com.spring.main.config;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RateLimiterConfiguration {
	
	static final Logger logger = LoggerFactory.getLogger(RateLimiterConfiguration.class);

	@Value("${resilience4j.ratelimiter.instanses.default.limit-for-period}")
	int maxRequest;
	
	@Value("${resilience4j.ratelimiter.instanses.default.limit-refresh-period}")
	String timeWindow;
	
	final ConcurrentHashMap<String, RateLimiter> apiRateLimit = new ConcurrentHashMap<>();
	
	@Bean
	RateLimiterRegistry rateLimiterRegistry() {
		logger.info("Entered RateLimiterConfiguration.rateLimiterRegistry() With Maximum Request : " + maxRequest + " & with Refresh Period Of : " + timeWindow);
		RateLimiterConfig config = RateLimiterConfig.custom()
								.limitForPeriod(maxRequest)
								.limitRefreshPeriod(Duration.parse("PT" + timeWindow.toUpperCase()))
								.timeoutDuration(Duration.ZERO)
								.build();
		logger.info("Exited RateLimiterConfiguration.rateLimiterRegistry() With Maximum Request : " + maxRequest + " & with Refresh Period Of : " + timeWindow);
		return RateLimiterRegistry.of(config);
	}
	
	public RateLimiter getOrCreateBucket(String apiName) {
		logger.info("Entered RateLimiterConfiguration.getOrCreateBucket() With Api Name : " + apiName);
		return apiRateLimit.computeIfAbsent(apiName, key -> rateLimiterRegistry().rateLimiter(apiName));
	}

}
