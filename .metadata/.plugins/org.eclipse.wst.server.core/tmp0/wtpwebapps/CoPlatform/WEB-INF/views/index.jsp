<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Hello world!</h1>
<table>
<tr>
	<th>Id</th>
	<th>邮箱</th>
	<th>用户名</th>
	<th>密码</th>
</tr>
<tr>
	<td>${model.admin.id}</td>
	<td>${model.admin.mail}</td>
	<td>${model.admin.name}</td>
	<td>${model.admin.password}</td>
</tr>
</table>
</body>
</html>