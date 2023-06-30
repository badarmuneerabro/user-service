package com.shop.userservice.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shop.userservice.dto.OrderDTO;
import com.shop.userservice.dto.UserDTO;
import com.shop.userservice.exception.InvalidCredentialsException;
import com.shop.userservice.exception.ResourceNotFoundException;
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
	
	@PostMapping("/")
	public ResponseEntity<?> saveUser(@Valid @RequestBody UserDTO userDTO)
	{
		userService.save(userDTO);
		
		System.out.println("USERID=" + userDTO.getId() +" saved..");
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(userDTO.getId()).toUri();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setLocation(uri);
		
		return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("id") long id) throws ResourceNotFoundException
	{
		
		UserDTO userDTO = verifyUser(id);
		
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		
	}
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<UserDTO> getUserByEmailAndPassword(@RequestParam("email") String email, @RequestParam("password") String password) 
			throws InvalidCredentialsException
	{
		UserDTO userDTO = userService.findByEmailAndPassword(email, password);
		
		if(userDTO == null)
			throw new InvalidCredentialsException("Invalid Credentials.");
		
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) throws ResourceNotFoundException
	{
		UserDTO userDTO = verifyUser(id);
		userService.deleteUser(userDTO);
		
		return new ResponseEntity<>(HttpStatus.OK);
		
		
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable("id") long id, @RequestBody UserDTO userDTO) throws ResourceNotFoundException
	{
		verifyUser(id);
		userDTO.setId(id);
		
		userService.save(userDTO);
		
		return new ResponseEntity<>(userDTO, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/{userId}/orders")
	public ResponseEntity<List<OrderDTO>> getOrdersOfUser(@PathVariable("userId") long userId) throws ResourceNotFoundException
	{
		verifyUser(userId);
		List<OrderDTO> list = userService.getOrdersByUserId(userId);
		
		return new ResponseEntity<List<OrderDTO>>(list, HttpStatus.OK);
	}
	
	
	private UserDTO verifyUser(long id) throws ResourceNotFoundException
	{
		UserDTO userDTO = userService.findById(id);
		
		if(userDTO == null)
			throw new ResourceNotFoundException("User with id=" + id + " not found.");
		
		return userDTO;
	}

}
