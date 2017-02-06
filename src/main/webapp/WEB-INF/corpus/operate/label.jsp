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
    	a:hover{
    		cursor: pointer;
    		text-decoration: none;
    		background-color: gray;
    	}
    	#touming{
            position: absolute;
            display: none;
            background-color: gray;
            top: 0;
            left: 0;
            height:100%;
            width:100%;
            filter:alpha(Opacity=60);
            -moz-opacity:0.6;
            opacity: 0.6
        }

        #hide{
            position: absolute;
            top: 30%;
            width: 60%;
            height: 40%;
            left: 20%;
            display: none;
            background-color: white;
            filter:alpha(Opacity=100);
            -moz-opacity:1;
            opacity: 1;
            
        }
    </style>
</head>

<body>
	<div class="col-xs-10 col-xs-offset-1 leadin">
		<jsp:include page="../../navigation.jsp"></jsp:include>
	</div>
		
		<div class="col-xs-10 col-xs-offset-1" align="center">
			<div class="col-xs-4 col-xs-offset-4" align="center"><h1>${corpus.name }</h1></div>
			<input type="hidden" id="hiddenCorpusId" value="${corpus.id }">
		</div>
		
		<div class="col-xs-1 col-xs-offset-1">
			<ul style="margin: 0;padding: 0">
				<li class="li"><a href="../data/detail.html?id=${corpus.id }&type=${corpus.type}">基本信息</a></li>
				<li class="li"><a href="../data/waves.html?id=${corpus.id }&type=${corpus.type}&labelType=${corpus.labelType }&name=${corpus.name }">音频列表</a></li>
				<li class="li"><a href="#" style="color:black;">标注管理</a></li>
				<li class="li"><a href="../training/list.html?id=${corpus.id }&type=${corpus.type}&labelType=${corpus.labelType}">训练集管理</a></li>
				<li class="li"><a href="../feature/action.html?id=${corpus.id }">特征管理</a></li>
			</ul>
		</div>
		
		
		<div class="col-md-9">
	             <div class="col-md-3" align="right">任务名称:</div>
	             <div class="col-md-9" align="left">
	                 <input id="task_name" type="text" name="task_name" class="col-md-8" onblur="checkTaskname()">
	                 <p id="tasknameNull" class="p">任务名不能为空</p>
	                 <p id="tasknameExist" class="p">任务名已存在</p>
	                    
	                 <label for="task_name"></label>
	             </div>
	             <div class="col-md-12" style="height: 10px;"><p> </p></div>
             
                <div class="col-md-3" align="right" data-toggle="tooltip" data-placement="right" title="请填写数据在服务器上的地址，如果您的数据不在服务器上，请先上传至服务器">数据地址:</div>
                <div class="col-md-9" align="left">
                    <input id="task_address" type="text" name="task_address" class="col-md-8" onblur="checkAddress()">
                    <p id="addressNull" class="p">数据地址不能为空</p>
                    <label for="task_address"></label>
                </div>
                 <div class="col-md-12" style="height: 10px;"><p> </p></div>
                <div class="col-md-3" align="right" data-toggle="tooltip" data-placement="right" title="优先级默认为中">优先级:</div>
                <div class="col-md-9" align="left">
                	<select id="task_pri" name="task_pri">
                        <option id="grade2" value="2">中</option>
                        <option id="grade1" value="1">高</option>
                        <option id="grade3" value="3">低</option>
                    </select>
                </div>
                <div class="col-md-12" style="height: 10px;"><p> </p></div>
                <div class="col-md-3" align="right" data-toggle="tooltip" data-placement="right" title="为此批数据的已知属性，比如已知该批数据为语音数据，请选择类型、语音，并点击添加按钮，若有多条已知属性，可继续添加">已知属性:</div>
                <div class="col-md-9" align="left" id="known">
                	<div id="checkedAttr"></div>
                    <select id="knownAttr" name="task_known_attrs" onchange="changeAttr(this.id)">
                        <option value="a">--请选择--</option>
                    </select>
                    <label for="knownAttr"></label>
                    <select id="knownAttrValue" name="attrbute_values" onchange="changeName(this.id)">
                        <option value="a">--请选择--</option>
                    </select>
                    <label for="knownAttrValue"></label>
                    <input type="button" onclick="addAttr()" value="添加属性">
                    <p id="yizhiAttr" class="p">请选择已知属性</p>
                </div>
                <div class="col-md-12" style="height: 10px;"><p> </p></div>
                <div class="col-md-3" align="right" class="col-md-2" data-toggle="tooltip" data-placement="right" title="属性为待标注属性，如果此批数据需要标注性别，请选择性别，然后在给出的属性值处选择待标注属性值">待标注属性:</div>
                <div class="col-md-9" align="left" id="unknown">
                	<div id="checkedUnknown"></div>
                    <select id="unknownAttr" name="task_label_attrs" onchange="changeUnknownAttr(this.id)">
                        <option value="a">--请选择--</option>
                    </select>
                    <div id="showUnknownAttrvalue" style="display: none;"></div>
                    <label for="unknownAttr"></label>
                    <input type="button" onclick="addmore()" value="添加属性">
                    <p id="biaozhuAttr" class="p">请选择待标注属性</p>
                </div>
                <div class="col-md-12" style="height: 10px;"><p> </p></div>
                <div class="col-md-3" align="right" >备注:</div>
                <div class="col-md-9" align="left">
                    <input id="task_beizhu" type="text" name="task_name" class="col-md-8" name="task_beizhu">
                    <label for="task_address"></label>
                </div>
                <div class="col-md-12" style="height: 10px;"><p> </p></div>
                <div class="col-md-6" align="right">
                    <input type="button" id="choose" value="提交" onclick="confirm()">
                </div>
    </div>

    <div class="touming" id="touming">
    </div>

    <div class="fileDisplay" id="hide">
        <form action="addTasks.html" method="post">
            <table class="table table-bordered table-striped table-condensed">
                <tbody id="confirm_tbody">
                <tr>
                    <td>任务名称
					<input type="hidden" name="id" value="${corpus.id }">
                    <input type="hidden" id="hide_task_name" name="task_name"></td>
                    <td id="confirm_task_name">
                    </td>
                </tr>
                <tr>
                    <td>数据路径
                    <input type="hidden" id="hide_task_address" name="file_path"></td>
                    <td id="confirm_task_address">
                    </td>
                </tr>
                <tr>
                    <td>优先级
                    <input type="hidden" id="hide_task_pri" name="priority"></td>
                    <td id="confirm_task_pri">
                    </td>
                </tr>
                <tr>
                    <td>已知属性
                    <input type="hidden" id="hide_knownAttr" name="yizhishuxing">
                        <input type="hidden" id="hide_knownAttrValue" name="knownAttrValue"></td>
                    <td id="confirm_knownAttr">
                    </td>
                </tr>
                <tr>
                    <td>标注属性
                    <input type="hidden" id="hide_unknownAttr" name="biaozhushuxing">
                    </td>
                    <td id="confirm_unknownAttr"></td>
                </tr>
                <tr>
                    <td>备注
                        <input type="hidden" id="hide_beizhu" name="task_desp"></td>
                    <td id="confirm_beizhu"></td>
                </tr>
                <tr>
                    <td>
                        <input type="button" value="取消" onclick="concel()">
                    </td>
                    <td>
                        <input type="submit" value="提交">
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
	
    <script type="text/javascript">
	    $(document).ready(function(){
			document.getElementById("welcome_admin").style.display = "none";
			document.getElementById("param_admin").style.display = "none";
			document.getElementById("user_admin").style.display = "none";
			document.getElementById("corpus_admin").style.display = "block";
			
			$.ajax({
	        	type:"get",
	    
				url:"attrname.html", 
				async:false,
				dataType:"json",
				success:function(data){
					if(data.error == null){
						handle(data.attributesAndValues);
					}else{
						alert(data.error);
					}
				},
				error:function(data){
					alert("连接失败！");
				}
	        });
		});
	    
	    
	    function handle(ret){
            $.each(ret,function(index,v){
                var op = new Option(ret[index].attrname,ret[index].aid);
                document.getElementById("knownAttr").options.add(op);
            });
        }
	    
	  //获取属性值
        function changeAttr(ret){
            if(document.getElementById("knownAttr").value == "a"){
                document.getElementById("knownAttrValue").innerHTML="<option value=\"a\">--请选择--</option>";
            }else{
                document.getElementById("knownAttr").name = $("#knownAttr").find("option:selected").text();
                document.getElementById("knownAttrValue").innerHTML="<option value=\"a\">--请选择--</option>";
                $.ajax({
                    type:"get",
                  
                    url:"queryAllValuesname.html?id="+$("#knownAttr").find("option:selected").val(),
                    async:false,
                    dataType:"json",
                    success:function(data){
                        handle_values(data.attrvalues,ret);
                    },
                    error:function(data){
                        alert("连接失败");
                    }
                });
            }
        }
	  
        function handle_values(ret,id){
            $.each(ret,function(index,v){
                var op1 = "<option value='"+ret[index].id+"'>"+ret[index].attrvalue;
                document.getElementById(id+"Value").innerHTML += op1;
            });
        }
        
        function changeName(ret){
            document.getElementById(ret).name = document.getElementById(ret).find("option:selected").text()
        }
        
		//获取属性
        
        function getObj(idAttr,idAttrvalue,textAttr,textAttrvalue){
        	var obj = new Object();
        	obj.idAttr = idAttr;
        	obj.idAttrvalue = idAttrvalue;
        	obj.textAttr = textAttr;
        	obj.textAttrvalue = textAttrvalue;
        	return obj;
        }
        
        var dataKnownAttr = [];
        var dataUnknownAttr = [];
        
        
      //添加已知属性
        function addAttr(){
        	var idAttr = $("#knownAttr").find("option:selected").val();
        	var idAttrvalue = $("#knownAttrValue").find("option:selected").val();
        	var textAttr = $("#knownAttr").find("option:selected").text();
        	var textAttrvalue = $("#knownAttrValue").find("option:selected").text();
        	
        	
        	if(document.getElementById("knownAttr").value!="a"&&document.getElementById("knownAttrValue").value!="a"){
        		var j = 0;
        		for(var i = 0; i < dataKnownAttr.length; i++){
		        	if(dataKnownAttr[i].idAttr == idAttr){
		        		j = 1;
		        	}
		        	
		        }
        		if(j == 1){
        			alert("该属性已经选择")
        		}else{
        			dataKnownAttr.push(getObj(idAttr,idAttrvalue,textAttr,textAttrvalue));
            		
            		var op_text = "<div style='padding-left:0;margin-left: 0;' id='attrknown"+idAttr+"' class='col-md-8'><div class='col-md-11' style='display:inline;padding-left:0;'>"+$("#knownAttr").find("option:selected").text()+"："+$("#knownAttrValue").find("option:selected").text()+"</div>";
/*             		var op_text = "<input id='attrknown"+idAttr+"' type='text' value='"+$("#knownAttr").find("option:selected").text()+"/"+$("#knownAttrValue").find("option:selected").text()+"' disabled='disabled'>"; */
    	            var del = "<div><a style='color:red;' name='"+idAttr+"' id='known"+idAttr+"' onclick='deleteAttr(this.id,this.name)'>删除</a></div></div><br>";
    	            
    	            document.getElementById("checkedAttr").innerHTML += op_text + del;
    	        	/* document.getElementById("knownAttrValue").innerHTML="<option value=\"0\">--请选择--</option>"; */
    	        	
    	        	document.getElementById("knownAttr").firstElementChild.selected = true;
    	        	document.getElementById("knownAttrValue").firstElementChild.selected = true;
    	        	
    	        	
    	        	//发送获取标注属性请求
    	        	$.ajax({
    		        	type:"get",
    		    
    					 url:"querylabelattr.html", 
    					async:false,
    					dataType:"json",
    					success:function(data){
    						handle_unknown(data.attributes);
    					},
    					error:function(data){
    						alert("连接失败！");
    					}
    		        });
        		}
        	}
        }
      
        function handle_unknown(ret){
        	document.getElementById("unknownAttr").innerHTML = "";
        	var op = new Option("--请选择--","a");
        	document.getElementById("unknownAttr").options.add(op);
        	$.each(ret,function(index,v){
        		var op_unknownAttr = new Option(ret[index].attrname,ret[index].id);
            	document.getElementById("unknownAttr").options.add(op_unknownAttr);
        	});
        }
        
        
      //添加属性
        function addmore(){
        		var idAttr = $("#unknownAttr").find("option:selected").val();
        		var idAttrvalue = "";
        		var textAttr = $("#unknownAttr").find("option:selected").text();
        		var textAttrvalue = "";
        	
        	var j = 0;
        	
        	if(document.getElementById("unknownAttr").value!="a"){
		        for(var i = 0; i < dataUnknownAttr.length; i++){
		        	if(dataUnknownAttr[i].idAttr == idAttr){
		        		j = 1;
		        	}
		        	
		        }
		        
		        if(j == 0){
		        		var obj = document.getElementsByName("checkbox");
		        		
		        		for(var i = 0; i<obj.length; i++){
		        			if(obj[i].checked == true){
		        				idAttrvalue += obj[i].value;
		        				textAttrvalue += obj[i].nextSibling.nodeValue;
		        				if(i != obj.length - 1){
		        					idAttrvalue += ":";
		        					textAttrvalue += "，";
		        				}
		        			}
		        		}
		        		
		        		if(textAttrvalue.substring(textAttrvalue.length-1, textAttrvalue.length) == "，"){
		        			textAttrvalue = textAttrvalue.substring(0, textAttrvalue.length - 1)
		        		}

		        		if(idAttrvalue == "" && document.getElementById("showUnknownAttrvalue").innerHTML != "<br>"){
		        			alert("请选择属性值");
		        		}else{
			        		dataUnknownAttr.push(getObj(idAttr,idAttrvalue,textAttr,textAttrvalue));
				        		
				        	var op_text = "<div style='padding-left:0;margin-left: 0;' id='attrunknown"+idAttr+"' class='col-md-8'><div class='col-md-11' style='display:inline;padding-left:0;'>"+$("#unknownAttr").find("option:selected").text()+"："+textAttrvalue+"</div>";
					        var del = "<div><a name='"+idAttr+"' style='color:red;' id='unknown"+idAttr+"' onclick='deleteAttr1(this.id,this.name)'>删除</a></div></div><br>";
					      
					        
					        document.getElementById("checkedUnknown").innerHTML += op_text + del;
				        	document.getElementById("showUnknownAttrvalue").innerHTML = "";
				        	document.getElementById("showUnknownAttrvalue").style.display = "none";
				        	document.getElementById("unknownAttr").firstElementChild.selected = true;
		        		}
		        }else{
		        		alert("该属性已经被选择");
		        }
		        
	        }else{
		    	alert("请选择属性值");
		    }
            
        }
      
      //确认提交
        function confirm(){
            if(document.getElementById("task_name").value == ""){
                document.getElementById("tasknameNull").style.display = "inline";
                return false;
            }else{
            	document.getElementById("tasknameNull").style.display = "none";
            	$.ajax({
                    type:"post",
                    url:"checkTaskname.html?task_name="+name,
                    async:false,
                    success:function(data){
                    	
                        if(data == "0"){
                            document.getElementById("tasknameNull").style.display = "none";
                			document.getElementById("tasknameExist").style.display = "inline";
                            document.getElementById("task_name").value = "";
                            document.getElementById("task_name").focus();
                        }else{
                        	document.getElementById("tasknameExist").style.display = "none";
                        }
                    },
                    error:function(data){
                        alert("连接失败");
                    }
                });
                if(document.getElementById("task_address").value == ""){
                	document.getElementById("addressNull").style.display = "inline";
                    return false;
                }else{
                	document.getElementById("addressNull").style.display = "none";
                   if(dataKnownAttr.length == 0){
                	   document.getElementById("yizhiAttr").style.display = "inline";
                        return false;
                    }else{
                        if(dataUnknownAttr.length == 0){
                        	document.getElementById("yizhiAttr").style.display = "none";
                        	document.getElementById("biaozhuAttr").style.display = "inline";
                            return false;
                        }else{
                        	document.getElementById("biaozhuAttr").style.display = "none";
                        }
                    }
                }
            }

            document.getElementById("touming").style.display = "block";
            document.getElementById("hide").style.display = "block";

            document.getElementById("hide_unknownAttr").value = "";
            document.getElementById("confirm_unknownAttr").innerHTML = "";
            
            document.getElementById("hide_knownAttr").value = "";
            document.getElementById("confirm_knownAttr").innerHTML = "";
            
            document.getElementById("confirm_task_pri").innerHTML = $("#task_pri").find("option:selected").val();
            document.getElementById("hide_task_pri").value = $("#task_pri").find("option:selected").val();

            document.getElementById("hide_task_name").value = document.getElementById("task_name").value;
            document.getElementById("confirm_task_name").innerHTML = document.getElementById("task_name").value;

            document.getElementById("hide_task_address").value = document.getElementById("task_address").value;
            document.getElementById("confirm_task_address").innerHTML = document.getElementById("task_address").value;
            
            document.getElementById("hide_beizhu").value = document.getElementById("task_beizhu").value;
            document.getElementById("confirm_beizhu").innerHTML = document.getElementById("task_beizhu").value;

			document.getElementById("hide_knownAttr").value = "";
            for(var i=0;i<dataKnownAttr.length;i++){
                document.getElementById("confirm_knownAttr").innerHTML += dataKnownAttr[i].textAttr + "：" + dataKnownAttr[i].textAttrvalue + "；";
                document.getElementById("hide_knownAttr").value += dataKnownAttr[i].idAttr + "：" + dataKnownAttr[i].idAttrvalue + "；";
            }
            
            document.getElementById("hide_unknownAttr").value = "";
            for(var i=0;i<dataUnknownAttr.length;i++){
                document.getElementById("confirm_unknownAttr").innerHTML += dataUnknownAttr[i].textAttr + "：" + dataUnknownAttr[i].textAttrvalue + "；";
                document.getElementById("hide_unknownAttr").value += dataUnknownAttr[i].idAttr + "：" + dataUnknownAttr[i].idAttrvalue + "；";
            }
            return true;
        }
      
        function concel(){
            document.getElementById("hide").style.display = "none";
            document.getElementById("touming").style.display = "none";
        }
        
      //判断任务名称是否存在
        function checkTaskname(){
        	var name = document.getElementById("task_name").value;
        	if(name == null || name == ""){
        		document.getElementById("tasknameNull").style.display = "inline";
                document.getElementById("tasknameExist").style.display = "none";
                document.getElementById("task_name").focus();
        	}else{
        		$.ajax({
                    type:"post",
                    url:"checkTaskname.html?task_name="+name,
                    async:false,
                    success:function(data){
                        if(data == "0"){
                            document.getElementById("tasknameNull").style.display = "none";
                			document.getElementById("tasknameExist").style.display = "inline";
                            document.getElementById("task_name").value = "";
                            document.getElementById("task_name").focus();
                        }else{
                        	document.getElementById("tasknameNull").style.display = "none";
                			document.getElementById("tasknameExist").style.display = "none";
                        }
                    },
                    error:function(data){
                        alert("连接失败");
                    }
                });
        	}
        }
      
      //获取属性值
        function changeUnknownAttr(ret){
        	document.getElementById("showUnknownAttrvalue").innerHTML = "";
        	if(document.getElementById("unknownAttr").value != "a"){
	        	var id = $("#unknownAttr").find("option:selected").val();
	        	$.ajax({
	            	type:"get",
	                  
	            	url:"queryAllValuesname.html?id="+id,
	                async:false,
	                dataType:"json",
	                success:function(data){
	                	handle_unknownValues(data.attrvalues);
	                },
	                error:function(data){
	                	alert("连接失败");
	                }
	          	});
        	}else{
        		document.getElementById("showUnknownAttrvalue").innerHTML = "";
        	}
        }
        
        function handle_unknownValues(ret){
        	$.each(ret,function(index,v){
        		document.getElementById("showUnknownAttrvalue").innerHTML += "<input type='checkbox' value='"+ret[index].id+"' name='checkbox'>"+ret[index].attrvalue;
        	});
        	document.getElementById("showUnknownAttrvalue").innerHTML += "<br>";
        	document.getElementById("showUnknownAttrvalue").style.display = "block";
        }
    </script>
</body>
</html>