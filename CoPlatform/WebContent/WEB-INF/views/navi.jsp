<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<ul class="nav nav-pills">
  <li role="presentation" class="active"><a href="#">员工管理</a></li>
  <li role="presentation"><a href="#">项目管理</a></li>
  <li role="presentation"><a href="#">任务管理</a></li>
  <li role="presentation"><a href="#">日志管理</a></li>
</ul>

<script type="text/javascript">
	var $nav;
	var $navItems;
	
	$(function() {
		$nav = $("ul.nav");
		$navItems = $("ul.nav li");

		$navItems.click(function(e) {
			var index = $(this).index();
			console.log(index);
			$navItems.removeClass("active"); 
			$nav.children().eq(index).addClass("active");
		});
	});

	function setCurrentNavItem(index) {
		$navItems.removeClass("active"); 
		$nav.children().eq(index).addClass("active");
	}
</script>