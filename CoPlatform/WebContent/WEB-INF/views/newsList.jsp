<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true" />

<body>
	<jsp:include page="navi.jsp" flush="true" />

	<div class="container-body">
		<h3>资讯列表<a id="newsManage" href="EditNews?companyId=${model.admin.company.id}" class="btn btn-primary create-button">管理</a></h3>
		<hr />
		<table
			class="table table-striped table-bordered table-hover table-responsive">
			<tr>
				<th>标题</th>
				<th>创建时间</th>
			</tr>
			<c:forEach items="${model.newsList}" var="news">
				<tr>
					<td><a href="NewsDetail?newsId=${news.id}" target="_blank">${news.title}</a></td>
					<td>${news.createdTime}</td>
				</tr>
			</c:forEach>
		</table>

	</div>

<script type="text/javascript">

</script>
</body>
</html>

<jsp:include page="footer.jsp"></jsp:include>