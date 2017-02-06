<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>注册</title>
	<link rel="stylesheet" href="css/bootstrap.min.css">
    <script src="js/jquery-2.1.4.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/sign/sign.js"></script>
    <style>
        p{
            color: red;
            display: none;
        }
    </style>
</head>

<body>
	<div class="container" style="top: 50px;position: relative">
		<button class="btn btn-success" onclick="backToIndex()">返回登录页面</button>
	</div>
	<div class="container" style="top: 100px;position: relative">
		<div class="col-xs-12" align="center">
			<c:if test="${register == 1 }">
				<span style="color:red">用户名不能为空</span>
			</c:if>
			<c:if test="${register == 2 }">
				<span style="color:red">密码不能为空</span>
			</c:if>
			<c:if test="${register == 3 }">
				<span style="color:red">邮箱不能为空</span>
			</c:if>
			<c:if test="${register == 4 }">
				<span style="color:red">身份证号不能为空</span>
			</c:if>
			<c:if test="${register == 5 }">
				<span style="color:red">手机号不能为空</span>
			</c:if>
			<c:if test="${register == 6 }">
				<span style="color:red">真实姓名不能为空</span>
			</c:if>
			<c:if test="${register == 7 }">
				<span style="color:red">用户名已存在</span>
			</c:if>
			
            <div class="col-xs-12" style="height: 10px"><p> </p></div>
		</div>
        <form action="register.html" method="post" onsubmit="return check();">
            <div class="col-xs-4" align="right">
                用户名：
            </div>
            <div class="col-xs-7">
                <input id="username" type="text" class="col-xs-6" name="username"
                       onfocus="username_focus(this.value,this.id,this.defaultValue)" onblur="username_blur(this.value,this.id,this.defaultValue)"
                       oninput="username_change(this.id)" style="color: #999">
                <p id="username_null">用户名不能为空</p>
                <p id="username_format"></p>
                <p id="username_exist">用户名已注册</p>
            </div>

            <div class="col-xs-12" style="height: 10px"><p> </p></div>

            <div class="col-xs-4" align="right">
                密码：
            </div>
            <div class="col-xs-7">
                <input id="password" type="text" class="col-xs-6" name="password"
                       onfocus="username_focus(this.value,this.id,this.defaultValue)" onblur="username_blur(this.value,this.id,this.defaultValue)"
                       onchange="username_change(this.id)" style="color: #999">
                <p id="password_null">密码不能为空</p>
                <p id="password_format">密码输入不一致</p>
                <p id="password_exist"></p>
            </div>

            <div class="col-xs-12" style="height: 10px"><p> </p></div>

            <div class="col-xs-4" align="right">
                确认密码：
            </div>
            <div class="col-xs-7">
                <input id="password_confirm" type="password" class="col-xs-6" name="psw_confirm"
                       onfocus="username_focus(this.value,this.id,this.defaultValue)" onblur="psw_blur(this.value,this.id,this.defaultValue)"
                       onchange="username_change(this.id)">
                <p id="password_confirm_null">密码不能为空</p>
                <p id="password_confirm_format">密码输入不一致</p>
                <p id="password_confirm_exist"></p>
            </div>

            <div class="col-xs-12" style="height: 10px"><p> </p></div>

            <div class="col-xs-4" align="right">
                真实姓名：
            </div>
            <div class="col-xs-7">
                <input id="truename" type="text" class="col-xs-6" name="truename"
                       onfocus="username_focus(this.value,this.id,this.defaultValue)" onblur="username_blur(this.value,this.id,this.defaultValue)"
                       onchange="username_change(this.id)">
                <p id="truename_null">真实姓名不能为空</p>
                <p id="truename_format"></p>
                <p id="truename_exist"></p>
            </div>

            <div class="col-xs-12" style="height: 10px"><p> </p></div>

            <div class="col-xs-4" align="right">
                性别：
            </div>
            <div class="col-xs-7">
                <div class="col-xs-2"><input id="gender_0" type="radio" name="gender" value="0" checked="checked">男</div>
                <div class="col-xs-2"><input id="gender_1" type="radio" name="gender" value="1">女</div>
            </div>

            <div class="col-xs-12" style="height: 10px"><p> </p></div>

            <div class="col-xs-4" align="right">
                身份证号：
            </div>
            <div class="col-xs-7">
                <input id="idCode" type="text" class="col-xs-6" name="idCode"
                       onfocus="username_focus(this.value,this.id,this.defaultValue)" onblur="username_blur(this.value,this.id,this.defaultValue)"
                       oninput="username_change(this.id)">
                <p id="idCode_null">身份证号不能为空</p>
                <p id="idCode_format">身份证号格式不正确</p>
                <p id="idCode_exist">身份证号已存在</p>
            </div>

            <div class="col-xs-12" style="height: 10px"><p> </p></div>

            <div class="col-xs-4" align="right">
                邮箱：
            </div>
            <div class="col-xs-7">
                <input id="email" type="text" class="col-xs-6" name="email"
                       onfocus="username_focus(this.value,this.id,this.defaultValue)" onblur="username_blur(this.value,this.id,this.defaultValue)"
                       oninput="username_change(this.id)">
                <p id="email_null">邮箱不能为空</p>
                <p id="email_format">邮箱格式不正确</p>
                <p id="email_exist">邮箱已存在</p>
            </div>

            <div class="col-xs-12" style="height: 10px"><p> </p></div>

            <div class="col-xs-4" align="right">
                手机号：
            </div>
            <div class="col-xs-7">
                <input id="tel" type="text" class="col-xs-6" name="tel"
                       onfocus="username_focus(this.value,this.id,this.defaultValue)" onblur="username_blur(this.value,this.id,this.defaultValue)"
                       oninput="username_change(this.id)">
                <p id="tel_null">手机号不能为空</p>
                <p id="tel_format">手机号格式不正确</p>
                <p id="tel_exist">手机号已存在</p>
            </div>

            <div class="col-xs-12" style="height: 50px"><p> </p></div>

            <div class="col-xs-4 col-xs-offset-4">
                <button class="btn btn-success" type="reset" style="margin-right: 100px;margin-left: 10px">重置</button>
                <button class="btn btn-success" type="submit">注册</button>
            </div>
        </form>
    </div>
    
    <script type="text/javascript">
    	function backToIndex(){
    		window.location.href = "index.jsp";
    	}
    </script>
</body>
</html>