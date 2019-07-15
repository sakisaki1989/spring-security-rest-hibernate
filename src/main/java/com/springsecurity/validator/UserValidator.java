package com.springsecurity.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.springsecurity.model.Users;
import com.springsecurity.service.JwtUserDetailsService;

@Component
public class UserValidator implements Validator {
	
	@Autowired
	@Qualifier("userService")
	UserDetailsService userService;
	
	@Override
	public boolean supports(Class<?> aClass) {
		 return Users.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		
		Users user = (Users) o;
		
		   if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
	            errors.rejectValue("username", "Size.userForm.username");
	        }
	        if (userService.loadUserByUsername(user.getUsername()) != null) {
	            errors.rejectValue("username", "Duplicate.userForm.username");
	        }

	        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
	        if (user.getPassword().length() < 6 || user.getPassword().length() > 70) {
	            errors.rejectValue("password", "Size.userForm.password");
	        }

	}

}
