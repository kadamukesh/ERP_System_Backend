package com.demo.klef.jfsd.springboot.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.klef.jfsd.springboot.extra.FacultyRequest;
import com.demo.klef.jfsd.springboot.model.CounsellingMapping;
import com.demo.klef.jfsd.springboot.model.Course;
import com.demo.klef.jfsd.springboot.model.CourseContent;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.FacultyCourseMapping;
import com.demo.klef.jfsd.springboot.model.Feedback;
import com.demo.klef.jfsd.springboot.model.Student;
import com.demo.klef.jfsd.springboot.repository.CounsellingRepository;
import com.demo.klef.jfsd.springboot.repository.CourseContentRepository;
import com.demo.klef.jfsd.springboot.repository.CourseRepository;
import com.demo.klef.jfsd.springboot.repository.FacultyCourseRepository;
import com.demo.klef.jfsd.springboot.repository.FacultyRepository;
import com.demo.klef.jfsd.springboot.repository.FeedbackRepository;

@Service
public class FacultyServiceImpl implements FacultyService
{
	@Autowired
	private FacultyRepository facultyRepository;
	
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;
    
    @Autowired
	  private FacultyCourseRepository facultyCourseRepository;
    
    @Autowired
    private CounsellingRepository counsellingRepository;
    
    
    

    
    @Autowired
    private CourseContentRepository courseContentRepository;
    
	@Override
	public Faculty checkFacultyLogin(FacultyRequest request) {
	    Faculty faculty = facultyRepository.findByEidAndPassword(request.getEid(), request.getPassword());
	    return faculty;
	}
	@Override
	public String ChangePF(Faculty f) {
	    Optional<Faculty> obj = facultyRepository.findByPassword(f.getPassword());
	    if (obj.isPresent()) {
	        Faculty ft = obj.get();
	        ft.setPassword(f.getNewPassword());
	        facultyRepository.save(ft);
	        return "Password updated successfully";
	    } else {
	        return "Old password is incorrect";
	    }
	}

	
	//vie feedback

    @Override
    public List<Course> getFacultyCourses(int facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found with ID: " + facultyId));
        List<FacultyCourseMapping> mappings = facultyCourseRepository.findByFaculty(faculty);
        return mappings.stream().map(FacultyCourseMapping::getCourse).collect(Collectors.toList());
    }

    @Override
    public List<Feedback> getFeedbackByFacultyAndCourse(int facultyId, int courseId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found with ID: " + facultyId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
        return feedbackRepository.findByFacultyAndCourse(faculty, course);
    }


   
//counselling
    @Override
    public List<Student> getAssignedStudents(int facultyId) {
        return counsellingRepository.findByFacultyId(facultyId)
                .stream()
                .map(CounsellingMapping::getStudent)
                .collect(Collectors.toList());
    }


	//upload content

    @Override
    public void uploadCourseContent(CourseContent courseContent) {

        courseContentRepository.save(courseContent);
    }



}
