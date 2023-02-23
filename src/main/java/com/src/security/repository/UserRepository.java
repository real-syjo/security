package com.src.security.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.src.security.model.User;


public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByUsername(String username);
	
	public User findByProviderId(String providerId);
}
