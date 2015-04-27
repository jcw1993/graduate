<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="header.jsp" flush="true" />
<body>
	<jsp:include page="navi.jsp" flush="true" />

	<div class="container-body">
		<h3>任务详情
		<a id="taskDelete" href="#" class="btn btn-danger create-button">删除任务</a>
		<a id="subTaskCreate" href="#" class="btn btn-primary create-button" style="margin-right:20px;">创建子任务</a>
		</h3>
		<hr />
		<form id="taskEditForm">
			<table class="table table-responsive">
				<c:set value="${model.task}" var="task" />
				<tr>
					<td><c:if test="${task.id != null && task.id != 0}">
							<input name="taskId" type="hidden" value="${task.id}" />
						</c:if></td>
					<td><input name="projectId" type="hidden"
						value="${task.projectId}" /></td>
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
					<td><label>所属项目</label></td>
					<td><label>${task.projectId}</label></td>
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
					<td>
						<select id="taskStatus" name="status">
							<option value="1"
								<c:if test="${task.status == 1}">slected="selected"</c:if>
							>未开始</option>
							<option value="2"
								<c:if test="${task.status == 2}">slected="selected"</c:if>>进行中</option>
							<option value="3"
								<c:if test="${task.status == 3}">slected="selected"</c:if>>已完成</option>
							<option value="4"
								<c:if test="${task.status == 4}">slected="selected"</c:if>>已失效</option>
						</select>
					</td>
				</tr>
			</table>
		</form>

		<c:if test="${model.members != null}">
		<h3>相关人员</h3>
		<c:forEach items="${model.members}" var="member">
			${member.name}
		</c:forEach>
		</c:if>

	<h3>任务分配</h3>
	<div id="taskAssignContent">
		<label>类型</label> <select id="employeeTypeSelect">
			<option value="0">公司职员</option>
			<option value="1">外聘人员</option>
		</select> <label>人员</label> <select id="employeeList">
		</select>
	</div>

	<button id="taskAssignSubmit" type="button" class="btn btn-info">确定</button>


	</div>

	
</body>
</html>



<script type="text/javascript">
	var EMPLOYEE_TYPE_MEMBER = 0;
	var EMPLOYEE_TYPE_OUT = 1;

	var $taskStartDatePicker = $("#taskStartDate");
	var $taskStartTimePicker = $("#taskStartTime");
	var $taskEndDatePicker = $("#taskEndDate");
	var $taskEndTimePicker = $("#taskEndTime");

	var $employeeTypeSelect = $("#employeeTypeSelect");
	var $employeeListSelect = $("#employeeList");
	var $taskAssignSubmit = $("#taskAssignSubmit");

	/*variables*/
	var members;
	var outEmployees;
	var currentEmployeeType = EMPLOYEE_TYPE_MEMBER;

	$employeeTypeSelect.change(function(e) {
		var typeIndex = $employeeTypeSelect[0].selectedIndex;
		$employeeListSelect.empty();
		console.log("selectIndex: " + typeIndex);
		if(typeIndex == 0) {
			currentEmployeeType = EMPLOYEE_TYPE_MEMBER;
			appendEmployees(EMPLOYEE_TYPE_MEMBER);
		}else {
			currentEmployeeType = EMPLOYEE_TYPE_OUT;
			appendEmployees(EMPLOYEE_TYPE_OUT);
		}
	});

	$taskAssignSubmit.click(function(e) {
		var employeeIndex = $employeeListSelect[0].selectedIndex;
		var employeeId = $employeeListSelect.children().eq(employeeIndex).val().trim();
		var companyId = "${model.admin.companyId}";
		console.log("xxx");
		assignTaskToEmployee(${model.task.id}, employeeId, currentEmployeeType, companyId);
	});

	/*functions*/
	function loadEmployees(employeeType) {
		var companyId = "${model.admin.companyId}";
		console.log("companyId: " + companyId);
		var url;
		if(employeeType == EMPLOYEE_TYPE_MEMBER) {
			url = "GetMemberList?companyId=" + companyId;
		}else {
			url = "GetOutEmployeeList?companyId=" + companyId;
		}

		$.ajax({
			url: url,
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("get employee success");	
					if(employeeType == EMPLOYEE_TYPE_MEMBER) {
						members = result.data;
						// TODO: shoule be extract to another place
						appendEmployees(EMPLOYEE_TYPE_MEMBER);
					}else {
						outEmployees = result.data;
					}
				}else {
					console.log("get employee error");
				}
			}
		});		
	}

	function appendEmployees(employeeType) {
		if(employeeType == EMPLOYEE_TYPE_MEMBER) {
			members.forEach(function(member){
				$employeeListSelect.append($("<option value='" + member.id + "'>" + member.name + "</option>"));
			}); 
		}else {
			outEmployees.forEach(function(outEmployee){
				$employeeListSelect.append($("<option value='" + outEmployee.id + "'>" + outEmployee.name + "</option>"));
			}); 
		}
	}

	function assignTaskToEmployee(taskId, employeeId, employeeType, companyId) {
		var url;
		if(employeeType == EMPLOYEE_TYPE_MEMBER) {
			url = "MemberTaskAssign?taskId=" + taskId + "&memberId=" + employeeId;
		}else {
			url = "OutEmployeeTaskAssign?taskId=" + taskId + "&outEmployeeId=" + employeeId + "&companyId=" + companyId;
		}

		$.ajax({
			url: url,
			success: function(result) {
				if(result.resultCode == 0) {
					console.log("分配任务成功");
					$taskAssignModal.modal("hide");
				}else {
					alert("失败，任务不能重复分配！");
				}
			}
		});
	}

	loadEmployees(EMPLOYEE_TYPE_MEMBER);
	loadEmployees(EMPLOYEE_TYPE_OUT);

	$taskStartDatePicker.datepicker();
	$taskStartTimePicker.timepicker({ "timeFormat": "H:i:s" });
	$taskEndDatePicker.datepicker();
	$taskEndTimePicker.timepicker({ "timeFormat": "H:i:s" });	

	
</script>