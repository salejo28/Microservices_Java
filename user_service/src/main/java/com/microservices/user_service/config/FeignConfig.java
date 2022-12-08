package com.microservices.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import feign.Util;
import feign.codec.Decoder;

@Configuration
public class FeignConfig {
  @Bean
  public Decoder feignDecoder() {
    return (response, type) -> {
      String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
      JavaType javaType = TypeFactory.defaultInstance().constructType(type);
      return new ObjectMapper().readValue(bodyStr, javaType);
    };
  }
}
