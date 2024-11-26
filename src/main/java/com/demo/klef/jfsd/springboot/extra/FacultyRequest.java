package com.demo.klef.jfsd.springboot.extra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyRequest 
{
private String eid;
private String password;
public String getEid() {
	return eid;
}
public void setEid(String eid) {
	this.eid = eid;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
}
