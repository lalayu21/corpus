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
    	.inline{
    		position: relative;
    		display: inline;
    		margin-right: 20px;
    	}
    	.tdleft{
    		vertical-align: middle;
    	}
    	.tdright{
    		vertical-align: middle;
    	}
    </style>
</head>

<body>
	<div class="col-xs-10 col-xs-offset-1 leadin">
		<jsp:include page="../../navigation.jsp"></jsp:include>
	</div>
		
		<div class="col-xs-10 col-xs-offset-1" align="center">
			
			<input type="hidden" id="hiddenCorpusId" value="${id }">
		</div>
		
		<div class="col-xs-1 col-xs-offset-1">
			<ul style="margin: 0;padding: 0;margin-top: 50px;">
				<li class="li"><a href="../data/detail.html?id=${id }&type=${corpus.type}">基本信息</a></li>
				<li class="li"><a href="../data/waves.html?id=${id }&type=${corpus.type}&labelType=${corpus.labelType }&name=${corpus.name }">音频列表</a></li>
				<li class="li"><a>标注管理</a></li>
				<li class="li"><a href="../training/list.html?id=${id }&type=${corpus.type}&labelType=${corpus.labelType}">训练集管理</a></li>
				<li class="li"><a href="#" style="color:black">特征管理</a></li>
			</ul>
		</div>

	
	<div class="col-xs-9" align="center">
	
		<div class="col-xs-12" align="center"><h1>${corpus.name }提特征</h1></div>
		<table class="table table-condensed table-bordered" style="width: 800px">
			<tr>
				<td class="tdleft col-xs-2" align="right">
					特征类型：
				</td>
				<td class="tdright col-xs-10">
					<div class="inline">
						<input type="radio" name="featuretype" id="mfcctype" value="0" checked="checked" onclick="showFeaturetype(this.value)">mfcc
					</div>
					<div class="inline">
						<input type="radio" name="featuretype" id="plptype" value="1" onclick="showFeaturetype(this.value)">plp
					</div>
					<div class="inline">
						<input type="radio" name="featuretype" id="fbanktype" value="2" onclick="showFeaturetype(this.value)">fbank
					</div>
				</td>
			</tr>
			<tr>
				<td class="tdleft col-xs-2" align="right">
					是否使用pitch：
				</td>
				<td class="tdright col-xs-10">
					<input type="radio" name="pitch" id="pitchyes" value="0" onclick="showpitch(0)">是
					<input type="radio" name="pitch" id="pitchno" value="1" checked="checked" onclick="showpitch(1)">否
				</td>
			</tr>
			<tr id="mfcc">
				<td class="tdleft col-xs-2" align="right">
					mfcc参数：
				</td>
				<td class="tdright col-xs-10">
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">是否使用能量：</div>
					<div class="col-xs-8">
						<input type="radio" name="energy" value="0">是
						<input type="radio" name="energy" value="1" checked="checked">否
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">mel-bins：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" name="melbins" id="melbins" value="40" onblur="if(this.value==''){this.value=40}">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">ceps：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" name="ceps" id="ceps" value="40" onblur="if(this.value==''){this.value=40}">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">低截止频率：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" name="lowFreq" id="lowFreq" value="20" onblur="if(this.value==''){this.value=20}">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">高截止频率：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" name="highFreq" id="highFreq" value="-400" onblur="if(this.value==''){this.value=-400}">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">是否压缩：</div>
					<div class="col-xs-8">
						<input type="radio" name="compress" id="compressyes" value="0" checked="checked">是
						<input type="radio" name="compress" id="compressno" value="1">否
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">write-utt2num-frames：</div>
					<div class="col-xs-8">
						<input type="radio" name="write-utt2num-frames" id="write-utt2num-framesyes" value="0">是
						<input type="radio" name="write-utt2num-frames" id="write-utt2num-framesno" value="1" checked="checked">否
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">sample-frequency：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" type="text" name="sample-frequency" id="sample-frequency" value="8000" onblur="if(this.value==''){this.value=8000}">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">frame-length：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" type="text" name="frame-length" id="frame-length" value="25" onblur="if(this.value==''){this.value=25}">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">frame-shift：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" type="text" name="frame-shift" id="frame-shift" value="0" onblur="if(this.value==''){this.value=0}">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">window-type：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" type="text" name="window-type" id="window-type">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">snip-edges：</div>
					<div class="col-xs-8">
						<input type="radio" name="snip-edges" id="snip-edgesyes" value="0">是
						<input type="radio" name="snip-edges" id="snip-edgesno" value="1" checked="checked">否
					</div>
				</td>
			</tr>
			<tr id="plp" style="display:none">
				<td class="tdleft" align="right">
					plp参数：
				</td>
				<td class="tdright">
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">sample-frequency：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" type="text" name="sample-frequency-plp" id="sample-frequency-plp" value="8000" onblur="if(this.value==''){this.value=8000}">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">是否使用能量：</div>
					<div class="col-xs-8">
						<input type="radio" name="energy-plp" value="0">是
						<input type="radio" name="energy-plp" value="1" checked="checked">否
					</div>
				</td>
			</tr>
			<tr id="fbank" style="display:none;">
				<td class="tdleft" align="right">
					fbank参数：
				</td>
				<td class="tdright">
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">sample-frequency：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" type="text" name="sample-frequency-fbank" id="sample-frequency-fbank" value="8000" onblur="if(this.value==''){this.value=8000}">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">window-type：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" type="text" name="window-type-fbank" id="window-type-fbank">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">是否使用能量：</div>
					<div class="col-xs-8">
						<input type="radio" name="energy-fbank" value="0">是
						<input type="radio" name="energy-fbank" value="1" checked="checked">否
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">dither：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" type="text" name="dither-fbank" id="dither-fbank" value="1" onblur="if(this.value==''){this.value=8000}">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">mel-bins：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" name="melbins-fbank" id="melbins-fbank" value="40" onblur="if(this.value==''){this.value=40}">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">低截止频率：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" name="lowFreq-fbank" id="lowFreq-fbank" value="20" onblur="if(this.value==''){this.value=20}">
					</div>
					
					<div class="col-xs-3" align="right" style="margin: 0;padding: 0">高截止频率：</div>
					<div class="col-xs-8">
						<input class="form-control" style="margin-top: 5px;" name="highFreq-fbank" id="highFreq-fbank" value="-400" onblur="if(this.value==''){this.value=-400}">
					</div>
				</td>
			</tr>
			<tr id="pitch" style="display:none;">
				<td class="tdleft" align="right">
					pitch参数：
				</td>
				<td class="tdright">
					<!-- <div class="col-xs-3">Pitch_postprocess_config:</div>
					<div class="col-xs-8"></div> -->
					<div class="col-xs-3" style="margin: 0;padding: 0">paste-length-tolerance:</div>
					<div class="col-xs-8">
						<input type="text" class="form-control" style="margin-top: 5px;" id="paste-length-tolerance" value="2" onblur="if(this.value==''){this.value=2}">
					</div>
					<div class="col-xs-3" style="margin: 0;padding: 0">--nccf-ballast-online:</div>
					<div class="col-xs-8">
						<input type="radio" name="mfcc-pitch-nccf-ballast-online" id="nccf-ballast-onlineyes" value="0" checked="checked">true
						<input type="radio" name="mfcc-pitch-nccf-ballast-online" id="nccf-ballast-onlineno" value="1">false
					</div>
				</td>
			</tr>
			<tr>
				<td class="tdleft" align="right">
					并行数：
				</td>
				<td class="tdright">
					<input class="form-control" name="parallel" id="parallel" value="4" onblur="if(this.value==''){this.value=4}">
				</td>
			</tr>
			<tr>
				<td class="tdleft" align="right">
					cmd：
				</td>
				<td class="tdright">
					<input type="radio" name="cmd" id="run" value="0" checked="checked">run.pl
					<input type="radio" name="cmd" id="queue" value="1">queue.pl
				</td>
			</tr>
			<tr>
				<td class="tdleft" align="right">
					特征存放位置：
				</td>
				<td class="tdright">
					<input class="form-control" name="path" id="path">
				</td>
			</tr>
			<!-- <tr>
				<td style="tdleft" align="right">
					是否使用能量：
				</td>
				<td class="tdright">
					<div class="inline">
						<input type="radio" name="energy" value="0">是
					</div>
					<div class="inline">
						<input type="radio" name="energy" value="1" checked="checked">否
					</div>
				</td>
			</tr> -->
			<!-- <tr>
				<td class="tdleft" align="right">
					mel-bins：
				</td>
				<td class="tdright">
					<input class="form-control" name="melbins" id="melbins" value="40" onblur="if(this.value==''){this.value=40}">
				</td>
			</tr> -->
			<!-- <tr>
				<td class="tdleft" align="right">
					ceps：
				</td>
				<td class="tdright">
					<input class="form-control" name="ceps" id="ceps" value="40" onblur="if(this.value==''){this.value=40}">
				</td>
			</tr> -->
			<!-- <tr>
				<td class="tdleft" align="right">
					低截止频率：
				</td>
				<td class="tdright">
					<input class="form-control" name="lowFreq" id="lowFreq" value="20" onblur="if(this.value==''){this.value=20}">
				</td>
			</tr>
			<tr>
				<td class="tdleft" align="right">
					高截止频率：
				</td>
				<td class="tdright">
					<input class="form-control" name="highFreq" id="highFreq" value="-400" onblur="if(this.value==''){this.value=-400}">
				</td>
			</tr> -->
			<!-- <tr>
				<td class="tdleft" align="right">
					并行数：
				</td>
				<td class="tdright">
					<input class="form-control" name="parallel" id="parallel" value="4" onblur="if(this.value==''){this.value=4}">
				</td>
			</tr> -->
			<!-- <tr>
				<td class="tdleft" align="right">
					是否压缩：
				</td>
				<td class="tdright">
					<input type="radio" name="compress" id="compressyes" value="0" checked="checked">是
					<input type="radio" name="compress" id="compressno" value="1">否
				</td>
			</tr> -->
			
		</table>
	</div>
	
	<div class="col-xs-10 col-xs-offset-1" align="center">
		<button class="btn btn-success" onclick="beginFeature(${id})">提特征</button>
	</div>
	
    <script type="text/javascript">

	    $(document).ready(function(){
			document.getElementById("welcome_admin").style.display = "none";
			document.getElementById("param_admin").style.display = "none";
			document.getElementById("user_admin").style.display = "none";
			document.getElementById("corpus_admin").style.display = "block";
		});
	    
	    
	    //获取像后台传递的json格式数据
	    function createJsonMfcc(id, featuretype, energy, melBins, ceps, lowFreq, highFreq, compress, writeutt2numframes, samplefrequency, framelength, framshift, windowtype, snipedges, path, pastelengthtolerance, nccfballastonline){
	    	var obj = new Object();
	    	obj.corpusID = id;
	    	obj.featuretype = featuretype;
	    	obj.energy = energy;
	    	obj.melBins = melBins;
	    	obj.ceps = ceps;
	    	obj.lowFreq = lowFreq;
	    	obj.highFreq = highFreq;
	    	obj.compress = compress;
	    	obj.writeutt2numframes =writeutt2numframes;
	    	obj.samplefrequency = samplefrequency;
	    	obj.framelength = framelength;
	    	obj.framshift = framshift;
	    	obj.windowtype = windowtype;
	    	obj.snipedges = snipedges;
	    	obj.path = path;
	    	obj.pastelengthtolerance = pastelengthtolerance;
	    	obj.nccfballastonline = nccfballastonline;
	    	return obj;
	    }
	    
	    function createJsonPlp(id, featuretype, energy, samplefrequency, path, pastelengthtolerance, nccfballastonline){
	    	var obj = new Object();
	    	obj.corpusID = id;
	    	obj.featuretype = featuretype;
	    	obj.energy = energy;
	    	obj.samplefrequency = samplefrequency;
	    	obj.path = path;
	    	obj.pastelengthtolerance = pastelengthtolerance;
	    	obj.nccfballastonline = nccfballastonline;
	    	return obj;
	    }
		function createJsonFbank(id, featuretype, energy, melBins, lowFreq, highFreq, samplefrequency, windowtype, dither, path, pastelengthtolerance, nccfballastonline){
	    	var obj = new Object();
	    	obj.corpusID = id;
	    	obj.featuretype = featuretype;
	    	obj.energy = energy;
	    	obj.melBins = melBins;
	    	obj.lowFreq = lowFreq;
	    	obj.highFreq = highFreq;
	    	obj.samplefrequency = samplefrequency;
	    	obj.windowtype = windowtype;
	    	obj.dither = dither;
	    	obj.path = path;
	    	obj.pastelengthtolerance = pastelengthtolerance;
	    	obj.nccfballastonline = nccfballastonline;
	    	return obj;
	    }
    	function beginFeature(id){
    		var featuretype = $("input[name='featuretype']:checked").val();
    		var pitch = $("input[name='pitch']:checked").val();
    		var path = $("#path").val();
    		var nj = $("#parallel").val();
    		var cmd = $("input[name='cmd']:checked").val();
    		var pastelengthtolerance = "";
    		var nccfballastonline = 0;
    		
    		var li = "";
    		
    		if(pitch == '0'){
    			pastelengthtolerance = $("#paste-length-tolerance").val();
    			nccfballastonline = $("input[name='nccf-ballast-online']:checked").val();
    		}
    		
    		if(featuretype == '0'){
    			var energy = $("input[name='energy']:checked").val();
    			var melBins = $("#melbins").val();
        		var ceps = $("#ceps").val();
        		var lowFreq = $("#lowFreq").val();
        		var highFreq = $("#highFreq").val();
        		var compress = $("input[name='compress']:checked").val();
        		var writeutt2numframes = $("input[name='write-utt2num-frames']:checked").val();
        		var samplefrequency = $("#sample-frequency").val();
        		var framelength = $("#frame-length").val();
        		var framshift = $("#frame-shift").val();
        		var windowtype = $("#window-type").val();
        		var snipedges = $("input[name='snip-edges']:checked").val();
        		li = JSON.stringify(createJsonMfcc(id, featuretype, energy, melBins, ceps, lowFreq, highFreq, compress, writeutt2numframes, samplefrequency, framelength, framshift, windowtype, snipedges, path, pastelengthtolerance, nccfballastonline));
    		}else{
    			if(featuretype == '1'){
    				var energy = $("input[name='energy-plp']:checked").val();
    				var samplefrequency = $("#sample-frequency-plp").val();
    				li = JSON.stringify(createJsonPlp(id, featuretype, energy, samplefrequency, path, pastelengthtolerance, nccfballastonline));
    			}else{
    				if(featuretype == '2'){
        				var samplefrequency = $("#sample-frequency-fbank").val();
        				var windowtype = $("#window-type-fbank").val();
        				var energy = $("input[name='energy-fbank']:checked").val();
        				var dither = $("#dither-fbank").val();
            			var melBins = $("#melbins-fbank").val();
                		var lowFreq = $("#lowFreq-fbank").val();
                		var highFreq = $("#highFreq-fbank").val();
                		li = JSON.stringify(createJsonFbank(id, featuretype, energy, melBins, lowFreq, highFreq, samplefrequency, windowtype, dither, path, pastelengthtolerance, nccfballastonline));
    				}else{
    					alert("请选择特征类型");
    					flag = 1;
    				}
    			}
    		}
    		
    		if(flag == 0){
    			if(path == null || path == ""){
    				alert("请输入特征存放位置")
    			}else{
    				$.ajax({
        		 		url:"getFeature.html?",
        				type:"post",
        				data:{jsonArray:li},
        				async:false,
        				    			
        				success:function(data){
        					alert(data);
        				},
        				error:function(data){
        					alert(data);
        				}
        			});
    			}
    		}
    	}
    	
    	//显示相应特征类型的参数
    	function showFeaturetype(value){
    		if(value == '0'){
    			$("#mfcc").show();
    			$("#plp").hide();
    			$("#fbank").hide();
    		}
    		if(value == '1'){
    			$("#mfcc").hide();
    			$("#plp").show();
    			$("#fbank").hide();
    		}
    		if(value == '2'){
    			$("#mfcc").hide();
    			$("#plp").hide();
    			$("#fbank").show();
    		}
    	}
    	
    	//使用pitch
    	function showpitch(flag){
    		if(flag == '0'){
    			$("#pitch").show();
    		}
    		if(flag == '1'){
    			$("#pitch").hide();
    		}
    	}
    </script>
</body>
</html>