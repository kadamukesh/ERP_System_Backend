package com.demo.klef.jfsd.springboot.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.FacultyLeave;


public interface FacultyLeaveRepository extends JpaRepository<FacultyLeave, Integer>
{
  List<FacultyLeave> findByFaculty(Faculty faculty);
}
