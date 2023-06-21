package com.shop.userservice.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.userservice.dto.UserDTO;
import com.shop.userservice.model.User;
import com.shop.userservice.repo.UserRepository;

@Service
public class UserServiceImp implements UserService 
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public List<UserDTO> findAll() 
	{
		List<User> userList = userRepository.findAll();
		
		List<UserDTO> list = new ArrayList<>();
		
		for(User user : userList)
		{
			UserDTO userDTO = mapper.map(user, UserDTO.class);
			list.add(userDTO);
		}
		
		return list;
	}
	

}
