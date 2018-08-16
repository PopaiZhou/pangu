// strPrintName 打印任务名
// printDatagrid 要打印的datagrid
function CreateFormPage(strPrintName, printDatagrid, path) {
    var beginDate= $("#searchBeginTime").val();
    var endDate= $("#searchEndTime").val();
    var getMonth= $("#searchMonth").val();
    var listTitle = $("#tablePanel").prev().text();
    listTitle = listTitle.replace("列表","");
    var companyName = "";
    //加载公司信息
    $.ajax({
        type:"get",
        url: path + "/systemConfig/findBy.action",
        dataType: "json",
        async: false,
        success: function (res) {
            if(res && res.rows) {
                var array = res.rows;
                for(var i=0; i<array.length; i++){
                    var name = array[i].name;
                    if(name === "company_name") {
                        companyName = array[i].value;
                    }
                }
            }
        },
        //此处添加错误处理
        error:function() {
            $.messager.alert('查询失败','查询系统配置信息异常，请稍后再试！','error');
            return;
        }
    });

    var tableString = '<div class="div-title">' + companyName + "-" + listTitle + '\n</div>';
        if(beginDate && endDate) {
            tableString+='\n<div class="div-time">日期：' + beginDate + ' 至 ' + endDate + ' \n</div>';
        }
        if(getMonth) {
            tableString += '\n<div class="div-time">月份：' + getMonth + ' \n</div>';
        }
        tableString+='\n<table cellspacing="0" class="pb">';
    var frozenColumns = printDatagrid.datagrid("options").frozenColumns;  // 得到frozenColumns对象
    var columns = printDatagrid.datagrid("options").columns;    // 得到columns对象
    var nameList = '';

    // 载入title
    if (typeof columns != 'undefined' && columns != '') {
        $(columns).each(function (index) {
            tableString += '\n<tr>';
            if (typeof frozenColumns != 'undefined' && typeof frozenColumns[index] != 'undefined') {
                for (var i = 0; i < frozenColumns[index].length; ++i) {
                    if (!frozenColumns[index][i].hidden) {
                        tableString += '\n<th width="' + frozenColumns[index][i].width + '"';
                        if (typeof frozenColumns[index][i].rowspan != 'undefined' && frozenColumns[index][i].rowspan > 1) {
                            tableString += ' rowspan="' + frozenColumns[index][i].rowspan + '"';
                        }
                        if (typeof frozenColumns[index][i].colspan != 'undefined' && frozenColumns[index][i].colspan > 1) {
                            tableString += ' colspan="' + frozenColumns[index][i].colspan + '"';
                        }
                        if (typeof frozenColumns[index][i].field != 'undefined' && frozenColumns[index][i].field != '') {
                            nameList += ',{"f":"' + frozenColumns[index][i].field + '", "a":"' + frozenColumns[index][i].align + '"}';
                        }
                        tableString += '>' + frozenColumns[0][i].title + '</th>';
                    }
                }
            }
            for (var i = 0; i < columns[index].length; ++i) {
                if (!columns[index][i].hidden) {
                    tableString += '\n<th width="' + columns[index][i].width + '"';
                    if (typeof columns[index][i].rowspan != 'undefined' && columns[index][i].rowspan > 1) {
                        tableString += ' rowspan="' + columns[index][i].rowspan + '"';
                    }
                    if (typeof columns[index][i].colspan != 'undefined' && columns[index][i].colspan > 1) {
                        tableString += ' colspan="' + columns[index][i].colspan + '"';
                    }
                    if (typeof columns[index][i].field != 'undefined' && columns[index][i].field != '') {
                        nameList += ',{"f":"' + columns[index][i].field + '", "a":"' + columns[index][i].align + '"}';
                    }
                    tableString += '>' + columns[index][i].title + '</th>';
                }
            }
            tableString += '\n</tr>';
        });
    }
    // 载入内容
    var rows = printDatagrid.datagrid("getRows"); // 这段代码是获取当前页的所有行
    var nl = eval('([' + nameList.substring(1) + '])');
    for (var i = 0; i < rows.length; ++i) {
        tableString += '\n<tr>';
        $(nl).each(function (j) {
            var e = nl[j].f.lastIndexOf('_0');

            tableString += '\n<td';
            if (nl[j].a != 'undefined' && nl[j].a != '') {
                tableString += ' style="text-align:' + nl[j].a + ';"';
            }
            tableString += '>';
            if (e + 2 == nl[j].f.length) {
                tableString += rows[i][nl[j].f.substring(0, e)];
            }
            else
                tableString += rows[i][nl[j].f];
            tableString += '</td>';
        });
        tableString += '\n</tr>';
    }
    tableString += '\n</table>';

    localStorage.setItem("tableString",tableString);

    window.open("../../js/print/print.html","location:No;status:No;help:No;dialogWidth:800px;dialogHeight:600px;scroll:auto;");
}

//新的订单打印
function CreateNewFormPageOrder(strPrintName, printDatagrid, path,systemName) {
    //公司名称
    var companyName = "";
    //传真号码
    var faxNum = "";
    //加载公司信息
    $.ajax({
        type:"get",
        url: path + "/systemConfig/findBy.action",
        dataType: "json",
        async: false,
        success: function (res) {
            if(res && res.rows) {
                var array = res.rows;
                for(var i=0; i<array.length; i++){
                    var name = array[i].name;
                    if(name === "company_name") {
                        companyName = array[i].value;
                    }
                    if(name === "company_fax") {
                        faxNum = array[i].value;
                    }
                }
            }
        },
        //此处添加错误处理
        error:function() {
            $.messager.alert('查询失败','查询系统配置信息异常，请稍后再试！','error');
            return;
        }
    });

    var accountList = null;
    //加载银行信息
    $.ajax({
        type:"get",
        url: path + "/account/getAccount.action",
        dataType: "json",
        async: false,
        success: function (systemInfo) {
            accountList = systemInfo.showModel.map.accountList;
        },
        //此处添加错误处理
        error:function() {
            $.messager.alert('查询失败','查询系统配置信息异常，请稍后再试！','error');
            return;
        }
    });

    // 载入内容
    var rows = printDatagrid.datagrid("getChecked"); // 这段代码是获取当前选中的行

    var customerNo = "";
    var customerName = "";

    //加载客户信息
    $.ajax({
        type:"get",
        url: path + "/customer/findById.action",
        dataType: "json",
        async: false,
        data: {
            "id": rows[0].OrganId
        },
        success: function (res) {
            if(res && res.rows && res.rows[0]) {
                customerNo = res.rows[0].customerNo;
                customerName = res.rows[0].customerName;
            }
        },
        //此处添加错误处理
        error:function() {
            $.messager.alert('查询失败','查询系统配置信息异常，请稍后再试！','error');
            return;
        }
    });

    //订单编号
    var Number = rows[0].Number;
    var status;
    if(rows[0].Status && rows[0].SendStatus){
        status = '订单已收款，已发货'
    }else if(rows[0].Status && !rows[0].SendStatus){
        status = '订单收款，待发货'
    }else if(!rows[0].Status && rows[0].SendStatus){
        status = '订单未收款，已发货'
    }else if(!rows[0].Status && !rows[0].SendStatus){
        status = '订单未收款，未发货'
    }

    var dataList = null;
    //获取订单明细
    $.ajax({
        type:"post",
        url: path + '/depotItem/findBy.action?HeaderId=' + rows[0].Id,
        dataType: "json",
        async: false,
        success: function (res) {
            dataList = res.rows;
        },
        error:function() {
            $.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
        }
    });

    var font = 'font-family:"宋体"';
    var tableString = '<div align="center"><table style="border-collapse:separate; border-spacing:0px 0px;"><tr><td colspan="4" rowspan="3"><img src="../../upload/images/logo.png" width="308" height="60"></td>' +
        '<td colspan="5">'+companyName+'</td></tr>' +
        '<tr><td colspan="5"><strong>SILEY DECORATIVE MATERIAL(SHANGHAI) LIMITED</strong></td></tr>' +
        '<tr><td colspan="2">FAX : '+faxNum+'</td><td colspan="4">www.siley.uk</td></tr>' +
        '<tr><td colspan="9" ><div align="center" style="font-size:32px;'+font+'">Kasich&amp;Raatz'+strPrintName+'</div></td></tr>' +
        '<tr><td width="100">订单号 ：</td><td colspan="8">'+Number+'</td></tr>' +
        '<tr><td width="100">订单状态 ：</td><td colspan="8">'+status+'</td></tr>' +
        '<tr><td>客户编号：</td><td colspan="2">'+customerNo+'</td><td>客户名称：</td><td>'+customerName+'</td></tr>' +
        '<tr><td>收货人：</td><td colspan="2">'+rows[0].Contacts+'</td><td>收货电话：</td><td>'+rows[0].Phonenum+'</td></tr>' +
        '<tr><td width="100">收货地址 ：</td><td colspan="8">'+rows[0].state+rows[0].city+rows[0].street+rows[0].address+'</td></tr>' +
        '<tr><td width="100">物流公司 ：</td><td>'+rows[0].Express+'</td><td>重量(KG)：</td><td>'+rows[0].Weight+'</td><td>运费预估(元)：</td><td>'+rows[0].Freight+'</td><td>&nbsp;&nbsp;&nbsp;&nbsp;若运费超出±10请联系本公司</td></tr>' +
        '<tr><td width="100">订单备注 ：</td><td colspan="8">'+rows[0].Remark+'</td></tr>';

    tableString = tableString + '<tr><td colspan="9"><table width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse;">' +
        '<tr><th>&nbsp;</th><th>版本名称</th><th>型号</th><th>规格</th><th>单价</th><th>数量</th><th>总价(元)</th></tr>';

    if(dataList != null){
        var allNum = 0;//数量总计
        var allPrice = 0; //总价总计
        for (var j = 0; j < dataList.length; j++) {
            var depotHeadItem = dataList[j];
            allNum = allNum + depotHeadItem.OperNumber;
            allPrice = allPrice + depotHeadItem.AllPrice;
            tableString = tableString + '<tr><td align="center">'+(j+1)+'</td><td align="center">'+depotHeadItem.TemplateName+'</td><td align="center">'+depotHeadItem.ProductName+'</td><td align="center">'+depotHeadItem.Unit+'</td>' +
                '<td align="center">'+depotHeadItem.UnitPrice+'</td><td align="center">'+depotHeadItem.OperNumber+'</td><td align="center">'+depotHeadItem.AllPrice+'</td></tr>';
        }
        tableString = tableString + '<tr><td align="center">合计</td><td></td><td></td><td></td><td></td><td align="center">'+allNum+'</td><td align="center">'+allPrice+'</td></tr>';
    }


    tableString = tableString + '</table></td></tr><tr><td>制单：</td><td colspan="5">'+systemName+'</td><td>业务经理:</td><td colspan="3">'+rows[0].SalesmanNo+'</td></tr>' +
        '<tr><td colspan="9">上海思黎装饰材料有限公司-Kasich&amp;Raatz</td></tr>';

    if(accountList != null){
        for (var i = 0; i < accountList.length; i++) {
            var account = accountList[i];
            if (account.name.indexOf('银行') != -1) {
                if(i == 0){
                    tableString = tableString +'<tr><td>汇款银行</td><td colspan="3">'+account.serialNo+'</td><td colspan="2">'+account.remark+'</td><td>'+account.name+'</td></tr>';
                }else{
                    tableString = tableString +'<tr><td>&nbsp;</td><td colspan="3">'+account.serialNo+'</td><td colspan="2">'+account.remark+'</td><td>'+account.name+'</td></tr>';
                }

            }
        }
    }
    tableString = tableString + '</table></div>';

    localStorage.setItem("tableString",tableString);

    window.open("../../js/print/print.html","location:No;status:No;help:No;dialogWidth:800px;dialogHeight:600px;scroll:auto;");
}


//新的发货单打印
function CreateNewFormPageSend(strPrintName, printDatagrid, path,systemName) {
    //公司名称
    var companyName = "";
    //传真号码
    var faxNum = "";
    //加载公司信息
    $.ajax({
        type:"get",
        url: path + "/systemConfig/findBy.action",
        dataType: "json",
        async: false,
        success: function (res) {
            if(res && res.rows) {
                var array = res.rows;
                for(var i=0; i<array.length; i++){
                    var name = array[i].name;
                    if(name === "company_name") {
                        companyName = array[i].value;
                    }
                    if(name === "company_fax") {
                        faxNum = array[i].value;
                    }
                }
            }
        },
        //此处添加错误处理
        error:function() {
            $.messager.alert('查询失败','查询系统配置信息异常，请稍后再试！','error');
            return;
        }
    });


    // 载入内容
    var rows = printDatagrid.datagrid("getChecked"); // 这段代码是获取当前选中的行

    var customerNo = "";
    var customerName = "";

    //加载客户信息
    $.ajax({
        type:"get",
        url: path + "/customer/findById.action",
        dataType: "json",
        async: false,
        data: {
            "id": rows[0].OrganId
        },
        success: function (res) {
            if(res && res.rows && res.rows[0]) {
                customerNo = res.rows[0].customerNo;
                customerName = res.rows[0].customerName;
            }
        },
        //此处添加错误处理
        error:function() {
            $.messager.alert('查询失败','查询系统配置信息异常，请稍后再试！','error');
            return;
        }
    });

    //订单编号
    var Number = rows[0].Number;
    var status;
    if(rows[0].Status && rows[0].SendStatus){
        status = '订单已收款，已发货'
    }else if(rows[0].Status && !rows[0].SendStatus){
        status = '订单收款，待发货'
    }else if(!rows[0].Status && rows[0].SendStatus){
        status = '订单未收款，已发货'
    }else if(!rows[0].Status && !rows[0].SendStatus){
        status = '订单未收款，未发货'
    }

    var dataList = null;
    //获取订单明细
    $.ajax({
        type:"post",
        url: path + '/depotItem/findBy.action?HeaderId=' + rows[0].Id,
        dataType: "json",
        async: false,
        success: function (res) {
            dataList = res.rows;
        },
        error:function() {
            $.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
        }
    });

    var font = 'font-family:"宋体"';
    var tempFreight = rows[0].Freight == undefined ? "" : rows[0].Freight;
    var tableString = '<div align="center"><table style="border-collapse:separate; border-spacing:0px 0px;"><tr><td colspan="4" rowspan="3"><img src="../../upload/images/logo.png" width="308" height="60"></td>' +
        '<td colspan="5">'+companyName+'</td></tr>' +
        '<tr><td colspan="5"><strong>SILEY DECORATIVE MATERIAL(SHANGHAI) LIMITED</strong></td></tr>' +
        '<tr><td colspan="2">FAX : '+faxNum+'</td><td colspan="4">www.siley.uk</td></tr>' +
        '<tr><td colspan="9" ><div align="center" style="font-size:32px;'+font+'">Kasich&amp;Raatz'+strPrintName+'</div></td></tr>' +
        '<tr><td width="100">订单号 ：</td><td colspan="8">'+Number+'</td></tr>' +
        '<tr><td width="100">订单状态 ：</td><td colspan="8">'+status+'</td></tr>' +
        '<tr><td>客户编号：</td><td colspan="2">'+customerNo+'</td><td>客户名称：</td><td>'+customerName+'</td></tr>' +
        '<tr><td>收货人：</td><td colspan="2">'+rows[0].Contacts+'</td><td>收货电话：</td><td>'+rows[0].Phonenum+'</td><td>运单号码：</td><td>'+rows[0].ExpressNumber+'</td></tr>' +
        '<tr><td width="100">收货地址 ：</td><td colspan="8">'+rows[0].state+rows[0].city+rows[0].street+rows[0].address+'</td></tr>' +
        '<tr><td width="100">物流公司 ：</td><td>'+rows[0].Express+'</td><td>重量(KG)：</td><td>'+rows[0].Weight+'</td><td>运费预估(元)：</td><td>'+tempFreight+'</td><td>若运费超出±10请联系本公司</td></tr>' +
        '<tr><td width="100">订单备注 ：</td><td colspan="8">'+rows[0].Remark+'</td></tr>';

    //出货单 删除单价 总价
    tableString = tableString + '<tr><td colspan="9"><table width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse;">' +
        '<tr><th>&nbsp;</th><th>版本名称</th><th>型号</th><th>规格</th><th>数量</th></tr>';

    if(dataList != null){
        var allNum = 0;//数量总计
        for (var j = 0; j < dataList.length; j++) {
            var depotHeadItem = dataList[j];
            allNum = allNum + depotHeadItem.OperNumber;
            tableString = tableString + '<tr><td align="center">'+(j+1)+'</td><td align="center">'+depotHeadItem.TemplateName+'</td><td align="center">'+depotHeadItem.ProductName+'</td><td align="center">'+depotHeadItem.Unit+'</td>' +
                '<td align="center">'+depotHeadItem.OperNumber+'</td></tr>';
        }
        tableString = tableString + '<tr><td align="center">合计</td><td></td><td></td><td></td><td align="center">'+allNum+'</td></tr>';
    }


    tableString = tableString + '</table></td></tr><tr><td>制单：</td><td colspan="2">'+systemName+'</td><td>业务经理:</td><td colspan="2">'+rows[0].SalesmanNo+'</td><td>发货员：</td><td>'+rows[0].SendPersonName+'</td></tr>';

    tableString = tableString + '</table></div>';

    localStorage.setItem("tableString",tableString);

    window.open("../../js/print/print.html","location:No;status:No;help:No;dialogWidth:800px;dialogHeight:600px;scroll:auto;");
}