package com.shop.userservice.service;

import java.util.List;

import com.shop.userservice.dto.UserDTO;

public interface UserService 
{
	List<UserDTO> findAll();
}
