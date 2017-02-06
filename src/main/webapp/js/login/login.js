
function check(){
	
	document.getElementById("passwordNull").style.display = "none";
	document.getElementById("usernameNull").style.display = "none";
	
	var _username = document.getElementById("username").value;
	var _password = document.getElementById("password").value;
	
	if(_username === null || _username === ""){
		document.getElementById("usernameNull").style.display = "inline";
		return false;
	}else{
		if(_password === null || _password === ""){
			document.getElementById("passwordNull").style.display = "inline";
			return false;
		}
		return true;
	}
}