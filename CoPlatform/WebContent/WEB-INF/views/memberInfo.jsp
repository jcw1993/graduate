<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<form id="memberEditForm">
	<table class="table table-responsive">
		<c:set value="${model.member}" var="member" />
		<tr>
			<td><input name="memberId" type="hidden" value="${member.id}"/></td>
			<td><input name="companyId" type="hidden" value="${member.company.id}"/></td>
		</tr>
		<tr>
			<td><label>用户名</label></td>
			<td><input name="name" type="text" value="${member.name}"/></td>
		</tr>
		<tr>
			<td><label>工号</label></td>
			<td><input name="workId" type="text" value="${member.workId}"/></td>
		</tr>
		<tr>
			<td><label>qq号</label></td>
			<td><input name="qqNumber" type="text" value="${member.qqNumber}"/></td>
		</tr>
		<tr>
			<td><label>微信号</label></td>
			<td><input name="wxNumber" type="text" value="${member.wxNumber}"/></td>
		</tr>
		<tr>
			<td><label>手机</label></td>
			<td><input name="phone" type="text" value="${member.phone}"/></td>
		</tr>
	</table>
</form>