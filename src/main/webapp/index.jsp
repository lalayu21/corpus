<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>登录</title>
	<link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-2.1.4.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/login/login.js"></script>
</head>

<body>
	
	<div id="body" style="position: absolute;">
		<script type="text/javascript">
			document.getElementById("body").style.width =  window.screen.availWidth + "px";
			document.getElementById("body").style.height =  window.screen.availHeight + "px";
		</script>
		<!-- <img src="image/background/background.jpg" style="position: absolute;top: 0px;left: 0px;width: 100%;height: 100%;">
	 -->
		<div class="col-md-12" style="height: 200px;"><p> </p></div>
		<form action="login.html" method="post" onsubmit="return check(this)">
			<div class="col-md-3 col-md-offset-5" style="background-color: #d5d5d5">
				<div class="col-md-12" style="height: 20px;"><p> </p></div>
				<div class="col-md-12" style="height: 50px;" align="center"><p><b style="font-size: x-large;">用户登录</b></p></div>
				<div class="col-md-3" align="right" style="height: 50px;font-size: large;padding: 0px;margin: 0px;">
					用户名
				</div>
				<div class="col-md-9" style="height: 50px;font-size: large;">
					<input type="text" id="username" name="username">
				</div>
				<div class="col-md-3" align="right" style="height: 50px;font-size: large;padding: 0px;margin: 0px;">
					密&nbsp;&nbsp;&nbsp;码
				</div>
				<div class="col-md-9" style="height: 50px;font-size: large;">
					<input type="password" id="password" name="password">
				</div>
				<div class="col-md-12" align="center">
					<c:if test="${message != '' && message != null }">
						<p style="color:red">${message }</p>
					</c:if>
					<p id="usernameNull" style="display:none;color:red">用户名不能为空</p>
					<p id="passwordNull" style="display:none;color:red">密码不能为空</p>
				</div>
				<div class="col-md-4 col-md-offset-4" align="center">
					<button class="btn btn-s btn-primary" type="submit" style="height: 30px;">登录</button>
				</div>
				<div class="col-md-12" style="height: 20px;"><p> </p></div>
				<div class="col-md-5" align="right">忘记密码？</div>
				<div class="col-md-5 col-md-offset-2">
					<a href="toReg.html">注册新用户</a>
				</div>
				<div class="col-md-12" style="height: 20px;"><p> </p></div>
			</div>
		</form>
	</div>
</body>
</html>