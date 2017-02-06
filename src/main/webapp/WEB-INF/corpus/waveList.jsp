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
    
    <script src="../js/wavesurfer/dist/wavesurfer.js"></script>
    <style>
        .li{
    		list-style-type: none;
    		display: inline-block;
    		margin: 0px;
    		padding: 0;
    	}
    	.p{
    		color:red;
    		display: none;
    	}
    	/* .tableBody{
    		border: solid 1px gray;
    		text-align: center;
    		vertical-align: middle;
    	} */
    	a:hover{
    		cursor: pointer;
    		background-color: #dcdcdc;
    	}
    	.page{
    		padding-left: 5px;
    		padding-right: 5px;
    		border: 1px solid #dcdcdc;
    		margin: 0px;
    	}
    </style>
</head>

<body>

	<div class="col-xs-10 col-xs-offset-1 leadin">
		<jsp:include page="../navigation.jsp"></jsp:include>
	</div>
	
    <div class="col-xs-10 col-xs-offset-1" align="center" style="margin-top: 10px;">
		<h1>${name }</h1>
		<input type="hidden" id="hiddenCorpusId" value="${corpus }">
		<input type="hidden" id="hiddenWaveId">
		<input type="hidden" id="hiddenLabelType" value="${labelType }"><!-- 标注工具 -->
		<input type="hidden" id="hiddenType"><!-- 标注的类型 -->
		<input type="hidden" id="hiddenLabelTypeName">
		<input type="hidden" id="hiddenWavePath" value="${path }">
		<input type="hidden" id="hiddenPage" value="1">
	</div>
	
	
	<div class="col-xs-1 col-xs-offset-1" style="margin-top: 10px;font-size: medium;">
		<ul style="margin: 0;padding: 0">
			<li class="li"><a href="detail.html?id=${corpus }&type=${type}">基本信息</a></li>
			<li class="li"><a href="#" style="color:black">音频列表</a></li>
			<li class="li"><a href="../label/action.html?id=${corpus }">标注管理</a></li>
			<li class="li"><a href="../training/list.html?id=${corpus }&type=${type}&labelType=${labelType}">训练集管理</a></li>
			<li class="li"><a href="../feature/action.html?id=${corpus }">特征管理</a></li>
		</ul>
	</div>
	
	
	<div class="col-xs-10 leadin" style="margin-bottom: 10px;margin-top: 10px;">
		
			<div class="col-xs-8" id="waveDetail">
			<div class="col-xs-12" align="left" style="margin-bottom: 10px;margin-top: 50px;">
				<div class="col-xs-3" style="margin: 0;padding: 0;">标注内容：</div>
				<c:if test="${labelType == '0' }">
					<c:if test="${context == '1' }">
						<button class="btn btn-gray btn-xs" id="labelTypeContext" name="2" onclick="changeType(this.name,this.id)">音频内容</button>
					</c:if>
					<c:if test="${gender == '1' }">
					<button class="btn btn-gray btn-xs" id="labelTypeGender" name="3" onclick="changeType(this.name,this.id)">性别</button>
					</c:if>
					<c:if test="${language == '1'}">
					<button class="btn btn-gray btn-xs" id="labelTypeLanguage" name="5" onclick="changeType(this.name,this.id)">语种</button>
					</c:if>
					<c:if test="${speaker == '1' }">
					<button class="btn btn-gray btn-xs" id="labelTypeSpeaker" name="4" onclick="changeType(this.name,this.id)">说话人</button>
					</c:if>
					<c:if test="${effective == '1'}">
					<button class="btn btn-gray btn-xs" id="labelTypeEffective" name="6" onclick="changeType(this.name,this.id)">有效语音</button>
					</c:if>
				</c:if>
				<c:if test="${labelType == '2' }">
					<c:forEach items="${fileResult }" var="file" varStatus="status">
						<button class="btn btn-gray btn-xs" id="fileResult${file.id }" onclick="changeFileResult(this.id)">${file.result }</button>
					</c:forEach>
				</c:if>
			</div>
			<div class="detailbody" id="detailbody" style="display:none;">
			<div id="waveform" class="col-xs-12" style="height: 128px;border: gray solid 1px;margin: 0px;padding: 0px;">
				<div class="progress progress-striped active" id="progress-bar">
					<div class="progress-bar progress-bar-info"></div>
				</div>
			</div>
			<div class="wavesurfer col-xs-12">
				<button class="wavesurfer" id="play" name="0" onfocus="playAudio(this.name, this.id)">播放/暂停</button>
				<c:if test="${labelType == '0' }">
					<div class="col-xs-12" style="padding: 0;margin-bottom: 10px;padding-right:10px;" align="right">
						当前为第<span id="piece">0</span>条
						<button class="btn btn-primary btn-xs" id="pre" name="0" onclick="getPre(this.name)">上一条</button>
						<button class="btn btn-primary btn-xs" id="next" name="0" onclick="getNext(this.name)">下一条</button>
					</div>
				</c:if>
			</div>
			
			<div class="col-xs-12" style="border: gray solid 1px;padding: 0px;">
				<div class="col-xs-4" style="padding: 0;margin: 0;">标注结果：</div>
					<div class="col-xs-4" align="right">
						<a id="labeledChange" onclick="changeResult(${labelType})">修改标注结果</a>
						<button class="btn btn-success btn-xs" id="labeledSave" style="display: none;" onclick="saveResult(${labelType})">保存</button>
						<button class="btn btn-xs" id="labeledConcel" style="display: none;" onclick="concelResult(${labelType})">取消</button>
					</div>
					<div class="col-xs-3" align="right">
						<a id="labeledDelete" onclick="deleteResult(${labelType})" style="color:red">删除音频</a>
					</div>
					
				<div class="col-xs-12" style="padding: 0;margin-left: 20px;margin-bottom: 10px;">
					<div id="notFileLabeled">
						<textarea id="context" rows="3" class="col-xs-11" disabled="disabled"></textarea>
						<input type="hidden" id="hideContext">
					</div>
					<div id="fileLabled" style="display:none">
						<c:if test="${labelType == '2' }">
							<c:forEach items="${fileResult }" var="file" varStatus="status">
								<input type="radio" id="radioboxFile${file.id }">${file.result }
							</c:forEach>
							<input type="radio" id="radioFile0" onfocus="createResult(0)" onblur="createResult(1)">其他
							<input type="text" id="createResult" style="display: none;">
						</c:if>
					</div>
				</div>
				<c:choose>
					<c:when test="${labelType == '1' }">
						<div class="col-xs-12" style="padding: 0;margin: 10px;">其他：</div>
						<div class="col-xs-12" style="padding: 0;margin-left: 20px;margin-bottom: 10px;">
							<textarea id="other" rows="3" class="col-xs-11"></textarea>
							<input type="hidden" id="hideOther">
						</div>
					</c:when>
				</c:choose>
			</div>
			</div>
		</div>
		<div id="detailsPage" class="col-xs-3" style="margin: 0px;padding: 0px;display:none;">
			<div class="col-xs-12" style="margin-bottom: 20px;padding: 0;">
				<div class="col-xs-8" style="padding: 0;margin: 0;padding-right: 5px;">
					<input class="form-control" type="text" name="queryContext" id="queryContext">
				</div>
				<div class="xol-xs-2">
					<button class="btn btn-primary" onclick="selectByInput()">查找</button>
				</div>
			</div>
			<div class="col-xs-8 col-xs-offset-1" style="border: 2px solid #00ccff;height:400px;overflow-y: scroll;padding: 0;">
				<jsp:include page="querywave_body.jsp"></jsp:include>
			</div>
			<div id="pageTag" class="col-xs-12" style="margin-top: 20px;display:none">
				<ul style="margin: 0;padding: 0;">
					<li class="li">
						共<span id="totalPage">${number }</span>条
					</li>
		        	<li class="li">
		        		<a class="page" id="page1" onclick="changePage(this.id)">上一页</a>
		        	</li>
		        	<li class="li" id="currentPage">
		        		1
		        	</li>
		        	<li class="li">
		        		<a class="page" id="page2" onclick="changePage(this.id)">下一页</a>
		        	</li>
		        	<!-- <li class="li">
		        		<a class="page" id="page2" onclick="changePage(this.id)">2</a>
		        	</li>
		        	<li class="li">
		        		<a class="page" id="page3" onclick="changePage(this.id)">3</a>
		        	</li>
		        	<li class="li">
		        		<a class="page" id="page4" onclick="changePage(this.id)">4</a>
		        	</li>
		        	<li class="li">
		        		<a class="page" id="page5" onclick="changePage(this.id)">5</a>
		        	</li> -->
		        	<li class="li">
		        		<input type="text" id="inputPage" style="position: relative;width: 30px;">
		        		<a id="page3" onclick="changePage(this.id)">跳转</a>
		        	</li>
		        </ul>
			</div>
				
		</div>	
	</div>
	
	
    <script type="text/javascript">
    var wavesurfer = Object.create(WaveSurfer);
    // Init & load audio file
	
    var firstWave = "";
			document.addEventListener('DOMContentLoaded', function () {
			    var options = {
			        container     : document.querySelector('#waveform'),
			        waveColor     : 'violet',
			        progressColor : 'black',
			        cursorColor   : 'navy',
			        hideScrollbar : false
			    };

			    if (location.search.match('scroll')) {
			        options.minPxPerSec = 100;
			        options.scrollParent = true;
			    }

			    // Init
			    wavesurfer.init(options);
			    // Load audio from URL
			    if($("#hiddenLabelType").val() == '1'){
			    	wavesurfer.load(firstWave);
			    }
			    //wavesurfer.load('../wave/demo.wav');

			    // Regions
			    if (wavesurfer.enableDragSelection) {
			        wavesurfer.enableDragSelection({
			            color: 'rgba(0, 255, 0, 0.1)'
			        });
			    }
			});
			
		//音频播放、暂停
		function playAudio(flag, id){
			if(flag == '0'){
				wavesurfer.play();
				$("#" + id).attr("name", 1);
			}else
				wavesurfer.playPause();
		}
    
	
	    $(document).ready(function(){
			document.getElementById("welcome_admin").style.display = "none";
			document.getElementById("param_admin").style.display = "none";
			document.getElementById("user_admin").style.display = "none";
			document.getElementById("corpus_admin").style.display = "block";
			  		


			if($("#hiddenLabelType").val() == '1'){
				$("#detailbody").show();
				$("#detailsPage").show();
				var start = 0;
	    		var end = 0;
	    		if(10 < parseInt($("#totalPage").html())){
	    			end = 10;
	    		}else{
	    			end = parseInt($("#totalPage").html());
	    		}
				setTimeout(getWaveList($("#hiddenCorpusId").val(), $("#hiddenLabelType").val(), start, end),1000);
	    		$("#page1").css=("background-color","#dcdcdc");
	    		$("#hiddenPage").val("1");
			}  
		});
	    

    	// Play at once when ready
    	// Won't work on iOS until you touch the page
    	wavesurfer.on('ready', function () {
    	    wavesurfer.play();
    	});

    	// Report errors
    	wavesurfer.on('error', function (err) {
    	    console.error(err);
    	});

    	// Do something when the clip is over
    	wavesurfer.on('finish', function () {
    	    console.log('Finished playing');
    	});


    	/* Progress bar */
    	document.addEventListener('DOMContentLoaded', function () {
    	    var progressDiv = document.querySelector('#progress-bar');
    	    var progressBar = progressDiv.querySelector('.progress-bar');

    	    var showProgress = function (percent) {
    	        progressDiv.style.display = 'block';
    	        progressBar.style.width = percent + '%';
    	    };

    	    var hideProgress = function () {
    	        progressDiv.style.display = 'none';
    	    };

    	    wavesurfer.on('loading', showProgress);
    	    wavesurfer.on('ready', hideProgress);
    	    wavesurfer.on('destroy', hideProgress);
    	    wavesurfer.on('error', hideProgress);
    	});
    	
    	function getPre(name){
    		concelResult();
    		if(name == '0'){
    			alert("已经是第一条");
    		}else{
    			var temp = parseInt(name) - 1;
        		$("#piece").html(temp);
        		$("#pre").attr("name", temp);
        		$("#next").attr("name", temp);
        		
        		getWavePraat(idList[temp]);
    		}
    	}
    	
    	function getNext(name){
    		concelResult();
    		if(parseInt(name) == idList.length - 1){
    			alert("已经是最后一条");
    		}else{
    			var temp = parseInt(name) + 1;
        		
        		getWavePraat(idList[temp]);
        		$("#piece").html(temp);
        		$("#pre").attr("name",temp);
        		$("#next").attr("name",temp);
    		}
    	}
    	
    	function getWaveWavetagger(id){
    		concelResult();
    		var labelType = $("#hiddenLabelType").val();
    		$.ajax({
				url:"waveDetail.html?id=" + id + "&type=" + $("#hiddenType").val() + "&labelType=" + labelType,
				type:"get",
				async:false,
				dataType:"json",
				
				success:function(data){
					alert("http://159.226.177.176:8000/" + $("#hiddenWavePath").val() + "/" + data.wave);
					firstWave = "http://159.226.177.176:8000/" + $("#hiddenWavePath").val() + "/" + data.wave;
					alert(firstWave)
					document.getElementById("context").value = data.context;
					$("#hideContext").val(data.context);
					document.getElementById("other").value = data.other;
					$("#hideOther").val(data.other);
					wavesurfer.load("http://159.226.177.176:8000/" + $("#hiddenWavePath").val() + "/" + data.wave);
				},
				error:function(data){
					alert("连接失败");
				}
			});
    	}
    	
    	function getWavePraat(id){
    		concelResult();
    		if(id == null){
    			alert("该语料未标注此项结果");
    		}else{
        		var labelType = $("#hiddenLabelType").val();
        		$.ajax({
    				url:"wavePraatDetail.html?id=" + id + "&type=" + $("#hiddenType").val() + "&labelType=" + labelType,
    				type:"get",
    				async:false,
    				dataType:"json",
    				
    				success:function(data){
						alert("http://159.226.177.176:8000/" + $("#hiddenWavePath").val() + "/" + data.wave + ".wav");
    		    	    wavesurfer.load("http://159.226.177.176:8000/" + $("#hiddenWavePath").val() +"/" + data.wave + ".wav");
    					alert(data.result)
    					$("#context").val(data.result);
    					alert(data.result)
    					$("#hideContext").val(data.result);
    				},
    				error:function(data){
    					alert("连接失败");
    				}
    			});
    		}
    	}
    	
    	//修改标注结果
    	function changeResult(labelType){
    		$("#labeledChange").hide();
    		$("#labeledSave").show();
    		$("#labeledConcel").show();
    		if(labelType == '2'){
    			$("#notFileLabeled").hide();
    			$("#fileLabeled").show();
    		}else{
    			$("#context").attr("disabled",false);
    		}
    	}
    	
    	function saveResult(labelType){
    		var flag = 0;
    		if(labelType == '0'){
    			//praat and file
    			var value = $("#context").val();
    			var id = parseInt($("#pre").attr("name"));
    			id = idList[id];
    			if(value == null){
    				alert("不能为空");
    			}else{
    				$.ajax({
    					url:"changeResultPraat.html?id=" + id + "&result=" + value,
    					type:"get",
    					async:false,
    					dataType:"json",
    					
    					success:function(data){
    						if(data.error == null){
    							$("#hideContext").val(value);
        						flag = 1;
    						}else{
    							alert(data.error);
    						}
    					},
    					error:function(data){
    						alert("连接失败");
    					}
    				});
    			}
    		}else{
    			if(labelType == "1"){
    				//wavetagger
        			var value = $("#context").val();
        			var other = $("#other").val();
        			if(value == null || other == null){
        				alert("不能为空")
        			}else{
        				$.ajax({
        					url:"changeResultPraat.html?id=" + id + "&result=" + value + "&other=" + other,
        					type:"get",
        					async:false,
        					dataType:"json",
        					
        					success:function(data){
        						if(data.error == null){
        							alert(data.error);
        						}else{
        							alert(data.message);
        							flag = 1;
        						}
        					},
        					error:function(data){
        						alert("连接失败");
        					}
        				});
        			}
    			}else{
    				
    			}
    		}
    		
    		if(flag == 1){
    			$("#labeledChange").show();
        		$("#labeledSave").hide();
        		$("#labeledConcel").hide();
        		$("#hideContext").val($("#context").val());
        		$("#context").attr("disabled",true);
    		}else{
    			alert("保存失败");
    		}
    	}
    	
    	function concelResult(){
    		$("#labeledChange").show();
    		$("#labeledSave").hide();
    		$("#labeledConcel").hide();
    		$("#context").val($("#hideContext").val());
    		$("#context").attr("disabled",true);
    	}
    	
    	//改变file标注时的标注结果
    	function changeFileResult(id){
    		$("#detailsPage").show();
    		$("#detailbody").show();
    		var resultId = id.substring(10,id.length);
    		if($("#hiddenLabelTypeName").val() != null){
    			$("#" + $("#hiddenLabelTypeName").val()).attr("class", "btn btn-gray btn-xs");
    		}
    		$("#hiddenLabelTypeName").val(resultId);
			$("#" + id).attr("class", "btn btn-primary btn-xs");
			alert("btn btn-primary btn-xs")
    		$("#context").val("");
    		var labelType = $("#hiddenLabelType").val();
    			$("#piece").html("0");
            	$("#pre").attr("name","0");
            	$("#next").attr("name","0");
        		var temp = parseInt($("#piece").html());

    			var start = 0;
    	    	var end = 0;
    	    	if(10 < parseInt($("#totalPage").html())){
    	    		end = 10;
    	    	}else{
    	    		end = parseInt($("#totalPage").html());
    	    	}
    	    	//getWaveList($("#hiddenCorpusId").val(), $("#hiddenLabelType").val(), start, end);

    			getWaveList(resultId, '2', start, end);
    	}
    	
    	//改变标注类型
    	function changeType(name,id){
    		$("#detailsPage").show();
    		$("#detailbody").show();
    		concelResult();
    		if($("#hiddenLabelTypeName").val() != null && $("#hiddenLabelTypeName").val() != ""){
    			$("#" + $("#hiddenLabelTypeName").val()).attr("class","btn btn-gray btn-xs");
    		}
    		$("#hiddenLabelTypeName").val(id);
    		$("#" + id).attr("class","btn btn-primary btn-xs");
    		$("#context").val("");
    		var waveid = $("#hiddenWaveId").val();
    		var labelType = $("#hiddenLabelType").val();
    		if(name != $("#hiddenType").val()){

    			$("#hiddenType").val(name);
    			$("#piece").html("0");
            	$("#pre").attr("name","0");
            	$("#next").attr("name","0");
        		var temp = parseInt($("#piece").html());

    			var start = 0;
    	    	var end = 0;
    	    	if(10 < parseInt($("#totalPage").html())){
    	    		end = 10;
    	    	}else{
    	    		end = parseInt($("#totalPage").html());
    	    	}
    	    	getWaveList($("#hiddenCorpusId").val(), labelType, name, start, end);
    	    		
        		/* $.ajax({
            		url:"getIdList.html?id=" + waveid + "&type=" + $("#hiddenType").val(),
            		type:"get",
            		async:false,
            		dataType:"json",
            				
            		success:function(data){
            				//返回的是一条语音的所有片段的id
            			var id = data.idList;
            			idList = [];
            			for(var i = 0; i < id.length; i++){
            				idList.push(id[i]);
            			}
            				//获取第一条数据
            			getWavePraat(idList[0]);
            		},
            		error:function(data){
            			alert("连接失败");
            		}
            	}); */
    		}
    	}
    	
    	function deleteResult(type){
    		var id = $("#hiddenWaveId").val();
    		var labelType = $("#hiddenLabelType").val();
    		if(type = '0'){
    			id = idList[parseInt($("#piece").html())];
    		}
    		//删除该id的音频
    		if(id == null || id == "" || labelType == "" || labelType == null){
    			alert("请求失败");
    		}else{
    			$.ajax({
    				url:"delete.html?id=" + id + "&labelType=" + labelType,
    				type:"get",
    				async:false,
    				
    				success:function(data){
    					/* var wave = $("#waveList" + id).attr("id"); 
    					wave = wave.substring(8,wave.length); */
    					if(labelType == '0'){
    						var piece = $("#next").attr("name");
    						for(var i = piece; i < idList.length; i++){
    							idList[i] = idList[i+1];
    						}
    						getWavePraat(idList[piece]);
    					}else{
        					$("#waveList" + id).remove();
    					}
    				},
    				error:function(data){
    					alert("请求失败");
    				}
    			});
    		}
    	}
    	
    	function getWaveFile(id){
    		if(id == null || id == ""){
    			alert("输入的内容不正确");
    		}else{
    			$.ajax({
    				url:"waveFileDetail.html?id=" + id,
    				type:"get",
    				async:false,
    				dataType:"json",
    				
    				success:function(data){
    					if(data.message == null || data.message == ""){
    						alert("http://159.226.177.176:8000/" + $("#hiddenWavePath").val() + "/" + data.wave + ".wav");
        		    	    wavesurfer.load("http://159.226.177.176:8000/" + $("#hiddenWavePath").val() + "/" + data.wave + ".wav");
        					$("#context").val(data.result);
        					$("#fileLabeled").attr("name",data.resultID);
    					}
    				},
    				error:function(data){
    					alert("连接失败");
    				}
    			});
    		}
    	}
    	
    	//改变页码
    	function changePage(id){
    		var page = $("#hiddenPage").val();
    		var start = 0;
    		var end = 0;
    		
    		if(id == "page1"){
    			if(parseInt(page) > 1 ){
    				start = parseInt(page) * 10 - 20;
    				end = (start + 10) > parseInt($("#totalPage").html()) ? parseInt($("#totalPage").html()) : (start + 10);
    				page = parseInt(page) - 1;
    			}
    		}else{
    			if(id == "page2"){
    				if(parseInt(page) * 10 < parseInt($("#totalPage").html())){
        				end = ((parseInt(page) + 1) * 10) > parseInt($("#totalPage").html()) ? parseInt($("#totalPage").html()) : ((parseInt(page) + 1) * 10);
        				start = parseInt(page) * 10;
        				page = parseInt(page) + 1;
    				}
    			}else{
    				page = $("#inputPage").val();
    				if(parseInt(page) * 10 - 10 < parseInt($("#totalPage").html())){
    					start = parseInt(page) * 10 - 10;
    					end = (start + 10) > parseInt($("#totalPage").html()) ? parseInt($("#totalPage").html()) : (strat + 10);
    				}
    			}
    		}
    		
    		$("#currentPage").html(page);
    		$("#hiddenPage").val(page);
    		    		
    		if(start == 0 && end == 0){
        		getWaveList($("#hiddenCorpusId").val(), $("#hiddenLabelType").val(), start, end);
    		}
    		
    		/* if((id == "page4" && $("#page4").html() == '4') || (id == "page5" && $("#page5").html() == '5') || (id == "page1" && $("#page1").html() == '1') || (id == "page2" && $("#page2").html() == '2') || id == "page3"){
        		$("#" + $("#hiddenPage").val()).css=("background-color","white");
    			$("#hiddenPage").val(id);
        		$("#" + id).css=("background-color","#dcdcdc");
    		}else{
    			if()
    		} */
    	}
    	
    	//为文件夹分类方式标注的音频添加新的标注结果
    	function createResult(flag){
    		if(flag == 0){
    			$("#createResult").show();
    		}else{
    			$("#createResult").value("");
    			$("#createResult").hide();
    		}
    	}
    </script>
</body>
</html>