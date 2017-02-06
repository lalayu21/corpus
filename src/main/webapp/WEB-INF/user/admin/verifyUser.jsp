<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>管理员审核用户</title>
	<link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/jquery-2.1.4.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <style>
        li{
    		list-style-type: none;
    		display: block;
    	}
    </style>
</head>

<body>
	<div class="col-xs-10 col-xs-offset-1">
		<jsp:include page="../../navigation.jsp"></jsp:include>
	</div>
	<div class="col-xs-1 col-xs-offset-1" style="margin-top: 20px;">
		<ul style="margin: 0;padding: 0;">
            <li><a href="#" style="color: black;""><img src="../image/right.png" style="width:10px;height:10px;">用户审核</a></li>
            <li><a href="queryUser.html"><img src="../image/right.png" style="width:10px;height:10px;">用户查询</a></li>
        </ul>
	</div>
	<div class="col-xs-8" style="margin-top: 20px">
		<jsp:include page="verifyUser_body.jsp"></jsp:include>
	</div>
	
	
    <script type="text/javascript">
	    $(document).ready(function(){
			document.getElementById("welcome_admin").style.display = "none";
			document.getElementById("param_admin").style.display = "none";
			document.getElementById("user_admin").style.display = "block";
		});
    </script>
    
    
	<script>
    	function pass(id,access){
    		$.ajax({
    			type:"get",
    			url:"verifyUserPass.html?id=" + id + "&access=" + access,
    			async:false,
    			
    			success:function(data){
    				if(data == '0'){
    					if(confirm("是否继续审核其他用户？")){
    						window.location.href = "verifyUser.html";
    					}else{
    						window.location.href = "";
    					}
    				}else{
    					if(data == '1'){
    						alert("系统原因，没有输入用户编号");
    					}else{
    						alert("系统原因，输入的用户编号不存在");
    					}
    				}
    			},
    			error:function(data){
    				alert("连接失败");
    			}
    		});
    	}
    	
    </script>
</body>
</html>