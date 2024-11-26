package com.demo.klef.jfsd.springboot.service;

import com.demo.klef.jfsd.springboot.extra.FacultyRequest;
import com.demo.klef.jfsd.springboot.extra.LoginRequest;

import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.Student;

public interface FacultyService 
{
	public Faculty checkFacultyLogin(FacultyRequest request);
	
	public String ChangePF(Faculty f);
}
