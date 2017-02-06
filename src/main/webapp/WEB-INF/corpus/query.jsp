<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>导入数据</title>
	<link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/jquery-2.1.4.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/sign/sign.js"></script>
    <style>
        li{
    		list-style-type: none;
    		display: block;
    	}
    	.p{
    		color:red;
    		display: none;
    	}
    	a{
    		cursor: pointer;
    	}
    </style>
    
</head>

<body>

	<div class="col-xs-10 col-xs-offset-1">
		<jsp:include page="../navigation.jsp"></jsp:include>
	</div>
	
	<div class="col-xs-1 col-xs-offset-1" style="margin-top: 20px;margin-bottom: 10px;">
		<ul style="margin: 0;padding: 0;">
            <li><a href="in.html"><img src="../image/right.png" style="width:15px;height:10px;margin-right:5px;">创建语料库</a></li>
            <li><a href="corpusList.html" style="color: black;"><img id="iamgeList" src="../image/down.png" style="width:15px;height:10px;margin-right:5px;">语料库列表</a>
            	<ul style="margin:0px;margin-left:5px;padding: 0px;">
            		<li><a id="labelType0" onclick="getCorpusType(0)"><img src="../image/right.png" style="width:15px;height:10px;margin-right:10px;">生语料</a></li>
            		<li><a id="labelType1" data-toggle="collapse" href="#labelType" onclick="getCorpusType(1)"><img name="1" id="labelTypeDown" src="../image/right.png" style="width:15px;height:10px;margin-right:10px;">熟语料</a></li>
            	</ul>
            	<!-- <ul id="labelType" class="collapse" style="margin:0;margin-left:20px;padding: 0;">
		        	<li><a id="labelType2" onclick="getCorpusType(2)">音频内容</a></li>
		        	<li><a id="labelType3" onclick="getCorpusType(3)">语种</a></li>
		        	<li><a id="labelType4" onclick="getCorpusType(4)">性别</a></li>
		        	<li><a id="labelType5" onclick="getCorpusType(5)">说话人</a></li>
		        	<li><a id="labelType6" onclick="getCorpusType(6)">有效语音</a></li>
		        </ul> -->
            </li>
        </ul>
        
        
        <input type="hidden" id="labelTypeChecked">
	</div>

	<div class="col-xs-9" style="margin-top: 20px;">
		<div class="col-xs-12" id="content" style="margin-bottom: 20px;">
			<div class="col-xs-7" align="center" style="font-size: medium;">
				<div style="padding-left: 5px;padding-right: 5px;display: inline;"><input type="radio" name="type" value="1" checked="checked">所有</div>
				<div style="padding-left: 5px;padding-right: 5px;display: inline;"><input type="radio" name="type" value="2">标注结果</div>
				<div style="padding-left: 5px;padding-right: 5px;display: inline;"><input type="radio" name="type" value="3">性别</div>
				<div style="padding-left: 5px;padding-right: 5px;display: inline;"><input type="radio" name="type" value="5">语种</div>
				<div style="padding-left: 5px;padding-right: 5px;display: inline;"><input type="radio" name="type" value="4">说话人</div>
				<div style="padding-left: 5px;padding-right: 5px;display: inline;"><input type="radio" name="type" value="6">有效语音</div>
			</div>
			<div class="col-xs-4">
				<input class="form-control" type="text" name="queryContext" id="queryContext">
			</div>
			<div class="xol-xs-1">
				<button class="btn btn-primary" onclick="selectByInput()">查找</button>
			</div>
		</div>
		<jsp:include page="query_body.jsp"></jsp:include>
	</div>
	
    <script type="text/javascript">
    
	    $(document).ready(function(){
			document.getElementById("welcome_admin").style.display = "none";
			document.getElementById("param_admin").style.display = "none";
			document.getElementById("user_admin").style.display = "none";
			document.getElementById("corpus_admin").style.display = "block";
		});
	    
	    function getCorpusType(value){
	    	alert(value);
	    	alert($("#labelTypeChecked").val())
	    	if($("#labelTypeChecked").val() != value || $("#labelTypeChecked").val() == null || $("#labelTypeChecked").val() == ""){
	    		alert(3)
	    		if($("#labelTypeChecked").val() != null && $("#labelTypeChecked").val() != ""){
		    		$("#labelType" + $("#labelTypeChecked").val()).css("color","");
	    		}
	    		alert("2")
	    		$("#labelTypeChecked").val(value);
	    		alert(4)
	    		$("#labelType" + value).css("color","black");
	    		alert(5)
	    		
	    		if(value == '0'){
	    			$("#content").hide();
	    		}else{
	    			$("#content").show();
	    		}
	    		
	    		getCorpusLabelType(value);
	    	}
	    }
	    //获取corpuslist
	    
	    function getCorpusLabelType(value){
	    	$("#queryContext").val("");
	    	var type = $("input[name='type']:checked").val();
	    	if(type != null && type != "" && value == '1'){
	    		value = type;
	    	}
	    	$.ajax({
	    		url:"corpusListLabelType.html?flag=" + value,
	    		type:"get",
	    		async:false,
	    		dataType:"json",
	    		
	    		success:function(data){
	    			showCorpus(data);
	    		},
	    		error:function(data){
	    			alert("连接失败");
	    		}
	    	});
	    }
	    
	    //将获取的corpus显示在页面中
	    function showCorpus(ret){
	    	$("#corpusTable").html("");
	    	var corpus = ret.corpus;
	    	var li = [];
	    	var type = $("#labelTypeChecked").val();
	    	$.each(corpus,function(index,v){
	    		temp = index + 1;
	    		li.push("<tr><td class='tableBody'>" + temp + "</td>");
	    		li.push("<td class='tableBody'><a href='detail.html?id=" + corpus[index].id + "&type=" + type +"' target='_blank'>" + corpus[index].name + "</a></td>");
	    		if(corpus[index].context == '1'){
		    		li.push("<td class='tableBody' style='color:red'>已标注</td>");
	    		}else{
	    			li.push("<td class='tableBody'>未标注</td>");
	    		}
	    		if(corpus[index].gender == '1'){
		    		li.push("<td class='tableBody' style='color:red'>已标注</td>");
	    		}else{
	    			li.push("<td class='tableBody'>未标注</td>");
	    		}
	    		if(corpus[index].speaker == '1'){
		    		li.push("<td class='tableBody' style='color:red'>已标注</td>");
	    		}else{
	    			li.push("<td class='tableBody'>未标注</td>");
	    		}
	    		if(corpus[index].language == '1'){
		    		li.push("<td class='tableBody' style='color:red'>已标注</td>");
	    		}else{
	    			li.push("<td class='tableBody'>未标注</td>");
	    		}
	    		if(corpus[index].effective == '1'){
		    		li.push("<td class='tableBody' style='color:red'>已标注</td>");
	    		}else{
	    			li.push("<td class='tableBody'>未标注</td>");
	    		}
	    		li.push("<td class='tableBody'>" + corpus[index].desp + "</td>");
	    		li.push("<td class='tableBody'><a class='manual' href='../label/action.html?id=" + corpus[index].id + "&type=" + corpus[index].labelType + "' target='_blank'>标注</a><a class='manual' href='../feature/action.html?id=" + corpus[index].id + "&type=" + corpus[index].labelType + "' target='_blank'>提特征</a><a class='manual' href='../training/action.html?id=" + corpus[index].id + "&type=" + corpus[index].labelType + "' target='_blank'>生成训练集和测试集</a></td></tr>")
	    	});
	    	$("#corpusTable").html(li.join(""));
	    }
	    
	    //根据用户输入内容查找
	    function selectByInput(){
	    	var type = $("input[name='type']:checked").val();
	    	
	    	if(type == null || type == ""){
	    		type = $("#labelTypeChecked").val();
	    	}
	    	
	    	var value = $("#queryContext").val();
	    	if(value == null){
	    		alert("请输入查询内容");
	    	}else{
	    		$.ajax({
	    			url:"corpusListByInput.html?",
	    			type:"post",
	    			data:{"type":type, "input":value},
	    			async:false,
	    			dataType:"json",
	    			
	    			success:function(data){
	    				showCorpus(data);
	    			},
	    			error:function(data){
	    				alert("连接失败");
	    			}
	    		});
	    	}
	    }
	    
	    
    </script>
</body>
</html>