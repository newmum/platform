package com.eve.gate.config;

import com.eve.gate.filter.OauthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public OauthFilter oauthFilter() {
        return new OauthFilter();
    }
}
