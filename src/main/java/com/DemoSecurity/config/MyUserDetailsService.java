package com.DemoSecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.DemoSecurity.Model.User;
import com.DemoSecurity.Repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=repo.findByName(username);
		if(user==null) {
			throw new UsernameNotFoundException("user not found");
		}
		return new UserPrinciple(user);
	}

}
