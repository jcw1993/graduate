<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true" />

<body>
	<%-- <jsp:include page="navi.jsp" flush="true" /> --%>
	
<div class="container-body">


			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">任务信息</h4>
				</div>
				



<form id="taskEditForm">
	<table class="table table-responsive">
		<c:set value="${model.wxtask}" var="task" />
		<tr>
			<td><c:if test="${task.id != null && task.id != 0}">
					<input name="taskId" type="hidden" value="${task.id}" />
				</c:if></td>
			<td><input name="projectId" type="hidden"
				value="${task.project.id}" /></td>
		</tr>
		<tr>
			<td><label>任务名称</label></td>
			<td><input name="name" type="text" value="${task.name}" style="border:0px"/></td>
		</tr>
		<tr>
			<td><label>任务描述</label></td>
			<td><input name="description" type="text"
				value="${task.description}" style="border:0px"/></td>
		</tr>
		<tr>
			<td><label>任务Id</label></td>
			<td><label>${task.project.id}</label></td>
		</tr>
		<tr>
			<td><label>开始时间</label></td>
			<td><input id="taskStartDate" name="startDate" type="text"
				placeholder="yyyy-MM-dd"
				value="<fmt:formatDate value='${task.startTime}' pattern='yyyy-MM-dd' />" style="border:0px"/>
				<input id="taskStartTime" name="startTime" type="text"
				placeholder="HH:mm:ss"
				value="<fmt:formatDate value='${task.startTime}' pattern='HH:mm:ss' />" style="border:0px"/>
			</td>
		<tr>
			<td><label>结束时间</label></td>
			<td><input id="taskEndDate" name="endDate" type="text"
				placeholder="yyyy-MM-dd"
				value="<fmt:formatDate value='${task.endTime}' pattern='yyyy-MM-dd' />" style="border:0px"/>
				<input id="taskEndTime" name="endTime" type="text"
				placeholder="HH:mm:ss"
				value="<fmt:formatDate value='${task.endTime}' pattern='HH:mm:ss' />" style="border:0px"/>
			</td>
		</tr>
		<tr>
			<td><label>当前状态</label></td>
			<td><input name="status" type="text" value="${task.status.id}" /></td>
		</tr>
	</table>
</form>

				<div class="modal-footer">
					<button id="taskEditSubmit" type="button" class="btn btn-primary"
						data-dismiss="modal">保存</button>
				</div>
			</div>

<script type="text/javascript">
	$taskStartDatePicker = $("#taskStartDate");
	$taskStartTimePicker = $("#taskStartTime");
	$taskEndDatePicker = $("#taskEndDate");
	$taskEndTimePicker = $("#taskEndTime");

	$taskStartDatePicker.datepicker();
	$taskStartTimePicker.timepicker({ "timeFormat": "H:i:s" });
	$taskEndDatePicker.datepicker();
	$taskEndTimePicker.timepicker({ "timeFormat": "H:i:s" });	
	
	var $taskEditSubmit = $("#taskEditSubmit");
	
	$taskEditSubmit.click(function(e) {
		
		var url = "/wechat/updateTask";
		var status = $("#status").val();
		var form = $("#taskEditForm");
		
		console.log("save task")
		var formData = $form.serialize();
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
	});
	
</script>
</div>
</body>