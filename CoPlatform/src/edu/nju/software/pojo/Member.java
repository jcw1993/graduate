package edu.nju.software.pojo;

public class Member {
	private int id;
	private String name;
	private Company company;
	private String workId;
	private String wxNumber;
	private String qqNumber;
	private String phone;
	
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getWxNumber() {
		return wxNumber;
	}
	public void setWxNumber(String wxNumber) {
		this.wxNumber = wxNumber;
	}
	public String getQqNumber() {
		return qqNumber;
	}
	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Member() {}
	
	public Member(int id) {
		this.id = id;
	}
	
	public Member(String name, Company company, String workId, 
			String qqNumber, String wxNumber, String phone) {
		this.name = name;
		this.company = company;
		this.workId = workId;
		this.qqNumber = qqNumber;
		this.wxNumber = wxNumber;
		this.phone = phone;
	}
	
	public Member(int id, String name, Company company, String workId, 
			String qqNumber, String wxNumber, String phone) {
		this.id = id;
		this.name = name;
		this.company = company;
		this.workId = workId;
		this.qqNumber = qqNumber;
		this.wxNumber = wxNumber;
		this.phone = phone;
	}
}
