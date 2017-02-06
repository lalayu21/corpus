<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>

	<table class="table table-condensed" style="padding: 0;margin: 0;">
        <thead>
        <tr>
            <!-- <th class="tableBody col-xs-2">序号</th> -->
            <!-- <th class="tableBody">名称</th> -->
        </tr>
        </thead>
        <tbody id="wavelisttable">
        
        <%-- <c:forEach items="${waves }" var="wave" varStatus="status">
            <tr id="waveList${wave.id }" style="height: 40px;">
                <td class="tableBody">${status.index + 1 }</td>
                <td class="tableBody">
                	<a onclick="getWaveDetail(${wave.id}, ${labelType })">${wave.wave }</a>
                	<a data-toggle="collapse" data-target="#wave_${wave.id }" onclick="getWaveDetail(${wave.id}, ${wave.labelType })">${wave.wave }</a>
                	<div id="wave_${wave.id }" style="margin: 0;padding: 0;" class="collapse showDetail">
                		<jsp:include page="praatBlock.jsp"></jsp:include>
                	</div>
                </td>
            </tr> 
        </c:forEach> --%>
        
        </tbody>
    </table>
    	
    
    <script type="text/javascript">
    
    	var idList = [];
    	
	    $(function () { $('.showDetail').collapse({
			toggle: false
		})});
	    
	    
	    function getWaveList(id, labelType, type, start, end){
			$("#pageTag").show();
	    	if(id === "" || id === "" || labelType == null || labelType == "" || start === null || start ==="" || end === null || end === ""){
	    		alert("信息有误");
	    	}else{
	    		$.ajax({
	    			url:"getWaveList.html?id=" + id + "&labelType=" + labelType + "&start=" + start + "&end=" + end + "&type=" + type,
	    			type:"get",
	    			async:false,
	    			dataType:"json",
	    			success:function(data){
	    				if(data.error == null || data.error == ""){
	    					var wave = data.wave;
	    					var li = [];
	    					$("#wavelisttable").html("");
	    					$.each(wave, function(index,v){
	    						li.push("<tr id='waveList" + v.id + "' style='height:40px;'>");
	    						li.push("<td class='tableBody'><a onclick='getWaveDetail(" + v.id + ")'>"+v.wave +"</a>");
	    						li.push("</td></tr>");
	    					});
	    					$("#wavelisttable").html(li.join(""));
	    					

	    					var waveid = $("#wavelisttable>:first").attr("id");
	    					$("#" + waveid).css("background-color","#dcdcdc");
	    					waveid = waveid.substring(8,waveid.length);
	    					$("#hiddenWaveId").val(waveid);
	    					
	    					getWaveDetail(waveid);
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
	    
	    
	    
	    
    	function getWaveDetail(id){
    		var labelType = $("#hiddenLabelType").val();
    		$("#waveList" + $("#hiddenWaveId").val()).css("background-color","white");
    		$("#waveList" + id).css("background-color","#dcdcdc");
    		$("#waveDetail").show();
    		concelResult();
    		if(id == null || labelType == null){
    			alert("信息有误，请刷新页面重试");
    		}else{
    			$("#hiddenWaveId").val(id);
    			//获取数据praat标注时id
    			var hiddenType = $("#hiddenType").val();
    			alert(labelType)
    			alert(hiddenType)
        		if(labelType == "0"){
        			if(hiddenType != null && hiddenType != ""){
        				alert(hiddenType)
            			$.ajax({
            				url:"getIdList.html?id=" + id + "&type=" + hiddenType,
            				type:"get",
            				async:false,
            				dataType:"json",
            				
            				success:function(data){
            					//返回的是一条语音的所有片段的id
            					var id = data.idList;
            					idList = [];
            					alert(id.length)
            					for(var i = 0; i < id.length; i++){
            						idList.push(id[i]);
            					}
            					//获取第一条数据
            					getWavePraat(idList[0]);
            	        		$("#piece").html(0);
            	        		$("#pre").attr("name",0);
            	        		$("#next").attr("name",0);
            				},
            				error:function(data){
            					alert("连接失败");
            				}
            			});
        			}
        			
        		}else{
        			if(labelType == '1')
        				getWaveWavetagger(id);
        			else
        				getWaveFile(id);
        		}
    			
    		}
    	}
    </script>
</body>
</html>