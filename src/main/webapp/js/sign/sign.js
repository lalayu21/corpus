/**
 * Created by LiuBin on 2016/7/11.
 */

//username onfocue
function username_focus(value,id,defaultValue){
    if(value == defaultValue){
        document.getElementById(id).value='';
        document.getElementById(id).style.color = "#000";
    }
}

//username onblur
function username_blur(value,id,defaultValue){
    if(!value){
        document.getElementById(id).value=defaultValue;
        document.getElementById(id).style.color='#999';
        document.getElementById(id + '_null').style.display = "inline";
    }else{
        switch(id){
            case 'username':
                $.ajax({
                    url:'checkUsername.html?username=' + value,
                    type:'get',
                    async:false,

                    success:function(username){
                        if(username != '0'){
                            document.getElementById("username_exist").style.display = "inline";
                        }
                    },
                    /*success:function(data){
                        alert(data.username);
                    },*/
                    error:function(username){
                        alert("连接失败");
                    }
                });break;
            case 'idCode':
                if(value.length != 18){
                    document.getElementById("idCode_format").style.display = "inline";
                    return false;
                }else{
                    var a = /\d{17}/;
                    var b = /\d{1}/;
                    if(a.exec(value) && (value.charAt(17) == 'x' || value.charAt(17) == 'X' || b.exec(value.charAt(17)))){
                    }else{
                        document.getElementById("idCode_format").style.display = "inline";
                    }
                };break;
            case 'email':
                var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
                var value = document.getElementById("email").value;
                if(myreg.test(value)){
                }else{
                    document.getElementById("email_format").style.display = "inline";
                };break;
            case 'tel':
                //判断手机号是否正确
                var reg_tel = /^1\d{10}$/;
                var value = document.getElementById("tel").value;
                if(reg_tel.exec(value)){
                }else{
                    document.getElementById("tel_format").style.display = "inline";
                };break;
            default:
                break;
        }
    }
}

//username change
function username_change(id){
    document.getElementById(id + "_null").style.display = "none";
    document.getElementById(id + "_exist").style.display = "none";
    document.getElementById(id + "_format").style.display = "none";
}

//password blur
function psw_blur(value,id,defaultValue){
    if(!value){
        document.getElementById(id).value=defaultValue;
        document.getElementById(id).style.color='#999';
        document.getElementById(id + '_null').style.display = "inline";
    }else{
        if(document.getElementById("password").value != document.getElementById("password_confirm").value){
            document.getElementById("password").value = "";
            document.getElementById("password_confirm").value = "";
            document.getElementById("password_format").style.display = "inline";
        }
    }
}

function check(){
    var flag = 0;
    //判断用户名是否为空
    if(document.getElementById("username").value == "" || document.getElementById("username").value == null){
        document.getElementById("username_null").style.display = "inline";
        return false;
    }else{
        //判断用户名格式
        var username_value = document.getElementById("username").value;

        //判断用户名是否存在
        $.ajax({
        	url:'checkUsername.html?username=' + username_value,
            type:'get',
            async:false,

            success:function(username){
            	if(username != '0'){
                    document.getElementById("username_exist").style.display = "inline";
                    flag = 1;
                }else{
                    //判断密码是否为空
                    if(document.getElementById("password").value == null || document.getElementById("password").value == ""){
                        document.getElementById("password_null").style.display = "inline";
                        flag = 1;
                    }else{
                        //判断确认密码是否为空
                        if(document.getElementById("password_confirm").value == "" || document.getElementById("password_confirm").value == null){
                            document.getElementById("password_confirm_null").style.display = "inline";
                            flag = 1;
                        }else{
                            //判断密码和确认密码输入是否一致
                            if(document.getElementById("password").value != document.getElementById("password_confirm").value){
                                document.getElementById("password").value = "";
                                document.getElementById("password_confirm").value = "";
                                document.getElementById("password_format").style.display = "inline";
                                flag = 1;
                            }else{
                                //判断真实姓名是否为空
                                if(document.getElementById("truename").value == "" || document.getElementById("truename").value == null){
                                    document.getElementById("truename_null").style.display = "inline";
                                    flag = 1;
                                }else{
                                    var idCode_value = document.getElementById("idCode").value;
                                    //判断身份账号是否为空
                                    if(idCode_value == "" || idCode_value == null){
                                        document.getElementById("idCode_null").style.display = "inline";
                                        flag = 1;
                                    }else{
                                        //判断身份证号格式是否正确
                                        if(idCode_value.length != 18){
                                            document.getElementById("idCode_format").style.display = "inline";
                                            flag = 1;
                                        }else{
                                            var a = /\d{17}/;
                                            var b = /\d{1}/;
                                            if(a.exec(idCode_value) && (idCode_value.charAt(17) == 'x' || idCode_value.charAt(17) == 'X' || b.exec(idCode_value.charAt(17)))){
                                                //判断邮箱是否为空
                                                if(document.getElementById("email").value == "" || document.getElementById("email").value == null){
                                                    document.getElementById("email_null").style.display = "inline";
                                                    flag = 1;
                                                }else{
                                                    //判断邮箱格式是否正确
                                                    var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
                                                    var email_value = document.getElementById("email").value;
                                                    if(myreg.test(email_value)){
                                                        //判断手机号是否正确
                                                        var tel_value = document.getElementById("tel").value;
                                                        if(tel_value == "" || tel_value == null){
                                                            document.getElementById("tel_null").style.display = "inline";
                                                            flag = 1;
                                                        }else{
                                                            var reg_tel = /^1\d{10}$/;
                                                            if(reg_tel.exec(tel_value)){
                                                            	
                                                            }else{
                                                                document.getElementById("tel_format").style.display = "inline";
                                                                flag = 1;
                                                            }
                                                        }
                                                    }else{
                                                        document.getElementById("email_format").style.display = "inline";
                                                        flag = 1;
                                                    }
                                                }
                                            }else{
                                                document.getElementById("idCode_format").style.display = "inline";
                                                flag = 1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            },
            error:function(username){
                alert("连接失败");
            }
        });
    }
    
    if(flag == 1){
    	return false;
    }
}


