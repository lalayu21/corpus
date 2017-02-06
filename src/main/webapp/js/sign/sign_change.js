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
function username_blur(value,id,userid,defaultValue){
    if(!value){
        document.getElementById(id).value=defaultValue;
        document.getElementById(id).style.color='#999';
        document.getElementById(id + '_null').style.display = "inline";
    }else{
        switch(id){
            case 'username':
                $.ajax({
                    url:'../checkUsername.html?username=' + value + "&id=" + userid,
                    type:'get',
                    async:false,

                    success:function(username){
                        if(username != '0'){
                            document.getElementById("username_exist").style.display = "inline";
                        }
                    },
                    error:function(username){
                        alert("连接失败");
                    }
                });break;
            case 'idCode':
                if(idCode_value.length != 18){
                    document.getElementById("idCode_format").style.display = "inline";
                    return false;
                }else{
                    var a = /\d{17}/;
                    var b = /\d{1}/;
                    if(a.exec(idCode_value) && (idCode_value.charAt(17) == 'x' || idCode_value.charAt(17) == 'X' || b.exec(idCode_value.charAt(17)))){
                    }else{
                        document.getElementById("idCode_format").style.display = "inline";
                    }
                };break;
            case 'email':
                var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
                var email_value = document.getElementById("email").value;
                if(myreg.test(email_value)){
                }else{
                    document.getElementById("email_format").style.display = "inline";
                };break;
            case 'tel':
                //判断手机号是否正确
                var reg_tel = /^1\d{10}$/;
                var tel_value = document.getElementById("tel").value;
                if(reg_tel.exec(tel_value)){
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



