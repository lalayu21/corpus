<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>详细信息</title>
	<link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/jquery-2.1.4.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/set/set.js"></script>
    
    <script src="../js/wavesurfer/dist/wavesurfer.js"></script>
    <style>
        .li{
    		list-style-type: none;
    		display: block;
    		font-size: medium;
    	}
    	.p{
    		color:red;
    		display: none;
    	}
    	a:HOVER {
			cursor: pointer;
		}
		.tdRight{
			border-right: 1px solid #cccccc;
			border-bottom: 1px solid #cccccc;
		}
		.tdBottom{
			border-right: 1px solid #cccccc;
		}
    </style>
</head>

<body>
	
	<div class="col-xs-10 col-xs-offset-1 leadin">
		<jsp:include page="../../navigation.jsp"></jsp:include>
	</div>
		
		<div class="col-xs-10 col-xs-offset-1" align="center">
			<div class="col-xs-4 col-xs-offset-4" align="center"><h1>${name }</h1></div>
			<div class="col-xs-4" align="right" style="margin-top: 25px;">
				<a href="../training/list.html?id=${id }&type=${type}&labelType=${labelType}">
					<button class="btn btn-primary">返回训练集列表</button>
				</a>
			</div>
			
			<input type="hidden" id="hiddenCorpusId" value="${id }">
		</div>
		
		<div class="col-xs-1 col-xs-offset-1">
			<ul style="margin: 0;padding: 0">
				<li class="li"><a href="../data/detail.html?id=${id }&type=${type}">基本信息</a></li>
				<li class="li"><a href="../data/waves.html?id=${id }&type=${type}&labelType=${labelType }&name=${name }">音频列表</a></li>
				<li class="li"><a href="../label/action.html?id=${id }">标注管理</a></li>
				<li class="li"><a href="#" style="color:black">训练集管理</a></li>
				<li class="li"><a href="../feature/action.html?id=${id }">特征管理</a></li>
			</ul>
		</div>
	
	<div class="col-xs-10" style="margin-top: 10px;">
		
	<!-- 	选择生成哪种标注结果的训练集和测试集 -->
		<div class="col-xs-12">
			<div class="col-xs-2 col-xs-offset-1" align="right"><span style="font-size: 15px;">训练集和测试集组名称：</span></div>
			<div class="col-xs-5">
				<input type="text" id="name" class="form-control">
			</div>
		</div>
		<div class="col-xs-12" style="margin-top: 10px;">
			<div class="col-xs-2 col-xs-offset-1" align="right"><span style="font-size: 15px;">选择标注类型：</span></div>
			<div class="col-xs-6">
				<c:if test="${corpus.context != 0 }">
					<div class="col-xs-12">
						<input type="radio" name="labelType" value="0">音频内容标注：${corpus.context}s
					</div>
				</c:if>
				<c:if test="${corpus.gender != 0 }">
					<div class="col-xs-12">
						<input type="radio" name="labelType" value="1" onclick="getGenderSet()">性别标注：${corpus.gender}s
					</div>
				</c:if>
				<c:if test="${corpus.language != 0 }">
					<div class="col-xs-12">
						<input type="radio" name="labelType" value="2" onclick="getLanguageSet()">语种标注：${corpus.language}s
					</div>
				</c:if>
				<c:if test="${corpus.speaker != 0 }">
					<div class="col-xs-12">
						<input type="radio" name="labelType" value="3" onclick="getSpeakerSet()">说话人标注：${corpus.speaker}s
					</div>
				</c:if>
				<c:if test="${corpus.effective != 0 }">
					<div class="col-xs-12">
						<input type="radio" name="labelType" value="4" onclick="getEffectiveSet()">有效话音标注：${corpus.effective}s
					</div>
				</c:if>
			</div>
		</div>
		
		<div class="col-xs-12" style="margin-top: 10px;">
			<div class="col-xs-2 col-xs-offset-1" align="right"><span style="font-size: 15px;">参数设置：</span></div>
			<!-- 是否使用所有数据 -->
			<div class="col-xs-9" style="height: 40px">
				<div class="col-xs-3">是否使用所有音频：</div>
				<div class="col-xs-8">
					<div class="col-xs-2">
						<input type="radio" name="allData" value="0" onclick="useAllData()">是
					</div>
					<div class="col-xs-2">
						<input type="radio" name="allData" value="0" checked="checked" onclick="usePartData()">否
					</div>
				</div>
			</div>
		</div>
		
		
		<!-- 如果使用所有数据 -->
		<div class="col-xs-12" id="allData" style="height: 40px;display:none;">
			<div class="col-xs-5" align="right">训练集和测试集时长比例：</div>
			<div class="col-xs-7">
				<input id="percent" style="width: 50px;margin-right: 5px;" type="text" oninput="checkInput_percent(this.value)">%
			</div>
		</div>
		
		<!-- 如果使用固定时长的方法 -->
		<div class="col-xs-12" id="partData">
			<div class="col-xs-5" align="right" style="height: 40px">训练集时长：</div>
			<div class="col-xs-7" style="height: 40px">
				<input id="trainTime" style="width: 50px;margin-right: 5px;" type="text" oninput="checkInput_train(this.value)">h
			</div>
			
			<div class="col-xs-5" align="right" style="height: 40px">测试集时长：</div>
			<div class="col-xs-7" style="height: 40px">
				<input id="testTime" style="width: 50px;margin-right: 5px;" type="text" oninput="checkInput_test(this.value)">h
			</div>
		</div>
		
		<!-- 性别 -->
		<div class="col-xs-12" id="getGenderSet">
		</div>
		
		<!-- 获取 -->
		<div class="col-xs-12" align="center" style="height: 40px;">
			<button class="btn btn-success" onclick="getSet()">生成训练集和测试集</button>
		</div>
		
	</div>
	<div>
		<input type="hidden" id="hideID" value="${id }">
		<input type="hidden" id="hideLabelType" value="${labelType }">
		<input type="hidden" id="hideTrain">
		<input type="hidden" id="hideTest">
	</div>
    
    <script type="text/javascript">

	    $(document).ready(function(){
			document.getElementById("welcome_admin").style.display = "none";
			document.getElementById("param_admin").style.display = "none";
			document.getElementById("user_admin").style.display = "none";
			document.getElementById("corpus_admin").style.display = "block";
		});
    
	    function checkInput_percent(value){
	    	var e = /[0-9]/;
	    	
	    	var oTxt1 = document.getElementById("percent");
	        var position=-1;
	        if(oTxt1.selectionStart){//非IE浏览器
	            position= oTxt1.selectionStart;
	        }else{//IE
	            var range = document.selection.createRange();
	            range.moveStart("character",-oTxt1.value.length);
	            position=range.text.length;
	        }
	        
	    	if(value.length > 2 || !e.exec(value.substring(position-1,position))){
	    		$("#percent").val(value.substring(0,position-1) + value.substring(position,value.length));
	    	}
	    }
	    
	    function checkInput_train(value){
	    	var temp = "";
	        var e1 = /\./;
	        
	    	var oTxt1 = document.getElementById("trainTime");
	        var position=-1;
	        if(oTxt1.selectionStart){//非IE浏览器
	            position= oTxt1.selectionStart;
	        }else{//IE
	            var range = document.selection.createRange();
	            range.moveStart("character",-oTxt1.value.length);
	            position=range.text.length;
	        }
	        
	    	if($("#hideTrain").val() != null && $("#hideTrain").val() != ""){
	    		temp = $("#hideTrain").val();
	    	}
	        
	        var e = /[0-9]/;
	        if(temp == "" || temp == null){
	        	if(!e.exec(value.substring(position-1,position))){
	        		$("#trainTime").val(value.substring(0,position-1) + value.substring(position,value.length));
	        	}
	        }else{
	        	if(e1.exec(temp)){
	        		if(!e.exec(value.substring(position-1,position))){
	        			$("#trainTime").val(value.substring(0,position-1) + value.substring(position,value.length));
	        		}
	        	}else{
	        		if(!e.exec(value.substring(position-1,position)) && !e1.exec(value.substring(position-1,position))){
	        			$("#tainTime").val(value.substring(0,position-1) + value.substring(position,value.length));
	        		}
	        	}
	        }
	        $("#hideTrain").val($("#trainTime").val());
	    }
	    
	    function checkInput_test(value){
	    	var temp = "";
	        var e1 = /\./;
	        
	    	var oTxt1 = document.getElementById("testTime");
	        var position=-1;
	        if(oTxt1.selectionStart){//非IE浏览器
	            position= oTxt1.selectionStart;
	        }else{//IE
	            var range = document.selection.createRange();
	            range.moveStart("character",-oTxt1.value.length);
	            position=range.text.length;
	        }
	        
	    	if($("#hideTest").val() != null && $("#hideTest").val() != ""){
	    		temp = $("#hideTest").val();
	    	}
	        
	        var e = /[0-9]/;
	        if(temp == "" || temp == null){
	        	if(!e.exec(value.substring(position-1,position))){
	        		$("#testTime").val(value.substring(0,position-1) + value.substring(position,value.length));
	        	}
	        }else{
	        	if(e1.exec(temp)){
	        		if(!e.exec(value.substring(position-1,position))){
	        			$("#testTime").val(value.substring(0,position-1) + value.substring(position,value.length));
	        		}
	        	}else{
	        		if(!e.exec(value.substring(position-1,position)) && !e1.exec(value.substring(position-1,position))){
	        			$("#testTime").val(value.substring(0,position-1) + value.substring(position,value.length));
	        		}
	        	}
	        }
	        $("#hideTest").val($("#testTime").val());
	    }
	    
	    function getJson(id, per, train, test, type, labelType, name){
	    	var obj = new Object();
	    	obj.id = id;
	    	obj.per = per;
	    	obj.train = train;
	    	obj.test = test;
	    	obj.type = type;
	    	obj.labelType = labelType;
	    	obj.name = name;
	    	return obj;
	    }
	    
	    function getSet(){
    		alert("1")
    		var id = $("#hideID").val();
    		var per = $("#percent").val();
    		var train = $("#hideTrain").val();
    		var test = $("#hideTest").val();
    		var type = $("#hideLabelType").val();
    		var labelType = $("input[name='labelType']:checked").val();
    		var name = $("#name").val();
    		
    		if(id == null || id == "" || name == "" || name == null || ((per == null || per == "") && (train == null || train == "" || test == null || test == ""))){
    			alert("输入的信息有误");
    		}else{
    			if(labelType == null){
    				alert("请选择使用的标注结果");
    			}else{
    				$.ajax({
        				url:"getSet.html?jsonArray=" + JSON.stringify(getJson(id, per, train, test, type, labelType, name)),
        				type:"get",
        				async:false,
        				dataType:"json",
        				
        				success:function(data){
        					if(data.error == null){
        						alert("1")
        						showWaveList(data.list);
        					}else{
        						alert(data.error);
        					}
        				},
        				error:function(data){
        					alert("连接失败");
        				}
        			});
    			}
    			
    		}
    	}	
	    
	    //获取gender的训练集
	    function getGenderSet(value){
	    	$.ajax({
	    		url:"getGenderResult.html?",
	    		type:"post",
	    		data:{"flag": value},
	    		async:false,
	    		
	    		success:function(data){
	    			var li = [];
	    			$.each(data,function(ret, v){
	    				li.push("<div class='col-xs-6' align='right'>" + ret.result + "：" + ret.time + "</div>");
	    				li.push("<div class='col-xs-6'><input type='text' name='labelSet" + value + "'></div>");
	    			});
	    			$("#getGenderSet").html(li.join(""));
	    		},
	    		error:function(data){
	    			alert(data);
	    		}
	    	});
	    }
	    
    </script>
</body>
</html>