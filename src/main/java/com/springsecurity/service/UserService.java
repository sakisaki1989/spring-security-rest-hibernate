package com.springsecurity.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.springsecurity.model.Roles;
import com.springsecurity.model.Users;

public interface UserService {

	void saveUser(Users user);

	Users loadUser(String username);
	
	Roles loadRole(String role);
}
