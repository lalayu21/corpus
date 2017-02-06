<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>登录首页</title>
	<link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-2.1.4.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</head>

<body>
	<div class="col-xs-10 col-xs-offset-1">
		<jsp:include page="../navigation.jsp"></jsp:include>
	</div>
	
	<script type="text/javascript">
		$(document).ready(function(){
			document.getElementById("welcome_admin").style.display = "block";
			document.getElementById("param_admin").style.display = "none";
			document.getElementById("user_admin").style.display = "none";
		});
	</script>
</body>
</html>