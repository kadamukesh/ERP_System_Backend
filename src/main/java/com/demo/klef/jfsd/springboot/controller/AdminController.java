package com.demo.klef.jfsd.springboot.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.demo.klef.jfsd.springboot.extra.LoginRequest;
import com.demo.klef.jfsd.springboot.model.Admin;
import com.demo.klef.jfsd.springboot.model.Course;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.FacultyCourseMapping;
import com.demo.klef.jfsd.springboot.model.FacultyLeave;
import com.demo.klef.jfsd.springboot.model.Student;
import com.demo.klef.jfsd.springboot.service.AdminService;

@Controller

 // 
//frontend is running on port 3000
@CrossOrigin
@ResponseBody
public class AdminController {

	
	
    @Autowired
    private AdminService adminService;
  
	
  // admin login
    @PostMapping("checkadminLogin")
    public ResponseEntity<?> checkAdminLogin(@RequestBody LoginRequest request) {
        Admin admin = adminService.checkAdminLogin(request);
        if (admin != null) 
        {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("role", "admin");  // Include the role
            return ResponseEntity.ok(response); // Return the response with role
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Login Details");
        }
    }

 
    //display students 
    @GetMapping("viewstudent")
    public ResponseEntity<List<Student>> display() {
        List<Student> students = adminService.viewall();
        return ResponseEntity.ok(students);
    }
    
    @PutMapping("updatestudent")
    public String update(@RequestBody	Student s)
    {
    	return adminService.updatestudent(s);
    }
    
    @DeleteMapping("deletestudent/{id}")
    public String delete(@PathVariable("id") String uid)
    {
    	return adminService.deletestudent(uid);
    }
    
    @DeleteMapping("deletefaculty/{id}")
    public String deleteF(@PathVariable("id") String eid)
    {
        return adminService.deleteFaculty(eid);
    }
    
    //addstudent
    @PostMapping("addstudent")
    public ResponseEntity<?> addStudent(
        @RequestPart("student") Student student, 
        @RequestPart("photo") MultipartFile photo
    ) {
    	   
    	try {
    	   
    		
    	    // Check if the UID exists in Student or Faculty records
            if (adminService.findByUid(student.getUid()) || adminService.findByEid(student.getUid())) {
    	        return ResponseEntity.status(HttpStatus.CONFLICT).body("ID already exists in Student or Faculty records");
    	    }

    	    // Set the student's image
    	    student.setImage(photo.getBytes());

    	    // Save the student
    	    adminService.addstudent(student);

    	    return ResponseEntity.ok("Student Added Successfully");
    	} catch (Exception e) {
    	    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add student: " + e.getMessage());
    	}

    }
    @PostMapping("addfaculty")
    public ResponseEntity<?> addFaculty(
        @RequestParam("EId") String eid,
        @RequestParam("name") String name,
        @RequestParam("gender") String gender,
        @RequestParam("age") int age,
        @RequestParam("department") String department,
        @RequestParam("email") String email,
        @RequestParam("contact") String contact,
        @RequestParam("dateofbirth") String dateOfBirth,
        @RequestParam("maritalStatus") String maritalStatus,
        @RequestParam("designation") String designation,
        @RequestParam("password") String password,
        @RequestParam("address") String address,
        @RequestParam("photo") MultipartFile photo) {

        try {
            Faculty faculty = new Faculty();
            faculty.setEid(eid);
            faculty.setName(name);
            faculty.setGender(gender);
            faculty.setAge(age);
            faculty.setDepartment(department);
            faculty.setEmail(email);
            faculty.setContact(contact);
            faculty.setDateofbirth(dateOfBirth);
            faculty.setMaritalStatus(maritalStatus);
            faculty.setDesignation(designation);
            faculty.setPassword(password);
            faculty.setAddress(address);
            faculty.setImage(photo.getBytes());

            if (adminService.findByEid(eid)||adminService.findByUid(eid)) {
    	        return ResponseEntity.status(HttpStatus.CONFLICT).body("ID already exists in Student or Faculty records");
            }

            adminService.addFaculty(faculty);
            return ResponseEntity.ok("Faculty Added Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add Faculty");
        }
    }

    @GetMapping("viewfaculty")
    public ResponseEntity<List<Faculty>> displayF() {
        List<Faculty> faculty = adminService.viewallF();
        return ResponseEntity.ok(faculty);
    }
    
    
    @PutMapping("updatefaculty")
    public String update(@RequestBody	Faculty f)
    {
    	return adminService.updateFaculty(f);
    }
 
    @GetMapping("viewsbyid/{id}") 
    public ResponseEntity<Student> viewSById(@PathVariable("id") int sid) {
        Student student = adminService.viewSById(sid);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    


   //Course Operations
    
    //view by id

    



//addstudent
@PostMapping("addcourse")
public ResponseEntity<?> addCourse(
@RequestBody Course course    
) {
    try {
        // Check if student exists
        if (adminService.findByCid(course.getCoursecode())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Course Id already exists");
        }
        // Save student
        adminService.addCourse(course);

        return ResponseEntity.ok("Course Added Successfully");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add Course");
    }
}

//display
@GetMapping("viewcourse")
public ResponseEntity<List<Course>> displayC() {
    List<Course> courses = adminService.viewallC();
    return ResponseEntity.ok(courses);
}

@PostMapping("/fcoursemapping")
public ResponseEntity<Map<String, String>> insertFacultyCourseMapping(@RequestBody Map<String, Object> payload) {
    Map<String, String> response = new HashMap<>();
    try {
      
        int fid = Integer.parseInt(payload.get("fid").toString());
        int cid = Integer.parseInt(payload.get("cid").toString());
        int section = Integer.parseInt(payload.get("section").toString());
        String ftype = payload.get("ftype").toString();

        Faculty faculty = adminService.displayFacultyById(fid);
        Course course = adminService.displayCourseById(cid);

        if (faculty == null || course == null) {
            response.put("message", "Invalid Faculty or Course ID.");
            return ResponseEntity.badRequest().body(response);
        }

        long existingMapping = adminService.checkFacultyCourseMapping(faculty, course, section);
        if (existingMapping > 0) {
            response.put("message", "Mapping Already Done");
        } else {
            // Create and save mapping
            FacultyCourseMapping fcm = new FacultyCourseMapping();
            fcm.setFaculty(faculty);
            fcm.setCourse(course);
            fcm.setSection(section);
            fcm.setFacultytype(ftype);

            adminService.facultyCourseMapping(fcm);
            response.put("message", "Faculty Course Mapping Done");
        }

        return ResponseEntity.ok(response);
    } catch (NumberFormatException e) {
        response.put("message", "Invalid input data: " + e.getMessage());
        return ResponseEntity.badRequest().body(response);
    } catch (Exception e) {
        response.put("message", "An error occurred: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}





@GetMapping("vfcmByid/{id}")
public ResponseEntity<?> displayvfcByid(@PathVariable("id") int fid) {
    List<FacultyCourseMapping> fv = adminService.viewCoursesByFacutly(fid);

    if (!fv.isEmpty()) {
        return ResponseEntity.ok(fv);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
    

//Leave

@PostMapping("/faculty/leave/apply")
public ResponseEntity<String> applyLeave(@RequestBody Map<String, Object> leaveDetails) {
    try {
      if (!leaveDetails.containsKey("facultyId")) {
            return ResponseEntity.badRequest().body("Faculty ID is required.");
        }
        // Extracting input data from the request body
        int facultyId = Integer.parseInt(leaveDetails.get("facultyId").toString());
        String startDateStr = leaveDetails.get("startDate").toString();
        String endDateStr = leaveDetails.get("endDate").toString();
        String reason = leaveDetails.get("reason").toString();

        // Parsing date strings to java.util.Date
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);

        // Validating and creating a FacultyLeave object
        FacultyLeave leave = new FacultyLeave();
        leave.setFaculty(adminService.viewFById(facultyId));
        leave.setStartDate(startDate);
        leave.setEndDate(endDate);
        leave.setReason(reason);
        leave.setStatus("Pending"); // Default status when applying leave

        // Save the leave request using the service
        String result = adminService.applyLeave(leave);

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
@GetMapping("/faculty/appliedforleave")
  public ResponseEntity<List<FacultyLeave>> getAllLeaves() {
      List<FacultyLeave> leaves = adminService.displayAllLeaves();
      return ResponseEntity.ok(leaves);
  }


// Updating leave status for admin
@PutMapping("/admin/update/{leaveId}")
  public ResponseEntity<FacultyLeave> updateLeaveStatus(@PathVariable int leaveId, @RequestParam String status) {
      FacultyLeave updatedLeave = adminService.updateStatusLeave(leaveId, status);
      return ResponseEntity.ok(updatedLeave);
  }


@GetMapping("/faculty/{facultyId}")
  public ResponseEntity<List<FacultyLeave>> getLeavesByFaculty(@PathVariable int facultyId) {
      List<FacultyLeave> leaves = adminService.viewStatusByFaculty(facultyId);
      if(!leaves.isEmpty())
      {
        return ResponseEntity.ok(leaves);          
      }
      else
      {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }
  }



}
