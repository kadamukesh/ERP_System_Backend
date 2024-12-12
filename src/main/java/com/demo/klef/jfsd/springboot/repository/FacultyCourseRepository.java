package com.demo.klef.jfsd.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.demo.klef.jfsd.springboot.model.Course;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.FacultyCourseMapping;

public interface FacultyCourseRepository extends CrudRepository<FacultyCourseMapping, Integer>
{
 
  
  
  @Query("SELECT COUNT(fcm) FROM FacultyCourseMapping fcm where fcm.faculty = ?1 and fcm.course = ?2 AND fcm.section = ?3 AND fcm.academicYear = ?4 AND fcm.semester = ?5")
  public long checkfcoursemapping(Faculty faculty, Course course, int section, String academicYear, String semester);



List<FacultyCourseMapping> findByFacultyAndSection(Faculty faculty, int section);


@Query("SELECT DISTINCT fcm.academicYear FROM FacultyCourseMapping fcm ORDER BY fcm.academicYear")
List<String> findDistinctAcademicYears();

@Query("SELECT DISTINCT fcm.semester FROM FacultyCourseMapping fcm ORDER BY fcm.semester")
List<String> findDistinctSemesters();

List<FacultyCourseMapping> findByAcademicYearAndSemester(String academicYear, String semester);

//v

//upload content
public List<FacultyCourseMapping> findByFaculty(Faculty faculty);



}
