<%@page import="com.jsh.util.Tools" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html>
<html>
<head>
    <title>地区统计</title>
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
    <script type="text/javascript" src="<%=path %>/js/pages/manage/city.js"></script>
    <script>
        var uid = ${sessionScope.user.id};

        $(document).ready(function() {
            initProvinceOnly();
        });

        jQuery.fn.rowspan = function(colIdx) { //封装的一个JQuery小插件
            return this.each(function(){
                var that;
                $('tr', this).each(function(row) {
                    $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
                        if (that!=null && $(this).html() == $(that).html()) {
                            rowspan = $(that).attr("rowSpan");
                            if (rowspan == undefined) {
                                $(that).attr("rowSpan",1);
                                rowspan = $(that).attr("rowSpan");
                            }
                            rowspan = Number(rowspan)+1;
                            $(that).attr("rowSpan",rowspan);
                            $(this).hide();
                        } else {
                            that = this;
                        }
                    });
                });
            });
        }

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
            <td>省份：</td>
            <td>
                <input id="state" name="state" class="easyui-combobox" style="width:100px;"  data-options="editable:false,prompt: '请选择省份',missingMessage:'请选择省份'" />
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
<div id="tablePanel" class="easyui-panel" style="padding:1px;top:300px;" title="地区对账单" iconCls="icon-list"
     collapsible="true" closable="false">
    <div id="tableData"></div>

</div>

<script type="text/javascript">
    var path = "<%=path %>";
    var cusUrl = path + "/customer/findBySelect_sup.action"; //客户接口
    //初始化界面
    $(function () {
        var thisDate = getNowFormatMonth(); //当前月份
        var thisDateTime = getNowFormatDateTime(); //当前时间
        $("#searchBeginTime").val(thisDate + "-01 00:00:00");
        $("#searchEndTime").val(thisDateTime);

    });

    //初始化键盘enter事件
    $(document).keydown(function (event) {
        //兼容 IE和firefox 事件
        var e = window.event || event;
        var k = e.keyCode || e.which || e.charCode;
        //兼容 IE,firefox 兼容
        var obj = e.srcElement ? e.srcElement : e.target;
        //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件 ---遗留问题 enter键效验 对话框会关闭问题
        if (k == "13" && (obj.id == "Type" || obj.id == "Name")) {
            $("#savePerson").click();
        }
        //搜索按钮添加快捷键
        if (k == "13" && (obj.id == "searchType")) {
            $("#searchBtn").click();
        }
    });
    //打印按钮
    $("#printBtn").off("click").on("click", function () {
        localStorage.setItem("tableString",$('#tableData').html());

        window.open("../../js/print/print.html","location:No;status:No;help:No;dialogWidth:800px;dialogHeight:600px;scroll:auto;");
    });

    $("#searchBtn").unbind().bind({
        click: function () {
            if($('#state').combobox('getValue') == ""){
                $.messager.alert('提示', '请选择省份！', 'info');
                return;
            }
            initPage();
        }
    });

    function initPage() {
        var font = 'font-family:"宋体"';
        var tableString = "";
        var areaName = $('#state').combobox('getText');
        tableString = tableString + '<table style="top:300px;border-bottom-color:#FFFFFF;width: 100%"><tr><td colspan="8"><div align="center" style="font-size:30px;'+font+'">'+areaName+'地区</div></td></tr>' +
                '<tr style="font-size: 16px;"><td colspan="8">对账单时间范围：'+$("#searchBeginTime").val()+' 至 '+$("#searchEndTime").val()+'</td></tr>';
        //获取订单明细
        $.ajax({
            type: "post",
            url: "<%=path %>/depotHead/findAreaReport.action",
            dataType: "json",
            async:false,
            data: ({
                pageNo: 0,
                pageSize: 0,
                BeginTime: $("#searchBeginTime").val(),
                EndTime: $("#searchEndTime").val(),
                state: $('#state').combobox('getText')
            }),
            success: function (res) {
                if (res && res.rows) {
                    var thisRows = res.rows;
                    tableString = tableString + '<div><table id="statementTable" width="100%" border="1" bordercolor="#000000" style="border-collapse:collapse;">' +
                            '<tr><th>单号</th><th>日期</th><th>客户编号</th><th>客户姓名</th>' +
                            '<th>省份</th><th>地区</th>'+
                            '<th>型号</th><th>样本名称</th><th>数量</th><th>单价</th><th>金额</th><th>总金额</th></tr>';
                    for (var i = 0; i < thisRows.length; i++) {
                        tableString = tableString + '<tr><td>' + thisRows[i].number + '</td><td>' + thisRows[i].oTime + '</td><td>' + thisRows[i].customerNo + '</td><td>' + thisRows[i].customerName + '</td>' +
                                '<td>' + thisRows[i].state + '</td><td>' + thisRows[i].city + '</td>' +
                                '<td>' + thisRows[i].productName + '</td><td>' + thisRows[i].templateName + '</td><td>' + thisRows[i].operNumber + '</td><td>' + thisRows[i].unitPrice.toFixed(2) + '</td><td>' + thisRows[i].allPrice.toFixed(2) + '</td><td id=price' + i + '>' + thisRows[i].allPrice.toFixed(2) + '</td></tr>';
                    }
                    tableString = tableString + '</table></div>';

                    //获取版本分类统计
                    $.ajax({
                        type: "post",
                        url: "<%=path %>/depotHead/findAreaReportTemplate.action",
                        dataType: "json",
                        async:false,
                        data: ({
                            pageNo: 0,
                            pageSize: 0,
                            BeginTime: $("#searchBeginTime").val(),
                            EndTime: $("#searchEndTime").val(),
                            state: $('#state').combobox('getText')
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


        tableString = tableString + '<br><br>';
        $('#tableData').html(tableString+"</table>");
        //$("#statementTable").rowspan(0);//传入的参数是对应的列数从0开始  第一列合并相同

        //var tab=document.getElementById("statementTable");
        //autoRowSpan(tab,0,0);
    }
</script>
</body>
</html>