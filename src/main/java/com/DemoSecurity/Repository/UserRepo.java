package com.DemoSecurity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DemoSecurity.Model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	User findByName(String username);
	
	

}
