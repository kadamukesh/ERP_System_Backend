package com.demo.klef.jfsd.springboot.controller;


import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.klef.jfsd.springboot.extra.StudentRequest;
import com.demo.klef.jfsd.springboot.model.Feedback;
import com.demo.klef.jfsd.springboot.model.Student;
import com.demo.klef.jfsd.springboot.service.StudentService;

@Controller
@ResponseBody
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

public class StudentController 
{

	@Autowired
	private StudentService studentservice;
	
	 
	

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

	
}
