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
            <li><a href="new.html" style="color: black;">新参数</a></li>
            <li><a href="query.html">参数查询</a></li>
        </ul>
	</div>
	
	<div class="col-xs-10 col-xs-offset-1">
		<div class="col-xs-4" align="right">
			参数名：
		</div>
		<div class="col-xs-8">
			<input type="text" id="param_name" onblur="checkParamName(this.value)" onchange="changeParamName()">
			<p class="p" id="name_null">参数名不能为空</p>
			<p class="p" id="name_exist">参数名已经存在</p>
		</div>
		<div class="col-xs-4" align="right">
			参数值：
		</div>
		<div class="col-xs-8">
			<div class="col-xs-12" id="valueArea"></div>
			<input type="text" id="param_value" name="addValueArea"><br>
			<button class="btn btn-success btn-xs" id="addValueArea" onclick="addValueArea()">确定</button>
		</div>
		
		<div class="col-xs-12" align="center">
			<button class="btn btn-xs btn-success" onclick="submitParam()">提交</button>
		</div>
	</div>
	
	
    <script type="text/javascript">
    
    	var valueStore = [];
    	
    	
	    $(document).ready(function(){
			document.getElementById("welcome_admin").style.display = "none";
			document.getElementById("param_admin").style.display = "block";
			document.getElementById("user_admin").style.display = "none";
		});
	    
	    function addValueArea(){
	    	var flag = 0;
	    	var value = document.getElementById("param_value").value;
	    	if(value == null || value == ""){
	    		alert("请输入参数值");
	    	}else{
	    		
	    		$.ajax({
	    			url:"checkParamValue?value=" + value,
	    			type:"post",
	    			async:false,
	    			
	    			success:function(data){
	    				if(data == "1"){
	    					alert("输入的参数值为空");
	    				}else{
	    					if(data == "2"){
	    						alert("参数值已经存在");
	    					}else{
	    						for(var i = 0; i < valueStore.length; i++){
	    			    			if(value == valueStore[i]){
	    			    				alert("你输入的参数值已经存在");
	    			    				flag = 1;
	    			    			}
	    			    		}
	    			    		if(flag == 0){
	    			    			valueStore.push(value);
	    			    			document.getElementById("valueArea").innerHTML += "<div class='col-xs-6' id='" + value + "'>" + value + 
	    			    				"</div><div class='col-xs-6' align='left' id='concel_" + value + "'><button class='btn btn-danger btn-xs' name='" + value + "' onclick='concelValue(this.name)'>取消</button></div>";
	    			    			document.getElementById("param_value").value = "";
	    			    		}
	    					}
	    				}
	    			},
	    			error:function(data){
	    				alert("连接失败");
	    			}
	    		});
	    		
	    	}
	    }
	    
	    function concelValue(value){
	    	document.getElementById(value).remove();
	    	document.getElementById("concel_" + value).remove();
	    	for(var i = 0; i < valueStore.length; i++){
	    		if(value == valueStore[i]){
	    			for(var j = i; j < valueStore.length - 1; j++){
	    				valueStore[j] = valueStore[j+1];
	    			}
	    			valueStore.pop();
	    		}
	    	}
	    }
	    
	    function changeParamName(){
	    	document.getElementById("name_null").style.display = "none";
	    	document.getElementById("name_exist").style.display = "none";
	    }
	    
	    function checkParamName(name){
	    	if(name == "" || name == null){
	    		document.getElementById("name_null").style.display = "inline";
	    	}else{
	    		$.ajax({
	    			type:"post",
	    			url:"checkParamName.html?name=" + name,
	    			async:false,
	    			
	    			success:function(data){
	    				if(data == '1'){
	    					document.getElementById("name_exist").style.display = "inline";
	    				}else{
	    					if(data == "2"){
	    						document.getElementById("name_null").style.display = "inline";
	    					}
	    				}
	    			},
	    			error:function(data){
	    				alert("连接失败");
	    			}
	    		});
	    	}
	    }
	    
	    function getObj(value){
	    	var obj = new Object();
	    	obj.value = value;
	    	return obj;
	    }
	    
	    
	    function submitParam(){
	    	var name = document.getElementById("param_name").value;
	    	var value = "";
	    	
	    	if(valueStore.length != 0){
	    		$.ajax({
	    			type:"post",
	    			url:"checkParamName.html?name=" + name,
	    			async:false,
	    			
	    			success:function(data){
	    				if(data == '1'){
	    					document.getElementById("name_exist").style.display = "inline";
	    				}else{
	    					if(data == '2'){
	    						document.getElementById("name_null").style.display = "inline";
	    					}else{
	    						for(var i = 0; i < valueStore.length; i++){
		    			    		value += valueStore[i] + "     ";
		    			    	}
		    		    		
		    		    		if(confirm("参数名为：" + name + "；参数值为：" + value)){
		    		    			
		    		    			var li = [];
		    		    			li.push(getObj(name));
		    				    	
		    				    	for(var i = 0; i < valueStore.length; i++){
		    				    		li.push(getObj(valueStore[i]));
		    				    	}
		    				    	
		    				    	
		    				    	$.ajax({
		    				    		type:"post",
		    				    		url:"add.html?newParam=" + JSON.stringify(li),
		    				    		async:false,
		    				    		/* dataType:"json", */
		    				    		/* contentType:"application/json;charset=utf-8", */
		    				    		/* data:JSON.stringify(li), */
		    				    		
		    				    		success:function(data){
		    				    			if(data == "1"){
		    				    				alert("失败");
		    				    			}else{
		    				    				if(confirm("继续添加参数请点击\"确定\"")){
		    				    					window.location.href = "new.html";
		    				    				}else{
		    				    					window.location.href = "";
		    				    				}
		    				    			}
		    				    		},
		    				    		error:function(data){
		    				    			alert("连接失败");
		    				    		}
		    				    	});
		    		    		}
	    					}
	    				}
	    			},
	    			error:function(data){
	    				alert("连接失败");
	    			}
	    		});
	    	}else{
	    		alert("请设置参数值");
	    	}
	    }
	    
    </script>
</body>
</html>