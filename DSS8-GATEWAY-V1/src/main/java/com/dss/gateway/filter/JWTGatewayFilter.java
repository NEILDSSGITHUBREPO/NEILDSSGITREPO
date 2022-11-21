package com.dss.gateway.filter;

import com.dss.gateway.client.RoleClient;
import com.dss.gateway.entity.Action;
import com.dss.gateway.entity.Permission;
import com.dss.gateway.entity.Resource;
import com.dss.gateway.entity.Role;
import com.dss.token.JsonWebToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@RefreshScope
@Component
public class JWTGatewayFilter implements GlobalFilter {

    @Autowired
    RouteValidator routerValidator;

    @Autowired
    JsonWebToken jsonWebToken;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String requestedResource = request.getPath().toString();
        requestedResource = requestedResource.substring(0, requestedResource.lastIndexOf('/'));

        String requestedMethod = Objects.requireNonNull(request.getMethod()).toString();

        //check if request is not on the open routes
        if (routerValidator.isSecured.test(request)) {
            if (!request.getHeaders().containsKey("Authorization")) {
                return setResponse(response, "Malformed Header", HttpStatus.BAD_REQUEST);
            }
            //validate token
            String bearer = request.getHeaders().getOrEmpty("Authorization").get(0);
            String token = bearer.substring(7);

            if (bearer.startsWith("Bearer ") || jsonWebToken.isNotExpired(token)) {
                Role role = RoleClient.getRoleInformation(jsonWebToken.getClaim(token, "role").asInt());
                Set<String> allowableResource = new HashSet<>();
                Set<String> allowableAction = new HashSet<>();

                for (Permission permission : role.getPermissions()) {
                    allowableResource.addAll(permission.getResources()
                            .parallelStream()
                            .map(Resource::getPath)
                            .collect(Collectors.toSet()));

                    allowableAction.addAll(permission.getActions()
                            .parallelStream()
                            .map(Action::getValue)
                            .collect(Collectors.toSet()));
                }


                if (!allowableResource.contains(requestedResource) || !allowableAction.contains(requestedMethod)) {
                    return setResponse(response, "Unsatisfied Privillege", HttpStatus.UNAUTHORIZED);
                }

            } else {
                return setResponse(response, "Malformed Header", HttpStatus.BAD_REQUEST);
            }
        }

        return chain.filter(exchange);
    }

    private static Mono<Void> setResponse(ServerHttpResponse response, String message, HttpStatus httpStatus) {
        response.setStatusCode(httpStatus);
        DataBuffer buffer = response
                .bufferFactory()
                .wrap(message.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(buffer));
    }
}
