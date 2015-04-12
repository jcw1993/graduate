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
	<h3>项目列表</h3>
	<hr />
<table class="table table-striped table-bordered table-hover table-responsive">
<tr>
	<th>项目名称</th>
	<th>项目描述</th>
	<th>公司Id</th>
	<th>开始时间</th>
	<th>结束时间</th>
	<th>当前进度</th>
</tr>
<c:forEach items="${model.projects}" var="project">
<tr>
	<td><a class="projectInfo" href="#" memberId="${project.id}">${project.name}</a></td>
	<td>${project.desc}</td>
	<td>${project.company.id}</td>
	<td>${project.startTime}</td>
	<td>${project.endTime}</td>
	<td>${project.progress}</td>
</tr>
</c:forEach>
</table>
</div>


<script type="text/javascript">

</script>
</body>
</html>