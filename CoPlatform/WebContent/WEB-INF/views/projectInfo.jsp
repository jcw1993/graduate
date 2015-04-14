<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<form id="projectEditForm">
	<c:set value="${model.project}" var="project" />
	<div>
		<input name="id" type="hidden" value="${project.id}"/>
		<input name="companyId" type="hidden" value="${project.company.id}"/>
	</div>
	<div>
		<label>项目名称</label>
		<input name="name" type="text" value="${project.name}"/>
	</div>
	<div>
		<label>项目描述</label>
		<input name="desc" type="text" value="${project.desc}"/>
	</div>
	<div>
		<label>公司Id</label>
		<input name="companyId" type="text" value="${project.company.id}"/>
	</div>
	<div>
		<label>开始时间</label>
		<input name="startTime" type="text" value="${project.startTime}"/>
	<div>
		<label>结束时间</label>
		<input name="endTime" type="text" value="${project.endTime}"/>
	</div>
	<div>
		<label>当前进度</label>
		<input name="progress" type="text" value="${project.progress}"/>
	</div>
</form>