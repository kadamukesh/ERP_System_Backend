package com.demo.klef.jfsd.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.Student;
import java.util.List;


@Repository
@EnableJpaRepositories
public interface StudentRepository extends JpaRepository<Student, Integer>
{
	 boolean existsByUid(String uid);//exist or not
	 
	 Optional<Student> findByUid(String uid);//finding that record
	 
	  public Student findByUidAndPassword(String uid, String password);//login
	  
	  Optional<Student> findByPassword(String pass);
	  
	  
	 
}

