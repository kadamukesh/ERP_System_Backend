package com.demo.klef.jfsd.springboot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.klef.jfsd.springboot.extra.FacultyRequest;

import com.demo.klef.jfsd.springboot.model.Faculty;
import com.demo.klef.jfsd.springboot.model.Student;
import com.demo.klef.jfsd.springboot.repository.FacultyRepository;

@Service
public class FacultyServiceImpl implements FacultyService
{
	@Autowired
	private FacultyRepository facultyRepository;

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


	
}
