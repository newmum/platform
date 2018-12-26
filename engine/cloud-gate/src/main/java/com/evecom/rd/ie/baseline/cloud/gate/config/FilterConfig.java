package com.evecom.rd.ie.baseline.cloud.gate.config;

import com.evecom.rd.ie.baseline.cloud.gate.filter.OauthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public OauthFilter oauthFilter() {
        return new OauthFilter();
    }
}
