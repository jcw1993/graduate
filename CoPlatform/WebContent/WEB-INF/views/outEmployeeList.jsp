<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true"/>

<body>
<jsp:include page="navi.jsp" flush="true" />

<div class="container-body">
	<h3>外聘人员列表</h3>
	<hr />
<table class="table table-striped table-bordered table-hover table-responsive">
<tr>
	<th>姓名</th>
	<th>微信号</th>
	<th>qq号</th>
	<th>手机</th>
</tr>
<c:forEach items="${model.outEmployees}" var="outEmployee">
<tr>
	<td><a class="memberInfo" href="#" outEmployeeId="${outEmployee.id}">${outEmployee.name}</a></td>
	<td>${outEmployee.wxNumber}</td>
	<td>${outEmployee.qqNumber}</td>
	<td>${outEmployee.phone}</td>
</tr>
</c:forEach>
</table>
</div>

<script type="text/javascript">

</script>
</body>
</html>