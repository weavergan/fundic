$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});

var editIndex = undefined;
function endEditing() {//该方法用于关闭上一个焦点的editing状态
    if (editIndex == undefined) {
        return true
    }
    if ($('#actualCodeList').datagrid('validateRow', editIndex)) {
        $('#actualCodeList').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
//点击单元格事件：
function onClickCell(index, field, value) {
    if (endEditing()) {
        if(field == "purchasePrice" || field == "sellCount"){
            $(this).datagrid('beginEdit', index);
            var ed = $(this).datagrid('getEditor', {index:index,field:field});
            $(ed.target).focus();
        }
        editIndex = index;
    }
    $('#actualCodeList').datagrid('onClickRow')
}
//单元格失去焦点执行的方法
function onAfterEdit(index, row, changes) {
    var updated = $('#actualCodeList').datagrid('getChanges', 'updated');
    if (updated.length < 1) {
        editRow = undefined;
        $('#actualCodeList').datagrid('unselectAll');
        return;
    } else {
        // 传值
        addOrUpdateOperation(index, row, changes);
    }


}

//用户添加操作
function addOrUpdateOperation(index, row, changes) {
    var purchasePrice = row.purchasePrice;// 申购金额
    var sellCount = row.sellCount;// 赎回份额
    var code = $("#fundcode").combobox('getValue');
    var date = row.date;
    if(!isFloat(purchasePrice)) {
        alert("请输入正确的申购金额！");
        return ;
    }
    var purchasePriceCent = Math.round(purchasePrice * 100);
    if(!isFloat(sellCount)) {
        alert("请输入正确的赎回份额！");
        return ;
    }

    var url = '/fund/mem/setOperation.do';
    //获取用户保存的code
    $.ajax({
        type : "GET",
        url : url,
        data: {
            code: code,
            date: date,
            purchasePrice: purchasePriceCent,
            sellCount: sellCount

        },
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success : function(data) {
            if(data.success) {
                //alert("保存成功，请点击刷新列表");
            } else {
                fail(data);
            }
        }
    });
}