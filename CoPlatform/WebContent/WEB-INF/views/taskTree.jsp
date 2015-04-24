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

function getTree() {
  // Some logic to retrieve, or generate tree structure
  return [
  {
    text: "Parent 1",
    nodes: [
      {
        text: "Child 1",
        nodes: [
          {
            text: "Grandchild 1"
          },
          {
            text: "Grandchild 2"
          }
        ]
      },
      {
        text: "Child 2"
      }
    ]
  },
  {
    text: "Parent 2",
    href: "http://www.baidu.com"
  },
  {
    text: "Parent 3"
  },
  {
    text: "Parent 4"
  },
  {
    text: "Parent 5"
  }
];
}

$('#tree').treeview({
	data: getTree(),
	enableLinks: true,
	levels: 3});

function buildTextNode(task) {
	return 
}

function Task(projectId, name, description, startTime, endTime, depth, parent) {
	this.projectId = projectId;
	this.name = name;
	this.description = description;
	this.startTime = startTime;
	this.endTime = endTime;
	this.depth = paent;
	this.parent = parent;
}

Task.property.convertToTreeViewData() {
	
}

function loadData() {
	$.ajax({
		url: "",
		success: function(result) {
			if(result.resultCode == 0) {
				console.log("load data success");
				var taskMap = result.data;
				console.log(taskMap);
			}else {
				console.log("load data fail");
			}
		}
	});
}
</script>

</body>