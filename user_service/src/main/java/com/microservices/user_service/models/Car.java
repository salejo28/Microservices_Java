package com.microservices.user_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
  private int id;
  private String brand;
  private String model;
  private int userId;
}
