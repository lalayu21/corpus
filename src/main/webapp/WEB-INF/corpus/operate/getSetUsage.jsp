<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>
	<table class="table table-condensed table-bordered">
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
	<script type="text/javascript">
	</script>
</body>
</html>