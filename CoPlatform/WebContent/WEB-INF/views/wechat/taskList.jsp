<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> -->

<jsp:include page="header.jsp" flush="true" />

<body>
	
<div class="container-body-mobile">

<c:if test="${model.tasks != null}">
	<c:forEach items="${model.tasks}" var="task">
	<div class="taskItem" taskId="${task.id}">
		<p>${task.name}<img class="seeDetail" src="<c:url value="/resources/images/arrow-right.png" />" /></p>
	</div>
	</c:forEach>
</c:if>
<c:if test="${model.tasks == null}">
	<h3>暂无任务</h3>
</c:if>
</div>

<script type="text/javascript">
	var $taskItem = $(".taskItem");
	$taskItem.click(function(e) {
		var taskId = $(this).attr("taskId");
		window.location = "TaskInfo?taskId=" + taskId;
	});
</script>

</body>