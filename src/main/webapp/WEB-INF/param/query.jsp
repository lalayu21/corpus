<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>添加参数</title>
	<link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/jquery-2.1.4.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/sign/sign.js"></script>
    <style>
        .li{
    		list-style-type: none;
    		display: inline;
    	}
    	.p{
    		color:red;
    		display: none;
    	}
    </style>
</head>

<body>
	<div class="col-xs-10 col-xs-offset-1">
		<jsp:include page="../navigation.jsp"></jsp:include>
	</div>
	<div class="col-xs-10 col-xs-offset-1">
		<ul class="nav nav-pills" role="tablist">
            <li><a href="new.html">新参数</a></li>
            <li><a href="query.html" style="color: black;">参数查询</a></li>
        </ul>
	</div>
	
	<div class="col-xs-10 col-xs-offset-1">
		<jsp:include page="query_body.jsp"></jsp:include>
	</div>
	
	
    <script type="text/javascript">
    
	    $(document).ready(function(){
			document.getElementById("welcome_admin").style.display = "none";
			document.getElementById("param_admin").style.display = "block";
			document.getElementById("user_admin").style.display = "none";
		});
	    
    </script>
</body>
</html>