var isStartCalValuation = 0;//是否开启自动获取估值
var startCalValuationStat;
var dateList = [];
var sumIncList = [];
var allProfitPotList = [];
var ac_dateList = [];
var ac_sumIncList = [];
var ac_allProfitPotList = [];

$(function() {
	getUserAuth();
	$('#startDate').datebox().datebox('calendar').calendar({
		validator: function(date){
			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
			return d1 >= date;
		}
	});
	$('#endDate').datebox().datebox('calendar').calendar({
		validator: function(date){
			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
			return d1 >= date;
		}
	});
	getCodes();
});

function columnStyler(value, row, index) {
	if(value > 0) {
		return 'color:red;';
	} else {
		return 'color:green;';
	}
}

function editableColumnStyler(index, row){
	return 'background-color:#E0DBDB;font-weight:bold;';
}

//获取用户基金
function getCodes() {
	var url = '/fund/getCodes.do';
	//获取用户保存的code
	$.ajax({
		type : "GET",
		url : url,
		contentType: "application/json; charset=utf-8",     
        dataType: "json", 
		success : function(data) {
			if(data.success) {
				setUserCodes(data.data);
	        } else {
	        	fail(data);
			}
		}
	});
}

//日期选中
//function onSelectStart(startdate){
//	$('#endDate').datebox().datebox('calendar').calendar({
//		validator: function(date){
//			var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
//			return d1 >= date && startdate <= date;
//		}
//	});
//}

//填充数据
function setUserCodes(data) {
	var json = [];
	for(var i in data) {
		if(data[i].selected === 1) {
			json.push({ "text": data[i].code, "value": data[i].code, "selected":true });
		} else {
			json.push({ "text": data[i].code, "value": data[i].code});
		}
	}
	clearData();
	$('#fundcode').combobox('loadData', json);

}

function clearData() {
	$('#fundcode').combobox("clear");//清除当前值
	$('#codeList').datagrid('loadData', { total: 0, rows: [] });

}

//获取list
function calList() {
	if(!checkInformation()) {
		return;
	}
	//clearCodeList();
	theoreticList();
	actualList();
}
function theoreticList() {
	$('#codeList').datagrid('loadData', { total: 0, rows: [] });
	$('#loading').show()
	var url = '/fund/getCodeList.do';
	var isStopProfit = 0;
	var isAutoSell = 0;
	// var isZS = 0;
	var isFenhongInvest = 0;
	var isIncrease = 0;
	var isSetMinRate = 0;
	if (document.getElementById("isStopProfit").checked){
		isStopProfit = 1;
	}
	if (document.getElementById("isAutoSell").checked){
		isAutoSell = 1;
	}
	// if (document.getElementById("isZS").checked){
	// 	isZS = 1;
	// }
	if (document.getElementById("isFenhongInvest").checked){
		isFenhongInvest = 1;
	}
	if (document.getElementById("isIncrease").checked){
		isIncrease = 1;
	}
	if (document.getElementById('isSetMinRate') && document.getElementById("isSetMinRate").checked){
		isSetMinRate = 1;
	}
	$.ajax({
		type : "GET",
		url : url,
		data: {
			code:$("#fundcode").combobox('getValue'),
			purchaseFee:Number($("#purchaseFee").textbox('getValue')) * 100,
			sellFee:Number($("#sellFee").textbox('getValue')) * 100,
			baseMultiple:$("#baseMultiple").length > 0 ? $("#baseMultiple").textbox('getValue') : 1.2,
			startInvest:Number($("#startInvest").textbox('getValue')) * 100,
			moreMultiple:$("#moreMultiple").length > 0 ? $("#moreMultiple").textbox('getValue') : 0.1,
			// firstInvest:$("#firstInvest").textbox('getValue'),
            totalInvest:$("#totalInvest").textbox('getValue'),
			isStopProfit:isStopProfit,
			stopProfitPot:Number($("#stopProfitPot").textbox('getValue')) * 100,
			isAutoSell:isAutoSell,
			startSell:Number($("#startSell").textbox('getValue')) * 100,
			minSellCount:$("#minSellCount").textbox('getValue'),
			minHoldCount:$("#minHoldCount").textbox('getValue'),
			startDate:$('#startDate').datebox('getValue'),
			endDate:$("#endDate").datebox('getValue'),
			// isZS:isZS,
			isFenhongInvest:isFenhongInvest,
			isIncrease:isIncrease,
            riskRate:$("#riskRate").length > 0 ?$("#riskRate").textbox('getValue') : 40,
			isSetMinRate:isSetMinRate,
			guzhiFrom:$("#guzhiFrom").combobox('getValue')
		},
		contentType: "application/json; charset=utf-8",
        dataType: "json",
		success : function(data) {
			if(data.success) {
				setCodeList(data.data);
	        } else {
	        	fail(data);
			}
		}
	});
}
function actualList() {
	$('#actualCodeList').datagrid('loadData', { total: 0, rows: [] });
	$('#loadingActual').show()

	var url = '/fund/getActualCodeList.do';
	var isStopProfit = 0;
	var isAutoSell = 0;
	// var isZS = 0;
	var isFenhongInvest = 0;
	var isIncrease = 0;
	var isSetMinRate = 0;
	if (document.getElementById("isStopProfit").checked){
		isStopProfit = 1;
	}
	if (document.getElementById("isAutoSell").checked){
		isAutoSell = 1;
	}
	// if (document.getElementById("isZS").checked){
	// 	isZS = 1;
	// }
	if (document.getElementById("isFenhongInvest").checked){
		isFenhongInvest = 1;
	}
	if (document.getElementById("isIncrease").checked){
		isIncrease = 1;
	}
	if (document.getElementById('isSetMinRate') && document.getElementById("isSetMinRate").checked){
		isSetMinRate = 1;
	}
	$.ajax({
		type : "GET",
		url : url,
		data: {
			code:$("#fundcode").combobox('getValue'),
			purchaseFee:Number($("#purchaseFee").textbox('getValue')) * 100,
			sellFee:Number($("#sellFee").textbox('getValue')) * 100,
			baseMultiple:$("#baseMultiple").length > 0 ? $("#baseMultiple").textbox('getValue') : 1.2,
			startInvest:Number($("#startInvest").textbox('getValue')) * 100,
			moreMultiple:$("#moreMultiple").length > 0 ? $("#moreMultiple").textbox('getValue') : 0.1,
			// firstInvest:$("#firstInvest").textbox('getValue'),
			totalInvest:$("#totalInvest").textbox('getValue'),
			isStopProfit:isStopProfit,
			stopProfitPot:Number($("#stopProfitPot").textbox('getValue')) * 100,
			isAutoSell:isAutoSell,
			startSell:Number($("#startSell").textbox('getValue')) * 100,
			minSellCount:$("#minSellCount").textbox('getValue'),
			minHoldCount:$("#minHoldCount").textbox('getValue'),
			startDate:$('#startDate').datebox('getValue'),
			endDate:$("#endDate").datebox('getValue'),
			//isZS:isZS,
			isFenhongInvest:isFenhongInvest,
			isIncrease:isIncrease,
			riskRate:$("#riskRate").length > 0 ?$("#riskRate").textbox('getValue') : 40,
			isSetMinRate:isSetMinRate,
			guzhiFrom:$("#guzhiFrom").combobox('getValue')
		},
		contentType: "application/json; charset=utf-8",
        dataType: "json",
		success : function(data) {
			if(data.success) {
				setActualCodeList(data.data);
	        } else {
	        	fail(data);
			}
		}
	});
}

//保存参数
function saveData() {
	if(!checkInformation()) {
		return;
	}
	var url = '/fund/saveData.do';
	var isStopProfit = 0;
	var isAutoSell = 0;
	// var isZS = 0;
	var isFenhongInvest = 0;
	var isIncrease = 0;
	var isSetMinRate = 0;
	if (document.getElementById("isStopProfit").checked){
		isStopProfit = 1;
	}
	if (document.getElementById("isAutoSell").checked){
		isAutoSell = 1;
	}
	// if (document.getElementById("isZS").checked){
	// 	isZS = 1;
	// }
	if (document.getElementById("isFenhongInvest").checked){
		isFenhongInvest = 1;
	}
	if (document.getElementById("isIncrease").checked){
		isIncrease = 1;
	}
	if (userAuth > 1 && document.getElementById("isSetMinRate").checked){
		isSetMinRate = 1;
	}
	$.ajax({
		type : "GET",
		url : url,
		data: {
			code:$("#fundcode").combobox('getValue'),
			purchaseFee:Number($("#purchaseFee").textbox('getValue')) * 100,
			sellFee:Number($("#sellFee").textbox('getValue')) * 100,
			baseMultiple:$("#baseMultiple").length > 0 ? $("#baseMultiple").textbox('getValue') : 1.2,
			startInvest:Number($("#startInvest").textbox('getValue')) * 100,
			moreMultiple:$("#moreMultiple").length > 0 ? $("#moreMultiple").textbox('getValue') : 0.1,
			// firstInvest:$("#firstInvest").textbox('getValue'),
			totalInvest:$("#totalInvest").textbox('getValue'),
			isStopProfit:isStopProfit,
			stopProfitPot:Number($("#stopProfitPot").textbox('getValue')) * 100,
			isAutoSell:isAutoSell,
			startSell:Number($("#startSell").textbox('getValue')) * 100,
			minSellCount:$("#minSellCount").textbox('getValue'),
			minHoldCount:$("#minHoldCount").textbox('getValue'),
			startDate:$('#startDate').datebox('getValue'),
			endDate:$("#endDate").datebox('getValue'),
			// isZS:isZS,
			isFenhongInvest:isFenhongInvest,
			isIncrease:isIncrease,
            riskRate:$("#riskRate").length > 0 ? $("#riskRate").textbox('getValue') : 40,
			isSetMinRate:isSetMinRate
		},
		contentType: "application/json; charset=utf-8",
        dataType: "json",
		success : function(data) {
			if(data.success) {
				alert("保存成功！");
	        } else {
	        	fail(data);
			}
		}
	});
}

//检查信息是否全
function checkInformation() {
	if(!checkCode($('#fundcode').combobox('getValue'))) {
		alert("基金代码不能为空！");
		return false;
	}
	if(isNull($('#startDate').datebox('getValue'))) {
		alert("起始日期不能为空！");
		return false;
	}
	if(isNull($('#endDate').datebox('getValue'))) {
		alert("结束日期不能为空！");
		return false;
	}
	if(!isFloat($('#purchaseFee').textbox('getValue'))) {
		alert("请输入正确的申购费率！");
		return false;
	}
	if(!isFloat($('#sellFee').textbox('getValue'))) {
		alert("请输入正确的赎回费率！");
		return false;
	}
	if(!isFloat($('#minSellCount').textbox('getValue'))) {
		alert("请输入正确的至少赎回份数！");
		return false;
	}
	if(!isFloat($('#minHoldCount').textbox('getValue'))) {
		alert("请输入正确的至少保留份数！");
		return false;
	}
	if(!isInteger($('#totalInvest').textbox('getValue'))) {
		alert("请输入正确的资金总量！");
		return false;
	}
	if(!isFloat($('#startSell').textbox('getValue'))) {
		alert("请输入正确的起卖点位！");
		return false;
	}
	if(!isFloat($('#stopProfitPot').textbox('getValue'))) {
		alert("请输入正确的止盈点位！");
		return false;
	}
	if(!isFloat($('#startInvest').textbox('getValue'))) {
		alert("请输入正确的起投跌幅！");
		return false;
	}
	if(userAuth > 1) {
		if(!isInteger($('#riskRate').textbox('getValue'))) {
			alert("请输入正确的风控仓位！");
			return false;
		}
		if(!isFloat($('#baseMultiple').textbox('getValue'))) {
			alert("请输入正确的倍数！");
			return false;
		}
		if(!isFloat($('#moreMultiple').textbox('getValue'))) {
			alert("请输入正确的加倍！");
			return false;
		}
	}

	return true;
}

//清空list
function clearCodeList() {
	$('#codeList').datagrid('loadData', { total: 0, rows: [] });
	$('#actualCodeList').datagrid('loadData', { total: 0, rows: [] });

}

//填充list
function setCodeList(data) {
	dateList = [];
	sumIncList = [];
	allProfitPotList = [];
	var json = [];
	for(var i in data) {
		json.push(data[i]);
		dateList.push(data[i].date);
		sumIncList.push(data[i].sumInc);
		allProfitPotList.push(data[i].allProfitPot);
	}
	$('#codeList').datagrid('loadData', json);
	//alert(data.length);
	var minHeight = 640;
	$('#main_layout').layout("resize",{
		height:Math.max((data.length * 25 + 240), minHeight) + "px"
	});
	$('#loading').hide()
}

function setActualCodeList(data) {
	ac_dateList = [];
	ac_sumIncList = [];
	ac_allProfitPotList = [];
	var json = [];
	for(var i in data) {
		json.push(data[i]);
		ac_dateList.push(data[i].date);
		ac_sumIncList.push(data[i].sumInc);
		ac_allProfitPotList.push(data[i].allProfitPot);
	}
	$('#actualCodeList').datagrid('loadData', json);
	$('#loadingActual').hide()
}

function fail(data){
	dealError(data);
}

//获取实时估值
function startValuation() {
	if(isStartCalValuation == 0) {
		calValuation();
		startCalValuationStat = window.setInterval(calValuation, 30000); 
		$('#calValuation span:last').html('停止');
		isStartCalValuation = 1;
	} else {
		startCalValuationStat = window.clearInterval(startCalValuationStat);
		$('#calValuation span:last').html('实时估值');
		isStartCalValuation = 0;
	}
	
}

function calValuation() { 
	var oldValuation = $('#guzhi').textbox('getValue');
	var url = '/fund/mem/calValuation.do';
	$.ajax({
		type : "GET",
		url : url,
		data: {
			code:$("#fundcode").combobox('getValue'),
			guzhiFrom:$("#guzhiFrom").combobox('getValue')
		},
		contentType: "application/json; charset=utf-8",     
        dataType: "json", 
		success : function(data) {
			if(data.success) {
				setValuation(data.data, oldValuation);
	        } else {
	        	fail(data);
			}
		}
	});
}

//填充实时估值
function setValuation(data, oldValuation) {
	$('#guzhi').textbox('setValue', data[0]);
	$('#guzhangfu').textbox('setValue', data[1]);
	if(oldValuation != data[0]) {
		var d = new Date($("#endDate").datebox('getValue').replace(/-/g, "/"));
		if(d.setHours(0,0,0,0) == new Date().setHours(0,0,0,0)) {//如果是今天
			calList();
		}
	}
}


//止盈提醒事件
function checkIsStopProfit(obj) {
	if (obj.checked === true){
		$("#stopProfitPot").textbox('enable'); 
	} else {
		$("#stopProfitPot").textbox('disable'); 
	}

}
//折算提醒事件
function checkIsZS(obj) {
}
//红利再投事件
function checkFenhongInvest(obj) {
}
//加仓优先事件
function checkIsIncrease(obj) {
	if (obj.checked === true){
		$("#riskRate").textbox('enable');
	} else {
		$("#riskRate").textbox('disable');
	}
}
//越长越卖事件
function checkIsAutoSell(obj) {
	if (obj.checked === true){
		$("#startSell").textbox('enable'); 
		$("#minSellCount").textbox('enable'); 
		$("#minHoldCount").textbox('enable'); 
	} else {
		$("#startSell").textbox('disable'); 
		$("#minSellCount").textbox('disable'); 
		$("#minHoldCount").textbox('disable'); 
	}
}
//保证追加事件
function checkIsSetMinRate(obj) {
}

function checkSmsSubscription(obj) {
	if(userAuth > 1) {//会员才能使用
		if (obj.checked === true) {//勾选
			if (window.confirm('勾选后，每天14：50分会收到是否加仓及申购金额的短信提醒(请确保该基金数据已经保存，否则无法发送)，确认订阅？')) {
				var url = '/fund/mem/smsSubscription.do';
				$.ajax({
					type: "GET",
					url: url,
					data: {
						code: $("#fundcode").combobox('getValue')
					},
					contentType: "application/json; charset=utf-8",
					dataType: "json",
					success: function (data) {
						if (data.success) {
							alert("订阅成功");
						} else {
							fail(data);
							obj.checked = false;
						}
					}
				});

				return true;
			} else {
				obj.checked = false;
				return false;
			}
		} else {
			if (window.confirm('取消订阅将不再受到此类短信，确认取消吗？')) {
				var url = '/fund/mem/cancelSmsSub.do';
				$.ajax({
					type: "GET",
					url: url,
					data: {
						code: $("#fundcode").combobox('getValue')
					},
					contentType: "application/json; charset=utf-8",
					dataType: "json",
					success: function (data) {
						if (data.success) {
							alert("取消成功");
						} else {
							obj.checked = true;
							fail(data);
						}
					}
				});
				return true;
			} else {
				obj.checked = true;
				return false;
			}
		}
	} else {
		alert("抱歉，该功能暂时只对VIP用户开放，请升级之后使用！")
	}
}

//改变基金代码
function changeCode() {
	var value = $("#fundcode").combobox('getValue');
	if(value.length == 6) {
		getOneCode(value);
		// if(userAuth > 1) {
		// 	getSmsSubscription(value);
		// }
	}
}

//获取代码属性
function getOneCode(value) {
	var url = '/fund/getOneCode.do';
	//获取用户保存的code
	$.ajax({
		type: "GET",
		url: url,
		data: {
			code: value
		},
		contentType: "application/json; charset=utf-8",     
        dataType: "json", 
		success: function(data) {
			if(data.success) {
				setOneCode(data.data);
	        } else {
	        	fail(data);
			}
		}
	});
}

function setOneCode(data) {
	if(data != null) {
		$("#purchaseFee").textbox('setValue',data.purchaseFee/100);
		$("#sellFee").textbox('setValue',data.sellFee/100);
		$("#baseMultiple").textbox('setValue',data.baseMultiple);
		$("#startInvest").textbox('setValue',data.startInvest/100);
		$("#moreMultiple").textbox('setValue',data.moreMultiple);
		// $("#firstInvest").textbox('setValue',data.firstInvest);
        $("#totalInvest").textbox('setValue',data.totalInvest);
		if(data.isStopProfit === 1) {
			document.getElementById("isStopProfit").checked = true;
			$("#stopProfitPot").combobox('enable'); 
		} else {
			document.getElementById("isStopProfit").checked = false;
			$("#stopProfitPot").combobox('disable');
		}
		$("#stopProfitPot").textbox('setValue',data.stopProfitPot/100);
		if(data.isAutoSell === 1) {
			document.getElementById("isAutoSell").checked = true;
			$("#startSell").combobox('enable');
			$("#minSellCount").combobox('enable'); 
			$("#minHoldCount").combobox('enable'); 
		} else {
			document.getElementById("isAutoSell").checked = false;
			$("#startSell").combobox('disable');
			$("#minSellCount").combobox('disable'); 
			$("#minHoldCount").combobox('disable'); 
		}
		$("#startSell").textbox('setValue',data.startSell/100);
		$("#minSellCount").textbox('setValue',data.minSellCount);
		$("#minHoldCount").textbox('setValue',data.minHoldCount);
		if(data.startDate) {
			$("#startDate").datebox('setValue',data.startDate);
		} else {
			$("#startDate").datebox('setValue',now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate());
		}
		if(userAuth == 1) {
			$("#endDate").datebox('setValue',yesterday.getFullYear() + "-" + (yesterday.getMonth() + 1) + "-" + yesterday.getDate());
		} else {
			$("#endDate").datebox('setValue',now.getFullYear() + "-" + (now.getMonth() + 1) + "-" + now.getDate());
		}
		// if(data.isZS === 1) {
		// 	document.getElementById("isZS").checked = true;
		// } else {
		// 	document.getElementById("isZS").checked = false;
		// }
		if(data.isFenhongInvest === 1) {
			document.getElementById("isFenhongInvest").checked = true;
		} else {
			document.getElementById("isFenhongInvest").checked = false; 
		}
        $("#riskRate").textbox('setValue',data.riskRate);
		if(data.isIncrease === 1) {
			document.getElementById("isIncrease").checked = true;
            $("#riskRate").combobox('enable');
        } else {
			document.getElementById("isIncrease").checked = false;
            $("#riskRate").combobox('disable');
		}
		if(userAuth > 1) {//会员才显示
			if(data.isSetMinRate === 1) {
				document.getElementById("isSetMinRate").checked = true;
			} else {
				document.getElementById("isSetMinRate").checked = false; 
			}
		}
		
		//第一次输入不调用
		if($('#startDate').datebox('getValue') == "" && $('#endDate').datebox('getValue') == "") {
			return;
		}
		calList();
	} else {
		resetParameters();
	}
	
}


function deleteData() {
	if(!confirm("删除基金会连同删除该基金下保存的参数数据，确定要删除？")) {
		return;
	 }
	var url = '/fund/deleteData.do';
	$.ajax({
		type : "GET",
		url : url,
		data: {
			code:$("#fundcode").combobox('getValue'),
		},
		contentType: "application/json; charset=utf-8",     
        dataType: "json", 
		success : function(data) {
			if(data.success) {
				alert("删除成功！");
				getCodes();
	        } else {
	        	fail(data);
			}
		}
	});
}

function exitLogin() {
	var url = '/user/exitLogin.do';
	$.ajax({
		type : "POST",
		url : url,
		contentType: "application/json; charset=utf-8",     
        dataType: "json" 
	});
	window.location.href= "/index.html";
}

function showChart() {
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('chart'));

    // 指定图表的配置项和数据
    var option = {
    	    title: {
    	        text: '涨幅折线图',
    	    },
    	    tooltip: {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	        data:['净收益率', '累计涨幅']
    	    },
    	    toolbox: {
    	        show: true,
    	        feature: {
    	            dataZoom: {},
    	            dataView: {readOnly: false},
    	            magicType: {type: ['line', 'bar']},
    	            restore: {},
    	            saveAsImage: {}
    	        }
    	    },
    	    xAxis:  {
    	        type: 'category',
    	        boundaryGap: false,
    	        data: dateList
    	    },
    	    yAxis: {
    	        type: 'value',
    	        axisLabel: {
    	            formatter: '{value} %'
    	        }
    	    },
    	    series: [
    	        {
    	            name:'净收益率',
    	            type:'line',
    	            data: allProfitPotList,
    	            markPoint: {
    	                data: [
    	                    {type: 'max', name: '最大值'},
    	                    {type: 'min', name: '最小值'}
    	                ]
    	            },
    	        },
    	        {
    	            name:'累计涨幅',
    	            type:'line',
    	            data: sumIncList,
    	            markPoint: {
    	                data: [
    	                    {type: 'max', name: '最大值'},
    	                    {type: 'min', name: '最小值'}
    	                ]
    	            },
    	        }
    	    ]
    	};

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    
    $("#chart").dialog('open'); 
}

function showActualChart() {
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('actualChart'));

    // 指定图表的配置项和数据
    var option = {
    	    title: {
    	        text: '涨幅折线图',
    	    },
    	    tooltip: {
    	        trigger: 'axis'
    	    },
    	    legend: {
    	        data:['净收益率', '累计涨幅']
    	    },
    	    toolbox: {
    	        show: true,
    	        feature: {
    	            dataZoom: {},
    	            dataView: {readOnly: false},
    	            magicType: {type: ['line', 'bar']},
    	            restore: {},
    	            saveAsImage: {}
    	        }
    	    },
    	    xAxis:  {
    	        type: 'category',
    	        boundaryGap: false,
    	        data: ac_dateList
    	    },
    	    yAxis: {
    	        type: 'value',
    	        axisLabel: {
    	            formatter: '{value} %'
    	        }
    	    },
    	    series: [
    	        {
    	            name:'净收益率',
    	            type:'line',
    	            data: ac_allProfitPotList,
    	            markPoint: {
    	                data: [
    	                    {type: 'max', name: '最大值'},
    	                    {type: 'min', name: '最小值'}
    	                ]
    	            },
    	        },
    	        {
    	            name:'累计涨幅',
    	            type:'line',
    	            data: ac_sumIncList,
    	            markPoint: {
    	                data: [
    	                    {type: 'max', name: '最大值'},
    	                    {type: 'min', name: '最小值'}
    	                ]
    	            },
    	        }
    	    ]
    	};

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    
    $("#actualChart").dialog('open'); 
}

function getUserAuth(){
	var url = '/user/getAuth.do';
	//获取用户保存的code
	$.ajax({
		type : "GET",
		url : url,
		contentType: "application/json; charset=utf-8",     
        dataType: "json", 
		success : function(data) {
			if(data.success) {
				userAuth = data.data;
	        } else {
	        	fail(data);
			}
		}
	});
}
//获取短信订阅
function getSmsSubscription(value) {
	var url = '/fund/mem/getSmsSubscription.do';
	//获取用户保存的code
	$.ajax({
		type : "GET",
		url : url,
		data: {
			code: value
		},
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success : function(data) {
			if(data.success) {
				setSmsSubscription(data.data);
			} else {
				fail(data);
			}
		}
	});
}
var setSmsSubscription = function (data) {
	if(data == 1) {
		document.getElementById("smsSubscription").checked = true;
	} else {
		document.getElementById("smsSubscription").checked = false;
	}
};
