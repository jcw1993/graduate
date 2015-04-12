<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true"/>

<body>
<jsp:include page="navi.jsp" flush="true" />

<div class="container-body">
	<h3>员工列表</h3>
	<hr />
<table class="table table-striped table-bordered table-hover table-responsive">
<tr>
	<th>用户名</th>
	<th>公司Id</th>
	<th>工号</th>
	<th>微信号</th>
	<th>qq号</th>
	<th>手机</th>
</tr>
<c:forEach items="${model.members}" var="member">
<tr>
	<td><a class="memberInfo" href="#" memberId="${member.id}">${member.name}</a></td>
	<td>${member.company.id}</td>
	<td>${member.workId}</td>
	<td>${member.wxNumber}</td>
	<td>${member.qqNumber}</td>
	<td>${member.phone}</td>
</tr>
</c:forEach>
</table>
</div>

<div id="memberEditModal" class="modal fade userArchiveEditDlg">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">职员信息</h4>
      </div>
      <div id="memberEditContent" class="modal-body">
      </div>
      <div class="modal-footer">
        <button id="memberEditSubmit" type="button" class="btn btn-primary" data-dismiss="modal">提交</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
	var $memberInfoLink = $(".memberInfo");
	var $memberEditModal = $("#memberEditModal");
	var $memberEditContent = $("#memberEditContent");
	var $memberEditSubmit = $("#memberEditSubmit");

	$memberInfoLink.click(function(e) {
		var memberId = $(this).attr("memberId");
		console.log("get member info, memberId: " + memberId);
		$memberEditContent.load("GetMemberInfo?memberId=" + memberId, function(response, status, xhr) {
			if(status == "error") {
				$memberEditContent.load("Error");
			}
		});
		$memberEditModal.modal();
	});

	$memberEditSubmit.click(function(e) {
		console.log("submit member info");
		$.post(
			"UpdateMember",
			$("form").serialize(),
			function(result) {
				if(result.resultCode == 0) {
					console.log("success");
					location.reload();
				}else {
					console.log("edit member info error, error code : " + result.resultCode + ";error message: " + result.message);
				}
			}
		);
	});
</script>
</body>
</html>