package com.microservices.user_service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.microservices.user_service.models.Car;

@FeignClient(name = "car-service")
public interface CarFeignClient {
  @RequestMapping(method = RequestMethod.POST, value = "/car")
  Car save(@RequestBody Car car);

  @GetMapping("/car/byUser/{userId}")
  List<Car> getByUserId(@PathVariable("userId") int userId);
}
