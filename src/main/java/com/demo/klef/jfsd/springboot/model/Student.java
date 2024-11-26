package com.demo.klef.jfsd.springboot.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_table")
public class Student
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) //you can take this manually through form

  @Column(name = "sid")
  private int id;
  @Column(name = "sname",nullable = false,length = 50)
  private String name;
  @Column(name = "sgender",nullable = false,length = 10)
  private String gender;
  @Column(name = "sage",nullable = false)
  private double age;
  @Column(name = "sdepartment",nullable = false,length = 30)
  private String department;
  @Column(name = "semail",nullable = false,length = 50,unique = true)
  private String email;
  @Column(name = "scontact",nullable = false,length = 20,unique = true)
  private String contact;
  @Column(name="sdob",nullable=false,length = 20)
  private String dateofbirth;

  @Column(name = "sadmissiontype",nullable = false,length = 30)
  private String AdmissionType ;

  @Column(name = "spassword", nullable = false)
  private String password;

  @Column(name = "saddress", nullable = false, length = 250)
  private String address;
  @Lob
  @Column(name = "photo", columnDefinition = "LONGBLOB")
  private byte[] image;
  @Column(name = "suid",nullable = false,length = 30)
  private String uid;
  @Column(name = "new_password", nullable = true)
  private String newPassword; 
  

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

public String getAdmissionType() {
	return AdmissionType;
}
public void setAdmissionType(String admissionType) {
	AdmissionType = admissionType;
}

public byte[] getImage() {
	return image;
}
public void setImage(byte[] image) {
	this.image = image;
}
public String getUid() {
	return uid;
}
public void setUid(String uid) {
	this.uid = uid;
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
public String getNewPassword() {
	return newPassword;
}
public void setNewPassword(String newPassword) {
	this.newPassword = newPassword;
}




}
