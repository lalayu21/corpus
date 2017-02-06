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
    <script src="../js/sign/sign.js"></script>
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
    </style>
</head>

<body>
	<div class="col-xs-10 col-xs-offset-1 leadin">
		<jsp:include page="../navigation.jsp"></jsp:include>
	</div>
	
	<div class="col-xs-10 col-xs-offset-1" align="center">
		<h1>${corpus.name }</h1>
		<input type="hidden" id="hiddenCorpusId" value="${id }">
	</div>
	
	<div class="col-xs-1 col-xs-offset-1">
		<ul style="margin: 0;padding: 0">
			<li class="li"><a href="#" style="color:black">基本信息</a></li>
			<li class="li"><a href="waves.html?id=${id }&type=${type}&labelType=${corpus.labelType }&name=${corpus.name }">音频列表</a></li>
			<li class="li"><a href="../label/action.html?id=${id }">标注管理</a></li>
			<li class="li"><a href="../training/list.html?id=${id }&type=${type}&labelType=${corpus.labelType}">训练集管理</a></li>
			<li class="li"><a href="../feature/action.html?id=${id }">特征管理</a></li>
		</ul>
	</div>
	
	<div class="col-xs-8 leadin" style="margin-bottom: 10px;margin-top: 10px;">
		
		<div id="detailsPage" class="col-xs-8 col-xs-offset-2" style="padding: 0px;">
			<table class="table table-bordered table-condense">
				<tr>
					<td class="col-xs-1">名称</td>
					<td class="col-xs-9">${corpus.name }</td>
				</tr>
				
				<tr>
					<td>信息</td>
					<td>
						<div>
							<c:choose>
								<c:when test="${corpus.type == '0' }">生语料</c:when>
								<c:otherwise>熟语料</c:otherwise>
							</c:choose>
						</div>
							
						</div>
						<div>
							<c:choose>
								<c:when test="${corpus.labelType == '0' }">wavetagger标注</c:when>
								<c:when test="${corpus.labelType == '1' })">praat标注</c:when>
								<c:otherwise>文件夹标注</c:otherwise>
							</c:choose>
						</div>
						
						<div>已标注：
							<c:if test="${corpus.context == '1' }">音频内容</c:if>
							<c:if test="${corpus.gender == '1' }">性别</c:if>
							<c:if test="${corpus.language == '1' }">语种</c:if>
							<c:if test="${corpus.speaker == '1' }">说话人</c:if>
							<c:if test="${corpus.effective == '1' }">有效语音</c:if>
							<c:if test="${corpus.labelType == '2' }">（
								<c:forEach items="${fileResult }" var="file" varStatus="status">
									<c:if test="${status.index != 0 }">、</c:if>${file.result }
								</c:forEach>）
							</c:if>
						</div>
						<div>${corpus.wavePath }</div>
						<div>${corpus.labelPath }</div>
					</td>
				</tr>
				<tr>
					<td>描述</td>
					<td>${corpus.desp }</td>
				</tr>
				<tr>
					<td>操作</td>
					<td>
						<a href="../feature/getFeature.html?id=${id }">
							<button class="btn btn-primary btn-xs" style="margin-top: 5px;margin-bottom: 5px;">提特征</button>
						</a>
						
						<span id="getFeature">0次</span>
						
						<br>
						
						<a href="label.html?id=${id }">
							<button class="btn btn-primary btn-xs" style="margin-top: 5px;margin-bottom: 5px;">标注</button>
						</a>
						
						<span id="lable">0次</span>
						
						<br>
						
						<a href="getSet.html?id=${id }">
							<button class="btn btn-primary btn-xs" style="margin-top: 5px;margin-bottom: 5px;">生成训练集和测试集</button>
						</a>
						
						<span id="getSet">0次</span>
						
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	
    <script type="text/javascript">
    
    $(document).ready(function(){
		document.getElementById("welcome_admin").style.display = "none";
		document.getElementById("param_admin").style.display = "none";
		document.getElementById("user_admin").style.display = "none";
		document.getElementById("corpus_admin").style.display = "block";
	});
    
    	/* 提特征；
    	特征路径，IP，username，password */
    	function getFeature(id){
    		
    	}
    	
    	/* 标注
    	标注导出路径，IP，username，password，标注格式 */
    	function label(id){
    		
    	}
    	
    	/* 生成训练集和测试集
    	文档存放路径，IP，username，password，训练集和测试集的比例 */
    	function getSet(id){
    		
    	}
    </script>
</body>
</html>