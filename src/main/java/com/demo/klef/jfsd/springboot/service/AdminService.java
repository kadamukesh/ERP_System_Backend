package com.demo.klef.jfsd.springboot.service;

import java.util.List;

import com.demo.klef.jfsd.springboot.extra.LoginRequest;
import com.demo.klef.jfsd.springboot.model.Admin;
import com.demo.klef.jfsd.springboot.model.Course;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.FacultyCourseMapping;
import com.demo.klef.jfsd.springboot.model.FacultyLeave;
import com.demo.klef.jfsd.springboot.model.Student;

public interface AdminService 
{
	//login
	public Admin checkAdminLogin(LoginRequest request);
	
	//student
	public String addstudent(Student s);
	public boolean findByUid(String uid);
	//viewall
	public List<Student> viewall();
	//update
	public String updatestudent(Student s);
	//delete
	public String deletestudent(String uid);
	public Student viewSById(int sid);
	
	//Faculty
	
	public String addFaculty(Faculty f);
	
	public List<Faculty> viewallF();
	public String updateFaculty(Faculty f);
	public String deleteFaculty(String eid);
	public boolean findByEid(String eid);
	public Faculty viewFById(int fid);
	
	
	//course
	public String  addCourse(Course c);
	
	public boolean findByCid(String cid);
	
	public List<Course> viewallC();
	
	 public String facultyCourseMapping(FacultyCourseMapping fcm);
	  public List<FacultyCourseMapping> displayFacultyCourseMapping();
	  
	  
	  public long checkFacultyCourseMapping(Faculty f, Course c, int section, String academicYear, String semester);
	  
	  public Faculty displayFacultyById(int fid);
	  public Course displayCourseById(int cid);
	  public List<FacultyCourseMapping> viewCoursesByFacutly(int fid);
	  
	  
	  //Leave Management
	//faculty leave
	  public String applyLeave(FacultyLeave facultyLeave);
	  public FacultyLeave updateStatusLeave(int leaveid,String status);
	  public List<FacultyLeave>displayAllLeaves();
	  public List<FacultyLeave>viewStatusByFaculty(int fid);
	  
	  
//Counselling
	  public String mapFacultyToStudent(int facultyId, int studentId);
	
	
	

}
