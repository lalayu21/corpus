<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style>
    	.list{
    		border: 1px solid gray;
    		text-align: center;
    		vertical-align: middle;
    	}
    </style>
</head>

<body>
	<table class="table table-condensed table-bordered">
		<thead>
			<tr>
				<th class="list">序号</th>
				<th class="list">名称</th>
				<th class="list">所有数据</th>
				<th class="list">训练数据(h)</th>
				<th class="list">测试数据(h)</th>
				<th class="list">使用次数</th>
				<th class="list">状态</th>
			</tr>
		</thead>
		<tbody id="listBody">
			<c:forEach items="${list }" var="list" varStatus="status">
				<tr>
					<td class="list">${status.index + 1 }</td>
					<td class="list">${list.name }</td>
					<c:choose>
						<c:when test="${list.type == 0 }">
							<td class="list">是</td>
						</c:when>
						<c:otherwise>
							<td class="list">否</td>
						</c:otherwise>
					</c:choose>
					<td class="list">${list.train }</td>
					<td class="lsit">${list.test }</td>
					<td class="list"><a data-toggle="modal" data-target="#useTime" onclick="getUseTime(${list.id})">${list.useTime }</a></td>
					<td class="list">
						<c:choose>
							<c:when test="${list.flag == '0' })">
								正在生成
							</c:when>
							<c:otherwise>已生成</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<script type="text/javascript">
	
		//获取某个训练集的使用情况
		function getUseTime(id){
			alert(id)
			$("#hideSetID").val(id);
			alert($("#hideSetID").val())
			if(id == null || id == ""){
				alert("输入数据有误");
			}else{
				$.ajax({
					url:"usage.html?id=" + id,
					type:"get",
					async:false,
					dataType:"json",
					
					success:function(data){
						alert("成功");
						showUsage(data);
					},
					error:function(data){
						alert("连接失败");
					}
				});
			}
		}
		
		//将获取的训练集的使用情况显示在table中
		function showUsage(ret){
			$("#usageBody").html("");
			var list = ret.list;
			$.each(list, function(index, v){
				var number = index + 1;
				var li = [];
				li.push("<tr><td class='list'>" + number + "</td>");
				li.push("<td class='list'>" + v.user + "</td>");
				li.push("<td class='list'>" + v.time + "</td>");
				li.push("<td class='list'>" + v.result + "</td>");
				li.push("<td class='list'>" + v.desp + "</td>");
				li.push("<td class='list'><a data-toggle='modal' data-target='#useSet' onclick='getUsageById(" + v.id + ")'>修改</a></td>");
				if(v.flag == 0){
					li.push("<td class='list'>正在导出</td></tr>");
				}else{
					li.push("<td class='list'>导出完成</td></tr>");
				}
				$("#usageBody").append(li.join(""));
			});
		}
		
		function setNewUsage(flag){
			$("#confirmSave").attr("name",flag);
		}
		
		function getUsageById(id){
			if(id != null || id != ""){
				$("#hideUsageID").val(id);
				$.ajax({
					url:"getUsageById.html?id=" + id,
					type:"get",
					async:false,
					dataType:"json",
					
					success:function(data){
						if(data.error == null){
							var obj = data.obj;
							$("#username").val(obj.user);
					    	$("#desp").val(obj.desp);
					    	$("#wer").val(obj.result);
						}else{
							alert(data.error);
						}
					},
					error:function(data){
						alert("连接失败");
					}
				});
				setNewUsage(1);
			}else{
				alert("输入的信息有误");
			}
		}
	</script>
</body>
</html>