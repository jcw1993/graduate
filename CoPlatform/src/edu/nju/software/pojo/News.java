package edu.nju.software.pojo;

import java.io.Serializable;
import java.util.Date;

/*
 * 资讯
 */
public class News implements Serializable{

	private static final long serialVersionUID = -8115554939230798153L;

    private int id;
    private String title;
    private String content;
    private Date createdTime;
    private Date publishTime;
    
    //getter and setter
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
}
