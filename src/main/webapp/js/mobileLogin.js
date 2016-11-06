//发送注册码
function sendRegCode() {
	if(!isMobileNum($('#username_r').val())) {
		alert("请输入正确的手机号！");
		return;
	}
	var url = '/user/sendRegCode.do';
	$.ajax({
		type : "GET",
		url : url,
		data: {
			username:$('#username_r').val()
		},
		contentType: "application/json; charset=utf-8",     
        dataType: "json", 
		success : function(data) {
			if(data.success) {
				alert("发送成功");
	        } else {
	        	fail(data);
			}
		}
	});
	showtime(30); //显示30秒倒计时
}

//重置密码发送注册码
function sendRegCode2() {
	if(!isMobileNum($('#username_f').val())) {
		alert("请输入正确的手机号！");
		return;
	}
	var url = '/user/sendRegCode.do';
	$.ajax({
		type : "GET",
		url : url,
		data: {
			username:$('#username_f').val()
		},
		contentType: "application/json; charset=utf-8",     
        dataType: "json", 
		success : function(data) {
			if(data.success) {
				alert("发送成功");
	        } else {
	        	fail(data);
			}
		}
	});
	showtime2(30); //显示30秒倒计时
}

function showtime(t){ 
	$('#sendCodeButtun').attr('disabled',"true");
    for(i=1; i<=t; i++) { 
        window.setTimeout("update_p(" + i + "," + t + ")", i * 1000); 
    } 
 
} 

function showtime2(t){ 
	$('#sendCodeButtun_f').attr('disabled',"true");
    for(i=1; i<=t; i++) { 
        window.setTimeout("update_p(" + i + "," + t + ")", i * 1000); 
    } 
 
} 
function update_p(num, t) { 
    if(num == t) { 
    	$('#sendCodeButtun_f').val("重新发送"); 
    	$('#sendCodeButtun_f').removeAttr("disabled"); 
    } 
    else { 
        printnr = t - num; 
        $('#sendCodeButtun_f').val("" + printnr + "秒"); 
    } 
} 

//登录
function submitForm(){
	if(!checkLogin()) {
		return;
	}
	var url = '/user/loginAction.do';
	//获取用户保存的code
	$.ajax({
		type : "GET",
		url : url,
		data: {
			username:$('#username').val(),
			password:$('#password').val()
		},
		contentType: "application/json; charset=utf-8",     
        dataType: "json", 
		success : function(data) {
			if(data.success) {
				window.location.href= "/jsp/main.jsp";
	        } else {
	        	fail(data);
			}
		}
	});
	
}

//注册
function submitRegister(){
	if(!checkRegister()) {
		return;
	}
	var url = '/user/registerAction.do';
	//获取用户保存的code
	$.ajax({
		type : "GET",
		url : url,
		data: {
			username:$('#username_r').val(),
			regCode:$('#regCode').val(),
			password:$('#password_r').val(),
			password2:$('#password2_r').val(),
			checkNum:$('#checknum').val()
		},
		contentType: "application/json; charset=utf-8",     
        dataType: "json", 
		success : function(data) {
			if(data.success) {
				window.location.href= "/jsp/main.jsp";
	        } else {
	        	fail(data);
	        	changeCheckNum();
			}
		}
	});
}
//注册验证码
function changeCheckNum() {
	document.getElementById("checkNumImage").src = "/user/showCheckNum.do?timestamp=" + new Date().getTime();
}

//找回密码
function resetPassword(){
	if(!checkRegister2()) {
		return;
	}
	var url = '/user/resetPasswordAction.do';
	//获取用户保存的code
	$.ajax({
		type : "GET",
		url : url,
		data: {
			username:$('#username_f').val(),
			regCode:$('#regCode_f').val(),
			password:$('#password_f').val(),
			password2:$('#password2_f').val(),
			checkNum:$('#checknum_f').val()
		},
		contentType: "application/json; charset=utf-8",     
		dataType: "json", 
		success : function(data) {
			if(data.success) {
				alert("密码重置成功，请重新登录！");
				window.location.href= "/mobile/login.jsp";
			} else {
				fail(data);
				changeCheckNum2();
			}
		}
	});
}
//找回密码验证码
function changeCheckNum2() {
	document.getElementById("checkNumImage2").src = "/user/showCheckNum.do?timestamp=" + new Date().getTime();
}

function fail(data){
	dealLoginError(data);
}