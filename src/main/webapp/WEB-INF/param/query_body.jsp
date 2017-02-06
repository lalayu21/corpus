<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>参数列表</title>
	<link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/jquery-2.1.4.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/sign/sign.js"></script>
    <style type="text/css">
    	.tableBody{
    		border: solid 1px gray;
    		text-align: center;
    		vertical-align: middle;
    	}
    </style>
</head>

<body>
	<table class="table table-bordered table-striped table-condensed">
        <thead>
        <tr>
            <th class="tableBody">序号</th>
            <th class="tableBody">参数名</th>
            <th class="tableBody">参数值</th>
            <th class="tableBody">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${param }" var="param" varStatus="status">
            <tr>
                <td class="tableBody">${status.index + 1 }</td>
                <td class="tableBody">${param.name }</td>
                <td class="tableBody">${param.value }</td>
                <td class="tableBody">
                    <a href="changeInfo.html?id=${param.id }" target="_blank">修改</a>
                    <a onclick="deleteParam(${param.id},${param.name })">删除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <script type="text/javascript">
    	function deleteParam(id, name){
    		if(confirm("确定要删除参数\"" + name + "\"吗？")){
    			$.ajax({
    				type:"post",
    				url:"?id=" + id,
    				async:false,
    				
    				success:function(data){
    					if(data == '0'){
    						alert("删除成功");
    						window.location.href = "";
    					}else{
    						if(data == '1'){
    							alert("系统原因，请刷新页面重试");
    						}else{
    							if(data == '2'){
    								alert("该参数正在使用中，无法删除");
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
    </script>
</body>
</html>