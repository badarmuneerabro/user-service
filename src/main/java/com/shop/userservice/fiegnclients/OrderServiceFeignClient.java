package com.shop.userservice.fiegnclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.shop.userservice.dto.OrderDTO;

@FeignClient(name = "ORDER-SERVICE", path = "orders")
public interface OrderServiceFeignClient 
{
	@GetMapping("/users/{userId}")
	ResponseEntity<List<OrderDTO>> getOrdersOfUser(@PathVariable("userId") long userId);

}
