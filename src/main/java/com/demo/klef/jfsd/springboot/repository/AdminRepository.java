package com.demo.klef.jfsd.springboot.repository;

import com.demo.klef.jfsd.springboot.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
	

		//for login
	  public Admin findByUsernameAndPassword(String username, String password);

	 //Counselling
}
