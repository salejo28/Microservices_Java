package com.microservices.authservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservices.authservice.entity.AuthUser;
import com.microservices.authservice.repository.AuthUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private AuthUserRepository authUserRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<AuthUser> user = authUserRepository.findByUsername(username);
    if (!user.isPresent())
      throw new UsernameNotFoundException("User not found");
    return new User(user.get().getUsername(), user.get().getPassword(), null);
  }

}
