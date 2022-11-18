package com.dss.auth.token;

import com.dss.token.JsonWebToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JWT {

    @Value("${jwt.token.key}")
    String apiKey;

    @Value("${jwt.token.duration}")
    int expiration;

    @Bean
    public JsonWebToken jsonWebToken(){
        return new JsonWebToken(apiKey, expiration);
    }
}
