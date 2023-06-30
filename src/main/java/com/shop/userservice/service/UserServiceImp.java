package com.shop.userservice.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.userservice.dto.OrderDTO;
import com.shop.userservice.dto.UserDTO;
import com.shop.userservice.fiegnclients.OrderServiceFeignClient;
import com.shop.userservice.model.User;
import com.shop.userservice.repo.UserRepository;

@Service
public class UserServiceImp implements UserService 
{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private OrderServiceFeignClient orderClient;

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

	@Override
	public void save(UserDTO userDTO) 
	{
		User user = mapper.map(userDTO, User.class);
		
		userRepository.save(user);
		
		userDTO.setId(user.getId());
	}
	
	
	@Override
	public UserDTO findById(long id) 
	{
		User user = userRepository.findById(id).orElse(null);
		
		if(user == null) return null;
		UserDTO userDTO = mapper.map(user, UserDTO.class);
		
		return userDTO;
		
		
	}
	
	
	@Override
	public UserDTO findByEmailAndPassword(String email, String password) 
	{
		User user = userRepository.findByEmailAndPassword(email, password);
		
		return (user == null) ? null : mapper.map(user, UserDTO.class);
	}
	
	
	@Override
	public void deleteUser(UserDTO userDTO) 
	{
		User user = mapper.map(userDTO, User.class);
		userRepository.delete(user);
	}
	
	
	@Override
	public List<OrderDTO> getOrdersByUserId(long userId) 
	{
		List<OrderDTO> list = orderClient.getOrdersOfUser(userId).getBody();
		return list;
	}

}
