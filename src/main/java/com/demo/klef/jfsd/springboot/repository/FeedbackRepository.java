package com.demo.klef.jfsd.springboot.repository;

import com.demo.klef.jfsd.springboot.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.Course;


@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
	
	List<Feedback> findByFacultyAndCourse(Faculty faculty, Course course);
	
    boolean existsByFacultyIdAndSection(int facultyId, int section);
    
    //v
 

}

