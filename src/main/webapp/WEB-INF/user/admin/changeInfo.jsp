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
    <script src="../js/sign/sign_change.js"></script>
    <style>
    	.style{
    		margin-top: 10px;
    		margin-bottom: 10px;
    	}
    	.p{
    		color: red;
    		display: none;
    	}
    </style>
</head>

<body>
	<div class="col-xs-10 col-xs-offset-1" align="center"><h2>用户信息修改</h2></div>
	<div class="col-xs-12" style="height: 20px;"><p> </p></div>
	<div class="col-xs-10 col-xs-offset-1">	
			<div class="col-xs-5 style" align="right">用户名：</div>
			<div class="col-xs-7 style">
				<input type="text" id="username" name="username" value="${user.username }" onblur="username_blur(this.value,this.id,${user.id}, this.defaultValue)"
				oninput="username_change(this.id)">
				<input type="hidden" id="username_hide" value="${user.username }">
				<p class="p" id="username_null">用户名不能为空</p>
				<p class="p" id="username_exist">用户名存在</p>
			</div>
			<div class="col-xs-5 style" align="right">密码：</div>
			<div class="col-xs-7 style">
				<input type="text" id="password" name="password" value="${user.password }">
				<input type="hidden" id="password_hide" value="${user.password }">
				<p class="p" id="password_null">用户名不能为空</p>
			</div>
			<div class="col-xs-5 style" align="right">真实姓名：</div>
			<div class="col-xs-7 style">
				<input type="text" id="truename" name="truename" value="${user.truename }" onblur="username_blur(this.value,this.id,this.defaultValue)">
				<input type="hidden" id="truename_hide" value="${user.truename }">
				<p class="p" id="truename_null">真实姓名不能为空</p>
			</div>
			<div class="col-xs-5 style" align="right">身份证号：</div>
			<div class="col-xs-7 style">
				<input type="text" id="idCode" name="idCode" value="${user.idCode }" onblur="username_blur(this.value,this.id,this.defaultValue)">
				<input type="hidden" id="idCode_hide" value="${user.idCode }">
				<p class="p" id="idCode_null">身份证号不能为空</p>
				<p class="p" id="idCode_exist">身份证号已经存在</p>
				<p class="p" id="idCode_format">身份证号格式不正确</p>
			</div>
			<div class="col-xs-5 style" align="right">邮箱：</div>
			<div class="col-xs-7 style">
				<input type="text" id="email" name="email" value="${user.email }" onblur="username_blur(this.value,this.id,this.defaultValue)">
				<input type="hidden" id="email_hide" value="${user.email }">
				<p class="p" id="email_null">邮箱不能为空</p>
				<p class="p" id="email_exist">邮箱已经存在</p>
				<p class="p" id="email_format">邮箱格式不正确</p>
			</div>
			<div class="col-xs-5 style" align="right">手机号：</div>
			<div class="col-xs-7 style">
				<input type="text" id="tel" name="tel" value="${user.tel }" onblur="username_blur(this.value,this.id,this.defaultValue)">
				<input type="hidden" id="tel_hide" value="${user.tel }">
				<p class="p" id="tel_null">号码不能为空</p>
				<p class="p" id="tel_format">号码格式不正确</p>
			</div>
			<div class="col-xs-5 style" align="right">性别：</div>
			<div class="col-xs-7 style">
				<c:if test="${user.gender == '0' }">
					<input type="radio" name="gender" value="0" checked="checked">男
					<input type="radio" name="gender" value="1" style="margin-left: 20px;">女
				</c:if>
				<c:if test="${user.gender == '1' }">
					<input type="radio" name="gender" value="0">男
					<input type="radio" name="gender" value="1" checked="checked" style="margin-left: 20px;">女
				</c:if>
			</div>
			<div class="col-xs-5 style" align="right">权限：</div>
			<div class="col-xs-7 style">
				<c:choose>
					<c:when test="${user.access == 1 }">
						<input type="radio" name="access" value="1" checked="checked">管理员
						<input type="radio" name="access" value="2" style="margin-left: 20px;">普通用户
					</c:when>
					<c:otherwise>
						<input type="radio" name="access" value="1">管理员
						<input type="radio" name="access" value="2" style="margin-left: 20px;" checked="checked">普通用户
					</c:otherwise>
				</c:choose>
			</div>
			
			<div class="col-xs-12 style" align="center" style="margin-top: 20px;">
				<button class="btn btn-success" style="margin-right: 10px;" onclick="save(${user.id})">保存</button>
				<button class="btn btn-gray" style="margin-left: 10px;" onclick="concel(${user.id})">取消</button>
			</div>
	</div>
	
	<script>
		$(document).ready(function(){
			
		});
		
		function save(id){
			var flag = 0;
		    //判断用户名是否为空
		    if(document.getElementById("username").value == "" || document.getElementById("username").value == null){
		        document.getElementById("username_null").style.display = "inline";
		        return false;
		    }else{
		        //判断用户名格式
		        var username_value = document.getElementById("username").value;

		        //判断用户名是否存在
		        $.ajax({
		        	url:'../checkUsername.html?username=' + username_value + "&id=" + id,
		            type:'get',
		            async:false,

		            success:function(username){
		            	if(username != '0'){
		                    document.getElementById("username_exist").style.display = "inline";
		                    flag = 1;
		                }else{
		                    //判断密码是否为空
		                    if(document.getElementById("password").value == null || document.getElementById("password").value == ""){
		                        document.getElementById("password_null").style.display = "inline";
		                        flag = 1;
		                    }else{
		                        var truename = document.getElementById("truename").value;
		                                //判断真实姓名是否为空
		                                if(truename == "" || truename == null){
		                                    document.getElementById("truename_null").style.display = "inline";
		                                    flag = 1;
		                                }else{
		                                    var idCode_value = document.getElementById("idCode").value;
		                                    //判断身份账号是否为空
		                                    if(idCode_value == "" || idCode_value == null){
		                                        document.getElementById("idCode_null").style.display = "inline";
		                                        flag = 1;
		                                    }else{
		                                        //判断身份证号格式是否正确
		                                        if(idCode_value.length != 18){
		                                            document.getElementById("idCode_format").style.display = "inline";
		                                            flag = 1;
		                                        }else{
		                                            var a = /\d{17}/;
		                                            var b = /\d{1}/;
		                                            if(a.exec(idCode_value) && (idCode_value.charAt(17) == 'x' || idCode_value.charAt(17) == 'X' || b.exec(idCode_value.charAt(17)))){
		                                                //判断邮箱是否为空
		                                                if(document.getElementById("email").value == "" || document.getElementById("email").value == null){
		                                                    document.getElementById("email_null").style.display = "inline";
		                                                    flag = 1;
		                                                }else{
		                                                    //判断邮箱格式是否正确
		                                                    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		                                                    var email_value = document.getElementById("email").value;
		                                                    if(myreg.test(email_value)){
		                                                        //判断手机号是否正确
		                                                        var tel_value = document.getElementById("tel").value;
		                                                        if(tel_value == "" || tel_value == null){
		                                                            document.getElementById("tel_null").style.display = "inline";
		                                                            flag = 1;
		                                                        }else{
		                                                            var reg_tel = /^1\d{10}$/;
		                                                            if(reg_tel.exec(tel_value)){
		                                                            	
		                                                            	//向后台传参数
		                                                            	sendUser(username_value,truename,idCode_value,email_value,tel_value,id);
		                                                            	
		                                                            }else{
		                                                                document.getElementById("tel_format").style.display = "inline";
		                                                                flag = 1;
		                                                            }
		                                                        }
		                                                    }else{
		                                                        document.getElementById("email_format").style.display = "inline";
		                                                        flag = 1;
		                                                    }
		                                                }
		                                            }else{
		                                                document.getElementById("idCode_format").style.display = "inline";
		                                                flag = 1;
		                                            }
		                                        }
		                                    }
		                                }
		                            }
		                }
		            },
		            error:function(username){
		                alert("连接失败");
		            }
		        });
		    }
		    
		    if(flag == 1){
		    	return false;
		    }
		}
		
		function sendUser(username,truename,idCode,email,tel,id){
			var gender = $("input:radio[name='gender']:checked").val();
			var access = $("input:radio[name='access']:checked").val();
			
			$.ajax({
				url:"../changeUserInfo.html?username=" + username + "&truename=" + truename + "&idCode=" + idCode + "&email=" + email + "&tel=" + tel + "&gender=" + gender + "&access=" + access + "&id=" + id,
				type:"get",
				async:false,
				
				success:function(data){
					alert(data);
					/* window.opener.location.reload(true); */
					parent.location.reload();
				},
				error:function(data){
					alert(data.error);
					alert("连接失败")
				}
			});
		}
		
		function concel(id){
			/* document.getElementById("username").value = $("username_hide").val();
			document.getElementById("password").value = $("password_hide").val();
			document.getElementById("truename").value = $("truename_hide").val();
			document.getElementById("idCode").value = $("idCode_hide").val();
			document.getElementById("email").value = $("email_hide").val();
			document.getElementById("tel").value = $("tel_hide").val();
			window.opener.location.reload(true); */
			window.close();
		}
		
		
	</script>
</body>
</html>