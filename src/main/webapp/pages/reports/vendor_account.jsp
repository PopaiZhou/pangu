<%@page import="com.jsh.util.Tools" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html>
<html>
<head>
    <title>供应商对账</title>
    <meta charset="utf-8">
    <!-- 指定以IE8的方式来渲染 -->
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon"/>
    <script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js/print/print.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="<%=path %>/css/common.css"/>
    <script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=path %>/js/common/outlook_in.js"></script>
    <script type="text/javascript" src="<%=path %>/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
    <script>
        var uid = ${sessionScope.user.id};

        function autoRowSpan(tb, row, col) {
            var lastValue = "";
            var value = "";
            var pos = 1;
            var tdSum = 0;
            var cellValue = 0;
            for (var i = row; i < tb.rows.length; i++) {
                value = tb.rows[i].cells[col].innerText;
                if (lastValue == value) {//判断产品名称是否相同
                    if (!isNaN(tb.rows[i].cells[10].innerText))//判断是否为数字类型
                        cellValue =cellValue+ parseFloat(tb.rows[i].cells[10].innerText);
                    tb.rows[i].deleteCell(col);
                    tb.rows[i - pos].cells[col].rowSpan = tb.rows[i - pos].cells[col].rowSpan + 1;//设置单元格rowSpan的值
                    var z = tb.rows[i].cells.length;
                    tb.rows[i].deleteCell(z - 1);
                    tb.rows[i - pos].cells[z].rowSpan = tb.rows[i - pos].cells[z].rowSpan + 1//合并标煤合计那一列的单元格
                    tb.rows[i - pos].cells[11].innerText = cellValue;//进行复制
                    pos++;
                } else {//产品名称不同的处理
                    lastValue = value;
                    cellValue = parseFloat(tb.rows[i].cells[10].innerText);
                    if (!isNaN(cellValue))
                        tb.rows[i].cells[11].innerText = cellValue;
                    pos = 1;
                    tdSum = 0;
                }
            }
        }
    </script>
</head>
<body>
<!-- 查询 -->
<div id="searchPanel" class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true"
     closable="false">
    <table id="searchTable">
        <tr>
            <td>供应商：</td>
            <td>
                <input id="OrganId" name="OrganId" style="width:120px;"/>
            </td>
            <td>&nbsp;</td>
            <td>单据日期：</td>
            <td>
                <input type="text" name="searchBeginTime" id="searchBeginTime"
                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="txt Wdate" style="width:140px;"/>
            </td>
            <td>-</td>
            <td>
                <input type="text" name="searchEndTime" id="searchEndTime"
                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="txt Wdate" style="width:140px;"/>
            </td>
            <td>&nbsp;</td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>
                &nbsp;&nbsp;
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-print" id="printBtn">打印</a>
            </td>
        </tr>
    </table>
</div>

<!-- 数据显示table -->
<div id="tablePanel" class="easyui-panel" style="padding:1px;top:300px;" title="供应商对账单" iconCls="icon-list"
     collapsible="true" closable="false">
    <div id="tableData"></div>
</div>

<script type="text/javascript">
    var path = "<%=path %>";
    var supUrl = path + "/supplier/findBySelect_sup.action"; //供应商接口
    //初始化界面
    $(function () {
        var thisDate = getNowFormatMonth(); //当前月份
        var thisDateTime = getNowFormatDateTime(); //当前时间
        $("#searchBeginTime").val(thisDate + "-01 00:00:00");
        $("#searchEndTime").val(thisDateTime);
        initSupplier(); //初始化供应商信息
    });


    //初始化供应商
    function initSupplier() {
        $('#OrganId').combobox({
            url: supUrl,
            valueField: 'id',
            textField: 'supplier',
            filter: function (q, row) {
                var opts = $(this).combobox('options');
                return row[opts.textField].indexOf(q) > -1;
            }
        });
    }

    //初始化键盘enter事件
    $(document).keydown(function (event) {
        //兼容 IE和firefox 事件
        var e = window.event || event;
        var k = e.keyCode || e.which || e.charCode;
        //兼容 IE,firefox 兼容
        var obj = e.srcElement ? e.srcElement : e.target;
        //搜索按钮添加快捷键
        if (k == "13" && (obj.id == "searchType")) {
            $("#searchBtn").click();
        }
    });

    //增加
    var url;
    var personID = 0;
    //保存编辑前的名称
    var orgPerson = "";

    $("#searchBtn").unbind().bind({
        click: function () {
            if($('#OrganId').combobox('getValue') == ""){
                $.messager.alert('提示', '请选择供应商！', 'info');
                return;
            }
            initPage();
        }
    });

    function initPage() {
        var font = 'font-family:"宋体"';
        var tableString = "";
        tableString = tableString + '<table style="top:300px;border-bottom-color:#FFFFFF;width: 100%"><tr><td colspan="8"><div align="center" style="font-size:30px;'+font+'">Kasich&amp;Raatz供应商账单</div></td></tr>' +
                '<tr style="font-size: 16px;"><td colspan="8">对账单时间范围：'+$("#searchBeginTime").val()+' 至 '+$("#searchEndTime").val()+'</td></tr>' +
                '<tr style="font-size: 16px;"><td colspan="8">来往单位：'+$('#OrganId').combobox('getText');

        //加载供应商信息
        $.ajax({
            type:"get",
            url: path + "/supplier/findById.action",
            dataType: "json",
            async: false,
            data: {
                "supplierID": $('#OrganId').combobox('getValue')
            },
            success: function (res) {
                if(res && res.rows && res.rows[0]) {
                    tableString = tableString + '<tr style="font-size: 16px;"><td align="left" width="80px">联系人：</td><td align="left" width="80px" colspan="7">'+res.rows[0].contacts+'</td></tr>';
                    tableString = tableString + '<tr style="font-size: 16px;"><td width="80px">联系电话：</td><td width="80px" colspan="7">'+res.rows[0].phonenum+'</td></tr>';
                    tableString = tableString + '<tr style="font-size: 16px;"><td width="80px">传真：</td><td width="80px" colspan="7">'+res.rows[0].fax+'</td></tr>'
                }
            },
            //此处添加错误处理
            error:function() {
                $.messager.alert('查询失败','查询系统配置信息异常，请稍后再试！','error');
                return;
            }
        });

        //获取订单明细
        $.ajax({
            type: "post",
            url: "<%=path %>/depotHead/findSupplierStatementAccount.action",
            dataType: "json",
            async: false,
            data: ({
                pageNo: 0,
                pageSize: 0,
                BeginTime: $("#searchBeginTime").val(),
                EndTime: $("#searchEndTime").val(),
                OrganId: $('#OrganId').combobox('getValue')
            }),
            success: function (res) {
                if (res && res.rows) {
                    var thisRows = res.rows;
                    tableString = tableString + '<div><table id="statementTable" width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse;">' +
                            '<tr><th>单号</th><th>日期</th><th>客户编号</th><th>客户姓名</th>' +
                            '<th>省份</th><th>地区</th>'+
                            '<th>型号</th><th>样本名称</th><th>数量</th><th>单价</th><th>金额</th><th>总金额</th></tr>';
                    for (var i = 0; i < thisRows.length; i++) {
                        tableString = tableString + '<tr>' +
                                '<td>' + thisRows[i].number + '</td><td>' + thisRows[i].oTime + '</td><td>' + thisRows[i].customerNo + '</td><td>' + thisRows[i].customerName + '</td>' +
                                '<td>' + thisRows[i].state + '</td><td>' + thisRows[i].city + '</td>' +
                                '<td>' + thisRows[i].productName + '</td><td>' + thisRows[i].templateName + '</td><td>' + thisRows[i].operNumber + '</td><td>' + thisRows[i].taxUnitPrice.toFixed(2) + '</td><td>' + thisRows[i].allPrice.toFixed(2) + '</td><td id=price' + i + '>' + thisRows[i].allPrice.toFixed(2) + '</td></tr>';
                    }
                    tableString = tableString + '</table></div>';

                    //获取版本分类统计
                    $.ajax({
                        type: "post",
                        url: "<%=path %>/depotHead/findSupplierStatementTemplate.action",
                        dataType: "json",
                        async: false,
                        data: ({
                            pageNo: 0,
                            pageSize: 0,
                            BeginTime: $("#searchBeginTime").val(),
                            EndTime: $("#searchEndTime").val(),
                            OrganId: $('#OrganId').combobox('getValue')
                        }),
                        success: function (res) {
                            if (res && res.rows) {
                                var thisRows = res.rows;

                                tableString = tableString + '<br><br><br>';
                                tableString = tableString + '<div style="font-size: 16px;">总计版本分类统计：</div>';
                                tableString = tableString + '<div><table id="templateTable" width="50%" border="1" bordercolor="#000000" style="border-collapse:collapse;"><tr><th>版本种类</th><th>数量（米）</th><th>金额合计（元）</th></tr>';

                                var allNum = 0;
                                var allPrice = 0;
                                for (var i = 0; i < thisRows.length; i++) {
                                    allNum = allNum + thisRows[i].OperNumber;
                                    allPrice = allPrice + thisRows[i].AllPrice;
                                    tableString = tableString + '<tr><td>'+thisRows[i].templateName+'</td><td>'+thisRows[i].OperNumber.toFixed(2)+'</td><td>'+thisRows[i].AllPrice.toFixed(2)+'</td></tr>';
                                }
                                tableString = tableString + '<tr><td colspan="3" align="center">合计数量：'+allNum.toFixed(2)+'米&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;合计金额：'+allPrice.toFixed(2)+'元</td><tr>';
                                tableString = tableString + '</table></div>';
                            }
                        },
                        error: function () {
                            $.messager.alert('提示', '网络异常请稍后再试！', 'error');
                            return;
                        }
                    });
                }
            },
            error: function () {
                $.messager.alert('提示', '网络异常请稍后再试！', 'error');
                return;
            }
        });


        tableString = tableString + '<br><br><div style="font-size: 20px;">如有对账单有任何问题，请在账单中标注，并在账单生成一个月内通知本公司！</div>';
        $('#tableData').html(tableString+"</table>");

        var tab=document.getElementById("statementTable");
        //autoRowSpan(tab,0,0);

    }


    //打印按钮
    $("#printBtn").off("click").on("click", function () {
        localStorage.setItem("tableString",$('#tableData').html());

        window.open("../../js/print/print.html","location:No;status:No;help:No;dialogWidth:800px;dialogHeight:600px;scroll:auto;");
    });
</script>
</body>
</html>