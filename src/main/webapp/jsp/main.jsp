<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="org.fund.user.entity.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
    User user = (User)session.getAttribute("currentUser");
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="../js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="../js/easyui/demo/demo.css">
<link rel="stylesheet" type="text/css" href="../css/main.css">
<script type="text/javascript" src="../js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../js/main.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/echarts.min.js"></script>
<title>风险大师</title>
</head>
<body>
	<div style="width:100%" align="right"><span>当前用户：<%=user.getUsername().substring(0, 3) %>****<%=user.getUsername().substring(7) %>
	<%
	    if(user.getAuth()==3) {
	%>
	【SVIP】
	<%
	    } else if(user.getAuth()==2) {
		    String expiredDate = user.getExpiredDate().toString();
		    String expiredDateStr = expiredDate.substring(0, 4) + "年" +  expiredDate.substring(4, 6) + "月" + expiredDate.substring(6) + "日";
	%>
	【VIP】　到期时间：
	<%=expiredDateStr%>
	<%
	    } else {
	%>
	【普通用户】
	</span>
	&nbsp;
	<span><a href="https://shop63419396.taobao.com/" target="_blank">升级会员</a></span>
	<%
	    }
	%>
	&nbsp;
	<a href="javascript:void(0)" onclick="exitLogin()">退出</a>
	</div>
	<div><span style="font-size:28px;font-weight:bold;padding-left:30px;">风险大师</span></div>
	<div style="margin: 20px 0;"></div>
	<div id="main_layout" class="easyui-layout" style=" height: 100%;">
		<div data-options="region:'north'" style="height: 100px">
			<div class="north-left-content">
				<span>基金代码：</span>
				<select class="easyui-combobox" id="fundcode" name="fundcode" style="width:80px;height:30px;"  data-options="onChange:changeCode">
					<!-- <option value="590008">590008</option>
					<option value="000471" selected>000471</option> -->
				</select>
				<span>　起始时间：</span>
				<input id="startDate" class="easyui-datebox" style="width:100px;height:30px;">
				<span>　结束时间：</span>
				<input id="endDate" class="easyui-datebox" style="width:100px;height:30px;">
				<span>　</span>
				<a id="calList" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px" onclick="calList()">计算</a>
				<a id="deleteData" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px" onclick="deleteData()">删除</a>
			</div>
			<div class="north-left-content">
				<span>基金公司：</span>
				<select id="guzhiFrom" class="easyui-combobox" style="width:80px;height:30px">
					<option value="0" selected="selected">天天基金</option>
				    <option value="1">好买基金</option>
				    <option value="2">爱基金</option>
				</select>
				<span>　　　估值：</span>
				<input id="guzhi" class="easyui-textbox" style="width:100px;height:30px">
				<span>　　　涨幅：</span>
				<input id="guzhangfu" class="easyui-textbox" style="width:80px;height:30px">
				<span>%　</span>
				<%
				    if(user.getAuth() != 1) {
				%> 
				<a id="calValuation" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px" onclick="startValuation()">实时估值</a>
				<%
				    } else {
				%>
				<a id="calValuation" href="javascript:void(0)" class="easyui-linkbutton" disabled="true" style="width:80px" onclick="noPermission()" title="该功能开通会员才能使用">实时估值</a>
				<%
				    }
				%>
				<a id="saveData" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px" onclick="saveData()">保存数据</a>
			</div>
		</div>
		<div data-options="region:'west',split:true" title="参数" style="width:220px" >
			<div align="center">
				<table style="width:90%;margin-top:10px;"  cellpadding="1px">
					<tr>
						<td align="right">止盈提醒</td>
						<td><input id="isStopProfit" type="checkbox" onclick="checkIsStopProfit(this)"></td>
						<td align="right">红利再投</td>
						<td><input id="isFenhongInvest" type="checkbox" onclick="checkFenhongInvest(this)"></td>
						<%--<td align="right">折算提醒</td>
						<td><input id="isZS" type="checkbox" onclick="checkIsZS(this)"></input></td>--%>
					</tr>
					<tr>
						<td align="right">加仓优先</td>
						<td><input id="isIncrease" type="checkbox" onclick="checkIsIncrease(this)"></td>
						<td align="right">越涨越卖</td>
						<td><input id="isAutoSell" type="checkbox" onclick="checkIsAutoSell(this)"></td>
					</tr>
					<tr>
						<%
						    if(user.getAuth() != 1) {
						%> 
						<td align="right">修正追加</td>
						<td><input id="isSetMinRate" type="checkbox" onclick="checkIsSetMinRate(this)"></td>
						<%--<td align="right">短信订阅</td>
						<td><input id="smsSubscription" type="checkbox" onclick="checkSmsSubscription(this)"></td>--%>
						<%
						    }
						%>
					</tr>
				</table>
			</div>
			<div align="center">
				<table style="width:90%;margin-top:50px;" cellpadding="1">
					<tr>
						<td align="center">申购费率：</td>
						<td align="left"><input id="purchaseFee" class="easyui-textbox" style="width:50px;height:30px" value="0.15"><label>%</label></td>
					</tr>
					<tr>
						<td align="center">赎回费率：</td>
						<td align="left"><input id="sellFee" class="easyui-textbox" style="width:50px;height:30px" value="0.5"><label>%</label></td>
					</tr>
					<tr>
						<td align="center">至少赎回：</td>
						<td><input id="minSellCount" class="easyui-textbox" style="width:50px;height:30px" disabled="disabled" value="10">
							<label>份</label>
						</td>
					</tr>
					<tr>
						<td align="center">至少保留：</td>
						<td><input id="minHoldCount" class="easyui-textbox" style="width:50px;height:30px" disabled="disabled" value="100">
							<label>份</label>
						</td>
					</tr>
					<tr>
						<td align="center">资金总量：</td>
						<td><input id="totalInvest" class="easyui-textbox" style="width:50px;height:30px" value="50000">
							<label>元</label>
						</td>
					</tr>
					<tr>
						<td align="center">起卖点位：</td>
						<td><input id="startSell" class="easyui-textbox" style="width:50px;height:30px" disabled="disabled" value="5">
							<label>%</label>
						</td>
					</tr>
					<tr>
						<td align="center">止盈点位：</td>
						<td><input id="stopProfitPot" class="easyui-textbox" style="width:50px;height:30px" disabled="disabled" value="10">
							<label>%</label>
						</td>
					</tr>
					<tr>
						<td align="center">起投跌幅：</td>
						<td><input id="startInvest" class="easyui-numberspinner"  value="-0.5" data-options="precision:2,min:-9.00,max:9.00,required:true,increment:0.10" style="width:60px;height:30px">
							<label>%</label>
						</td>
					</tr>
					<%
					    if(user.getAuth() != 1) {
					%>
					<tr>
						<td align="center">风控仓位：</td>
						<td align="left"><input id="riskRate" class="easyui-textbox" style="width:60px;height:30px" disabled="disabled" value="50">
							<label>%</label>
						</td>
					</tr>
					<tr>
						<td align="center">倍　　数：</td>
						<td align="left"><input id="baseMultiple" class="easyui-numberspinner"  value="1.2" data-options="precision:2,min:1.0,max:2,required:true,increment:0.05" style="width:60px;height:30px"></td>
					</tr>
					<tr>
						<td align="center">加　　倍：</td>
						<td><input id="moreMultiple" class="easyui-numberspinner"  value="0.1" data-options="precision:2,min:0,max:0.30,required:true,increment:0.05" style="width:60px;height:30px">
						</td>
					</tr>
					<%
					    }
					%>
				</table>		
			</div>
		</div>
		<div data-options="region:'center'" style="">
			<div class="easyui-tabs" data-options="fit:true,border:false,plain:true">
				<div title="理论计算" style="padding: 10px">
					<div id="chart" class="easyui-dialog" title="涨幅折线图" style="width: 600px;height:400px;" data-options="iconCls:'pag-list',modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"></div>
					<a id="showChart" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px" onclick="showChart()">折线图</a>
					<div style="height:10px"></div>
					<table id="codeList" class="easyui-datagrid" url="" iconCls="icon-save">
						<thead>
							<tr>
								<!-- <th field="no" width="3%" align="center">ID</th> -->
								<th field="date" align="center">日期</th>
								<th field="value" align="center">净值</th>
								<th field="increase" align="center" styler="columnStyler">涨幅(%)</th>
								<th field="sumInc" align="center">累涨(%)</th>
								<th field="purchasePrice" align="center">申购金额</th>
								<%
								    if(user.getAuth() != 1) {
								%> 
								<th field="purchaseCount" align="center">确认份额</th>
								<%
								    }
								%>
								<th field="sellCount" align="center">赎回份额</th>
								<%
								    if(user.getAuth() != 1) {
								%> 
								<th field="sellPrice" align="center">确认金额</th>
								<%
								    }
								%>
								<th field="holdCount" align="center">持有份额</th>
								<th field="holdPrice" align="center">资产市值</th>
								<th field="allInvest" align="center">总投入</th>
								<th field="allProfitPrice" align="center">净盈亏</th>
								<th field="allProfitPot" align="center" styler="columnStyler">收益(%)</th>
								<%
								    if(user.getAuth() != 1) {
								%> 
								<th field="holdValue" align="center">持仓成本</th>
								<th field="fundStatus" align="center">申购状态</th>
								<th field="fundSellStatus" align="center">赎回状态</th>
								<th field="fenhong" align="center">分红配送</th>
								<%
								    }
								%>
								</tr>
							</thead>
						</table>
				</div>
				<div title="实际操作" style="padding: 10px">
					<div id="actualChart" class="easyui-dialog" title="涨幅折线图" style="width: 600px;height:400px;" data-options="iconCls:'pag-list',modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true"></div>
					<a id="showActualChart" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px" onclick="showActualChart()">折线图</a>
					<div style="height:10px"></div>
					<table id="actualCodeList" class="easyui-datagrid" url="" iconCls="icon-save">
						<thead>
							<tr>
								<!-- <th field="no" width="3%" align="center">ID</th> -->
								<th field="ac_date" align="center">日期</th>
								<th field="ac_value" align="center">净值</th>
								<th field="ac_increase" align="center" styler="columnStyler">涨幅(%)</th>
								<th field="ac_sumInc" align="center">累涨(%)</th>
								<th field="ac_purchasePrice" align="center">申购金额</th>
								<%
								    if(user.getAuth() != 1) {
								%> 
								<th field="ac_purchaseCount" align="center">确认份额</th>
								<%
								    }
								%>
								<th field="ac_sellCount" align="center">赎回份额</th>
								<%
								    if(user.getAuth() != 1) {
								%> 
								<th field="ac_sellPrice" align="center">确认金额</th>
								<%
								    }
								%>
								<th field="ac_holdCount" align="center">持有份额</th>
								<th field="ac_holdPrice" align="center">资产市值</th>
								<th field="ac_allInvest" align="center">总投入</th>
								<th field="ac_allProfitPrice" align="center">净盈亏</th>
								<th field="ac_allProfitPot" align="center" styler="columnStyler">收益(%)</th>
								<%
								    if(user.getAuth() != 1) {
								%> 
								<th field="ac_holdValue" align="center">持仓成本</th>
								<th field="ac_fundStatus" align="center">申购状态</th>
								<th field="ac_fundSellStatus" align="center">赎回状态</th>
								<th field="ac_fenhong" align="center">分红配送</th>
								<%
								    }
								%>
								</tr>
							</thead>
						</table>
				</div>
			</div>
		</div>
		<!-- <div data-options="region:'east',split:true" title="历史操作" style="width:200px;">
			<table id="operateList" class="easyui-datagrid" style="width:98%" url="" iconCls="icon-save">
				<thead>
					<tr>
						<th field="date" width="50%" align="center">日期</th>
						<th field="op" width="20%" align="center">操作</th>
						<th field="number" width="30%" align="center">金额（元）/份额（份）</th>
					</tr>
				</thead>
			</table>
		</div> -->
	</div>
</body>
</html>
