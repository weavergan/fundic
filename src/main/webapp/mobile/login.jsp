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
<div id="loginDiv1">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">登录</h4>
              </div>
              <div class="modal-body login">
              <form id="loginForm">
		        <p><label>手机号：　</label><input type="text" name="username" id="username" class="text"></p>
		        <p><label>密码：　　</label><input type="password" name="password" id="password" class="text"></p>
		        <p><input type="button" value="登 录" class="btn" onclick="submitForm()">
		        <input type="reset" value="清 空" class="btn">
		        <input type="reset" value="注册" class="btn" onclick="location.href='/mobile/register.jsp'">
		        <a href="/mobile/forgetPassword.jsp" data-dismiss="modal" data-toggle="modal">忘记密码</a></p>
		      </form>
              </div>
            </div>
          </div>
</div>
</body>
</html>
