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
<!-- <link href='http://fonts.useso.com/css?family=Lato:300,400,700,300italic,400italic' rel='stylesheet' type='text/css'> -->
<!-- <link href='http://fonts.useso.com/css?family=Raleway:400,300,700' rel='stylesheet' type='text/css'> -->
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
  <div style="font-family:微软雅黑" class="navbar navbar-default navbar-fixed-top">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
        <a data-toggle="modal" href="#loginDiv1" class="navbar-brand smoothScroll"><i class="fa fa-location-arrow"></i> 登陆</a>
		<a data-toggle="modal" href="#registerDiv1" class="navbar-brand smoothScroll"><i class="fa fa-location-arrow"></i> 注册</a> </div>
        <script type="text/javascript">
        </script>
	  <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-right">
          <li> <a href="#headerwrap" class="smoothScroll">主页</a></li>
          <li> <a href="#about" class="smoothScroll">介绍</a></li>
          <!--<li> <a href="#services" class="smoothScroll">服务</a></li>-->
          <li> <a href="#portfolio" class="smoothScroll">示例</a></li>
          <!--<li> <a href="#team" class="smoothScroll">团队</a></li>-->
          <li> <a href="#contact" class="smoothScroll">联系方式</a></li>
          <li> <a href="https://shop63419396.taobao.com/" target="view_window">淘宝店铺</a></li>
          <li> <a href="http://wpa.qq.com/msgrd?v=3&uin=640261692&site=qq&menu=yes" target="view_window">在线咨询</a></li>
          <!--<li><li> <a href="http://123.56.181.105:8888/" target="view_window">博客</a></li>-->
          <li> <a href="help.html" target="view_window">帮助</a></li>
        </ul>
      </div>
      <!--/.nav-collapse --> 
    </div>
  </div>
</div>

<!-- ==== HEADERWRAP ==== -->
<div id="headerwrap" name="home">
  <header style="font-family:微软雅黑" class="clearfix">
    <h1>什么是增投</h1>
    <p><br>增投是一种以定投为基础、将定投的优势发挥到极致的开放式基金投资策略</br></br>支持：XP / WIN7 / WIN8 / WIN10</p>
    <a href="/file/setup.exe" target="view_window" class="smoothScroll btn btn-lg">下载软件</a> </header>
</div>
<!-- /headerwrap --> 

<!-- ==== ABOUT ==== -->
<div id="about" name="about">
  <div style="font-family:微软雅黑" class="container">
    <div class="row white">
      <div class="col-md-6"> <img class="img-responsive" src="assets/img/about/profits.jpeg" align=""> </div>
      <div class="col-md-6">
        <h3>增投跟定投有什么区别</h3>
        <p>定投投资周期长，最短一周一投；增投只要市场走势符合条件每天都可以投。定投的投资金额固定，增投的投资金额不固定，净值越低，投入越大。定投需要数年的坚持才能盈利，增投只需要数月就可以看到回报。</p>
        <h3>投资小贴士</h3>
        <p>用增投法买基金相当于买了一个不固定的股基-货基组合，将股基的风险降到债基的水平，却依然享受股基的收益，比较适合每天能在固定时间14:50-15:00抽出时间进行操作的上班一族。<strong>我们只提供网络服务，不会以任何形式向您集资，您的投资标的为各大基金公司旗下的开放式基金，且全程由您自己操作，我们不会索要您的任何账户信息。</strong></p>
      </div>
    </div>
    <!-- row --> 
  </div>
</div>
<!-- container --> 

<!-- ==== SERVICES ==== -->
<div id="services" name="services">
  <div style="font-family:微软雅黑" class="container">
    <div class="row">
      <h2 class="centered">我们的服务</h2>
      <hr>
      <div class="col-lg-8 col-lg-offset-2">
        <p class="large">网页版跨平台支持Android、iOS、Windows、Mac、Linux等系统</p>
      </div>
      <div class="col-lg-3 callout"> <i class="fa fa-desktop fa-3x"></i>
        <h3>电脑版</h3>
        <p>一个Windows安装程序，可以更方便地打开与计算，使用账号登录</p>
      </div>
      <div class="col-lg-3 callout"> <i class="fa fa-dot-circle-o fa-3x"></i>
        <h3>网页版</h3>
        <p>可以使用手机号免费注册一个账号，但部分功能可能会受到限制</p>
      </div>
      <div class="col-lg-3 callout"> <i class="fa fa-gears fa-3x"></i>
        <h3>我们的博客</h3>
        <p>大家可以在这里积极讨论增投法使用技巧及心得体会</p>
      </div>
      <div class="col-lg-3 callout"> <i class="fa fa-shopping-cart fa-3x"></i>
        <h3>淘宝店铺</h3>
        <p>拍之前请与卖家联系，确认卖家在线再付款</p>
      </div>
    </div>
    <!-- row --> 
  </div>
</div>
<!-- container --> 

<!-- ==== PORTFOLIO ==== -->
<div id="portfolio" name="portfolio">
  <div style="font-family:微软雅黑" class="container">
    <div class="row">
      <h2 class="centered">经典示例</h2>
      <hr>
      <div class="col-lg-8 col-lg-offset-2 centered">
        <p class="large">以下是非常经典的操作示例，在净值下降的第一天开始投入，到反弹后的最高点结束，反弹后无论是否超过之前的最高净值，该方法都能获得不少的盈利。</p>
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
                <h5>鹏华国防(160630)</h5></figcaption>
              <!-- /figcaption --> 
            </figure>
            <!-- /figure --> 
            <p class="centered"><a data-toggle="modal" href="#myModal1" class="btn btn-default">详细操作</a></p>
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
                <h4 class="modal-title">鹏华国防(2015.6.15-2015.7.24)</h4>
              </div>
              <div class="modal-body">
                <p><img class="img-responsive" src="assets/img/portfolio/folio01-preview.png" alt=""></p>
                <p>操作详情：前期买入，越跌越买；后期卖出，越涨越卖。</p>
                <p><b><a href="assets/img/portfolio/folio01-preview.png" target="view_window">查看大图</a></b></p>
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
                <h5>国富价值(450004)</h5></figcaption>
              <!-- /figcaption --> 
            </figure>
            <!-- /figure -->
            <p class="centered"><a data-toggle="modal" href="#myModal2" class="btn btn-default">详细操作</a></p>
           </div>
          <!-- /grid-mask --> 
        </div>
        
        <div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">国富价值(2015.8.18-2015.12.25)</h4>
              </div>
              <div class="modal-body">
                <p><img class="img-responsive" src="assets/img/portfolio/folio02-preview.png" alt=""></p>
                <p>操作详情：前期买入，越跌越买；后期卖出，越涨越卖。</p>
                <p><b><a href="assets/img/portfolio/folio02-preview.png" target="view_window">查看大图</a></b></p>
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
                <h5>富国券商(161027)</h5></figcaption>
              <!-- /figcaption --> 
            </figure>
            <!-- /figure --> 
            <p class="centered"><a data-toggle="modal" href="#myModal3" class="btn btn-default">详细操作</a></p>
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
                <h4 class="modal-title">富国券商(2015.11.12-2015.12.23)</h4>
              </div>
              <div class="modal-body">
                <p><img class="img-responsive" src="assets/img/portfolio/folio03-preview.png" alt=""></p>
                <p>操作详情：前期买入，越跌越买；后期卖出，越涨越卖。</p>
                <p><b><a href="assets/img/portfolio/folio03-preview.png" target="view_window">查看大图</a></b></p>
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
                <h5>富国发展(000471)</h5></figcaption>
              <!-- /figcaption --> 
            </figure>
            <!-- /figure --> 
            <p class="centered"><a data-toggle="modal" href="#myModal4" class="btn btn-default">详细操作</a></p>
          </div>
          <!-- /grid-mask --> 
        </div>
        
        <div class="modal fade" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">富国发展(2015.12.23-2016.2.22)</h4>
              </div>
              <div class="modal-body">
                <p><img class="img-responsive" src="assets/img/portfolio/folio04-preview.png" alt=""></p>
                <p>操作详情：前期买入，越跌越买；后期卖出，越涨越卖。</p>
                <p><b><a href="assets/img/portfolio/folio04-preview.png" target="view_window">查看大图</a></b></p>
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
                <h5>中邮战略(590008)</h5></figcaption>
              <!-- /figcaption --> 
            </figure>
            <!-- /figure --> 
            <p class="centered"><a data-toggle="modal" href="#myModal5" class="btn btn-default">详细操作</a></p>
          </div>
          <!-- /grid-mask --> 
        </div>
        
        <div class="modal fade" id="myModal5" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">中邮战略(2016.2.23-4.14)</h4>
              </div>
              <div class="modal-body">
                <p><img class="img-responsive" src="assets/img/portfolio/folio05-preview.png" alt=""></p>
                <p>操作详情：前期买入，越跌越买；后期卖出，越涨越卖。</p>
                <p><b><a href="assets/img/portfolio/folio05-preview.png" target="view_window">查看大图</a></b></p>
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

        <!-- PORTFOLIO IMAGE 6 -->
        <div class="col-md-4">
          <div class="grid mask">
            <figure> <img class="img-responsive" src="assets/img/portfolio/folio06.png" alt="">
              <figcaption>
                <h5>金鹰资源(210001)</h5></figcaption>
              <!-- /figcaption --> 
            </figure>
            <!-- /figure --> 
            <p class="centered"><a data-toggle="modal" href="#myModal6" class="btn btn-default">详细操作</a></p>
          </div>
          <!-- /grid-mask --> 
        </div>
        <!-- /col --> 
      </div>
      <!-- /row --> 

        <div class="modal fade" id="myModal6" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">金鹰资源(2016.8.18-2016.11.15)</h4>
              </div>
              <div class="modal-body">
                <p><img class="img-responsive" src="assets/img/portfolio/folio06-preview.png" alt=""></p>
                <p>操作详情：前期买入，越跌越买；后期卖出，越涨越卖。</p>
                <p><b><a href="assets/img/portfolio/folio06-preview.png" target="view_window">查看大图</a></b></p>
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
  <div style="font-family:微软雅黑" class="container">
    <div class="row centered">
      <h2 class="centered">我们的开发团队</h2>
      <hr>
      <div class="col-lg-3 centered"> <img class="img img-circle" src="assets/img/team/team01.jpg" height="120px" width="120px" alt="">
        <h4>常惭愧</h4>
        <p>CEO&创建者<br>
		增投算法的发明者，增投软件的作者，网站的管理者以及客服</p>
      </div>
      <div class="col-lg-3 centered"> <img class="img img-circle" src="assets/img/team/team02.jpg" height="120px" width="120px" alt="">
        <h4>Weaver Gan</h4>
        <p>网络工程师<br>
        网站的开发者及维护者</p>
      </div>
      <div class="col-lg-3 centered"> <img class="img img-circle" src="assets/img/team/team03.jpg" height="120px" width="120px" alt="">
        <h4>未招募</h4>
        <p>软件工程师<br>
		负责安卓客户端的开发及长期的维护</p>
      </div>
      <div class="col-lg-3 centered"> <img class="img img-circle" src="assets/img/team/team04.jpg" height="120px" width="120px" alt="">
        <h4>未招募</h4>
        <p>软件工程师<br>
		负责苹果客户端的开发及长期的维护</p>
      </div>
      <div class="col-lg-8 col-lg-offset-2 centered">
        <p class="large">我们的目的是将您从无常的股市中解放出来，用您宝贵的时间和精力做更多有益于社会和人民的事。</p>
      </div>
    </div>
  </div>
  <!-- row --> 
</div>
<!-- container --> 

<!-- ==== CONTACT ==== -->
<div id="contact" name="contact">
  <div style="font-family:微软雅黑" class="container">
    <div class="row">
      <h2 class="centered">联系方式</h2>
      <hr>
      <div class="col-md-4 centered"> <i class="fa fa-map-marker fa-2x"></i>
        <p>北京市朝阳区北苑路32号</p>
      </div>
      <div class="col-md-4"> <i class="fa fa-envelope-o fa-2x"></i>
        <p>einstein_cool@126.com</p>
      </div>
      <div class="col-md-4"> <i class="fa fa-phone fa-2x"></i>
        <p>+86 186 1388 9910</p>
      </div>
    </div>
    <div class="row">
      <div class="col-lg-8 col-lg-offset-2 centered">
        <p>如果您有好的建议，或对我们的服务有意见，请给我们发送电子邮件；或者扫描下方的二维码，关注后反馈；或者<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=41hYDKD "><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="基金交流" title="基金交流"></a>114242968反馈。</p>
        <p align="center"><img class="img-responsive centered" src="assets/img/about/8cm.jpg" alt=""></p>
        <a href="https://shop63419396.taobao.com/" target="view_window" class="smoothScroll btn btn-lg">升级VIP会员</a>
        <!-- form --> 
      </div>
    </div>
    <!-- row --> 
    
  </div>
</div>
<!-- container -->

<div id="footerwrap">
  <div style="font-family:微软雅黑" class="container">
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
