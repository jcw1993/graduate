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
<div id="tree"></div>
<script type="text/javascript">

loadData();

function loadData() {
	$.ajax({
		url: "GetTaskTree?projectId=10",
		success: function(result) {
			if(result.resultCode == 0) {
				console.log("load data success");
				var taskList = result.data;
				console.log(taskList);
				var taskTree = buildTree(taskList);
				var viewData = convertToNodes(taskTree, taskList);
				console.log(JSON.stringify(viewData));
				$('#tree').treeview({
					data: viewData,
					enableLinks: true,
					color: "#428bca",
				    iicon: "glyphicon glyphicon-stop",
				    // expandIcon: 'glyphicon glyphicon-chevron-right',
				    // collapseIcon: 'glyphicon glyphicon-chevron-down',
					// nodeIcon: 'glyphicon glyphicon-bookmark',
					levels: 4});
			}else {
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
			var parentTask = findNode(taskTree, task.parentId);
			console.log("parent task: " + task.parentId);
			console.log("child task: " + task.id + ", name: " + task.name);
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
	console.log("find node, taskId: " + id);
	console.log(taskTree);
	if(taskTree == null || taskTree == undefined || !id) {
		return null;
	}
	
	console.log(taskTree.get(id));
	if(taskTree.get(id) != undefined) {
		console.log("retrun ");
		return taskTree.get(id);
	}else {
		var result;
		taskTree.forEach(function(value, key) {
			console.log(value);
			result = findNode(value, id);
			if(result != undefined) {
				console.log("return result");
				return;
			} 
		});
		// console.log("return undefined");
		return result;
	}
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