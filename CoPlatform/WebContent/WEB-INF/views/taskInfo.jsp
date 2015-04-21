<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<form id="taskEditForm">
	<table class="table table-responsive">
		<c:set value="${model.task}" var="task" />
		<tr>
			<td><c:if test="${task.id != null && task.id != 0}">
					<input name="taskId" type="hidden" value="${task.id}" />
				</c:if></td>
			<td><input name="projectId" type="hidden"
				value="${task.project.id}" /></td>
		</tr>
		<tr>
			<td><label>任务名称</label></td>
			<td><input id="taskName" name="name" type="text" value="${task.name}" /></td>
		</tr>
		<tr>
			<td><label>任务描述</label></td>
			<td><input id="taskDescription" name="description" type="text"
				value="${task.description}" /></td>
		</tr>
		<tr>
			<td><label>项目Id</label></td>
			<td><label>${task.project.id}</label></td>
		</tr>
		<tr>
			<td><label>开始时间</label></td>
			<td><input id="taskStartDate" name="startDate" type="text"
				placeholder="yyyy-MM-dd"
				value="<fmt:formatDate value='${task.startTime}' pattern='yyyy-MM-dd' />" />
				<input id="taskStartTime" name="startTime" type="text"
				placeholder="HH:mm:ss"
				value="<fmt:formatDate value='${task.startTime}' pattern='HH:mm:ss' />" />
			</td>
		<tr>
			<td><label>结束时间</label></td>
			<td><input id="taskEndDate" name="endDate" type="text"
				placeholder="yyyy-MM-dd"
				value="<fmt:formatDate value='${task.endTime}' pattern='yyyy-MM-dd' />" />
				<input id="taskEndTime" name="endTime" type="text"
				placeholder="HH:mm:ss"
				value="<fmt:formatDate value='${task.endTime}' pattern='HH:mm:ss' />" />
			</td>
		</tr>
		<tr>
			<td><label>当前状态</label></td>
			<td><input id="taskStatus" name="status" type="text" value="${task.status.id}" /></td>
		</tr>
	</table>
</form>

<script type="text/javascript">
	$taskStartDatePicker = $("#taskStartDate");
	$taskStartTimePicker = $("#taskStartTime");
	$taskEndDatePicker = $("#taskEndDate");
	$taskEndTimePicker = $("#taskEndTime");

	$taskStartDatePicker.datepicker();
	$taskStartTimePicker.timepicker({ "timeFormat": "H:i:s" });
	$taskEndDatePicker.datepicker();
	$taskEndTimePicker.timepicker({ "timeFormat": "H:i:s" });		
</script>