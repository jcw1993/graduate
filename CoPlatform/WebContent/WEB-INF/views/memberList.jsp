<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true"/>

<body>
<jsp:include page="navi.jsp" flush="true" />

<div class="container-body">
<table>
<tr>
	<th>Id</th>
	<th>用户名</th>
	<th>公司Id</th>
	<th>工号</th>
	<th>微信号</th>
	<th>qq号</th>
	<th>手机</th>
</tr>
<c:forEach items="${model.members}" var="member">
<tr>
	<td>${member.id}</td>
	<td>${member.name}</td>
	<td>${member.company.id}</td>
	<td>${member.workId}</td>
	<td>${member.wxNumber}</td>
	<td>${member.qqNumber}</td>
	<td>${member.phone}</td>
</tr>
</c:forEach>
</table>
</div>
<script type="text/javascipt">
	$(function() {
		setCurrentNavItem(0);
	});
</script>
</body>
</html>