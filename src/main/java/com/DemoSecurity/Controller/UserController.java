package com.DemoSecurity.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.DemoSecurity.Model.User;
import com.DemoSecurity.Repository.UserRepo;
import com.DemoSecurity.Service.JwtService;

@RestController
public class UserController  {
	
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private JwtService service ;
	
	@Autowired
	private AuthenticationManager autMan ;
	
	private BCryptPasswordEncoder en=new BCryptPasswordEncoder(12);


	@PostMapping("/register")
   public String register(@RequestBody User user) 
	   {
		System.err.println("register");
		if(user==null) {return "user not found"; }
		user.setPassword(en.encode(user.getPassword()));
		repo.save(user);
		return "sucess"; }
	
	
	@PostMapping("/login")
	public String login(@RequestBody User user) {
		
		
		
		
		Authentication authentication=
				autMan.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
		if(authentication.isAuthenticated()) {
			return service.generateKey(user.getName());
		}
		
		return "fail";

	}
	
	

}
