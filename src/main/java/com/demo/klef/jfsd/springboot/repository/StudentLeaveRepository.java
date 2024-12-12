package com.demo.klef.jfsd.springboot.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.klef.jfsd.springboot.model.Student;
import com.demo.klef.jfsd.springboot.model.StudentLeave;


public interface StudentLeaveRepository extends JpaRepository<StudentLeave, Integer>
{
  List<StudentLeave> findByStudent(Student student);
}


