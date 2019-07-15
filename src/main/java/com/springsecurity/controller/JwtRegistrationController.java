package com.springsecurity.controller;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.constants.IConstants;
import com.springsecurity.model.JwtRequest;
import com.springsecurity.model.JwtUserResponse;
import com.springsecurity.model.Roles;
import com.springsecurity.model.Users;
import com.springsecurity.security.JwtTokenUtil;
import com.springsecurity.service.UserService;
import com.springsecurity.validator.UserValidator;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JwtRegistrationController {

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@PostMapping("/registration")
	public ResponseEntity<?> addUser(@RequestBody JwtRequest registrationRequest, BindingResult bindingResult) {
		Users user = new Users(registrationRequest.getUsername(),
					passwordEncoder.encode(registrationRequest.getPassword()), IConstants.ONE,
					userService.loadRole(registrationRequest.getRole()) !=null ? userService.loadRole(registrationRequest.getRole()) : new Roles(registrationRequest.getRole()));

	
		userValidator.validate(user, bindingResult);

		if (bindingResult.hasErrors()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(bindingResult.getFieldError());
		}

		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRoles().getRole());
		final String token = jwtTokenUtil.generateToken(user);
		user.setToken(token);
		userService.saveUser(user);
		return ResponseEntity.ok(new JwtUserResponse(user));
	}

}
