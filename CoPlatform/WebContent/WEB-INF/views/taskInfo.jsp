<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<form id="taskEditForm">
	<table class="table table-responsive">
		<c:set value="${model.task}" var="task" />
		<tr>
			<td><input name="taskId" type="hidden" value="${task.id}"/></td>
			<td><input name="projectId" type="hidden" value="${task.project.id}"/></td>
		</tr>
		<tr>
			<td><label>任务名称</label></td>
			<td><input name="name" type="text" value="${task.name}"/></td>
		</tr>
		<tr>
			<td><label>任务描述</label></td>
			<td><input name="description" type="text" value="${task.description}"/></td>
		</tr>
		<tr>
			<td><label>任务Id</label></td>
			<td><label>${task.project.id}</label></td>
		</tr>
		<tr>
			<td><label>开始时间</label></td>
			<td><input name="startTime" type="text" value="${task.startTime}"/></td>
		</tr>
		<tr>
			<td><label>结束时间</label></td>
			<td><input name="endTime" type="text" value="${task.endTime}"/></td>
		</tr>
		<tr>
			<td><label>当前状态</label></td>
			<td><input name="status" type="text" value="${task.status.id}"/></td>
		</tr>
	</table>
</form>
