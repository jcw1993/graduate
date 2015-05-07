<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="header.jsp" flush="true" />
<body>


	<div id="borderLogo">
		<div class="logoImg"></div>
	</div>

	<div id="content" class="main fontSize2">
		<p class="title" align="left">${model.wxnews.title}</p>
		<span class="src">${model.wxnews.createdTime}</span>

		


		<p class="text"></p>
		
		<p></p>
		<p class="text"></p>
		<div class="preLoad" style="width: 100%; min-height: 129px">
			<div class="img">
				<img src="./腾讯新闻_files/1000"
					preview-src="http://inews.gtimg.com/newsapp_match/0/44857511/1000"
					style="width: 100%; display: block;">
			</div>
			<div class="tip">Co-Work</div>
		</div>
		<p class="text">${model.wxnews.content}</p>
		<p></p>
		
</body>
</html>