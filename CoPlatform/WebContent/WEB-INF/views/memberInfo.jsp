<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<form id="memberEditForm">
	<c:set value="${model.member}" var="member" />
	<div>
		<input name="id" type="hidden" value="${member.id}"/>
		<input name="companyId" type="hidden" value="${member.company.id}"/>
	</div>
	<div>
		<label>用户名</label>
		<input name="name" type="text" value="${member.name}"/>
	</div>
	<div>
		<label>工号</label>
		<input name="workId" type="text" value="${member.workId}"/>
	</div>
	<div>
		<label>qq号</label>
		<input name="qqNumber" type="text" value="${member.qqNumber}"/>
	</div>
	<div>
		<label>微信号</label>
		<input name="wxNumber" type="text" value="${member.wxNumber}"/>
	</div>
	<div>
		<label>手机</label>
		<input name="phone" type="text" value="${member.phone}"/>
	</div>
</form>