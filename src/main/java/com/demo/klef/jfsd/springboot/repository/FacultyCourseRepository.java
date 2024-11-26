package com.demo.klef.jfsd.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.demo.klef.jfsd.springboot.model.Course;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.FacultyCourseMapping;

public interface FacultyCourseRepository extends CrudRepository<FacultyCourseMapping, Integer>
{
  @Query("SELECT COUNT(fcm) FROM FacultyCourseMapping fcm where fcm.faculty = ?1 and fcm.course = ?2 AND fcm.section = ?3")
  public long checkfcoursemapping(Faculty faculty,Course course,int section);

public List<FacultyCourseMapping> findByFaculty(Faculty faculty);

List<FacultyCourseMapping> findByFacultyAndSection(Faculty faculty, int section);
  
 
  
  

 

}
