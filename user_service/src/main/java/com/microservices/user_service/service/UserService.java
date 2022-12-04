package com.microservices.user_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.user_service.entity.User;
import com.microservices.user_service.models.Bike;
import com.microservices.user_service.models.Car;
import com.microservices.user_service.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RestTemplate restTemplate;

  public List<User> getAll() {
    return userRepository.findAll();
  }

  public User getUserById(int id) {
    return userRepository.findById(id).orElse(null);
  }

  public User save(User user) {
    User newUser = userRepository.save(user);
    return newUser;
  }

  public List<Car> getCars(int uid) {
    List<Car> cars = restTemplate.getForObject("http://localhost:8002/car/byUser/" + uid, List.class);
    return cars;
  }

  public List<Bike> getBikes(int uid) {
    return restTemplate.getForObject("http://localhost:8003/bike/byUser" + uid, List.class);
  }
}
