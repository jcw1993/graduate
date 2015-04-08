<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>

<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />" />
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-theme.min.css" />" />

<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.11.2.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/bootstrap.min.js" />"></script>

<title>南大任务协同平台-登录</title>
</head>
<body>
<div class="container">
<div class="row col-xs-4">
<form id="loginForm" action="Login" method="post">
<div class="input-group">
	<span class="input-group-addon">邮箱</span>
  	<input type="text" class="form-control" id="mail" name="mail" placeholder="邮箱" aria-describedby="basic-addon2" />
</div>
<div class="input-group">
	<span class="input-group-addon">密码</span>
  	<input type="password" class="form-control" id="password" name="password" placeholder="密码" aria-describedby="basic-addon2" />
</div>
<a id="login" class="btn btn-primary" href="#">登录</a>
</form>
</div>
</div>

<script type="text/javascript">

	var $mailInput;
	var $passwordInput;
	var $loginBtn;
	var $loginForm;
	
	$(function() {
		$mailInput = $("#mail");
		$passwordInput = $("#password");
		$loginLink = $("#login");
		$loginForm = $("#loginForm");
		
		$loginLink.click(function(e) {
			console.log("log");
			var mail = $mailInput.val();
			var password = $passwordInput.val();
			console.log("mail: " + mail);
			console.log("password: " + password);
			$loginForm.submit();
		}); 
	});
</script>
</body>
</html>