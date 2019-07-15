package com.springsecurity.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springsecurity.DAO.RoleDAO;
import com.springsecurity.DAO.UserDAO;
import com.springsecurity.model.Roles;
import com.springsecurity.model.Users;

@Service(value = "userService")
public class JwtUserDetailsService implements UserDetailsService, UserService {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RoleDAO roleDAO;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users userInfo = userDAO.getUserInfo(username);
		if (userInfo == null)
			return null;
		GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRoles().getRole());
		UserDetails userDetails = (UserDetails) new User(userInfo.getUsername(), userInfo.getPassword(),
				Arrays.asList(authority));
		return userDetails;
	}

	@Override
	@Transactional
	public void saveUser(Users user) {
		userDAO.saveUser(user);
	}
	
	@Override
	@Transactional
	public Roles loadRole(String role) {
		return roleDAO.getRole(role);
	}


	@Override
	@Transactional
	public Users loadUser(String username) {
		Users userInfo = userDAO.getUserInfo(username);
		if (userInfo == null) return null;
		return new Users(
				userInfo.getUsername(),
				userInfo.getPassword(),
				userInfo.getEnabled(),
				userInfo.getToken(),
				userInfo.getRoles()
				);
	}
}
