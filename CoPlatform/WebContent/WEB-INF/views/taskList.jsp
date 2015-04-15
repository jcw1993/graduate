<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="table table-striped table-bordered table-hover table-responsive">
	<tr>
		<th>任务名称</th>
		<th>任务列表</th>
		<th>项目Id</th>
		<th>开始时间</th>
		<th>结束时间</th>
		<th>当前状态</th>
	</tr>
	<c:forEach items="${model.tasks}" var="task">
	<tr>
		<td>${task.name}</td>
		<td>${task.description}</td>
		<td>${task.project.id}</td>
		<td><fmt:formatDate value="${task.startTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<td><fmt:formatDate value="${task.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		<td>${task.status.id}</td>
	</tr>
	</c:forEach>
</table>