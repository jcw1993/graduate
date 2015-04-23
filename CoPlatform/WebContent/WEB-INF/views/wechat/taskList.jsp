<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> -->

<jsp:include page="header.jsp" flush="true" />

<body>
	
<div class="container-body">
<c:if test="${model.wxtasks != null}">
	<table
		class="table table-striped table-bordered table-hover table-responsive">
		<tr>
			<th>任务名称</th>
			<th>任务列表</th>
			<th>所属项目</th>
			<th>开始时间</th>
			<th>结束时间</th>
			<th>当前状态</th>
		</tr>
		<c:forEach items="${model.wxtasks}" var="task">
			<tr>
			<% String path = request.getContextPath()+"/wechat/TaskInfo?taskId=";%> 
				<td><a href="<%=path %>${task.id}">${task.name}</a></td>
				<td>${task.description}</td>
				<td>${task.project.name}</td>
				<td><fmt:formatDate value="${task.startTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><fmt:formatDate value="${task.endTime}"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>${task.status.id}</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${model.wxtasks == null}">
	<h3>暂无任务</h3>
</c:if>
</div>
</body>