package com.demo.klef.jfsd.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.klef.jfsd.springboot.extra.StudentRequest;
import com.demo.klef.jfsd.springboot.model.Course;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.FacultyCourseMapping;
import com.demo.klef.jfsd.springboot.model.Feedback;
import com.demo.klef.jfsd.springboot.model.Student;
import com.demo.klef.jfsd.springboot.repository.CourseRepository;
import com.demo.klef.jfsd.springboot.repository.FacultyCourseRepository;
import com.demo.klef.jfsd.springboot.repository.FacultyRepository;
import com.demo.klef.jfsd.springboot.repository.FeedbackRepository;
import com.demo.klef.jfsd.springboot.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService
{
	@Autowired
	private StudentRepository repository;


    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private FacultyCourseRepository facultyCourseMappingRepository;



	

	









	@Override
	public Student checkStudentLogin(StudentRequest request) {
		 Student st = repository.findByUidAndPassword(request.getUid(), request.getPassword());
		    return st;
	}








	@Override
	public String ChangePS(Student s) 
	{
		  Optional<Student> obj = repository.findByPassword(s.getPassword());
		    if (obj.isPresent()) {
		        Student st = obj.get();
		        st.setPassword(s.getNewPassword()); // Set the new password correctly
		        repository.save(st); // Save the updated password
		        return "Password updated successfully";
		    } else {
		        return "Old password is incorrect";
		    }
	}








	@Override
	public Student displayStudentByID(int sid) {
		// TODO Auto-generated method stub
		return repository.findById(sid).get();
	}


	 @Override
	    @Transactional
	    public void addFeedback(int facultyId, String section, Map<String, Integer> responses) {
	        if (responses == null || responses.isEmpty()) {
	            throw new IllegalArgumentException("Responses map cannot be null or empty.");
	        }

	        Faculty faculty = facultyRepository.findById(facultyId)
	                .orElseThrow(() -> new IllegalArgumentException("Invalid faculty ID: " + facultyId));

	        int sectionInt;
	        try {
	            sectionInt = Integer.parseInt(section);
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("Invalid section: " + section);
	        }

	        List<FacultyCourseMapping> mappings = facultyCourseMappingRepository.findByFacultyAndSection(faculty, sectionInt);
	        if (mappings.isEmpty()) {
	            throw new IllegalArgumentException("No mapping found for faculty ID: " + facultyId + " and section: " + section);
	        }

	        FacultyCourseMapping mapping = mappings.get(0);

	        List<Feedback> feedbackList = new ArrayList<>();

	        responses.forEach((questionId, rating) -> {
	            if (rating == null || rating < 1 || rating > 5) {
	                throw new IllegalArgumentException("Invalid rating value for question: " + questionId);
	            }

	            Feedback feedback = new Feedback();
	            feedback.setFaculty(faculty);
	            feedback.setCourse(mapping.getCourse());
	            feedback.setSection(sectionInt);
	            feedback.setQuestionId(questionId);
	            feedback.setRating(rating);

	            feedbackList.add(feedback);
	        });

	        feedbackRepository.saveAll(feedbackList);
	    }

	    @Override
	    public boolean hasFeedbackBeenSubmitted(int facultyId, String section) {
	        int sectionInt;
	        try {
	            sectionInt = Integer.parseInt(section);
	        } catch (NumberFormatException e) {
	            throw new IllegalArgumentException("Invalid section: " + section);
	        }

	        return feedbackRepository.existsByFacultyIdAndSection(facultyId, sectionInt);
	    }
	 
	   @Override
	    public List<Feedback> getFeedbackByFacultyAndCourse(int facultyId, int courseId) {
	        Faculty faculty = facultyRepository.findById(facultyId)
	                .orElseThrow(() -> new RuntimeException("Faculty not found"));
	        Course course = courseRepository.findById(courseId)
	                .orElseThrow(() -> new RuntimeException("Course not found"));

	        return feedbackRepository.findByFacultyAndCourse(faculty, course);
	    }







	

	

}
