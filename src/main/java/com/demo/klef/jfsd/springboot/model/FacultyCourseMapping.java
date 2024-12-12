package com.demo.klef.jfsd.springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name="facultycoursemapping_table")
public class FacultyCourseMapping 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mappingid;
  
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
  
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
  	
    @Column(nullable = false)
    private int section;
  
    @Column(nullable = false)
    private String facultytype;

    @Column(nullable = false)
    private String academicYear;

    @Column(nullable = false)
    private String semester;

    // Getters and setters

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

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}