package com.springsecurity.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.DAO.UserDAO;
import com.springsecurity.model.JwtRequest;
import com.springsecurity.model.JwtResponse;
import com.springsecurity.model.JwtURLResponse;
import com.springsecurity.model.JwtUserResponse;
import com.springsecurity.model.Users;
import com.springsecurity.security.JwtTokenUtil;
import com.springsecurity.service.UserService;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JwtController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		Users user = userService.loadUser(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(user);
		user.setToken(token);
		
		return ResponseEntity.ok(new JwtUserResponse(user));
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@RequestMapping(value = "/basic_embed", method = RequestMethod.GET)
	public ResponseEntity<?> goBasicEmbed(JwtRequest authenticationRequest) throws Exception {
		String basic_embedurl = "http://public.tableau.com/views/RegionalSampleWorkbook/Storms";
		return ResponseEntity.ok(new JwtURLResponse(basic_embedurl));
	}
	
	@GetMapping("/dynamicLoad")
	public ResponseEntity<?> goDynamicLoad(JwtRequest authenticationRequest) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/export2pdf")
	public ResponseEntity<?> goExportToPDF(JwtRequest authenticationRequest) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/filter")
	public ResponseEntity<?> goFilter(JwtRequest authenticationRequest) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/getData")
	public ResponseEntity<?> goGetData(JwtRequest authenticationRequest) throws Exception {

		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

}
