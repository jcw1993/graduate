<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<jsp:include page="header.jsp" flush="true" />

<body>
	
<div class="container-body">
	<h3>项目列表<a id="projectCreate" href="#" class="btn btn-primary create-button" style="margin-right:50px;">创建项目</a></h3>
	<hr />
	<div class="container-fluid">
	<c:forEach items="${model.projects}" var="project">
		<div class="bottom-line project-item">
			<div class="row">
				<div class="col-xs-6">
					<a class="projectInfo btn" projectId="${project.id}">${project.name}</a>
				</div>
				<div style="float:right">
				<div class="col-xs-4">
					<a class="projectDelete btn btn-default btn-sm" projectId="${project.id}">删除项目</a> 
				</div>	
				<div class="col-xs-2">
					<a 
					class="btn btn-info btn-sm taskListBtn" data-toggle="collapse"
					href="#projectTaskArea${project.id}" aria-expanded="false"
					aria-controls="collapseExample" projectId="${project.id}" style="margin-left:10px;">查看任务</a>
				</div>
				</div>					
			</div>
		 	<div id="projectTaskArea${project.id}" class="collapse taskList">
						
			</div>
		</div>
	</c:forEach>
	</div>
	</div>
</div>
<div id="tree"></div>
<script type="text/javascript">

var $taskListBtn = $(".taskListBtn");

var projectTaskMap = new Map();

$taskListBtn.click(function(e) {
	var projectId = $(this).attr("projectId");
	console.log("see project tasks, projectId:" + projectId);
	if(!projectTaskMap.get(projectId)) {
		loadData(projectId);
	}

	if($(this).text() == "查看任务") {
		$(this).text("隐藏任务");
	}else {
		$(this).text("查看任务");
	}
});

function loadData(projectId) {
	$.ajax({
		url: "GetTaskTree?projectId=" + projectId,
		success: function(result) {
			if(result.resultCode == 0) {
				console.log("load data success");
				var taskList = result.data;
				console.log(taskList);
				var taskTree = buildTree(taskList);
				projectTaskMap.set(projectId, taskTree);
				var viewData = convertToNodes(taskTree, taskList);
				console.log(JSON.stringify(viewData));
				$("#projectTaskArea" + projectId).treeview({
					data: viewData,
					enableLinks: true,
					color: "#428bca",
				    icon: "glyphicon glyphicon-stop",
				    // expandIcon: 'glyphicon glyphicon-chevron-right',
				    // collapseIcon: 'glyphicon glyphicon-chevron-down',
					// nodeIcon: 'glyphicon glyphicon-bookmark',
					levels: 5});
			}else {
				console.log("error message: " + result.message);
				console.log("load data fail");
			}
		}
	});
}

function buildTree(taskList) {
	var taskTree = new Map();

	taskList.forEach(function(task) {
		if(task.parentId == 0) {
			taskTree.set(task.id, new Map());
		}else {
			console.log("parent task: " + task.parentId);
			console.log("child task: " + task.id + ", name: " + task.name);
			var parentTask = findNode(taskTree, task.parentId);
			console.log(parentTask == null);
			if(parentTask != undefined) {
				parentTask.set(task.id, new Map());
			}
		}
	});
	console.log(taskTree);
	return taskTree;
}

function findNode(taskTree, id) {
	console.log(taskTree);
	if(taskTree == null || taskTree == undefined || !id) {
		return null;
	}
	
	console.log(taskTree.get(id));
	// if(taskTree.get(id) != undefined) {
	// 	return taskTree.get(id);
	// }else {
	// 	var result;
	// 	taskTree.forEach(function(value, key) {
	// 		console.log(value);
	// 		result = findNode(value, id);
	// 		if(result != undefined) {
	// 			console.log("result:");
	// 			console.log(result);
	// 			return true;
	// 		} 
	// 	});
	// 	return result;
	// }
	var result;
	if(taskTree.get(id) != undefined) {
		result = taskTree.get(id);
	}else {
		var subResult;
		taskTree.forEach(function(value, key) {
			subResult = findNode(value, id);
			if(subResult != undefined) {
				result = subResult;
				return false;
			} 
		});
	}
	return result;
}


function getTask(taskList, id) {
	var result;
	taskList.forEach(function(task) {
		if(task.id == id) {
			result = task;
			return;
		}
	});
	return result;
}

function convertToNodes(taskTree, taskList) {
	if(taskTree == null || taskTree == undefined || taskTree.size == 0) {
		console.log("taskTree null");
		return null;
	}
	var data = [];
	console.log(taskTree.size);
	taskTree.forEach(function(value, key) {
		var taskId = key;
		var task = getTask(taskList, taskId);
		var taskNode = {};
		
		taskNode["text"] = task.name;
		taskNode["href"] = "GetTaskInfo?taskId=" + taskId;
		if(value) {
			var subData = convertToNodes(value, taskList);
			if(subData) {
				taskNode["nodes"] = subData;
			}
		}else {
			console.log("value null");
		}
		data.push(taskNode);
	});
	console.log(data);
	return data;
}

</script>

</body>