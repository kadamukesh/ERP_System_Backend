package com.demo.klef.jfsd.springboot.service;

import java.util.List;
import java.util.Map;

import com.demo.klef.jfsd.springboot.extra.StudentRequest;
import com.demo.klef.jfsd.springboot.model.CourseContent;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.FacultyCourseMapping;
import com.demo.klef.jfsd.springboot.model.FeePayments;
import com.demo.klef.jfsd.springboot.model.Feedback;
import com.demo.klef.jfsd.springboot.model.Student;
import com.demo.klef.jfsd.springboot.model.StudentLeave;

public interface StudentService
{


public Student checkStudentLogin(StudentRequest request);
public String ChangePS(Student s);
public Student displayStudentByID(int sid);
void addFeedback(int facultyId, String section, Map<String, Integer> responses);
boolean hasFeedbackBeenSubmitted(int facultyId, String section);
List<Feedback> getFeedbackByFacultyAndCourse(int facultyId, int courseId);

public Student viewSById(int sid);

//Hostel Management



public String applyLeave(StudentLeave studentLeave);
public StudentLeave updateStatusLeave(int leaveid,String status);
public List<StudentLeave>displayAllLeaves();
public List<StudentLeave>viewStatusByStudent(int sid);


//STdent course registrartion
List<FacultyCourseMapping> getAllFacultyCourseMappings();
void registerCourses(String studentUsername, List<Integer> mappingIds) throws Exception;
List<FacultyCourseMapping> getRegisteredCourses(String studentUsername) throws Exception;

List<String> getUniqueAcademicYears();
List<String> getUniqueSemesters();
List<FacultyCourseMapping> getFacultyCourseMappings(String academicYear, String semester);


//councellor
Faculty getStudentCounselor(int studentId);


//View Course COntent
public List<CourseContent>    viewCoursesBySectionId(int fcmId);

//Pay Fee

public List<FeePayments> viewFeePayments(int studentId);
public void payFeePayments(FeePayments feePayments);
}
