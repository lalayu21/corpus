<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>导入数据</title>
	<link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/jquery-2.1.4.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <script src="../js/sign/sign.js"></script>
    <style>
        li{
    		list-style-type: none;
    		display: inline;
    	}
    	.p{
    		color:red;
    		display: none;
    	}
    </style>
</head>

<body>
	<div class="col-xs-10 col-xs-offset-1 leadin">
		<jsp:include page="../navigation.jsp"></jsp:include>
	</div>
	<div class="col-xs-1 col-xs-offset-1 leadin" style="margin-top: 20px;margin-bottom: 10px;">
		<ul style="margin: 0;padding: 0;">
            <li><a href="in.html" style="color: black;"><img src="../image/right.png" style="width:10px;height:10px;">创建语料库</a></li>
            <li><a href="corpusList.html"><img src="../image/right.png" style="width:10px;height:10px;">语料库列表</a></li>
        </ul>
	</div>
	
	<div class="col-xs-10 leadin" style="margin-bottom: 10px;margin-top: 20px;">
		<div id="corpusName" class="col-xs-12 leadin">
			<div class="col-xs-3 leadin" align="right" style="vertical-align: middle;">语料库名称：</div>
			<div class="col-xs-9 leadin">
				<input class="leadin col-xs-7" type="text" name="corpus_name" id="corpus_name" onblur="checkName(this.value, this.id)" oninput="nameInput()">
				<p class="p" id="nameExist">语料库名称已经存在</p><p class="p" id="nameNull">语料库名称不能为空</p>
			</div>
		</div>
		
		<div><p> </p></div>
		
		<!-- 选择了语料类型之后
			生语料：只有音频位置
			熟语料：音频+标注位置
		 -->
		<div id="corpusType" class="col-xs-12 leadin" style="margin-bottom: 10px;margin-top: 10px;">
			<div class="leadin col-xs-3" align="right">类型：</div>
			<div class="leadin col-xs-9">
				<div style="margin: 0px;padding: 0px;display: inline;margin-right: 10px;"><input class="leadin" type="radio" name="corpus_type" id="corpus_type_no" onclick="corpus_type_no()" value="0">生语料</div>
				<div style="margin: 0px;padding: 0px;display: inline;margin-left: 10px;margin-right: 10px;"><input class="leadin" type="radio" name="corpus_type" id="corpus_type_yes" onclick="corpus_type_yes()" value="1">熟语料</div>
				<p class="p" id="typeNull">请选择音频是否标注</p>
			</div>
		</div>
		
		<div id="corpusLabelType" class="col-xs-12 leadin" style="margin-bottom: 10px;margin-top: 10px;">
			<div class="leadin col-xs-3" align="right">标注工具：</div>
			<div class="leadin col-xs-9">
				<div style="margin: 0px;padding: 0px;display: inline;margin-right: 10px;"><input class="leadin" type="radio" name="corpus_labelType" id="corpus_labelType_praat" value="0" checked="checked" onclick="changeLabelType(this.value)">praat</div>
				<div style="margin: 0px;padding: 0px;display: inline;margin-left: 10px;margin-right: 10px;"><input class="leadin" type="radio" name="corpus_labelType" id="corpus_labelType_wavetagger" value="1" onclick="changeLabelType(this.value)">wavetagger</div>
				<div style="margin: 0px;padding: 0px;display: inline;margin-left: 10px;margin-right: 10px;"><input class="leadin" type="radio" name="corpus_labelType" id="corpus_labelType_file" value="2" onclick="changeLabelType(this.value)">文件夹分类</div>
			</div>
		</div>
		
		<div id="corpusLabelResultWavetagger" class="col-xs-12 leadin" style="margin-bottom: 10px;margin-top: 10px;display:none">
			<div class="leadin col-xs-3" align="right">标注结果：</div>
			<div class="leadin col-xs-9">
				<div style="margin: 0px;padding: 0px;display: inline;margin-right: 10px;"><input class="leadin" type="checkbox" name="corpus_labelResultWavetagger" id="corpus_labelResult_contextWavetagger" onclick="corpus_labelResult_genderWavetagger(this.id)" value="0">音频内容</div>
				<div style="margin: 0px;padding: 0px;display: inline;margin-left: 10px;margin-right: 10px;"><input class="leadin" type="checkbox" name="corpus_labelResultWavetagger" id="corpus_labelResult_genderWavetagger" onclick="corpus_labelResult_genderWavetagger(this.id)" value="1">性别</div>
				<div style="margin: 0px;padding: 0px;display: inline;margin-left: 10px;margin-right: 10px;"><input class="leadin" type="checkbox" name="corpus_labelResultWavetagger" id="corpus_labelResult_personWavetagger" onclick="corpus_labelResult_genderWavetagger(this.id)" value="2">说话人</div>
				<div style="margin: 0px;padding: 0px;display: inline;margin-left: 10px;margin-right: 10px;"><input class="leadin" type="checkbox" name="corpus_labelResultWavetagger" id="corpus_labelResult_languageWavetagger" onclick="corpus_labelResult_genderWavetagger(this.id)" value="3">语种</div>
				<div style="margin: 0px;padding: 0px;display: inline;margin-left: 10px;margin-right: 10px;"><input class="leadin" type="checkbox" name="corpus_labelResultWavetagger" id="corpus_labelResult_soundWavetagger" onclick="corpus_labelResult_genderWavetagger(this.id)" value="4">有效话音</div>
				<p class="p" id="labelTypeNullWavetagger">请选择标注类型</p>
			</div>
		</div>
		
		<div id="corpusLabelResultFile" class="col-xs-12 leadin" style="margin-bottom: 10px;margin-top: 10px;display:none">
			<div class="leadin col-xs-3" align="right">标注结果：</div>
			<div class="leadin col-xs-9">
				<div style="margin: 0px;padding: 0px;display: inline;margin-right: 10px;"><input class="leadin" type="radio" name="corpus_labelResultFile" id="corpus_labelResult_contextFile" onclick="corpus_labelResult_genderFile(this.id)" value="0">音频内容</div>
				<div style="margin: 0px;padding: 0px;display: inline;margin-left: 10px;margin-right: 10px;"><input class="leadin" type="radio" name="corpus_labelResultFile" id="corpus_labelResult_genderFile" onclick="corpus_labelResult_genderFile(this.id)" value="1">性别</div>
				<div style="margin: 0px;padding: 0px;display: inline;margin-left: 10px;margin-right: 10px;"><input class="leadin" type="radio" name="corpus_labelResultFile" id="corpus_labelResult_personFile" onclick="corpus_labelResult_genderFile(this.id)" value="2">说话人</div>
				<div style="margin: 0px;padding: 0px;display: inline;margin-left: 10px;margin-right: 10px;"><input class="leadin" type="radio" name="corpus_labelResultFile" id="corpus_labelResult_languageFile" onclick="corpus_labelResult_genderFile(this.id)" value="3">语种</div>
				<div style="margin: 0px;padding: 0px;display: inline;margin-left: 10px;margin-right: 10px;"><input class="leadin" type="radio" name="corpus_labelResultFile" id="corpus_labelResult_soundFile" onclick="corpus_labelResult_genderFile(this.id)" value="4">有效话音</div>
				<p class="p" id="labelTypeNullFile">请选择标注类型</p>
			</div>
		</div>
		
		<div id="corpusWavePath" class="col-xs-12 leadin" style="margin-bottom: 10px;margin-top: 10px;">
			<div class="leadin col-xs-3" align="right" style="vertical-align: middle;">音频文件路径：</div>
			<div class="leadin col-xs-9">
				<div class="col-xs-3" style="margin-top: 5px;">音频文件服务器ip：</div>
				<div class="col-xs-9" style="margin-top: 5px;">
					<input class="leadin col-xs-5" type="text" name="corpus_waveIp" id="corpus_waveIp" onblur="checkWaveIp(this.value)" oninput="waveIpInput()">
					<p class="p" id="waveIpNull">音频文件ip不能为空</p>
				</div>
				<div class="col-xs-3" style="margin-top: 5px;">音频文件服务器用户名：</div>
				<div class="col-xs-9" style="margin-top: 5px;">
					<input class="leadin col-xs-5" type="text" name="corpus_waveUsername" id="corpus_waveUsername" onblur="checkWaveUsername(this.value)" oninput="waveUsernameInput()">
					<p class="p" id="waveUsernameNull">音频文件服务器用户名不能为空</p>
				</div>
				<div class="col-xs-3" style="margin-top: 5px;">音频文件服务器密码：</div>
				<div class="col-xs-9" style="margin-top: 5px;">
					<input class="leadin col-xs-5" type="text" name="corpus_wavePassword" id="corpus_wavePassword" onblur="checkWavePassword(this.value)" oninput="wavePasswordInput()">
					<p class="p" id="wavePasswordNull">音频文件服务器密码不能为空</p>
				</div>
				<div class="col-xs-3" style="margin-top: 5px;">音频文件路径：</div>
				<div class="col-xs-9" style="margin-top: 5px;">
					<input class="leadin col-xs-5" type="text" name="corpus_wavePath" id="corpus_wavePath" onblur="checkWavePath(this.value)" oninput="waveInput()">
					<p class="p" id="wavePathNull">音频文件路径不能为空</p>
				</div>
			</div>
		</div>
		
		<div id="corpusLabelPath" class="col-xs-12 leadin" style="margin-bottom: 10px;margin-top: 10px;">
			<div class="leadin col-xs-3" align="right" style="vertical-align: middle;">标注文件路径：</div>
			<div class="leadin col-xs-9">
				<div class="col-xs-3" style="margin-top: 5px;">标注文件服务器ip：</div>
				<div class="col-xs-9" style="margin-top: 5px;">
					<input class="leadin col-xs-5" type="text" name="corpus_labelIp" id="corpus_labelIp" onblur="checkLabelIp(this.value)" oninput="labelIpInput()">
					<p class="p" id="labelIpNull">标注文件ip不能为空</p>
				</div>
				<div class="col-xs-3" style="margin-top: 5px;">标注文件服务器用户名：</div>
				<div class="col-xs-9" style="margin-top: 5px;">
					<input class="leadin col-xs-5" type="text" name="corpus_labelUsername" id="corpus_labelUsername" onblur="checkLabelUsername(this.value)" oninput="labelUsernameInput()">
					<p class="p" id="labelUsernameNull">标注文件服务器用户名不能为空</p>
				</div>
				<div class="col-xs-3" style="margin-top: 5px;">标注文件服务器密码：</div>
				<div class="col-xs-9" style="margin-top: 5px;">
					<input class="leadin col-xs-5" type="text" name="corpus_labelPassword" id="corpus_labelPassword" onblur="checkLabelPassword(this.value)" oninput="labelPasswordInput()">
					<p class="p" id="labelPasswordNull">标注文件服务器密码不能为空</p>
				</div>
				<div class="col-xs-3" style="margin-top: 5px;">标注文件路径：</div>
				<div class="col-xs-9" style="margin-top: 5px;">
					<input class="leadin col-xs-5" type="text" name="corpus_labelPath" id="corpus_labelPath" onblur="checkLabelPath(this.value)" oninput="labelInput()">
					<p class="p" id="labelPathNull">标注文件路径不能为空</p>
				</div>
			</div>
		</div>
		
		<div id="waveFmt" class="col-xs-12 leadin" style="margin-bottom: 10px;margin-top: 10px;">
			<div class="leadin col-xs-3" align="right" style="vertical-align: middle;">音频格式：</div>
			<div class="leadin col-xs-9" style="padding: 0;margin: 0">
				<!-- 是否有文件头 -->
				<div class="col-xs-12" style="padding: 0">
					<div class="col-xs-2">文件头：</div>
					<div class="col-xs-2"><input type="radio" name="head" id="head0" value="0">无</div>
					<div class="col-xs-2"><input type="radio" name="head" id="head1" value="1">有</div>
					<div class="col-xs-6"><p class="p" id="headNull">请选择音频是否有文件头</p></div>
				</div>
				<!-- 编码方式 -->
				<div class="col-xs-12" style="padding: 0">
					<div class="col-xs-2">编码方式：</div>
					<div class="col-xs-2"><input type="radio" name="code" id="code0" value="0">线性PCM</div>
					<div class="col-xs-2"><input type="radio" name="code" id="code1" value="1">ALAW PCM</div>
					<div class="col-xs-2"><input type="radio" name="code" id="code2" value="2">ULAW PCM</div>
					<div class="col-xs-4"><p class="p" id="codeNull">请选择编码方式</p></div>
				</div>
				<!-- 采样率 -->
				<div class="col-xs-12" style="padding: 0">
					<div class="col-xs-2">采样率：</div>
					<div class="col-xs-2"><input type="radio" name="sample" id="sample0" value="0">8K</div>
					<div class="col-xs-2"><input type="radio" name="sample" id="sample1" value="1">16K</div>
					<div class="col-xs-6"><p class="p" id="sampleNull">请选择采样频率</p></div>
				</div>
				<!-- 量化数 -->
				<div class="col-xs-12" style="padding: 0">
					<div class="col-xs-2">量化数：</div>
					<div class="col-xs-2"><input type="radio" name="bitpersamples" id="bitpersamples0" value="0">8b</div>
					<div class="col-xs-2"><input type="radio" name="bitpersamples" id="bitpersamples1" value="1">16b</div>
					<div class="col-xs-6"><p class="p" id="bitpersamplesNull">请选择量化数</p></div>
				</div>
				<!-- 通道数 -->
				<div class="col-xs-12" style="padding: 0">
					<div class="col-xs-2">声道数：</div>
					<div class="col-xs-2"><input type="radio" name="channel" id="channel0" value="1">1</div>
					<div class="col-xs-2"><input type="radio" name="channel" id="channel1" value="2">2</div>
					<div class="col-xs-6"><p class="p" id="channelNull">请选择声道数</p></div>
				</div>
			</div>
		</div>
		
		<!-- <div id="corpusInfo" class="leadin" style="margin-bottom: 10px;margin-top: 10px;">
			<div class="leadin col-xs-4" align="right">采样频率：</div>
			<div class="leadin col-xs-8">
				<div style="margin: 0px;padding: 0px;display: inline;margin-right: 10px;"><input class="leadin" type="radio" name="corpus_ratio" id="corpus_ratio_8k" value="0">8k</div>
				<div style="margin: 0px;padding: 0px;display: inline;margin-left: 10px;margin-right: 10px;"><input class="leadin" type="radio" name="corpus_ratio" id="corpus_ratio_16k" value="1">16k</div>
			</div>
		</div> -->
		
		<div id="corpusDesp" class="col-xs-12 leadin" style="margin-bottom: 10px;margin-top: 10px;">
			<div class="leadin col-xs-3" align="right">备注：</div>
			<div class="leadin col-xs-9">
				<textarea class="leadin col-xs-7" rows="3" id="corpus_desp" style="resize:none;"></textarea>
			</div>
		</div>
		
		<div id="corpusSubmit" class="col-xs-12 leadin" style="margin-bottom: 10px;margin-top: 10px;" align="center">
			<button class="btn btn-xs btn-success" type="button" onclick="corpus_leadin()">开始导入</button>
		</div>
	</div>
	
	
    <script type="text/javascript">
    
	    $(document).ready(function(){
			document.getElementById("welcome_admin").style.display = "none";
			document.getElementById("param_admin").style.display = "none";
			document.getElementById("user_admin").style.display = "none";
			document.getElementById("corpus_admin").style.display = "block";
		});
	    
	    function corpus_type_no(){
	    	document.getElementById("corpusLabelResultWavetagger").style.display = "none";
	    	document.getElementById("corpusLabelResultFile").style.display = "none";
	    	document.getElementById("corpusLabelType").style.display = "none";
	    	document.getElementById("corpusLabelPath").style.display = "none";
	    }
	    
	    function corpus_type_yes(){
	    	document.getElementById("corpusLabelType").style.display = "inline";
	    	document.getElementById("corpusLabelPath").style.display = "inline";
	    	var type = $("input[name='corpus_labelType']:checked").val();
	    	if(type == '0'){
		    	document.getElementById("corpusLabelResultWavetagger").style.display = "none";
		    	document.getElementById("corpusLabelResultFile").style.display = "none";
	    	}else{
	    		if(type == '1'){
	    	    	document.getElementById("corpusLabelResultWavetagger").style.display = "inline";
	    	    	document.getElementById("corpusLabelResultFile").style.display = "none";
	    		}else{
	    	    	document.getElementById("corpusLabelResultWavetagger").style.display = "none";
	    	    	document.getElementById("corpusLabelResultFile").style.display = "inline";
	    		}
	    	}
	    }
	    
	    /* function corpus_labelResult_text(id){
	    	document.getElementById("labelTypeNull").style.display = "none";
	    	if(document.getElementById(id).checked == true){
	    		document.getElementById("corpus_labelType_file").disabled = "disabled";
	    	}else{
	    		document.getElementById("corpus_labelType_file").disabled = "";
	    	}
	    } */
	    
	    /* function corpus_labelResult_genderFile(id){
	    	document.getElementById("labelTypeNullFile").style.display = "none";
	    }
	    function corpus_labelResult_genderWavetagger(id){
	    	document.getElementById("labelTypeNullWavetagger").style.display = "none";
	    } */
	    
	    function changeLabelType(value){
	    	if(value == '0'){
		    	document.getElementById("corpusLabelResultWavetagger").style.display = "none";
		    	document.getElementById("corpusLabelResultFile").style.display = "none";
		    	var checkbox = $("input[name='corpus_labelResultWavetagger']:checked");
		    	for(var i = 0; i < checkbox.length; i++){
		    		checkbox[i].checked = "";
		    	}
		    	var radio = $("input[name='corpus_labelResultFile']:checked");
		    	radio.checked = "";
	    	}else{
	    		if(value == '1'){
	    	    	document.getElementById("corpusLabelResultWavetagger").style.display = "inline";
	    	    	document.getElementById("corpusLabelResultFile").style.display = "none";
			    	var radio = $("input[name='corpus_labelResultFile']:checked");
			    	radio.checked = "";
	    		}else{
	    	    	document.getElementById("corpusLabelResultWavetagger").style.display = "none";
	    	    	document.getElementById("corpusLabelResultFile").style.display = "inline";
			    	var checkbox = $("input[name='corpus_labelResultWavetagger']:checked");
			    	for(var i = 0; i < checkbox.length; i++){
			    		checkbox[i].checked = "";
			    	}
	    		}
	    	}
	    }
	    
	    function checkName(value, id){
	    	if(value == null || value == ""){
	    		document.getElementById("nameNull").style.display = "inline";
	    	}else{
	    		var obj = new Object();
	    		obj.value = value;
	    		var li = [];
	    		li.push(obj);
	    		$.ajax({
	    			type:"get",
	    			url:"checkName.html?name=" +JSON.stringify(li),
	    			async:false,
	    			
	    			success:function(data){
	    				if(data == '1'){
	    					document.getElementById("nameNull").style.display = "inline";
	    				}else{
	    					if(data == '2'){
	    						document.getElementById("nameExist").style.display = "inline";
	    					}
	    				}
	    			},
	    			error:function(data){
	    				alert("连接失败");
	    			}
	    		});
	    	}
	    }
	    
	    function nameInput(){
	    	document.getElementById("nameNull").style.display = "none";
	    	document.getElementById("nameExist").style.display = "none";
	    }
	    function checkWavePath(value){
	    	if(value == null || value == ""){
	    		document.getElementById("wavePathNull").style.display = "inline";
	    	}
	    }
	    
	    function waveInput(){
    		document.getElementById("wavePathNull").style.display = "none";
	    }

	    function checkLabelPath(value){
	    	if(value == null || value == ""){
	    		document.getElementById("labelPathNull").style.display = "inline";
	    	}
	    }
	    
	    function labelInput(){
    		document.getElementById("labelPathNull").style.display = "none";
	    }
	    
	    function getObj(name, type, labelType, wavePath, labelPath, desp, context, gender, language, effective, speaker, head, code, sample, bitpersamples, channel, waveip, waveusername, wavepassword, labelip, labelusername, labelpassword){
	    	var obj = new Object();
	    	obj.name = name;
	    	obj.type = type;
	    	obj.labelType = labelType;
	    	obj.wavePath = wavePath;
	    	obj.labelPath = labelPath;
	    	obj.desp = desp;
	    	obj.context = context;
	    	obj.gender = gender;
	    	obj.language = language;
	    	obj.effective = effective;
	    	obj.speaker = speaker;
	    	obj.head = head;
	    	obj.code = code;
	    	obj.sample = sample;
	    	obj.bitpersamples = bitpersamples;
	    	obj.channel = channel;
	    	obj.waveip = waveip;
	    	obj.waveusername = waveusername;
	    	obj.wavepassword = wavepassword;
	    	obj.labelip = labelip;
	    	obj.labelusername = labelusername;
	    	obj.labelpassword = labelpassword;
	    	return obj;
	    }
	    
	    function corpus_leadin(){
	    	var flag = 0;	  
	    	var name = "";
	    	var type = 0;
	    	var labelType = 0;
	    	var context = 0;
	    	var gender = 0;
	    	var language = 0;
	    	var effective = 0;
	    	var speaker = 0;
	    	var desp = "";
	    	var head = 0;
	    	var code = 0;
	    	var sample = 0;
	    	var bitpersample = 0;
	    	var channel = 0;
	    	
	    	var value = document.getElementById("corpus_name").value;
	    	if(value == null || value == ""){
	    		document.getElementById("nameNull").style.display = "inline";
	    		flag = 1;
	    	}else{
	    		name = value;
	    		
	    		$.ajax({
	    			type:"get",
	    			url:"checkName.html?name=" +name,
	    			async:false,
	    			
	    			success:function(data){
	    				if(data == '1'){
	    					document.getElementById("nameNull").style.display = "inline";
	    					flag = 1;
	    				}else{
	    					if(data == '2'){
	    						document.getElementById("nameExist").style.display = "inline";
	    						flag = 1;
	    					}else{
	    						
	    						/* 生语料还是熟语料 */
	    						value = $("input[name='corpus_type']:checked").val();
	    						if(value == null){
	    							document.getElementById("typeNull").style.display = "inline";
	    							flag = 1;
	    						}else{
		    			    		type = value;	
	    						}    						

    							/* 获取标注工具 */
    							labelType = $("input[name='corpus_labelType']:checked").val();
	    						
	    						/* 获取标注的形式：音频内容、性别、说话人、非语音等 */
	    						var label = "";
	    						alert(value)
	    						if(labelType == '1'){
	    							alert(7)
	    							label = $("input[name='corpus_labelResultWavetagger']:checked");
	    							if(label.length == 0){
		    							document.getElementById("labelTypeNullWavetagger").style.display = "inline";
		    							flag = 1;
	    							}else{
	    								for(var i = 0; i < label.length; i++){
	    									value = label[i].value;
	    									if(value == 0){
	    										context = 1;
	    									}else{
	    										if(value == 1){
	    											gender = 1;
	    										}else{
	    											if(value == 2){
	    												speaker = 1;
	    											}else{
	    												if(value == 3){
	    													language = 1;
	    												}else{
	    													if(value == 4){
	    														effective = 1;
	    													}
	    												}
	    											}
	    										}
	    									}
	    								}
	    							}
	    						}else{
	    							alert(8)
	    							if(labelType == '2'){
		    							value = $("input[name='corpus_labelResultFile']:checked").val();
		    							if(value == null || value == ""){
			    							document.getElementById("labelTypeNullFile").style.display = "inline";
			    							flag = 1;
		    							}else{
		    								if(value == 0){
	    										context = 1;
	    									}else{
	    										if(value == 1){
	    											gender = 1;
	    										}else{
	    											if(value == 2){
	    												speaker = 1;
	    											}else{
	    												if(value == 3){
	    													language = 1;
	    												}else{
	    													if(value == 4){
	    														effective = 1;
	    													}
	    												}
	    											}
	    										}
	    									}
		    							}
	    							}
	    						}
	    						alert(9)
	    							
	    						if(flag == 1){
	    							alert(10)
	    							flag = 1;
	    						}else{
	    							/* 获取音频文件路径 */
	    							value = document.getElementById("corpus_wavePath").value;
	    							if(value == null || value == ""){
	    								document.getElementById("wavePathNull").style.display = "inline";
	    								flag = 1;
	    							}else{
	    								wavePath = value;
	    								
		    							/* 获取标注文件路径 */
		    							value = document.getElementById("corpus_labelPath").value;
		    							if(value == null || value == ""){
		    								alert(1)
		    								document.getElementById("labelPathNull").style.display = "inline";
		    								flag = 1;
		    							}else{
		    								head = $("input[name='head']:checked").val();
		    								if(head == null){
			    								alert(2)
		    									flag = 1;
		    									document.getElementById("headNull").style.display = "inline";
		    								}
		    								code = $("input[name='code']:checked").val();
		    								if(code == null){
			    								alert(3)
		    									flag = 1;
		    									document.getElementById("codeNull").style.display = "inline";
		    								}
		    								sample = $("input[name='sample']:checked").val();
		    								if(sample == null){
			    								alert(4)
		    									flag = 1;
		    									document.getElementById("sampleNull").style.display = "inline";
		    								}
		    								bitpersamples = $("input[name='bitpersamples']:checked").val();
		    								if(bitpersamples == null){
			    								alert(6)
		    									flag = 1;
		    									document.getElementById("bitpersamplesNull").style.display = "inline";
		    								}
		    								channel = $("input[name='channel']:checked").val();
		    								if(channel == null){
			    								alert(6)
		    									flag = 1;
		    									document.getElementById("channelNull").style.display = "inline";
		    								}
		    					    		labelPath = value;
			    							/* 获取语料库描述信息 */
			    							desp = document.getElementById("corpus_desp").value;
		    							}
	    							}
	    						}
	    					}
	    				}
	    			},
	    			error:function(data){
	    				alert("连接失败");
	    			}
	    		});
	    	}
	    	
	    	var waveip = $("#corpus_waveIp").val();
	    	var waveusername = $("#corpus_waveUsername").val();
	    	var wavepassword = $("#corpus_wavePassword").val();

	    	var labelip = $("#corpus_labelIp").val();
	    	var labelusername = $("#corpus_labelUsername").val();
	    	var labelpassword = $("#corpus_labelPassword").val();
	    	
	    	if(waveip == null || waveip == ""){
				document.getElementById("waveIpNull").style.display = "inline";
				flag =1;
	    	}
	    	if(waveusername == null || waveusername == ""){
				document.getElementById("waveUsernameNull").style.display = "inline";
				flag =1;
	    	}
	    	if(wavepassword == null || wavepassword == ""){
				document.getElementById("wavePasswordNull").style.display = "inline";
				flag =1;
	    	}
	    	if(labelip == null || labelip == ""){
				document.getElementById("labelIpNull").style.display = "inline";
				flag =1;
	    	}
	    	if(labelusername == null || labelusername == ""){
				document.getElementById("labelUsernameNull").style.display = "inline";
				flag =1;
	    	}
	    	if(labelpassword == null || labelpassword == ""){
				document.getElementById("labelPasswordNull").style.display = "inline";
				flag =1;
	    	}
	    	
	    	if(flag == 0){
	    		var li = getObj(name, type, labelType, wavePath, labelPath, desp, context, gender, language, effective, speaker, head, code, sample, bitpersamples, channel, waveip, waveusername, wavepassword, labelip, labelusername, labelpassword);
	    		$.ajax({
	    			type:"post",
	    			url:"begin.html?",
	    			data:{corpus:JSON.stringify(li)},
	    			async:false,
	    			
	    			success:function(data){
	    				if(data == '0'){
	    					alert("开始导入");
	    					location.reload(true);
	    				}else{
	    					if(data == '1'){
	    						document.getElementById("name_null").style.display = "inline";
	    					}else{
	    						if(data == '2'){
	    							document.getElementById("name_exist").style.display = "inline";
	    						}else{
	    							if(data == '3')
	    								alert("导入失败");
	    							else
	    								alert(data);
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