package com.demo.klef.jfsd.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.klef.jfsd.springboot.model.StudentCourseRegistration;
import java.util.List;


public interface StudentCourseRegistrationRepository extends JpaRepository<StudentCourseRegistration, Integer> {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM StudentCourseRegistration s WHERE s.studentId = :studentId AND s.facultyCourseMappingId = :mappingId")
    boolean existsByStudentIdAndFacultyCourseMappingId(@Param("studentId") int studentId, @Param("mappingId") int mappingId);
    
    List<StudentCourseRegistration> findByStudentId(int studentId);
    
    
    

}