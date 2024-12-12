package com.demo.klef.jfsd.springboot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "counselling_mapping")
public class CounsellingMapping 
{
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int mappingid;
	  
	    @ManyToOne
	    @JoinColumn(name = "faculty_id")
	    private Faculty faculty;
	    

	    @ManyToOne
	    @JoinColumn(name = "student_id")
	    private Student student;


		public int getMappingid() {
			return mappingid;
		}


		public void setMappingid(int mappingid) {
			this.mappingid = mappingid;
		}


		public Faculty getFaculty() {
			return faculty;
		}


		public void setFaculty(Faculty faculty) {
			this.faculty = faculty;
		}


		public Student getStudent() {
			return student;
		}


		public void setStudent(Student student) {
			this.student = student;
		}
}
