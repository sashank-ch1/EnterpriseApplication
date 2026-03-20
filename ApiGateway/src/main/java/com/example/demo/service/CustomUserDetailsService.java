package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utility.JwtUtil;


public class CustomUserDetailsService implements UserDetailsService{
 @Autowired
 public UserRepository userrepo;
 @Autowired
 public AuthenticationManager authmanager;
 @Autowired
 public JwtUtil jwtutil;
 @Autowired
 private PasswordEncoder passwordEncoder; // Injects the BCrypt bean
 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user =userrepo.findByUsername(username)
				           .orElseThrow(()->new RuntimeException("User Not Found"));
		return new org.springframework.security.core.userdetails.User(
		        user.getUsername(),
		        user.getPassword(),
		        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRoles()))
		);
	}
	public String login(String username,String Password) {
	 	   authmanager.authenticate(new UsernamePasswordAuthenticationToken(username,Password));
	 	   return jwtutil.generateToken(username, Password);
	    }
	
	/////used to encrypt the password
	 public void registerUser(User user) {
	        // Encode the raw password (e.g., "s@123") into a hash
	        String encodedPassword = passwordEncoder.encode(user.getPassword());
	        
	        // Replace the raw password with the hashed one
	        user.setPassword(encodedPassword);
	        
	        userrepo.save(user);
	    }
}
