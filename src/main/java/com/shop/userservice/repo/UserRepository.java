package com.shop.userservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shop.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> 
{

	User findByEmailAndPassword(String email, String password);
	
}
