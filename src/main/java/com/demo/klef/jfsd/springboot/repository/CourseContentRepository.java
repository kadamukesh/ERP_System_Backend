package com.demo.klef.jfsd.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.klef.jfsd.springboot.model.CourseContent;
import com.demo.klef.jfsd.springboot.model.FacultyCourseMapping;

@Repository
public interface CourseContentRepository extends JpaRepository<CourseContent, Integer> {


    List<CourseContent> findByFacultyCourseMapping(FacultyCourseMapping facultyCourseMapping);
    
    
    
}