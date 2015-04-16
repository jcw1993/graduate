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
	<a id="projectCreate" href="#" class="btn btn-primary">创建项目</a>

<c:forEach items="${model.works}" var="work">
<c:set value="${work.key}" var="project" />
<c:set value="${work.value}" var="tasks" />

<div class="row">
<a class="projectInfo btn" projectId="${project.id}">${project.name}</a>
<a class="projectDelete" projectId="${project.id}">删除</a> 
<a class="btn" data-toggle="collapse" href="#projectTaskArea${project.id}" aria-expanded="false" aria-controls="collapseExample">展开/收起</a>
<div id="projectTaskArea${project.id}" class="collapse">
	<a class="taskCreate btn btn-primary" href="#" projectId="${project.id}">创建任务</a>
	<c:if test="${tasks != null}">
		<table class="table table-striped table-bordered table-hover table-responsive">
			<tr>
				<th>任务名称</th>
				<th>任务列表</th>
				<th>项目Id</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>当前状态</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${tasks}" var="task">
			<tr>
				<td class="taskNameTd">
					<a class="taskInfo" href="#" taskId="${task.id}">${task.name}</a>
					<span><a class="taskDelete" href="#"><img src="<c:url value='/resources/images/delete.png' />"></a></span>
				</td>
				<td>${task.description}</td>
				<td>${task.project.id}</td>
				<td><fmt:formatDate value="${task.startTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><fmt:formatDate value="${task.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>${task.status.id}</td>
				<td><a class="taskAssign" href="#" taskId="${task.id}">分配任务</a></td>
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
        <button id="projectEditSubmit" type="button" class="btn btn-primary" data-dismiss="modal">保存</button>
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
        <button id="taskEditSubmit" type="button" class="btn btn-primary" data-dismiss="modal">保存</button>
      </div>
    </div>
  </div>
</div>

<div id="taskAssignModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">任务分配</h4>
      </div>
      <div id="taskAssignContent" class="modal-body">
      	<label>类型</label>
<!--       	<select id="memberList">
      		<option value="member">公司职员</option>
      		<option value="member">外聘人员</option>
      	</select> -->

      	<label>人员</label>
      	<select id="memberList">
      	<c:forEach items="${model.members}" var="member">
      		<option value="${member.id}">${member.name}</option>
      	</c:forEach>
      	</select>
      </div>
      <div class="modal-footer">
        <button id="taskEditSubmit" type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
      </div>
    </div>
  </div>
</div>

<div id="projectCreateModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">创建项目</h4>
      </div>
      <div id="projectCreateContent" class="modal-body">
      </div>
      <div class="modal-footer">
        <button id="projectCreateSubmit" type="button" class="btn btn-primary" data-dismiss="modal">创建</button>
      </div>
    </div>
  </div>
</div>

<div id="taskCreateModal" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">创建任务</h4>
      </div>
      <div id="taskCreateContent" class="modal-body">
      </div>
      <div class="modal-footer">
        <button id="taskCreateSubmit" type="button" class="btn btn-primary" data-dismiss="modal">创建</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
	/*constants*/
	var SAVE_TYPE_CREATE = 0;
	var SAVE_TYPE_UPDATE = 1;

	/*components*/
	var $projectInfoLink = $(".projectInfo");
	var $projectEditModal = $("#projectEditModal");
	var $projectEditContent = $("#projectEditContent");
	var $projectEditSubmit = $("#projectEditSubmit");
	var $projectDeleteBtn = $(".projectDelete");

	var $taskInfoLink = $(".taskInfo");
	var $taskEditModal = $("#taskEditModal");
	var $taskEditContent = $("#taskEditContent");
	var $taskEditSubmit = $("#taskEditSubmit");
	var $taskDeleteBtn = $(".taskDelete");
	var $taskNameTd = $(".taskNameTd");

	var $taskAssignBtn = $(".taskAssign");

	var $taskAssignModal = $("#taskAssignModal");
	var $memberListSelect = $("#memberList");

	var $projectCreateBtn = $("#projectCreate");
	var $projectCreateModal = $("#projectCreateModal");
	var $projectCreateContent = $("#projectCreateContent");
	var $projectCreateSubmit = $("#projectCreateSubmit");

	var $taskCreateBtn = $(".taskCreate");
	var $taskCreateModal = $("#taskCreateModal");
	var $taskCreateContent = $("#taskCreateContent");
	var $taskCreateSubmit = $("#taskCreateSubmit");


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

	$projectDeleteBtn.click(function(e) {
		var projectId = $(this).attr("projectId");
		deleteProject(projectId);
	});

	$projectEditSubmit.click(function(e) {
		console.log("submit project info");
		saveProject($("#projectEditForm"), SAVE_TYPE_UPDATE);
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
		saveTask($("#taskEditForm"), SAVE_TYPE_UPDATE);
	});

	$taskDeleteBtn.click(function(e){
		console.log("delete click");
		var taskId = $(this).parent().prev().attr("taskId");
		deleteTask(taskId);
	});

	$taskNameTd.on("mouseover", function(e) {
		$(this).find("a.taskDelete").show();
	});

	$taskNameTd.on("mouseout", function(e) {
		$(this).find("a.taskDelete").hide();
	});

	$taskAssignBtn.click(function(e) {
		console.log("task assign click");
		var taskId = $(this).attr("taskId");
		console.log("taskId: " + taskId);
		if($memberListSelect.children().length == 0) {
			loadMember();
		}
		$taskAssignModal.modal();
	});

	$projectCreateBtn.click(function(e) {
		console.log("click create project");
		var companyId = "${currentAdmin.company.id}";
		$projectCreateContent.load("CreateProject?companyId=" + companyId, function(response, status, xhr) {
			if(status == "error") {
				$projectEditContent.load("Error");
			}
		});
		$projectCreateModal.modal();
	});

	$projectCreateSubmit.click(function(e) {
		saveProject($("#projectEditForm"), SAVE_TYPE_CREATE);
	});

	$taskCreateBtn.click(function(e) {
		var projectId = $(this).attr("projectId");
		console.log("click task, projectId: " + projectId);
		$taskCreateContent.load("CreateTask?projectId=" + projectId, function(response, status, xhr) {
			if(status == "error") {
				$taskCreateContent.load("Error");
			}
		});
		$taskCreateModal.modal();
	});

	$taskCreateSubmit.click(function(e) {
		saveTask($("#taskEditForm"), SAVE_TYPE_CREATE);
	});

	/*functions*/
	function loadMember() {
		var companyId = "${currentAdmin.company.id}";
		console.log("companyId: " + companyId);
		$.ajax({
			url: "GetMemberList?companyId=" + companyId,
			success: function(result) {
				if(result.resultCode == 0) {
					var members = result.data;
					members.forEach(function(member){
						$memberListSelect.append($("<option value='" + member.id + "'>" + member.name + "</option>"));
					}); 
				}else {
					console.log("get member list error");
				}
			}
		});
	}

	function saveProject($form, saveType) {
		var formData = $form.serialize();
		var url;
		if(saveType == SAVE_TYPE_CREATE) {
			url = "CreateProject";
		}else {
			url = "UpdateProject";
		}

		$.ajax({
			url: url,
			data: formData,
			method: "post",
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("success");
					location.reload();
				}else {
					console.log("save task info error, error code : " + result.resultCode + ";error message: " + result.message);
				}
			} 
		});
	}

	function saveTask($form, saveType) {
		var formData = $form.serialize();
		var url;
		if(saveType == SAVE_TYPE_CREATE) {
			url = "CreateTask";
		}else {
			url = "UpdateTask";
		}

		$.ajax({
			url: url,
			data: formData,
			method: "post",
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("success");
					location.reload();
				}else {
					console.log("save task info error, error code : " + result.resultCode + ";error message: " + result.message);
				}
			} 
		});
	}

	function deleteTask(taskId) {
		$.ajax({
			url: "DeleteTask?taskId=" + taskId,
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("delete task success");
					location.reload();
				}else {
					console.log("delete task error");
					alert("删除失败, 请重试");
				}
			}
		});
	}

	function deleteProject(projectId) {
		$.ajax({
			url: "DeleteProject?projectId=" + projectId,
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("delete project success");
					location.reload();
				}else {
					console.log("delete project fail");
				}
			}
		});
	}


	$taskDeleteBtn.hide();

</script>
</body>
</html>