<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="header.jsp" flush="true"/>

<body>
<jsp:include page="navi.jsp" flush="true" />

<div class="container-body">
	<h3>工作内容</h3>
	<hr />

<div class="container-fluid">
<c:forEach items="${model.works}" var="work">
<c:set value="${work.key}" var="project" />
<c:set value="${work.value}" var="tasks" />

<div class="row">
<a class="projectInfo btn" projectId="${project.id}">${project.name}</a>
<a class="btn" data-toggle="collapse" href="#projectTaskArea${project.id}" aria-expanded="false" aria-controls="collapseExample">展开/收起</a>
<div id="projectTaskArea${project.id}" class="collapse">
	<c:if test="${tasks != null}">
		<table class="table table-striped table-bordered table-hover table-responsive">
			<tr>
				<th>任务名称</th>
				<th>任务列表</th>
				<th>项目Id</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>当前状态</th>
			</tr>
			<c:forEach items="${tasks}" var="task">
			<tr>
				<td><a class="taskInfo" href="#" taskId="${task.id}">${task.name}</a></td>
				<td>${task.desc}</td>
				<td>${task.project.id}</td>
				<td><fmt:formatDate value="${task.startTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><fmt:formatDate value="${task.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>${task.status.id}</td>
			</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${tasks == null}"><p>暂无任务</p></c:if>
</div>
</div>
</c:forEach>
</div>
</div>

<div id="projectEditModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">项目信息</h4>
      </div>
      <div id="projectEditContent" class="modal-body">
      </div>
      <div class="modal-footer">
        <button id="projectEditSubmit" type="button" class="btn btn-primary" data-dismiss="modal">提交</button>
      </div>
    </div>
  </div>
</div>

<div id="taskEditModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">任务信息</h4>
      </div>
      <div id="taskEditContent" class="modal-body">
      </div>
      <div class="modal-footer">
        <button id="taskEditSubmit" type="button" class="btn btn-primary" data-dismiss="modal">提交</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
	var $projectInfoLink = $(".projectInfo");
	var $projectEditModal = $("#projectEditModal");
	var $projectEditContent = $("#projectEditContent");
	var $projectEditSubmit = $("#projectEditSubmit");

	var $taskInfoLink = $(".taskInfo");
	var $taskEditModal = $("#taskEditModal");
	var $taskEditContent = $("#taskEditContent");
	var $taskEditSubmit = $("#taskEditSubmit");

	$projectInfoLink.click(function(e) {
		var projectId = $(this).attr("projectId");
		console.log("get project info, projectId: " + projectId);
		$projectEditContent.load("GetProjectInfo?projectId=" + projectId + "&companyId=" + ${currentAdmin.company.id}, function(response, status, xhr) {
			if(status == "error") {
				$projectEditContent.load("Error");
			}
		});
		$projectEditModal.modal();
	});

	$projectEditSubmit.click(function(e) {
		console.log("submit project info");
		var formData = $("#projectEditForm").serialize();
		$.ajax({
			url: "UpdateProject",
			data: formData,
			method: "post",
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("success");
					location.reload();
				}else {
					console.log("edit project info error, error code : " + result.resultCode + ";error message: " + result.message);
				}
			} 
		});
	});


	$taskInfoLink.click(function(e) {
		var taskId = $(this).attr("taskId");
		// var projectId = $(this).;
		var projectId = 0;
		console.log("get task info, taskId: " + taskId + ", projectId: " + projectId);
		$taskEditContent.load("GetTaskInfo?taskId=" + taskId + "&projectId=" + projectId, function(response, status, xhr) {
			if(status == "error") {
				$taskEditContent.load("Error");
			}
		});
		$taskEditModal.modal();
	});

	$taskEditSubmit.click(function(e) {
		console.log("submit task info");
		var formData = $("#taskEditForm").serialize();
		$.ajax({
			url: "UpdateTask",
			data: formData,
			method: "post",
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("success");
					location.reload();
				}else {
					console.log("edit task info error, error code : " + result.resultCode + ";error message: " + result.message);
				}
			} 
		});
	});
</script>
</body>
</html>