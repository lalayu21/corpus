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
            <th class="tableBody">用户名</th>
            <th class="tableBody">真实姓名</th>
            <th class="tableBody">身份证号</th>
            <th class="tableBody">邮箱</th>
            <th class="tableBody">手机号</th>
            <th class="tableBody">性别</th>
            <th class="tableBody">权限</th>
            <th class="tableBody">注册时间</th>
            <th class="tableBody">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${user }" var="user" varStatus="status">
            <tr>
                <td class="tableBody">${status.index + 1 }</td>
                <td class="tableBody">${user.username }</td>
                <td class="tableBody">${user.truename }</td>
                <td class="tableBody">${user.idCode }</td>
                <td class="tableBody">${user.email }</td>
                <td class="tableBody">${user.tel }</td>
                
                <c:choose>
                	<c:when test="${user.gender == 1 }">
                		<td class="tableBody">女</td>
                	</c:when>
                	<c:otherwise>
                		<td class="tableBody">男</td>
                	</c:otherwise>
                </c:choose>
                
                <c:choose>
                	<c:when test="${user.access == 1 }">
                		<td class="tableBody">管理员</td>
                	</c:when>
                	<c:otherwise>
                		<td class="tableBody">普通用户</td>
                	</c:otherwise>
                </c:choose>
                
                
                <td class="tableBody">${user.applytime }</td>
                <td class="tableBody">
                    <a onclick="pass(${user.id},1)" style="padding-right: 10px">通过</a>
                    <a onclick="pass(${user.id},3)" style="padding-left: 10px">不通过</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
</body>
</html>