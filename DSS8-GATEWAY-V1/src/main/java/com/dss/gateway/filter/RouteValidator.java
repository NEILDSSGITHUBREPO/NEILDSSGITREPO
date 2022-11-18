package com.dss.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    //list of all open routes
    //open routes does not need any jwt token
    public static final List<String> openApiEndpoints = List.of("/api/v1/auth/login"
            ,"/api/v1/auth/register");

    //is the requested endpoint on open routes
    public Predicate<ServerHttpRequest> isSecured = serverHttpRequest -> openApiEndpoints.stream()
            .noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));
}
