package edu.nju.software.dao;

import edu.nju.software.pojo.Project;

public interface ProjectDao {
	public Project getById(int id);
	
	public int create(Project project);
	
	public void update(Project project);
	
	public void delete(int id);
}
