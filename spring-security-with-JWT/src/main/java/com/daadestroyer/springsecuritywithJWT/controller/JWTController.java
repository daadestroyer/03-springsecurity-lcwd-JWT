package com.daadestroyer.springsecuritywithJWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.daadestroyer.springsecuritywithJWT.entity.JwtRequest;
import com.daadestroyer.springsecuritywithJWT.entity.JwtResponse;
import com.daadestroyer.springsecuritywithJWT.helper.JWTHelper;
import com.daadestroyer.springsecuritywithJWT.service.CustomUserDetailService;

@RestController
public class JWTController {

	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private JWTHelper jwtHelper;
	@Autowired
	AuthenticationManager authenticationManager;

	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
		System.out.println("JWT Request = " + jwtRequest);

		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		} catch (BadCredentialsException e) {
			e.printStackTrace();
			throw new Exception("Bad Credentials");
		}

		// fine area
		UserDetails userDetails = this.customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
		String generatedToken = this.jwtHelper.generateToken(userDetails);
		System.out.println("JWT Token = " + generatedToken);

		// return token in json form {"token":"value"}

		return ResponseEntity.ok(new JwtResponse(generatedToken));
	}

	@GetMapping("/test")
	public String test() {
		return "testing...";
	}
}
