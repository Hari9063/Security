package com.DemoSecurity.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.DemoSecurity.Students;
import com.DemoSecurity.Repository.Repository;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class MYController {
	@Autowired
	private Repository repo;
	
	
	@GetMapping
	public String get() 
	{
		return "Sucess";
		
	}

	
	@GetMapping("/csrf")
	public CsrfToken getSessionId (HttpServletRequest rep)
	    {
		return (CsrfToken) rep.getAttribute("_csrf");
		}
		
	@GetMapping("/students")
	public List<Students> getStudents(){
		return repo.findAll();
	}
	@PostMapping("/students")
	public String addStudent(@RequestBody Students student) {
		if(student==null) {
			return "student not detected";
		}
		repo.save(student);
		return "Sucess";
	}

}
