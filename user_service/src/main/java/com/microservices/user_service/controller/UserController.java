package com.microservices.user_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.user_service.entity.User;
import com.microservices.user_service.models.Bike;
import com.microservices.user_service.models.Car;
import com.microservices.user_service.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping
  public ResponseEntity<List<User>> getAll() {
    List<User> users = userService.getAll();
    if (users.isEmpty())
      return ResponseEntity.noContent().build();

    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getById(@PathVariable("id") int id) {
    User user = userService.getUserById(id);
    if (user == null)
      return ResponseEntity.notFound().build();

    return ResponseEntity.ok(user);
  }

  @PostMapping
  public ResponseEntity<User> save(@RequestBody User user) {
    User savedUser = userService.save(user);
    return ResponseEntity.ok(savedUser);
  }

  @GetMapping("/cars/{uid}")
  public ResponseEntity<List<Car>> getCars(@PathVariable("uid") int uid) {
    User user = userService.getUserById(uid);
    if (user == null)
      return ResponseEntity.notFound().build();
    List<Car> cars = userService.getCars(uid);
    return ResponseEntity.ok(cars);
  }

  @GetMapping("/bikes/{uid}")
  public ResponseEntity<List<Bike>> getBikes(@PathVariable("uid") int uid) {
    User user = userService.getUserById(uid);
    if (user == null)
      return ResponseEntity.notFound().build();
    List<Bike> bikes = userService.getBikes(uid);
    return ResponseEntity.ok(bikes);
  }

  @PostMapping("/saveCar/{uid}")
  public ResponseEntity<Car> saveCar(@PathVariable("uid") int uid, @RequestBody Car car) {
    if (userService.getUserById(uid) == null)
      return ResponseEntity.notFound().build();
    Car newCar = userService.saveCar(uid, car);
    return ResponseEntity.ok(newCar);
  }

  @PostMapping("/saveBike/{uid}")
  public ResponseEntity<Bike> saveBike(@PathVariable("uid") int uid, @RequestBody Bike bike) {
    if (userService.getUserById(uid) == null)
      return ResponseEntity.notFound().build();
    Bike newBike = userService.saveBike(uid, bike);
    return ResponseEntity.ok(newBike);
  }

  @GetMapping("/getAll/{userId}")
  public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable("userId") int userId) {
    Map<String, Object> result = userService.getUserAndVehicles(userId);
    return ResponseEntity.ok(result);
  }
}
