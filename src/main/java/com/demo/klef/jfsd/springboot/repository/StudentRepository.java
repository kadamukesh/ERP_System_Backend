package com.demo.klef.jfsd.springboot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.Student;

@Repository
@EnableJpaRepositories
public interface StudentRepository extends JpaRepository<Student, Integer>
{
	 boolean existsByUid(String uid);
	 
	 Optional<Student> findByUid(String uid);
	 
	  public Student findByUidAndPassword(String uid, String password);
	  
	  Optional<Student> findByPassword(String pass);
	  
	 
}

