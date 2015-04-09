<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<nav class="navbar navbar-default">
  <div class="container-fluid nav-bkg-color">
    
    <div class="collapse navbar-collapse color-white" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
      	<li class="title"><h4>任务协同平台</h4></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">员工管理 </a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="#">公司职员</a></li>
            <li><a href="#">外聘人员</a></li>
          </ul>
        </li>
        <li><a href="#">项目管理</a></li>
        <li><a href="#">任务管理</a></li>
        <li><a href="#">日志管理</a></li>
      </ul>
     
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

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