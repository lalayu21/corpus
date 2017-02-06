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
    <style>
    	.li{/* 
    		text-decoration: none; */
    		display: block;
    		font-size: medium;
    	}
    	.p{
    		display: inline;
    		color: red;
    	}
    	a:HOVER {
			cursor: pointer;
		}
		.listUseSet{
			width: 200px;
			padding-top: 5px;
			padding-bottom: 5px;
		}
		.listUseSet1{
			width: 200px;
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
				<a href="../training/action.html?id=${corpus }&type=${type}&labelType=${labelType}">
					<button class="btn btn-success">新建训练集</button>
				</a>
			</div>
		<input type="hidden" id="hiddenCorpusId" value="${corpus }">
	</div> 
		
	<div class="col-xs-1 col-xs-offset-1">
		<ul style="margin: 0;padding: 0">
			<li class="li"><a href="../data/detail.html?id=${corpus }&type=${type}">基本信息</a></li>
			<li class="li"><a href="../data/waves.html?id=${corpus }&type=${type}&labelType=${labelType }&name=${name }">音频列表</a></li>
			<li class="li"><a href="../label/action.html?id=${corpus }">标注管理</a></li>
			<li class="li"><a href="#" style="color:black">训练集管理</a></li>
			<li class="li"><a href="../feature/action.html?id=${corpus }">特征管理</a></li>
		</ul>
	</div>
	<!-- 显示训练集列表 -->
	<div class="col-xs-9">
		<jsp:include page="getSetListbody.jsp"></jsp:include>
	</div>
	
	<!-- 显示使用情况 -->
	<div class="modal fade" id="useTime" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title">这里是name的使用情况</h4>
	            </div>
	            <div class="modal-body">
	            	<table class="usageTable" style="width: 500px;]">
						<thead>
							<tr>
								<th class="list">序号</th>
								<th class="list">人员</th>
								<th class="list">时间</th>
								<th class="list">词错误率</th>
								<th class="list">备注</th>
								<th class="list">操作</th>
								<th class="list">状态</th>
							</tr>
						</thead>
						<tbody id="usageBody"></tbody>
					</table>
	            </div>
	            <div class="modal-footer">
	            	<a data-toggle="modal" data-target="#useSet" onclick="setNewUsage(0)">
	            		<button class="btn btn-success">使用</button>
	            	</a>
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            </div>
	        </div><!-- /.modal-content -->
		</div>
	</div>
	<!-- 使用训练集 -->
	<div class="modal fade" id="useSet" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title">使用训练集</h4>
	            </div>
	            <div class="modal-body">
	            	<table>
	            		<tr>
	            			<td class="listUseSet1" align="right">使用者：</td>
	            			<td class="listUseSet">
	            				<input type="text" id="username" class="form-control">
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="listUseSet1" align="right">数据存放服务器ip：</td>
	            			<td class="listUseSet">
	            				<input type="text" id="remote_ip" class="form-control">
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="listUseSet1" align="right">数据存放服务器用户名：</td>
	            			<td class="listUseSet">
	            				<input type="text" id="remote_username" class="form-control">
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="listUseSet1" align="right">数据存放服务器密码：</td>
	            			<td class="listUseSet">
	            				<input type="text" id="remote_password" class="form-control">
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="listUseSet1" align="right">数据存放目录：</td>
	            			<td class="listUseSet">
	            				<input type="text" id="remote_path" class="form-control">
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="listUseSet1" align="right">WER：</td>
	            			<td class="listUseSet">
	            				<input type="text" id="wer" class="form-control">
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="listUseSet1" align="right">描述：</td>
	            			<td class="listUseSet">
	            				<textarea rows="3" cols="" class="form-control" id="desp" style="resize:none"></textarea>
	            			</td>
	            		</tr>
	            	</table>
	            </div>
	            <div class="modal-footer">
	            	<button class="btn btn-success" id="confirmSave" name="0" style="margin-right: 20px;" onclick="confirmUse()">确定</button>
	            	<button class="btn btn-default" data-dismiss="modal" style="margin-left: 20px;">取消</button>
	            </div>
	        </div>
	    </div>
	</div>
	
	<div>
		<input type="hidden" id="hideSetID">
		<input type="hidden" id="hideUsageID">
	</div>
	
    <script type="text/javascript">
    

	    $(document).ready(function(){
			document.getElementById("welcome_admin").style.display = "none";
			document.getElementById("param_admin").style.display = "none";
			document.getElementById("user_admin").style.display = "none";
			document.getElementById("corpus_admin").style.display = "block";
		});
	    
	    //将username、desp、id转成json格式的字符串
	    function usageJSON(setID, username, wer, desp, flag, wavePath, ip, linuxUser, linuxPaw){
	    	var obj = new Object();
	    	obj.setID = setID;
	    	obj.username = username;
	    	obj.wer = wer;
	    	obj.desp = desp;
	    	obj.flag = flag;
	    	obj.wavePath = wavePath;
	    	obj.ip = ip;
	    	obj.linuxUser = linuxUser;
	    	obj.linuxPaw = linuxPaw;
	    	return obj;
	    }
	    
	    //将使用setID的信息写入数据库
	    function confirmUse(){
	    	
	    	var ip = $("#remote_ip").val();
	    	var linuxUser = $("#remote_username").val();
	    	var linuxPaw = $("#remote_password").val();
	    	
	    	var wavePath = $("#remote_path").val();
	    	
	    	alert(ip + "   " + linuxUser + "   " + linuxPaw + "   " + wavePath);
	    	
	    	
	    	var flag = $("#confirmSave").attr("name");
	    	var username = $("#username").val();
	    	var desp = $("#desp").val();
	    	var wer = $("#wer").val();
	    	var setID = 0;
	    	alert($("#hideSetID").val())
	    	if(flag == '0'){
	    		alert("n1")
		    	setID = $("#hideSetID").val();
	    	}else{
	    		alert("n2")
	    		if(flag == '1'){
		    		alert("n3")
			    	setID = $("#hideUsageID").val();
	    		}
	    	}
	    	alert(setID + "setid")
	    	
	    	if(setID == null || setID == "" || username == null || username == "" || wavePath == null || wavePath == "" || ip == null || ip == "" || linuxUser == null || linuxUser == "" || linuxPaw == "" || linuxPaw == null){
	    		alert("输入信息有误");
	    	}else{
	    		alert(setID, username, wer, desp, flag, wavePath, ip, linuxUser, linuxPaw);
	    		alert(JSON.stringify(usageJSON(setID, username, wer, desp, flag, wavePath, ip, linuxUser, linuxPaw)))
	    		$.ajax({
	    			url:"setUsage.html?jsonArray=" + JSON.stringify(usageJSON(setID, username, wer, desp, flag, wavePath, ip, linuxUser, linuxPaw)),
	    			type:"get",
	    			async:false,
	    			dataType:"json",
	    			
	    			success:function(data){
	    				if(data.error == null || data.error == ""){
	    					alert("成功");
	    					/* $("#desp").val("");
	    					$("#wer").val("");
	    					$("#username").val(""); */
	    					location.reload(true);
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
    
    </script>
</body>
</html>