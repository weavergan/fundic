<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>风险大师</title>

<!-- Bootstrap core CSS -->
<link href="../assets/css/bootstrap.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="../assets/css/main.css" rel="stylesheet">
<link href="../assets/css/font-awesome.min.css" rel="stylesheet">
<link href="../assets/css/animate-custom.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="../css/login.css">

<script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="../assets/js/modernizr.custom.js"></script>
<script type="text/javascript" src="../js/mobileLogin.js"></script>
<script type="text/javascript" src="../js/util.js"></script>

</head>

<body data-spy="scroll" data-offset="0" data-target="#navbar-main">
<div id="forgetPassword">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">密码重置</h4>
              </div>
              <div class="modal-body login">
              <form id="resetPasswordForm">
		        <p><label>手机号：　</label><input type="text" id="username_f" class="text">
		        </p>
		        <p><label>　　　　　</label><input id="sendCodeButtun_f" type="button" value="发送注册码" style="width: 100px;margin-left:0px;" class="btn" onclick="sendRegCode2()"></p>
		        <p><label>注册码：　</label><input type="text" id="regCode_f" class="text"></p>
		        <p><label>新密码：　</label><input type="password" id="password_f" class="text"></p>
		        <p><label>确认密码：</label><input type="password" id="password2_f" class="text"></p>
		        <p><label>验证码：　</label><input type="text" id="checknum_f" class="text"></p>
		        <p><label>　　　　　</label><img id="checkNumImage2" src="/user/showCheckNum.do" height="20px" width="50px" onclick="changeCheckNum2()"> </p>
		        <p><input type="button" value="提 交" class="btn" onclick="resetPassword()">
		        <input type="reset" value="清 空" class="btn">
		        <a href="/mobile/login.jsp" data-dismiss="modal" data-toggle="modal">返回登录</a></p>
		      </form>
              </div>
            </div>
          </div>
</div>
</body>
</html>
