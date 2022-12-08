package com.microservices.bike_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.bike_service.entity.Bike;
import com.microservices.bike_service.repository.BikeRepository;

@Service
public class BikeService {
  @Autowired
  BikeRepository bikeRepository;

  public List<Bike> getAll() {
    return bikeRepository.findAll();
  }

  public Bike getBikeById(int id) {
    return bikeRepository.findById(id).orElse(null);
  }

  public Bike save(Bike bike) {
    return bikeRepository.save(bike);
  }

  public List<Bike> getByUserId(int userId) {
    return bikeRepository.getByUserId(userId);
  }
}
