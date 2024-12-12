	package com.demo.klef.jfsd.springboot.service;
	
	import java.util.List;

import com.demo.klef.jfsd.springboot.extra.FacultyRequest;
import com.demo.klef.jfsd.springboot.model.Course;
import com.demo.klef.jfsd.springboot.model.CourseContent;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.Feedback;
import com.demo.klef.jfsd.springboot.model.Student;
	
	public interface FacultyService 
	{
		public Faculty checkFacultyLogin(FacultyRequest request);
		
		public String ChangePF(Faculty f);
		
		
		//view feedback
		 List<Course> getFacultyCourses(int facultyId);
		    List<Feedback> getFeedbackByFacultyAndCourse(int facultyId, int courseId);
	  
	//counselling
		    List<Student> getAssignedStudents(int facultyId);
	
		    
		    
		    //upload content
		    public void uploadCourseContent(CourseContent courseContent);         	
		  
	}
