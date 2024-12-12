package com.demo.klef.jfsd.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.klef.jfsd.springboot.model.FacultyStudentMapping;

public interface FacultyStudentMappingRepository extends JpaRepository<FacultyStudentMapping, Integer> {
	
	
}
