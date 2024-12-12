package com.demo.klef.jfsd.springboot.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.klef.jfsd.springboot.extra.LoginRequest;
import com.demo.klef.jfsd.springboot.model.Admin;
import com.demo.klef.jfsd.springboot.model.CounsellingMapping;
import com.demo.klef.jfsd.springboot.model.Course;
import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.FacultyCourseMapping;
import com.demo.klef.jfsd.springboot.model.FacultyLeave;
import com.demo.klef.jfsd.springboot.model.Student;
import com.demo.klef.jfsd.springboot.repository.AdminRepository;
import com.demo.klef.jfsd.springboot.repository.CounsellingRepository;
import com.demo.klef.jfsd.springboot.repository.CourseRepository;
import com.demo.klef.jfsd.springboot.repository.FacultyCourseRepository;
import com.demo.klef.jfsd.springboot.repository.FacultyLeaveRepository;
import com.demo.klef.jfsd.springboot.repository.FacultyRepository;
import com.demo.klef.jfsd.springboot.repository.FacultyStudentMappingRepository;
import com.demo.klef.jfsd.springboot.repository.StudentRepository;


@Service
public class AdminServiceImpl implements AdminService {
	
	  @Autowired
	  private AdminRepository adminRepository;
	  @Autowired
	  private StudentRepository studentRepository;
	  @Autowired
	  private FacultyRepository facultyRepository;
	  
	  @Autowired
	  private CourseRepository courseRepository;
	  
	  @Autowired
	  private FacultyCourseRepository facultyCourseRepository;
	  
	  @Autowired
	  private FacultyLeaveRepository facultyLeaveRepository;
	  
	  
	  @Autowired
	  private  CounsellingRepository counsellingRepository;

	  @Autowired
	  private FacultyStudentMappingRepository facultyStudentMappingRepository;
	  
	  //login
	@Override
	public Admin checkAdminLogin(LoginRequest request)
	{
		 Admin admin = adminRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword());
		    return admin;
	}

	//add student using student repo
	@Override
	public String addstudent(Student s) {
		 studentRepository.save(s);
		 return "Student Added Successfully";
	}

	
	@Override
	public List<Student> viewall() {
		return studentRepository.findAll();
	}

	//update
	@Override
	public String updatestudent(Student s) 
	{
		Optional<Student> obj=studentRepository.findByUid(s.getUid());
		if(obj.isPresent())
		{
			Student st=obj.get();
			st.setName(s.getName());
			st.setContact(s.getContact());
			st.setDepartment(s.getDepartment());
			st.setEmail(s.getEmail());
			st.setAdmissionType(s.getAdmissionType());
			studentRepository.save(st);
			return "Student updated";
		}
		else
		{
			return "Student Id Not Found";
		}
		
	}

	@Override
	public String deletestudent(String uid) {
		// TODO Auto-generated method stub
		Optional<Student> obj=studentRepository.findByUid(uid);
		if(obj.isPresent())
		{
			Student st=obj.get();
			
			studentRepository.delete(st);
			return "Student Deleted";
		}
		else
		{
			return "Id Not Found";
		}
		}
	
	@Override
	public String deleteFaculty(String eid) {
		// TODO Auto-generated method stub
				Optional<Faculty> obj=facultyRepository.findByEid(eid);
				if(obj.isPresent())
				{
					Faculty ft=obj.get();
					
					facultyRepository.delete(ft);
					return "Faculty Deleted";
				}
				else
				{
					return "Id Not Found";
				}
	}
	
	@Override
	public boolean findByUid(String uid) {
		// TODO Auto-generated method stub
		return studentRepository.existsByUid(uid);
	}

	


	//Faculty
	@Override
	public String addFaculty(Faculty f) {
		
		facultyRepository.save(f);
		return "Faculty Added";
	}


	@Override
	public List<Faculty> viewallF() {
		// TODO Auto-generated method stub
		return facultyRepository.findAll();
	}

	@Override
	public String updateFaculty(Faculty f) {
		Optional<Faculty> obj=facultyRepository.findByEid(f.getEid());
		if(obj.isPresent())
		{
			Faculty ft=obj.get();
			ft.setName(f.getName());
			ft.setContact(f.getContact());
			ft.setDepartment(f.getDepartment());
			ft.setEmail(f.getEmail());
			ft.setDesignation(f.getDesignation());
			facultyRepository.save(ft);
			return "Faculty updated";
		}
		else
		{
			return "Faculty Id Not Found";
		}
	}
	
	
	@Override
	public Faculty viewFById(int fid) {
		 return facultyRepository.findById(fid)
			        .orElseThrow(() -> new RuntimeException("faculty not found with id: " + fid));
	}


	@Override
	public boolean findByEid(String eid) {
		// TODO Auto-generated method stub
		return facultyRepository.existsByEid(eid);
	}

	@Override
	public Student viewSById(int sid) {
	    return studentRepository.findById(sid)
	        .orElseThrow(() -> new RuntimeException("Student not found with id: " + sid));
	}
	
	
	
	//Course..
	

	@Override
	public String addCourse(Course c) {
		
		courseRepository.save(c);
		return "Course Added Successfully";
	}

	@Override
	public boolean findByCid(String cid) {
		
		return courseRepository.existsByCoursecode(cid);
	}

	@Override
	public List<Course> viewallC() {
		
		return courseRepository.findAll();
	}

	@Override
	public String facultyCourseMapping(FacultyCourseMapping fcm) {
		facultyCourseRepository.save(fcm);
		
		return "Faculty Course Mapping Done";
	}

	@Override
	public List<FacultyCourseMapping> displayFacultyCourseMapping() {
		return (List<FacultyCourseMapping>) facultyCourseRepository.findAll();

	}

	@Override
	public long checkFacultyCourseMapping(Faculty f, Course c, int section, String academicYear, String semester) {
	    return facultyCourseRepository.checkfcoursemapping(f, c, section, academicYear, semester);
	}

	@Override
	public Faculty displayFacultyById(int fid) {
		// TODO Auto-generated method stub
		return facultyRepository.findById(fid).get();
	}

	@Override
	public Course displayCourseById(int cid) {
		// TODO Auto-generated method stub
		return courseRepository.findById(cid).get();
	}



	@Override
	public List<FacultyCourseMapping> viewCoursesByFacutly(int fid) {
		Faculty faculty = facultyRepository.findById(fid).get();
		return facultyCourseRepository.findByFaculty(faculty);
	}

	//Leave
	
	@Override
    public String applyLeave(FacultyLeave facultyLeave) {
        facultyLeave.setStatus("Pending");
         facultyLeaveRepository.save(facultyLeave);
         return "Faculty Leave Sent Successfully";
    }

    @Override
    public List<FacultyLeave> displayAllLeaves() {
        return facultyLeaveRepository.findAll();
    }

  

    @Override
    public FacultyLeave updateStatusLeave(int leaveId, String status) {
        FacultyLeave leaveRequest = facultyLeaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave request not found with id: " + leaveId));
        leaveRequest.setStatus(status);
        return facultyLeaveRepository.save(leaveRequest);
    }
    
    @Override
    public List<FacultyLeave> viewStatusByFaculty(int fid) {
        Faculty faculty = facultyRepository.findById(fid)
                .orElseThrow(() -> new RuntimeException("Faculty not found with id: " + fid));
        return facultyLeaveRepository.findByFaculty(faculty);
    }

	




//COunselling
    
    @Override
    public String mapFacultyToStudent(int facultyId, int studentId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found with id: " + facultyId));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));

        if (counsellingRepository.existsByStudent(student)) {
            return "Mapping already exists for this student";
        }

        CounsellingMapping mapping = new CounsellingMapping();
        mapping.setFaculty(faculty);
        mapping.setStudent(student);
        counsellingRepository.save(mapping);

        return "Faculty mapped to student successfully";
    }


	
}

	


