package com.demo.klef.jfsd.springboot.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.demo.klef.jfsd.springboot.extra.FacultyRequest;
import com.demo.klef.jfsd.springboot.model.Course;
import com.demo.klef.jfsd.springboot.model.CourseContent;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.Feedback;
import com.demo.klef.jfsd.springboot.model.Student;
import com.demo.klef.jfsd.springboot.repository.FacultyCourseRepository;
import com.demo.klef.jfsd.springboot.service.FacultyService;

@Controller
@CrossOrigin
public class FacultyController 
{
	@Autowired
	private FacultyService fservice;
	
	 @Autowired
	  private FacultyCourseRepository facultyCourseRepository;
	
	 //login
	@PostMapping("checkfacultyLogin")
	public ResponseEntity<?> checkFacultyLogin(@RequestBody FacultyRequest request) {
	    Faculty faculty = fservice.checkFacultyLogin(request);
	    if (faculty != null) {
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Login successful");
	        response.put("role", "faculty"); 
	        response.put("sname", faculty.getName()); // Include faculty name (sname)
	        
	        response.put("gender", faculty.getGender()); 
	        response.put("age", String.valueOf(faculty.getAge())); 
	        response.put("dept", faculty.getDepartment()); 
	        response.put("desig", faculty.getDesignation()); 
	        response.put("mstatus", faculty.getMaritalStatus()); 
	        response.put("email", faculty.getEmail()); 
	        response.put("contact", faculty.getContact()); 
	        response.put("dob", faculty.getDateofbirth()); 
	        response.put("address", faculty.getAddress()); 
	        response.put("eid", faculty.getEid()); 
	        response.put("fid", String.valueOf(faculty.getId())); // Convert `int` to `String`

	        
	       
 
	        
	        
	        
	      

	        String base64Image = Base64.getEncoder().encodeToString(faculty.getImage());
	        response.put("image", base64Image);				
	        return ResponseEntity.ok(response); 
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Login Details");
	    }
	}
	
	@PutMapping("updatepass")
	public ResponseEntity<String> updatepass(@RequestBody Faculty f) {
	    String result = fservice.ChangePF(f);
	    if ("Password updated successfully".equals(result)) {
	        return ResponseEntity.ok(result);
	    } else {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result); // Send 400 for incorrect password
	    }
	}

	
	//view feedback
	
	
	

    @GetMapping("/{facultyId}/courses")
    public ResponseEntity<?> getFacultyCourses(@PathVariable String facultyId) {
        try {
            int id = Integer.parseInt(facultyId);
            List<Course> courses = fservice.getFacultyCourses(id);
            return ResponseEntity.ok(courses);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid faculty ID format");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{facultyId}/feedback/{courseId}")
    public ResponseEntity<?> getFacultyFeedback(
            @PathVariable String facultyId,
            @PathVariable String courseId) {
        try {
            int fId = Integer.parseInt(facultyId);
            int cId = Integer.parseInt(courseId);
            List<Feedback> feedback = fservice.getFeedbackByFacultyAndCourse(fId, cId);
            return ResponseEntity.ok(feedback);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid ID format");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Error: " + e.getMessage());
        }
    }
    
    //counselling

    @GetMapping("/{facultyId}/assigned-students")
    public ResponseEntity<List<Student>> getAssignedStudents(@PathVariable int facultyId) {
        List<Student> assignedStudents = fservice.getAssignedStudents(facultyId);
        return ResponseEntity.ok(assignedStudents);
    }
    
    //Upload Content
    
    @PostMapping("uploadContent")
    public ResponseEntity<?> uploadContext(@RequestParam MultipartFile file,
                                           @RequestParam int fcmId,
                                           @RequestParam String title) throws IOException {
        System.out.println(file.getOriginalFilename());
        System.out.println(fcmId+" "+ title);
        CourseContent courseContent = new CourseContent();
        courseContent.setTitle(title);
        courseContent.setFacultyCourseMapping(facultyCourseRepository.findById(fcmId).orElse(null));
      
        String data=null;
        if(!file.isEmpty()) {
            String fileType = file.getContentType();
            String fileData = Base64.getEncoder().encodeToString(file.getBytes());
            data = "data:"+fileType +";base64,"+fileData;

        }
        courseContent.setContent(data);
        fservice.uploadCourseContent(courseContent);
        return  ResponseEntity.ok(courseContent);
    }
}
