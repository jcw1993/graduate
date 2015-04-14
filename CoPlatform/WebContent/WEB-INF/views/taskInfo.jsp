<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<form id="taskEditForm">
	<c:set value="${model.task}" var="task" />
	<div>
		<input name="taskId" type="hidden" value="${task.id}"/>
		<input name="projectId" type="hidden" value="${task.project.id}"/>
	</div>
	<div>
		<label>任务名称</label>
		<input name="name" type="text" value="${task.name}"/>
	</div>
	<div>
		<label>任务描述</label>
		<input name="desc" type="text" value="${task.desc}"/>
	</div>
	<div>
		<label>任务Id</label>
		<input name="projectId" type="text" value="${task.project.id}"/>
	</div>
	<div>
		<label>开始时间</label>
		<input name="startTime" type="text" value="${task.startTime}"/>
	</div>
	<div>
		<label>结束时间</label>
		<input name="endTime" type="text" value="${task.endTime}"/>
	</div>
	<div>
		<label>当前状态</label>
		<input name="status" type="text" value="${task.status.id}"/>
	</div>
</form>