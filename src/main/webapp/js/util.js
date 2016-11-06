var now = new Date();
var yesterday = new Date();
yesterday.setDate(yesterday.getDate() - 1);

function dealLoginError(data) {
	var errorMsgList = [];
	for (var i = 0, len = data.errors.length; i < len; i++) {
		errorMsgList.push(data.errors[i]);
	}
	if (errorMsgList.length) {
		alert(errorMsgList.join('\n'));
//		clearForm();
	}
	
}

function dealError(data) {
	var errorMsgList = [];
	for (var i = 0, len = data.errors.length; i < len; i++) {
		errorMsgList.push(data.errors[i]);
	}
	if (errorMsgList.length) {
		alert(errorMsgList.join('\n'));
	}
	
}

//登录验证
function checkLogin() {
	if($('#username').val().trim() == "") {
		alert("请输入手机号！");
		return false;
	}else if(!isMobileNum($('#username').val().trim())) {
		alert("手机号格式不正确！");
		return false;
	} else if($('#password').val() == "") {
		alert("请输入密码！");
		return false;
	}
	return true;
}

//注册验证
function checkRegister() {
	if($('#username_r').val().trim() == "") {
		alert("请输入手机号！");
		return false;
	}else if(!isMobileNum($('#username_r').val().trim())) {
		alert("手机号格式不正确！");
		return false;
	} else if($('#regCode').val() == "") {
		alert("请输入注册码！");
		return false;
	} else if($('#password_r').val() == "") {
		alert("请输入密码！");
		return false;
	} else if(!isIncludeDigAndWord($('#password_r').val())) {
		alert("密码必须同时包括字母和数字！");
		return false;
	} else if($('#password2_r').val() != $('#password_r').val()) {
		alert("两次密码不一致！");
		return false;
	} else if($('#checknum').val() == "") {
		alert("请输入验证码");
		return false;
	}
	return true;
}

//重置密码验证
function checkRegister2() {
	if($('#username_f').val().trim() == "") {
		alert("请输入手机号！");
		return false;
	}else if(!isMobileNum($('#username_f').val().trim())) {
		alert("手机号格式不正确！");
		return false;
	} else if($('#regCode_f').val() == "") {
		alert("请输入注册码！");
		return false;
	} else if($('#password_f').val() == "") {
		alert("请输入密码！");
		return false;
	} else if(!isIncludeDigAndWord($('#password_f').val())) {
		alert("密码必须同时包括字母和数字！");
		return false;
	} else if($('#password2_f').val() != $('#password_f').val()) {
		alert("两次密码不一致！");
		return false;
	} else if($('#checknum_f').val() == "") {
		alert("请输入验证码");
		return false;
	}
	return true;
}

//正整数
function isInteger(obj) {
	var reg=/^[1-9][0-9]*$/;
	return reg.test(obj);
}

//为空
function isNull(obj) {
	return obj === "";
}

//两位浮点数
function isFloat(obj) {
	var reg=/^\-?[0-9]+(.[0-9]{1,2})?$/;
	return reg.test(obj);
}

//同时包括数字和字母
function isIncludeDigAndWord(obj) {
	var regDig = /[0-9]+/;
	var regWord = /[a-zA-Z]+/;
	return regDig.test(obj) && regWord.test(obj);
}

//手机号验证
function isMobileNum(obj) {
	var reg= /^0?(13[0-9]|15[012356789]|17[0678]|18[0-9]|14[57])[0-9]{8}$/;
	return reg.test(obj);
}

//验证邮箱
function checkEmail() {
	var email = $('#email').val();
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
	return reg.test(email); 
}

//检查基金代码
function checkCode(obj) {
	if(isNull(obj)) {
		return false;
	} else if(obj.length != 6) {
		return false;
	}
	return true;
}

function noPermission() {
	alert("抱歉，该功能开通会员之后才能使用！");
}

function resetParameters() {
	$("#purchaseFee").textbox('setValue', 0.15);
	$("#sellFee").textbox('setValue', 0.5);
	$("#minSellCount").textbox('setValue', 10);
	$("#minHoldCount").textbox('setValue', 100);
	$("#firstInvest").textbox('setValue', 500);
	$("#startSell").textbox('setValue', 5);
	$("#stopProfitPot").textbox('setValue', 10);
	$("#startInvest").textbox('setValue', -0.5);
	$("#baseMultiple").textbox('setValue', 1.2);
	$("#moreMultiple").textbox('setValue', 0.1);
	
	$("#isStopProfit").attr("checked", false);
	$("#stopProfitPot").combobox('disable');
	$("#isZS").attr("checked", false); 
	$("#isFenhongInvest").attr("checked", false); 
	$("#isIncrease").attr("checked", false); 
	$("#isAutoSell").attr("checked", false); 
	$("#startSell").combobox('disable');
	$("#minSellCount").combobox('disable'); 
	$("#minHoldCount").combobox('disable'); 
	$("#isSetMinRate").attr("checked", false); 
	
	$("#startDate").datebox('setValue','2016-04-15');
	$("#endDate").datebox('setValue',yesterday.getFullYear() + "-" + (yesterday.getMonth() + 1) + "-" + yesterday.getDate());
	
	$('#codeList').datagrid('loadData', { total: 0, rows: [] });
}
