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
<link href="assets/css/bootstrap.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="assets/css/main.css" rel="stylesheet">
<link href="assets/css/font-awesome.min.css" rel="stylesheet">
<link href="assets/css/animate-custom.css" rel="stylesheet">
<!-- <link href='http://fonts.useso.com/css?family=Lato:300,400,700,300italic,400italic' rel='stylesheet' type='text/css'>
<link href='http://fonts.useso.com/css?family=Raleway:400,300,700' rel='stylesheet' type='text/css'> -->
<link rel="stylesheet" type="text/css" href="css/login.css">

<script type="text/javascript" src="js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="assets/js/modernizr.custom.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/util.js"></script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body data-spy="scroll" data-offset="0" data-target="#navbar-main">
<div class="modal fade" id="loginDiv1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
		        <a href="#forgetPassword" target="view_window" data-dismiss="modal" data-toggle="modal">忘记密码</a></p>
		    </form>
              </div>
            </div>
          </div>
</div>
<div class="modal fade" id="registerDiv1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">注册</h4>
              </div>
              <div class="modal-body login">
              <form id="registerForm">
		        <p><label>手机号：　</label><input type="text" id="username_r" class="text">
		        </p>
		        <p><label>　　　　　</label><input id="sendCodeButtun" type="button" value="发送注册码" style="width: 100px;margin-left:0px;" class="btn" onclick="sendRegCode()"></p>
		        <p><label>注册码：　</label><input type="text" id="regCode" class="text"></p>
		        <p><label>密码：　　</label><input type="password" id="password_r" class="text"></p>
		        <p><label>确认密码：</label><input type="password" id="password2_r" class="text"></p>
		        <p><label>验证码：　</label><input type="text" name="checknum" id="checknum" class="text"></p>
		        <p><label>　　　　　</label><img id="checkNumImage" src="/user/showCheckNum.do" height="20px" width="50px" onclick="changeCheckNum()"> </p>
		        <p><input type="button" value="注 册" class="btn" onclick="submitRegister()">
		        <input type="reset" value="清 空" class="btn"></p>
		    </form>
              </div>
            </div>
          </div>
</div>
<div class="modal fade" id="forgetPassword" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
		        <input type="reset" value="清 空" class="btn"></p>
		    </form>
              </div>
            </div>
          </div>
</div>

<div id="navbar-main"> 
  <!-- Fixed navbar -->
  <div class="navbar navbar-default navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
        <a data-toggle="modal" href="#loginDiv1" class="navbar-brand smoothScroll"><i class="fa fa-location-arrow"></i> 登陆</a>
		<a data-toggle="modal" href="#registerDiv1" class="navbar-brand smoothScroll"><i class="fa fa-location-arrow"></i> 注册</a> </div>
        <script type="text/javascript">
        </script>
	  <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-right">
          <li><a href="#home" class="smoothScroll">主页</a></li>
          <li> <a href="#about" class="smoothScroll"> 关于</a></li>
          <li> <a href="#services" class="smoothScroll"> 服务</a></li>
          <li> <a href="#portfolio" class="smoothScroll"> 经典案例</a></li>
          <li> <a href="#team" class="smoothScroll"> 开发团队</a></li>
          <li> <a href="#contact" class="smoothScroll"> 联系我们</a></li>
        </ul>
      </div>
      <!--/.nav-collapse --> 
    </div>
  </div>
</div>

<!-- ==== HEADERWRAP ==== -->
<div id="headerwrap" name="home">
  <header class="clearfix">
    <h1>小耿教你买基金</h1>
    <p>增投是以定投为原型、买跌不买涨、越跌越买的一种投资策略，<br>
      其核心思想是缩小跌幅、放大涨幅，以抵抗基金净值波动带来的风险</p>
    <a href="/file/fundinc.exe" target="view_window" class="smoothScroll btn btn-lg">下载软件</a> </header>
</div>
<!-- /headerwrap --> 

<!-- ==== ABOUT ==== -->
<div id="about" name="about">
  <div class="container">
    <div class="row white">
      <div class="col-md-6"> <img class="img-responsive" src="assets/img/about/about1.jpg" align=""> </div>
      <div class="col-md-6">
        <h3>关于我们</h3>
        <p>增投的灵感来源于2015年6月份，并于8月份量化，9月份开始实践，在10-12月份的实践中逐步完善。最初投资规则只有一句话，然后以Excel为计算工具计算投入金额；后来开发了软件版，并在计算更为简单的前提下将方法大大完善； 再后来由于受到只能在电脑上运行、携带不方便的限制，逐步开发出了网页版和手机版，这样一来，无论你身处何地，只要能够上网，便随时能够享受到增投带来的便利。</p>
        <h3>为什么选择增投？</h3>
        <p>传统的定投风险较小，但是投资周期长；而投资周期较短的低买高卖手段对投资者的判断能力、心理素质要求较高，风险较大。增投同时具备投资周期短、风险较小的优点，收益率适中，长期保持稳定的收益，则在同样的时间内其收益可以超过定投，比较适合每天能在固定时间14:50-15:00抽出时间进行操作的上班一族。</p>
      </div>
    </div>
    <!-- row --> 
  </div>
</div>
<!-- container --> 

<!-- ==== SERVICES ==== -->
<div id="services" name="services">
  <div class="container">
    <div class="row">
      <h2 class="centered">我们的服务</h2>
      <hr>
      <div class="col-lg-8 col-lg-offset-2">
        <p class="large">所有软件的开发都是由开发者利用业余时间完成，请大家耐心等待！</p>
      </div>
      <div class="col-lg-3 callout"> <i class="fa fa-desktop fa-3x"></i>
        <h3>windows软件</h3>
        <p>不断升级中...</p>
      </div>
      <div class="col-lg-3 callout"> <i class="fa fa-gears fa-3x"></i>
        <h3>安卓app</h3>
        <p>开发中...</p>
      </div>
      <div class="col-lg-3 callout"> <i class="fa fa-dot-circle-o fa-3x"></i>
        <h3>苹果app</h3>
        <p>开发中...</p>
      </div>
      <div class="col-lg-3 callout"> <i class="fa fa-shopping-cart  fa-3x"></i>
        <h3>网页版</h3>
        <p>不断升级中...</p>
      </div>
                
    </div>
    <!-- row --> 
  </div>
</div>
<!-- container --> 

<!-- ==== PORTFOLIO ==== -->
<div id="portfolio" name="portfolio">
  <div class="container">
    <div class="row">
      <h2 class="centered">经典案例</h2>
      <hr>
      <div class="col-lg-8 col-lg-offset-2 centered">
        <p class="large">以下是最经典的操作案例，在净值下降的第一天开始投入，到反弹后的最高点结束，反弹后无论是否超过之前的最高净值，该方法都能获得不少的盈利。</p>
      </div>
    </div>
    <!-- /row -->
    <div class="container">
      <div class="row"> 
        
        <!-- PORTFOLIO IMAGE 1 -->
        <div class="col-md-4 ">
          <div class="grid mask">
            <figure> <img class="img-responsive" src="assets/img/portfolio/folio01.png" alt="">
              <figcaption>
                <h5>2015.6.15-7.24鹏华国防</h5>
                <a data-toggle="modal" href="#myModal1" class="btn btn-default">详细操作</a> </figcaption>
              <!-- /figcaption --> 
            </figure>
            <!-- /figure --> 
          </div>
          <!-- /grid-mask --> 
        </div>

        <!-- MODAL SHOW THE PORTFOLIO IMAGE. In this demo, all links point to this modal. You should create
						      a modal for each of your projects. -->
        
        <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">鹏华国防160630</h4>
              </div>
              <div class="modal-body">
                <p><img class="img-responsive" src="assets/img/portfolio/folio01-preview.png" alt=""></p>
                <p>操作详情：前期买入，越跌越买；后期卖出，越涨越卖。</p>
                <p><b><a href="http://pan.baidu.com/s/1Hui1g" target="view_window">下载软件</a></b></p>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
              </div>
            </div>
            <!-- /.modal-content --> 
          </div>
          <!-- /.modal-dialog --> 
        </div>
        <!-- /.modal --> 
        
        <!-- PORTFOLIO IMAGE 2 -->
        <div class="col-md-4">
          <div class="grid mask">
            <figure> <img class="img-responsive" src="assets/img/portfolio/folio02.png" alt="">
              <figcaption>
                <h5>2015.8.18-12.25国富价值</h5>
                <a data-toggle="modal" href="#myModal2" class="btn btn-default">详细操作</a> </figcaption>
              <!-- /figcaption --> 
            </figure>
            <!-- /figure --> 
          </div>
          <!-- /grid-mask --> 
        </div>
        
        <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">国富价值450004</h4>
              </div>
              <div class="modal-body">
                <p><img class="img-responsive" src="assets/img/portfolio/folio02-preview.png" alt=""></p>
                <p>操作详情：前期买入，越跌越买；后期卖出，越涨越卖。</p>
                <p><b><a href="http://pan.baidu.com/s/1Hui1g" target="view_window">下载软件</a></b></p>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
              </div>
            </div>
            <!-- /.modal-content --> 
          </div>
          <!-- /.modal-dialog --> 
        </div>
        <!-- /.modal --> 
        
        <!-- PORTFOLIO IMAGE 3 -->
        <div class="col-md-4">
          <div class="grid mask">
            <figure> <img class="img-responsive" src="assets/img/portfolio/folio03.png" alt="">
              <figcaption>
                <h5>2015.11.12-12.23富国券商</h5>
                <a data-toggle="modal" href="#myModal3" class="btn btn-default">详细操作</a> </figcaption>
              <!-- /figcaption --> 
            </figure>
            <!-- /figure --> 
          </div>
          <!-- /grid-mask --> 
        </div>
      </div>
      <!-- /row --> 
        
        <div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">富国券商161027</h4>
              </div>
              <div class="modal-body">
                <p><img class="img-responsive" src="assets/img/portfolio/folio03-preview.png" alt=""></p>
                <p>操作详情：前期买入，越跌越买；后期卖出，越涨越卖。</p>
                <p><b><a href="http://pan.baidu.com/s/1Hui1g" target="view_window">下载软件</a></b></p>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
              </div>
            </div>
            <!-- /.modal-content --> 
          </div>
          <!-- /.modal-dialog --> 
        </div>
        <!-- /.modal --> 
        
      <!-- PORTFOLIO IMAGE 4 -->
      <div class="row">
        <div class="col-md-4 ">
          <div class="grid mask">
            <figure> <img class="img-responsive" src="assets/img/portfolio/folio04.png" alt="">
              <figcaption>
                <h5>2015.12.23-2016.2.22富国发展</h5>
                <a data-toggle="modal" href="#myModal4" class="btn btn-default">详细操作</a> </figcaption>
              <!-- /figcaption --> 
            </figure>
            <!-- /figure --> 
          </div>
          <!-- /grid-mask --> 
        </div>
        
        <div class="modal fade" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">富国发展000471</h4>
              </div>
              <div class="modal-body">
                <p><img class="img-responsive" src="assets/img/portfolio/folio04-preview.png" alt=""></p>
                <p>操作详情：前期买入，越跌越买；后期卖出，越涨越卖。</p>
                <p><b><a href="http://pan.baidu.com/s/1Hui1g" target="view_window">下载软件</a></b></p>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
              </div>
            </div>
            <!-- /.modal-content --> 
          </div>
          <!-- /.modal-dialog --> 
        </div>
        <!-- /.modal --> 
        
        <!-- PORTFOLIO IMAGE 5 -->
        <div class="col-md-4">
          <div class="grid mask">
            <figure> <img class="img-responsive" src="assets/img/portfolio/folio05.png" alt="">
              <figcaption>
                <h5>2016.2.23-4.14中邮战略</h5>
                <a data-toggle="modal" href="#myModal5" class="btn btn-default">详细操作</a> </figcaption>
              <!-- /figcaption --> 
            </figure>
            <!-- /figure --> 
          </div>
          <!-- /grid-mask --> 
        </div>
        
        <div class="modal fade" id="myModal5" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">中邮战略590008</h4>
              </div>
              <div class="modal-body">
                <p><img class="img-responsive" src="assets/img/portfolio/folio05-preview.png" alt=""></p>
                <p>操作详情：前期买入，越跌越买；后期卖出，越涨越卖。</p>
                <p><b><a href="http://pan.baidu.com/s/1Hui1g" target="view_window">下载软件</a></b></p>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
              </div>
            </div>
            <!-- /.modal-content --> 
          </div>
          <!-- /.modal-dialog --> 
        </div>
        <!-- /.modal --> 

        <!-- /col --> 
      </div>
      <!-- /row --> 
    </div>
    <!-- /row --> 
  </div>
</div>
<!-- /container --> 

<!-- ==== TEAM MEMBERS ==== -->
<div id="team" name="team">
  <div class="container">
    <div class="row centered">
      <h2 class="centered">我们的开发团队</h2>
      <hr>
      <div class="col-lg-3 centered"> <img class="img img-circle" src="assets/img/team/team01.jpg" height="120px" width="120px" alt="">
        <h4>常惭愧</h4>
        <p>增投法的发明者<br>
		Windows软件的开发者</p>
      </div>
      <div class="col-lg-3 centered"> <img class="img img-circle" src="assets/img/team/team02.jpg" height="120px" width="120px" alt="">
        <h4>Weaver Gan</h4>
        <p>网络工程师<br>
        网页版的开发者</p>
      </div>
      <div class="col-lg-3 centered"> <img class="img img-circle" src="assets/img/team/team03.jpg" height="120px" width="120px" alt="">
        <h4>×××</h4>
        <p>软件工程师<br>
		安卓客户端的开发者</p>
      </div>
      <div class="col-lg-3 centered"> <img class="img img-circle" src="assets/img/team/team04.jpg" height="120px" width="120px" alt="">
        <h4>×××</h4>
        <p>软件工程师<br>
		苹果客户端的开发者</p>
      </div>
      <div class="col-lg-8 col-lg-offset-2 centered">
        <p class="large">我们致力于创造一个轻松和谐的投资氛围，将大部分工作交给CPU来完成，使您的情绪不再紧张，把更多的时间用在努力工作和陪伴家人上。</p>
      </div>
    </div>
  </div>
  <!-- row --> 
</div>
<!-- container --> 

<!-- ==== CONTACT ==== -->
<div id="contact" name="contact">
  <div class="container">
    <div class="row">
      <h2 class="centered">联系我们</h2>
      <hr>
      <div class="col-md-4 centered"> <i class="fa fa-map-marker fa-2x"></i>
        <p>北京市朝阳区北苑路32号</p>
      </div>
      <div class="col-md-4"> <i class="fa fa-envelope-o fa-2x"></i>
        <p>einstein_cool@126.com</p>
      </div>
      <div class="col-md-4"> <i class="fa fa-phone fa-2x"></i>
        <p>+86 186 12388 9910</p>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-8 col-lg-offset-2 centered">
        <p>如果您有好的建议，或对我们的服务有意见，请给我们发送电子邮件。或者加入讨论群：114242968。</p>
        <a href="https://shop63419396.taobao.com/" target="view_window" class="smoothScroll btn btn-lg">升级VIP会员</a></header>
        <!-- form --> 
      </div>
    </div>
    <!-- row --> 
    
  </div>
</div>
<!-- container -->

<div id="footerwrap">
  <div class="container">
    <div class="row">
      <div class="col-md-8"> <span class="copyright">Copyright &copy; 2015-2016 fundzt.com All Rights Reserved. 京ICP备16005910</span> </div>
      <div class="col-md-4">
        <ul class="list-inline social-buttons">
        </ul>
      </div>
    </div>
  </div>
</div>

<!-- Bootstrap core JavaScript
    ================================================== --> 
<!-- Placed at the end of the document so the pages load faster --> 

<script type="text/javascript" src="assets/js/bootstrap.min.js"></script> 
<script type="text/javascript" src="assets/js/retina.js"></script> 
<script type="text/javascript" src="assets/js/jquery.easing.1.3.js"></script> 
<script type="text/javascript" src="assets/js/smoothscroll.js"></script> 
<script type="text/javascript" src="assets/js/jquery-func.js"></script>
</body>
</html>
