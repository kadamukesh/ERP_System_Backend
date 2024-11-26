package com.demo.klef.jfsd.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.demo.klef.jfsd.springboot.model.Admin;
import com.demo.klef.jfsd.springboot.model.Faculty;

@Repository
@EnableJpaRepositories
public interface FacultyRepository extends JpaRepository<Faculty, Integer>
{
//to find particular thing present or not
	boolean existsByEid(String eid);
	
	Optional<Faculty> findByEid(String eid);
	
	  public Faculty findByEidAndPassword(String eid, String password);
	  
	  Optional<Faculty> findByPassword(String pass);
	  
	  
	 
}
