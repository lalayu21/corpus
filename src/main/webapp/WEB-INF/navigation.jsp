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
		<ul id="welcome_admin" class="nav nav-tabs" role="tablist" style="display:none;">
	        <li class="active" role="presentation" onclick="welcome_admin()">
	        		<a href="welcome.html">
				      	<img alt="首页" src="image/title/home.png" style="width: 50px;height:50px;">
					</a> 
	        </li>
	        <!-- <li role="presentation" onclick="param_admin()"><a href="param/query.html">参数管理</a> </li> -->
	        <li role="presentation" onclick="user_admin()"><a href="user/queryUser.html">
	        	<img alt="用户管理" src="image/title/user.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <li role="presentation" onclick="corpus_admin()"><a href="data/in.html">
	        	<img alt="语料库管理" src="image/title/data.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <li class="pull-right"><a href="logout.html" style="display:inline-block">退出</a></li>
	    </ul>
	    
		<ul id="param_admin" class="nav nav-tabs" role="tablist" style="display:none;">
	        <li role="presentation" onclick="welcome_admin()"><a href="../welcome.html">
	        	<img alt="首页" src="../image/title/home.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <!-- <li class="active" role="presentation" onclick="param_admin()"><a href="../param/query.html">参数管理</a> </li> -->
	        <li role="presentation" onclick="user_admin()"><a href="../user/queryUser.html">
	        	<img alt="用户管理" src="../image/title/user.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <li role="presentation" onclick="corpus_admin()"><a href="../data/in.html">
	        	<img alt="语料库管理" src="../image/title/data.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <li class="pull-right"><a href="../logout.html" style="display:inline-block">退出</a></li>
	    </ul>
	    
	    <ul id="user_admin" class="nav nav-tabs" role="tablist" style="display:none;">
	        <li role="presentation" onclick="welcome_admin()"><a href="../welcome.html">
	        	<img alt="首页" src="../image/title/home.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <!-- <li role="presentation" onclick="param_admin()"><a href="../param/query.html">参数管理</a> </li> -->
	        <li class="active" role="presentation" onclick="user_admin()"><a href="../user/queryUser.html">
	        	<img alt="用户管理" src="../image/title/user.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <li role="presentation" onclick="corpus_admin()"><a href="../data/in.html">
	        	<img alt="语料库管理" src="../image/title/data.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <li class="pull-right"><a href="../logout.html" style="display:inline-block">退出</a></li>
	    </ul>
	    
	    <ul id="corpus_admin" class="nav nav-tabs" role="tablist" style="display:none;">
	        <li role="presentation" onclick="welcome_admin()"><a href="../welcome.html">
	        	<img alt="首页" src="../image/title/home.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <!-- <li role="presentation" onclick="param_admin()"><a href="../param/query.html">参数管理</a> </li> -->
	        <li role="presentation" onclick="user_admin()"><a href="../user/queryUser.html">
	        	<img alt="用户管理" src="../image/title/user.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <li class="active" role="presentation" onclick="corpus_admin()"><a href="../data/in.html">
	        	<img alt="语料库管理" src="../image/title/data.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <li class="pull-right"><a href="../logout.html" style="display:inline-block">退出</a></li>
	    </ul>
	    
	    <ul id="operate_admin" class="nav nav-tabs" role="tablist" style="display:none;">
	        <li role="presentation" onclick="welcome_admin()"><a href="../welcome.html">
	        	<img alt="首页" src="../image/title/home.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <!-- <li role="presentation" onclick="param_admin()"><a href="../param/query.html">参数管理</a> </li> -->
	        <li role="presentation" onclick="user_admin()"><a href="../user/queryUser.html">
	        	<img alt="用户管理" src="../image/title/user.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <li role="presentation" onclick="corpus_admin()"><a href="../data/in.html">
	        	<img alt="语料库" src="../image/title/data.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <li class="active" role="presentation" onclick="corpus_admin()"><a href="../data/in.html">
	        	<img alt="数据管理" src="../image/title/data.png" style="width: 50px;height:50px;">
	        </a> </li>
	        <li class="pull-right"><a href="../logout.html" style="display:inline-block">退出</a></li>
	    </ul>

</body>
</html>