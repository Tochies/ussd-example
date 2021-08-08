package com.tochie.ussdexample.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Getter
@Setter
@Configuration
@EnableRedisRepositories
public class RedisConfiguration extends CachingConfigurerSupport {

    @Value("${tochie.cache.host}")
    private String host;

    @Value("${tochie.cache.port}")
    private int port;

    @Value("${tochie.cache.password}")
    private String password;

    @Value("{tochie.cache.default-ttl}")
    private String defaultTTL;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

}
