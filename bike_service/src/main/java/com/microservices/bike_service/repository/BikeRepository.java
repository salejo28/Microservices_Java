package com.microservices.bike_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.bike_service.entity.Bike;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {
  List<Bike> getByUserId(int userId);
}
