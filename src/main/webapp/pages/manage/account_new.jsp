<%@page import="com.jsh.util.Tools" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html>
<html>
<head>
    <title>结算账户</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <!-- 指定以IE8的方式来渲染 -->
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="<%=path %>/css/common.css"/>
    <script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=path %>/js/common/outlook_in.js"></script>
    <script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
</head>
<body>
<!-- 查询 -->
<div id="searchPanel" class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true"
     closable="false">
    <table id="searchTable">
        <tr>
            <td>名&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
            <td>
                <input type="text" name="searchName" id="searchName" style="width:70px;"/>
            </td>

            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td id="searchSerialNoLabel">编&nbsp;&nbsp;&nbsp;&nbsp;号：</td>
            <td>
                <input type="text" name="searchSerialNo" id="searchSerialNo" style="width:70px;"/>
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td id="searchRemarkLabel">备&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
            <td>
                <input type="text" name="searchRemark" id="searchRemark" style="width:70px;"/>
            </td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>&nbsp;&nbsp;
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" id="searchResetBtn">重置</a>
            </td>
        </tr>
    </table>
</div>

<!-- 数据显示table -->
<div id="tablePanel" class="easyui-panel" style="padding:1px;top:300px;" title="结算账户" iconCls="icon-list"
     collapsible="true" closable="false">
    <table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
</div>
<div id="accountDlg" class="easyui-dialog" style="width:380px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons" modal="true" collapsible="false" closable="true">
    <form id="accountFM" method="post" novalidate>
        <div class="fitem" style="padding:5px">
            <label id="nameLabel">名称</label>
            <input name="name" id="name" class="easyui-validatebox"
                   data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="serialNoLabel">编号</label>
            <input name="serialNo" id="serialNo" class="easyui-validatebox"
                   data-options="required:true,validType:'length[2,30]'" style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="initialAmountLabel">期初金额</label>
            <input name="initialAmount" id="initialAmount" type="text" class="easyui-numberbox"
                   data-options="min:0,precision:2" style="width: 230px;height: 20px"></input>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="currentAmountLabel">当前余额</label>
            <input name="currentAmount" id="currentAmount" type="text" disabled="disabled" class="easyui-numberbox"
                   data-options="min:0,precision:2" style="width: 230px;height: 20px"></input>
        </div>
        <div class="fitem" style="padding:5px">
            <label id="remarkLabel">备注</label>
            <textarea name="remark" id="remark" rows="2" cols="2" style="width: 230px;"></textarea>
        </div>
        <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
    </form>
</div>
<div id="accountDetailListDlg" class="easyui-dialog" style="width:900px;height:500px;padding:10px 20px" closed="true"
     modal="true" collapsible="false" closable="true">
    <table id="accountTableData" style="top:50px;border-bottom-color:#FFFFFF"></table>
</div>

<script type="text/javascript">
    //初始化界面
    $(function () {
        initTableData();
        ininPager();
    });

    //初始化表格数据
    function initTableData() {
        $('#tableData').datagrid({
            height: heightInfo,
            nowrap: false,
            rownumbers: false,
            //动画效果
            animate: false,
            //选中单行
            singleSelect: true,
            collapsible: false,
            //交替出现背景
            striped: true,
            url: '<%=path %>/account/findByNew.action?pageSize=' + initPageSize,
            pagination: true,
            pageSize: initPageSize,
            pageList: initPageNum,
            columns: [[
                {field: 'id', width: 35, align: "center", checkbox: true},
                {title: '名称', field: 'name', width: 200},
                {title: '编号', field: 'serialNo', width: 150, align: "center"},
                {title: '期初金额', field: 'initialAmount', width: 100, align: "center"},
                {title: '当前余额', field: 'currentAmount', width: 100, align: "center"},
                {
                    title: '是否默认', field: 'isDefault', width: 100, align: "center",
                    formatter: function (value, rec) {
                        if (rec.isDefault) {
                            return "<b style='color:green'>是</b>";
                        }
                        else {
                            return "否";
                        }
                    }
                },
                {title: '备注', field: 'remark', width: 100}
            ]],
            toolbar: [
            ],
            onLoadError: function () {
                $.messager.alert('页面加载提示', '页面加载异常，请稍后再试！', 'error');
                return;
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
        if (k == "13" && (obj.id == "searchName" || obj.id == "searchSerialNo" || obj.id == "searchRemark")) {
            $("#searchBtn").click();
        }
    });

    //分页信息处理
    function ininPager() {
        try {
            var opts = $("#tableData").datagrid('options');
            var pager = $("#tableData").datagrid('getPager');
            pager.pagination({
                onSelectPage: function (pageNum, pageSize) {
                    opts.pageNumber = pageNum;
                    opts.pageSize = pageSize;
                    pager.pagination('refresh',
                        {
                            pageNumber: pageNum,
                            pageSize: pageSize
                        });
                    showAccountDetails(pageNum, pageSize);
                }
            });
        }
        catch (e) {
            $.messager.alert('异常处理提示', "分页信息异常 :  " + e.name + ": " + e.message, 'error');
        }
    }
    //搜索处理
    $("#searchBtn").unbind().bind({
        click: function () {
            showAccountDetails(1, initPageSize);
            var opts = $("#tableData").datagrid('options');
            var pager = $("#tableData").datagrid('getPager');
            opts.pageNumber = 1;
            opts.pageSize = initPageSize;
            pager.pagination('refresh',
                {
                    pageNumber: 1,
                    pageSize: initPageSize
                });
        }
    });

    function showAccountDetails(pageNo, pageSize) {
        $.ajax({
            type: "post",
            url: "<%=path %>/account/findByNew.action",
            dataType: "json",
            data: ({
                name: $.trim($("#searchName").val()),
                serialNo: $.trim($("#searchSerialNo").val()),
                remark: $.trim($("#searchRemark").val()),
                pageNo: pageNo,
                pageSize: pageSize
            }),
            success: function (data) {
                $("#tableData").datagrid('loadData', data);
            },
            //此处添加错误处理
            error: function () {
                $.messager.alert('查询提示', '查询数据后台异常，请稍后再试！', 'error');
                return;
            }
        });
    }
    //重置按钮
    $("#searchResetBtn").unbind().bind({
        click: function () {
            $("#searchName").val("");
            $("#searchSerialNo").val("");
            $("#searchRemark").val("");
            //加载完以后重新初始化
            $("#searchBtn").click();
        }
    });
</script>
</body>
</html>
