package com.demo.klef.jfsd.springboot.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.klef.jfsd.springboot.extra.FacultyRequest;
import com.demo.klef.jfsd.springboot.extra.LoginRequest;

import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.service.FacultyService;

@Controller
@CrossOrigin
public class FacultyController 
{
	@Autowired
	private FacultyService fservice;
	
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

}
