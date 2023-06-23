package com.shop.userservice.service;

import java.util.List;

import com.shop.userservice.dto.UserDTO;

public interface UserService 
{
	List<UserDTO> findAll();
	
	void save(UserDTO userDTO);
	
	UserDTO findById(long id);
	
	void deleteUser(UserDTO userDTO);
	
	UserDTO findByEmailAndPassword(String email, String password);
}
