package edu.nju.software.pojo;

import java.util.Set;

public class Company {
	private int id;
	private String name;
	private String desc;
	private String phone;
	private String address;
	private Set<OutEmployee> outEmployees;
	
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Set<OutEmployee> getOutEmployees() {
		return outEmployees;
	}
	public void setOutEmployees(Set<OutEmployee> outEmployees) {
		this.outEmployees = outEmployees;
	}
	
	public Company() {}
	
	public Company(int id) {
		this.id = id;
	}

}
