package com.tochie.ussdexample.services;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * This solution adds phantom keys in Redis that apps written in other languages may not understand,
 * hence you may have to account for that, if the redis cluster is utilised by multiple cross-platform applications
 * these keys will get two values for same key(1 orignal and 1 phantom)
 **/
@Data
@RedisHash(value = "sessions", timeToLive = 60)
public class UssdSession implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String sessionId;
    private String serviceCode;
    private String phoneNumber;
    private String text;
    private String previousMenuLevel;
    private String currentMenuLevel;
}
