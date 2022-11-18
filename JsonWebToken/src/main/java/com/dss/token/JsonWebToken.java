package com.dss.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;


public class JsonWebToken {
    private final String key;
    private final int duration;
    private final JWTCreator.Builder jwtBuilder;

    public JsonWebToken(String key, int duration) {
        this.key = key;
        this.duration = duration;
        this.jwtBuilder = JWT.create();
    }

    public String generate() {
        Algorithm algorithm = Algorithm.HMAC512(key);
        jwtBuilder.withExpiresAt(LocalDateTime.now().plusMinutes(duration).toInstant(ZoneOffset.UTC));
        return jwtBuilder.sign(algorithm);
    }

    public Claim getClaim(String token, String claim) {
        Algorithm algorithm = Algorithm.HMAC512(key);
        DecodedJWT jwtVerifier = JWT.require(algorithm).build().verify(token);

        return jwtVerifier.getClaim(claim);
    }

    public Map<String, Claim> getClaims(String token) {
        Algorithm algorithm = Algorithm.HMAC512(key);
        DecodedJWT jwtVerifier = JWT.require(algorithm).build().verify(token);

        return jwtVerifier.getClaims();
    }

    public boolean isNotExpired(String token){
        Algorithm algorithm = Algorithm.HMAC512(key);
        DecodedJWT jwtVerifier = JWT.require(algorithm).build().verify(token);
        return jwtVerifier.getExpiresAt().toInstant().isBefore(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }
    public JWTCreator.Builder jwt() {
        return jwtBuilder;
    }


}
