package com.demo.klef.jfsd.springboot.model;

import jakarta.persistence.*;


@Entity

public class CourseContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fcm_id")
    private FacultyCourseMapping facultyCourseMapping;
    
    
    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FacultyCourseMapping getFacultyCourseMapping() {
		return facultyCourseMapping;
	}

	public void setFacultyCourseMapping(FacultyCourseMapping facultyCourseMapping) {
		this.facultyCourseMapping = facultyCourseMapping;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}