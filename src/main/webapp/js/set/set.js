
//判断当前用户选择的是否为使用所有数据
function useAllData(){
	$("#allData").show();
	$("#partData").hide();
	$("#trainTime").val("");
	$("#testTime").val("");
}

function usePartData(){
	$("#allData").hide();
	$("#partData").show();
	$("#percent").val("");
}
