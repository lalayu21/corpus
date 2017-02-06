<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>参数列表</title>
    <script src="../js/sign/sign.js"></script>
    <style type="text/css">
    	.tableBody{
    		border: solid 1px gray;
    		text-align: center;
    		vertical-align: middle;
    	}
    	.manual{
    		margin-left: 5px;
    		margin-right: 5px;
    	}
    </style>
</head>

<body>
	<table class="table table-bordered table-striped table-condensed">
        <thead>
        <tr>
            <th class="tableBody">序号</th>
            <th class="tableBody">名称</th>
            <th class="tableBody">音频内容</th>
            <th class="tableBody">性别</th>
            <th class="tableBody">说话人</th>
            <th class="tableBody">语种</th>
            <th class="tableBody">有效语音</th>
            <th class="tableBody">备注</th>
            <th class="tableBody">状态</th>
        </tr>
        </thead>
        <tbody id="corpusTable">
        <c:forEach items="${corpus }" var="corpus" varStatus="status">
            <tr>
                <td class="tableBody">${status.index + 1 }</td>
                <td class="tableBody"><a href="detail.html?id=${corpus.id }&type=${corpus.labelType}" target="_blank">${corpus.name }</a></td>
                <c:choose>
                	<c:when test="${corpus.context == '1' }">
                		<td class="tableBody" style="color:red">已标注</td>
                	</c:when>
                	<c:otherwise>
                		<td class="tableBody">未标注</td>
                	</c:otherwise>
                </c:choose>
                <c:choose>
                	<c:when test="${corpus.gender == '1' }">
                		<td class="tableBody" style="color:red">已标注</td>
                	</c:when>
                	<c:otherwise>
                		<td class="tableBody">未标注</td>
                	</c:otherwise>
                </c:choose>
                <c:choose>
                	<c:when test="${corpus.speaker == '1' }">
                		<td class="tableBody" style="color:red">已标注</td>
                	</c:when>
                	<c:otherwise>
                		<td class="tableBody">未标注</td>
                	</c:otherwise>
                </c:choose>
                <c:choose>
                	<c:when test="${corpus.language == '1' }">
                		<td class="tableBody" style="color:red">已标注</td>
                	</c:when>
                	<c:otherwise>
                		<td class="tableBody">未标注</td>
                	</c:otherwise>
                </c:choose>
                <c:choose>
                	<c:when test="${corpus.effective == '1' }">
                		<td class="tableBody" style="color:red">已标注</td>
                	</c:when>
                	<c:otherwise>
                		<td class="tableBody">未标注</td>
                	</c:otherwise>
                </c:choose>
                <td class="tableBody">${corpus.desp }</td>
                <td class="tableBody">
                	<c:choose>
                		<c:when test="${corpus.flag == '0' }">
                			未创建完成
                		</c:when>
                		<c:otherwise>
                			创建完成
                		</c:otherwise>
                	</c:choose>
                	<%-- <a class="manual" href="../label/action.html?id=${corpus.id }&type=${corpus.labelType}" target="_blank">标注</a>
                	<a class="manual" href="../feature/action.html?id=${corpus.id }&type=${corpus.labelType}" target="_blank">提特征</a>
                	<a class="manual" href="../training/action.html?id=${corpus.id }&type=${corpus.labelType}" target="_blank">获取训练集测试集</a> --%>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <script type="text/javascript">
    	
    </script>
</body>
</html>