package com.demo.klef.jfsd.springboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "faculty_table")
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Use this only if you don't take ID from the form
    @Column(name = "fid")
    private int id;

    @Column(name = "sname", nullable = false, length = 50)
    private String name;

    @Column(name = "sgender", nullable = false, length = 10)
    private String gender;

    @Column(name = "sage", nullable = false)
    private double age;

    @Column(name = "sdepartment", nullable = false, length = 30)
    private String department;

    @Column(name = "fdesignation", nullable = false, length = 30)
    private String designation;

    @Column(name = "fmaritalStatus", nullable = false, length = 10)
    private String maritalStatus;

    @Column(name = "semail", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "scontact", nullable = false, length = 20, unique = true)
    private String contact;

    @Column(name = "sdob", nullable = false, length = 20)
    private String dateofbirth;

    @Column(name = "spassword", nullable = false)
    private String password;

    @Column(name = "saddress", nullable = false, length = 250)
    private String address;

    @Lob
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name = "feid", nullable = false, unique = true)
    private String eid; // Renamed for clarity
    
    @Column(name = "new_password", nullable = true)
    private String newPassword; // Add newPassword field

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getEid() { // Getter renamed to match the new variable name
        return eid;
    }

    public void setEid(String eid) { // Setter renamed to match the new variable name
        this.eid = eid;
    }

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}


}
