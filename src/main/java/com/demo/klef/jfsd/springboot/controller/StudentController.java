package com.demo.klef.jfsd.springboot.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.klef.jfsd.springboot.extra.FeePaymentRequest;
import com.demo.klef.jfsd.springboot.extra.StudentRequest;
import com.demo.klef.jfsd.springboot.model.CourseContent;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.FacultyCourseMapping;
import com.demo.klef.jfsd.springboot.model.FeePayments;
import com.demo.klef.jfsd.springboot.model.Feedback;
import com.demo.klef.jfsd.springboot.model.Student;
import com.demo.klef.jfsd.springboot.model.StudentLeave;
import com.demo.klef.jfsd.springboot.repository.StudentRepository;
import com.demo.klef.jfsd.springboot.service.StudentService;

@Controller
@ResponseBody
@CrossOrigin
public class StudentController 
{

	@Autowired
	private StudentService studentservice;
	
	@Autowired
	private StudentRepository repository;
	
	 
	

	 //login
	@PostMapping("checkstudentLogin")
	public ResponseEntity<?> checkStudentLogin(@RequestBody StudentRequest request) {
	    Student student = studentservice.checkStudentLogin(request);
	    if (student != null) {
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Login successful");
	        response.put("role", "student");
	        response.put("name",student.getName());
	        response.put("sgender", student.getGender());
	        response.put("dept", student.getDepartment());
	        
	        
	        response.put("age", String.valueOf(student.getAge()));
	        response.put("email", student.getEmail());
	        response.put("contact", student.getContact());
	        response.put("dob", student.getDateofbirth());
	        response.put("atype", student.getAdmissionType());
	        response.put("uid", student.getUid());
	        response.put("sid", String.valueOf(student.getId()));
	        System.out.println("Department sent to frontend: " + student.getDepartment());

	        String base64Image = Base64.getEncoder().encodeToString(student.getImage());
	        response.put("image", base64Image); // Add image to the response

	        return ResponseEntity.ok(response); // Return the response with role
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Login Details");
	    }
	}
	
	
	 
		@PutMapping("updateSpass")
		public ResponseEntity<String> updatepass(@RequestBody	Student s) {
		    String result = studentservice.ChangePS(s);
		    if ("Password updated successfully".equals(result)) {
		        return ResponseEntity.ok(result);
		    } else {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result); // Send 400 for incorrect password
		    }
		}
		
		  @GetMapping("Sdisplay")
		    public Student Sdisplay(@RequestParam("id") int sid) {
		        return studentservice.displayStudentByID(sid);
		    }
		  
		  
@PostMapping("/addfeedback")
public ResponseEntity<Map<String, String>> addFeedback(@RequestBody Map<String, Object> payload) {
		        try {
		            System.out.println("Received payload: " + payload);

		            Integer facultyId = payload.get("facultyId") instanceof Number 
		                                ? ((Number) payload.get("facultyId")).intValue() 
		                                : null;
		            String section = (String) payload.get("section");
		            Map<String, Integer> responses = (Map<String, Integer>) payload.get("responses");

		            if (facultyId == null || section == null || responses == null || responses.isEmpty()) {
		                return ResponseEntity.badRequest().body(Map.of("error", "Missing or invalid payload data."));
		            }

		            // Check for duplicate submission
		            if (studentservice.hasFeedbackBeenSubmitted(facultyId, section)) {
		                return ResponseEntity.badRequest().body(Map.of("error", "Feedback has already been submitted for this faculty and section."));
		            }

		            studentservice.addFeedback(facultyId, section, responses);
		            return ResponseEntity.ok(Map.of("message", "Feedback submitted successfully"));
		        } catch (IllegalArgumentException e) {
		            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		        } catch (Exception e) {
		            e.printStackTrace();
		            return ResponseEntity.internalServerError().body(Map.of("error", "Error adding feedback: " + e.getMessage()));
		        }
		    }



		  
@GetMapping("/faculty/{facultyId}/course/{courseId}")
 public ResponseEntity<List<Feedback>> getFeedbackByFacultyAndCourse(
 @PathVariable int facultyId,
 @PathVariable int courseId)
 {
List<Feedback> feedback = studentservice.getFeedbackByFacultyAndCourse(facultyId, courseId);
  return ResponseEntity.ok(feedback);
}


//Hostel management


@PostMapping("/student/leave/apply")
public ResponseEntity<String> applyLeave(@RequestBody Map<String, Object> leaveDetails) {
    try {
      if (!leaveDetails.containsKey("studentId")) {
            return ResponseEntity.badRequest().body("Student ID is required.");
        }
        // Extracting input data from the request body
        int studentId = Integer.parseInt(leaveDetails.get("studentId").toString());
        String startDateStr = leaveDetails.get("startDate").toString();
        String endDateStr = leaveDetails.get("endDate").toString();
        String reason = leaveDetails.get("reason").toString();

        // Parsing date strings to java.util.Date
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);

        // Validating and creating a FacultyLeave object
        StudentLeave leave = new StudentLeave();
        leave.setStudent(studentservice.viewSById(studentId));
        leave.setStartDate(startDate);
        leave.setEndDate(endDate);
        leave.setReason(reason);
        leave.setStatus("Pending"); // Default status when applying leave

        // Save the leave request using the service
        String result = studentservice.applyLeave(leave);

        // Return success response
        return ResponseEntity.ok(result);
    } catch (NumberFormatException e) {
        return ResponseEntity.badRequest().body("Invalid faculty ID: " + e.getMessage());
    } catch (ParseException e) {
        return ResponseEntity.badRequest().body("Invalid date format. Use yyyy-MM-dd: " + e.getMessage());
    } catch (NullPointerException e) {
        return ResponseEntity.badRequest().body("Missing required fields: " + e.getMessage());
    }
}


// Getting leave requests for admin
@GetMapping("/student/appliedforleave")
  public ResponseEntity<List<StudentLeave>> getAllLeaves() {
      List<StudentLeave> leaves = studentservice.displayAllLeaves();
      return ResponseEntity.ok(leaves);
  }


// Updating leave status for admin
@PutMapping("/student/update/{leaveId}")
  public ResponseEntity<StudentLeave> updateLeaveStatus(@PathVariable int leaveId, @RequestParam String status) {
	StudentLeave updatedLeave = studentservice.updateStatusLeave(leaveId, status);
      return ResponseEntity.ok(updatedLeave);
  }


@GetMapping("/student/{studentId}")
  public ResponseEntity<List<StudentLeave>> getLeavesByFaculty(@PathVariable int studentId) {
      List<StudentLeave> leaves = studentservice.viewStatusByStudent(studentId);
      if(!leaves.isEmpty())
      {
        return ResponseEntity.ok(leaves);          
      }
      else
      {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }
  }


//Student Course Registration


@GetMapping("/faculty-course-mappings")
public ResponseEntity<List<FacultyCourseMapping>> getFacultyCourseMappings(
    @RequestParam String academicYear,
    @RequestParam String semester
) {
    List<FacultyCourseMapping> mappings = studentservice.getFacultyCourseMappings(academicYear, semester);
    return ResponseEntity.ok(mappings);
}

@GetMapping("/academic-years-and-semesters")
public ResponseEntity<Map<String, List<String>>> getAcademicYearsAndSemesters() {
    Map<String, List<String>> result = new HashMap<>();
    result.put("academicYears", studentservice.getUniqueAcademicYears());
    result.put("semesters", studentservice.getUniqueSemesters());
    return ResponseEntity.ok(result);
}

@PostMapping("/register-courses")
public ResponseEntity<?> registerCourses(@RequestBody Map<String, Object> payload) {
    try {
        String studentUsername = (String) payload.get("studentUsername");
        List<Integer> mappingIds = ((List<Number>) payload.get("mappingIds"))
            .stream()
            .map(Number::intValue)
            .collect(Collectors.toList());
        studentservice.registerCourses(studentUsername, mappingIds);
        return ResponseEntity.ok("Courses registered successfully");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}




@GetMapping("/registered-courses/{studentUsername}")
public ResponseEntity<?> getRegisteredCourses(@PathVariable String studentUsername) {
    try {
        return ResponseEntity.ok(studentservice.getRegisteredCourses(studentUsername));
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

//councellor
@GetMapping("/student/{studentId}/counselor")
public ResponseEntity<?> getStudentCounselor(@PathVariable int studentId) {
    try {
        Faculty counselor = studentservice.getStudentCounselor(studentId);
        if (counselor != null) {
            return ResponseEntity.ok(counselor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No counselor assigned");
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching counselor details");
    }
}
//Vieew Course content
@GetMapping("viewCourseContentBySection")
public  ResponseEntity<?> viewCourseContentBySection(@RequestParam int fcmId){
    List<CourseContent> contents = studentservice.viewCoursesBySectionId(fcmId);
    if(contents==null){
        return  ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(contents);
}
//Fee Payment
@PostMapping("payFee")
public ResponseEntity<?> payFee(@RequestBody FeePaymentRequest request) {

    FeePayments feePayments = new FeePayments();
    feePayments.setFeeType(request.getFeeType());
    feePayments.setAmount(request.getAmount());
    feePayments.setTransactionId(request.getTransactionId());
    feePayments.setStudent(repository.findById(request.getStudentId()).orElse(null));
    feePayments.setPaymentStatus(request.getPaymentStatus());
    studentservice.payFeePayments(feePayments);
    return  ResponseEntity.ok("Fee Payment Success");
}

@GetMapping("viewAllPayments")
public ResponseEntity<?> viewPayments(@RequestParam int studentId) {
    List<FeePayments> payments = studentservice.viewFeePayments(studentId);
    if(!payments.isEmpty()){
        return ResponseEntity.ok(payments);
    }
    return ResponseEntity.notFound().build();
}

}
