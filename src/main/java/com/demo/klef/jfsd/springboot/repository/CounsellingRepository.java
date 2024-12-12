package com.demo.klef.jfsd.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.klef.jfsd.springboot.model.CounsellingMapping;
import com.demo.klef.jfsd.springboot.model.Student;

public interface CounsellingRepository extends JpaRepository<CounsellingMapping, Integer> {
	boolean existsByStudent(Student student);
	CounsellingMapping findByStudent(Student student);

    List<CounsellingMapping> findByFacultyId(int facultyId);
}
