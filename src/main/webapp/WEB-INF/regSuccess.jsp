<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
		注册成功，请回到登录页面登录
		<button class="btn btn-success" onclick="gotoVerifyUser()">用户管理</button>
		
		<script type="text/javascript">
			function gotoVerifyUser(){
				window.location.href = "user/verifyUser.html";
			}
		</script>
</body>
</html>