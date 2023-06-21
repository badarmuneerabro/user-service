package com.shop.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.userservice.dto.UserDTO;
import com.shop.userservice.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserRestController 
{
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> getAll()
	{
		List<UserDTO> list = userService.findAll();
		
		return new ResponseEntity<List<UserDTO>>(list, HttpStatus.OK); 
	}

}
