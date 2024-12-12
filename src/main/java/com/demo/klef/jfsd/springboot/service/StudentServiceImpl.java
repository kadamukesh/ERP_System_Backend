package com.demo.klef.jfsd.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.klef.jfsd.springboot.extra.StudentRequest;
import com.demo.klef.jfsd.springboot.model.CounsellingMapping;
import com.demo.klef.jfsd.springboot.model.Course;
import com.demo.klef.jfsd.springboot.model.CourseContent;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.FacultyCourseMapping;
import com.demo.klef.jfsd.springboot.model.FeePayments;
import com.demo.klef.jfsd.springboot.model.Feedback;
import com.demo.klef.jfsd.springboot.model.Student;
import com.demo.klef.jfsd.springboot.model.StudentCourseRegistration;
import com.demo.klef.jfsd.springboot.model.StudentLeave;
import com.demo.klef.jfsd.springboot.repository.CounsellingRepository;
import com.demo.klef.jfsd.springboot.repository.CourseContentRepository;
import com.demo.klef.jfsd.springboot.repository.CourseRepository;
import com.demo.klef.jfsd.springboot.repository.FacultyCourseRepository;
import com.demo.klef.jfsd.springboot.repository.FacultyRepository;
import com.demo.klef.jfsd.springboot.repository.FeePaymentsRepository;
import com.demo.klef.jfsd.springboot.repository.FeedbackRepository;
import com.demo.klef.jfsd.springboot.repository.StudentCourseRegistrationRepository;
import com.demo.klef.jfsd.springboot.repository.StudentLeaveRepository;
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
    
    
    @Autowired
    private StudentLeaveRepository studentLeaveRepository;
    
    
    
    
    @Autowired
    private FeePaymentsRepository feePaymentsRepository;
    @Autowired
    private StudentCourseRegistrationRepository studentCourseRegistrationRepository;
    

    
    @Autowired
    private CourseContentRepository courseContentRepository;
    
    
  @Autowired
private CounsellingRepository counsellingRepository;


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




//Hostel Management

	@Override
	public String applyLeave(StudentLeave studentLeave) {
		studentLeave.setStatus("Pending");
         studentLeaveRepository.save(studentLeave);
         return "Student Leave Sent Successfully";
	}

	@Override
	public StudentLeave updateStatusLeave(int leaveid, String status) {
		StudentLeave leaveRequest = studentLeaveRepository.findById(leaveid)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + leaveid));
        leaveRequest.setStatus(status);
        return studentLeaveRepository.save(leaveRequest);
	}



	@Override
	public List<StudentLeave> displayAllLeaves() {
		 return studentLeaveRepository.findAll();
	}


	@Override
	public List<StudentLeave> viewStatusByStudent(int sid) {
		 Student student = repository.findById(sid)
	                .orElseThrow(() -> new RuntimeException("Faculty not found with id: " + sid));
	        return studentLeaveRepository.findByStudent(student);
	}








	@Override
	public Student viewSById(int sid) {
	    return repository.findById(sid)
	        .orElseThrow(() -> new RuntimeException("Student not found with id: " + sid));
	}






	//Student COurse Register

	@Override
    public List<FacultyCourseMapping> getAllFacultyCourseMappings() {
        return (List<FacultyCourseMapping>) facultyCourseMappingRepository.findAll();
    }

    @Override
    @Transactional
    public void registerCourses(String studentUsername, List<Integer> mappingIds) throws Exception {
        Student student = repository.findByUid(studentUsername)
                .orElseThrow(() -> new Exception("Student not found"));

        for (int mappingId : mappingIds) {
            if (studentCourseRegistrationRepository.existsByStudentIdAndFacultyCourseMappingId(student.getId(), mappingId)) {
                throw new Exception("Student is already registered for one of the selected courses");
            }

            FacultyCourseMapping mapping = facultyCourseMappingRepository.findById(mappingId)
                    .orElseThrow(() -> new Exception("Faculty-Course mapping not found"));

            StudentCourseRegistration registration = new StudentCourseRegistration();
            registration.setStudentId(student.getId());
            registration.setFacultyCourseMappingId(mappingId);
            registration.setMappingId(mappingId);
            studentCourseRegistrationRepository.save(registration);
        }
    }







    @Override
    public List<FacultyCourseMapping> getRegisteredCourses(String studentUsername) throws Exception {
        Student student = repository.findByUid(studentUsername)
                .orElseThrow(() -> new Exception("Student not found"));

        List<StudentCourseRegistration> registrations = studentCourseRegistrationRepository.findByStudentId(student.getId());

        return registrations.stream()
                .map(reg -> facultyCourseMappingRepository.findById(reg.getFacultyCourseMappingId())
                        .orElseThrow(() -> new RuntimeException("Faculty-Course mapping not found")))
                .collect(Collectors.toList());
    }







    @Override
    public List<String> getUniqueAcademicYears() {
        return facultyCourseMappingRepository.findDistinctAcademicYears();
    }

    @Override
    public List<String> getUniqueSemesters() {
        return facultyCourseMappingRepository.findDistinctSemesters();
    }

    @Override
    public List<FacultyCourseMapping> getFacultyCourseMappings(String academicYear, String semester) {
        return facultyCourseMappingRepository.findByAcademicYearAndSemester(academicYear, semester);
    }




//councellor
    @Override
    public Faculty getStudentCounselor(int studentId) {
        Student student = repository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        CounsellingMapping mapping = counsellingRepository.findByStudent(student);
        if (mapping != null) {
            return mapping.getFaculty();
        }
        return null;
    }

	//view course content
    
    @Override
    public List<CourseContent> viewCoursesBySectionId(int fcmId) {
    	FacultyCourseMapping s = facultyCourseMappingRepository.findById(fcmId).orElse(null);
        assert s != null;
        return courseContentRepository.findByFacultyCourseMapping(s);
    }
	
//Pay Fee
    @Override
    public List<FeePayments> viewFeePayments(int studentId) {
        Student student = repository.findById(studentId).orElse(null);
        return feePaymentsRepository.findByStudent(student);
    }

    @Override
    public void payFeePayments(FeePayments feePayments) {
    	feePaymentsRepository.save(feePayments);
    }

}
