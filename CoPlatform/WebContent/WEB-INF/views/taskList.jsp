<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> -->

<jsp:include page="header.jsp" flush="true"/>

<body>
<jsp:include page="navi.jsp" flush="true" />

<div class="container-body">
	<h3>任务列表</h3>
	<hr />
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
	<td><a class="taskInfo" href="#" taskId="${task.id}">${task.name}</a></td>
	<td>${task.desc}</td>
	<td>${task.project.id}</td>
	<td>${task.startTime}</td>
	<td>${task.endTime}</td>
	<td>${task.status.id}</td>
</tr>
</c:forEach>
</table>
</div>


<script type="text/javascript">

</script>
</body>
</html>