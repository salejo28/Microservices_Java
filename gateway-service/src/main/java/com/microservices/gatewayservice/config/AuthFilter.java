package com.microservices.gatewayservice.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import com.microservices.gatewayservice.dto.TokenDto;

import org.springframework.http.server.reactive.ServerHttpResponse;

import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
  private WebClient.Builder wBuilder;

  public AuthFilter(WebClient.Builder wBuilder) {
    super(Config.class);
    this.wBuilder = wBuilder;
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (((exchange, chain) -> {
      if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
        return onError(exchange, HttpStatus.BAD_REQUEST);
      String tokenHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
      if (!tokenHeader.contains("Bearer"))
        return onError(exchange, HttpStatus.BAD_REQUEST);
      return wBuilder.build()
          .post()
          .uri("http://auth-service/auth/token/validate").header("Authorization", tokenHeader)
          .retrieve().bodyToMono(TokenDto.class)
          .map(t -> {
            t.getToken();
            return exchange;
          }).flatMap(chain::filter);
    }));
  }

  public Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(status);
    return response.setComplete();
  }

  public static class Config {

  }
}
