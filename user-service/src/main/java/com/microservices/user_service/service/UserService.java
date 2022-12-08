package com.microservices.user_service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservices.user_service.entity.User;
import com.microservices.user_service.feignclients.BikeFeignClient;
import com.microservices.user_service.feignclients.CarFeignClient;
import com.microservices.user_service.models.Bike;
import com.microservices.user_service.models.Car;
import com.microservices.user_service.repository.UserRepository;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  private CarFeignClient carFeignClient;

  @Autowired
  private BikeFeignClient bikeFeignClient;

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
    return restTemplate.getForObject("http://localhost:8003/bike/byUser/" + uid, List.class);
  }

  public Car saveCar(int uid, Car car) {
    car.setUserId(uid);
    Car newCar = carFeignClient.save(car);
    return newCar;
  }

  public Bike saveBike(int uid, Bike bike) {
    bike.setUserId(uid);
    return bikeFeignClient.save(bike);
  }

  public Map<String, Object> getUserAndVehicles(int userId) {
    Map<String, Object> result = new HashMap<>();
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) {
      result.put("Message", "User did't exists");
      return result;
    }
    result.put("User", user);
    List<Car> cars = carFeignClient.getByUserId(userId);
    if (cars.isEmpty())
      result.put("Cars", "No cars");
    else
      result.put("Cars", cars);

    List<Bike> bikes = bikeFeignClient.getByUserId(userId);
    if (bikes.isEmpty())
      result.put("Bikes", "No bikes");
    else
      result.put("Bikes", bikes);

    return result;
  }
}
