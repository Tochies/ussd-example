package com.tochie.ussdexample.ussdExample;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tochie")
@EnableConfigurationProperties
@Data
public class ApplicationConfiguration {

    private CacheConfigurationProperties cache;

    @Getter(value = AccessLevel.PUBLIC)
    @Setter
    private static class CacheConfigurationProperties {
        private Integer port;
        private String host;
        private String password;
        private String defaultTtl;
    }
}