package com.DemoSecurity.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DemoSecurity.Students;

public interface Repository extends JpaRepository<Students, Integer> {

}
