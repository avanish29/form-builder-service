package com.form.builder.configservice.core.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private final long MAX_AGE_SECS = 3600;
	
	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		if(registry != null) {
			registry.addMapping("/api/**")
			.allowedOrigins("*")
			.allowedHeaders("Access-Control-Allow-Origin")
			.allowedMethods(HEAD.name(), OPTIONS.name(), GET.name(), POST.name(), PUT.name(), PATCH.name(), DELETE.name())
			.allowCredentials(false)
			.maxAge(MAX_AGE_SECS);
		}
	}
	
	@Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
    	GrantedAuthorityDefaults roleVoter = new GrantedAuthorityDefaults(StringUtils.EMPTY);
    	return roleVoter;
    }
}
