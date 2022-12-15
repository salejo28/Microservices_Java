package com.microservices.authservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservices.authservice.dto.AuthUserDto;
import com.microservices.authservice.dto.TokenDto;
import com.microservices.authservice.entity.AuthUser;
import com.microservices.authservice.repository.AuthUserRepository;
import com.microservices.authservice.security.JwtGenerator;

@Service
public class AuthUserService {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  AuthUserRepository authUserRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtGenerator jwtGenerator;

  public AuthUser save(AuthUserDto dto) {
    Optional<AuthUser> user = authUserRepository.findByUsername(dto.getUsername());
    if (!user.isPresent())
      return null;
    String password = passwordEncoder.encode(dto.getPassword());
    AuthUser authUser = AuthUser.builder().username(dto.getUsername()).password(password).build();
    return authUser;
  }

  public TokenDto login(AuthUserDto dto) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtGenerator.generateToken(authentication);
    return new TokenDto(token);
  }

  public TokenDto validate(String token) {
    if (!jwtGenerator.validate(token))
      return null;
    String username = jwtGenerator.getUsernameFromToken(token);
    if (authUserRepository.findByUsername(username).isPresent())
      return null;
    return new TokenDto(token);
  }
}
