<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> -->

<jsp:include page="header.jsp" flush="true" />

<body>
	
<div class="container-body-mobile">

<c:if test="${model.taskTree != null}">
aaa
 	<c:forEach items="${model.taskTree}" var="taskRoot">
		sss
		${taskRoot.key.name}
		 	<c:forEach items="${taskRoot.value}" var="taskChild">
		 	asas
				${taskChild.key.name}
		
			</c:forEach>
	</c:forEach>
</c:if>
<c:if test="${model.taskTree == null}">
	<h3>暂无任务</h3>
</c:if>
</div>

<script type="text/javascript">

</script>

</body>