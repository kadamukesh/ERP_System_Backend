package com.demo.klef.jfsd.springboot.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="facultycoursemapping_table")
public class FacultyCourseMapping 
{
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private int mappingid;
	  
	  @ManyToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "faculty_id") // foreign key column name
	  private Faculty faculty;
	  
	  @ManyToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name = "course_id") // foreign key column name
	  private Course course;
	  	
	  @Column(nullable = false)
	  private int section;
	  
	  @Column(nullable = false)
	  private String facultytype;

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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public String getFacultytype() {
		return facultytype;
	}

	public void setFacultytype(String facultytype) {
		this.facultytype = facultytype;
	}
}
