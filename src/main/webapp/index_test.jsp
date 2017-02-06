<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>详细信息</title>
    <script src="../js/jquery-2.1.4.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="css/bootstrap.min.css">
    <style>
    </style>
</head>

<body>
		<div><a>ni</a></div>
	<div class="col-xs-1 col-xs-offset-1">
		<a>标注管理人员</a>
	</div>
	<!-- 显示训练集列表 -->
	
	<!-- 显示使用情况 -->
	<div class="model fade" id="useTime" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	        <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title">这里是name的使用情况</h4>
	            </div>
	            <div class="modal-body">
	            	<table class="table">
						<thead>
							<tr>
								<th class="list">序号</th>
								<th class="list">人员</th>
								<th class="list">时间</th>
								<th class="list">词错误率</th>
								<th class="list">备注</th>
								<th class="list">操作</th>
							</tr>
						</thead>
						<tbody id="usageBody"></tbody>
					</table>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	            </div>
	        </div><!-- /.modal-content -->
	        </div>
	</div>
	<!-- 使用训练集 -->
	<!-- <div class="model fade" id="useSet" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title">使用训练集</h4>
	            </div>
	            <div class="modal-body">
	            	<table class="table table-condensed table-bordered">
	            		<tr>
	            			<td class="list">使用者：</td>
	            			<td class="list">
	            				<input type="text" id="username" class="form-control">
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="list">WER：</td>
	            			<td class="list">
	            				<input type="text" id="wer" class="form-control">
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="list">描述：</td>
	            			<td class="list">
	            				<textarea rows="3" cols="" class="col-xs-6" id="desp"></textarea>
	            			</td>
	            		</tr>
	            	</table>
	            </div>
	            <div class="modal-footer">
	            	<button class="btn btn-success" id="confirmSave" name="0" style="margin-right: 20px;" onclick="confrimUse()">确定</button>
	            	<button class="btn btn-default" data-dismiss="modal" style="margin-left: 20px;">取消</button>
	            </div>
	        </div>/.modal-content
	    </div>
	</div> -->
	
	
    <script type="text/javascript">

    
    </script>
</body>
</html>