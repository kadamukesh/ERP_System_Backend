package com.demo.klef.jfsd.springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "student_course_registration")
public class StudentCourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "faculty_course_mapping_id", nullable = false)
    private int facultyCourseMappingId;

    @Column(name = "student_id", nullable = false)
    private int studentId;

    @Column(name = "mapping_id")
    private Integer mappingId;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFacultyCourseMappingId() {
        return facultyCourseMappingId;
    }

    public void setFacultyCourseMappingId(int facultyCourseMappingId) {
        this.facultyCourseMappingId = facultyCourseMappingId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Integer getMappingId() {
        return mappingId;
    }

    public void setMappingId(Integer mappingId) {
        this.mappingId = mappingId;
    }
}

