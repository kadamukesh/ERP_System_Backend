package com.demo.klef.jfsd.springboot.service;

import java.util.List;
import java.util.Map;

import com.demo.klef.jfsd.springboot.extra.StudentRequest;
import com.demo.klef.jfsd.springboot.model.Feedback;
import com.demo.klef.jfsd.springboot.model.Student;

public interface StudentService
{


public Student checkStudentLogin(StudentRequest request);
public String ChangePS(Student s);
public Student displayStudentByID(int sid);


void addFeedback(int facultyId, String section, Map<String, Integer> responses);
boolean hasFeedbackBeenSubmitted(int facultyId, String section);



List<Feedback> getFeedbackByFacultyAndCourse(int facultyId, int courseId);
}
