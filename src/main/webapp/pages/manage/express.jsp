<%@page import="com.jsh.util.Tools" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html>
<html>
<head>
    <title>物流信息</title>
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
    <script type="text/javascript" src="<%=path %>/js/common/common.js"></script>
    <script src="<%=path %>/js/pages/manage/express.js"></script>
    <script>
        var kid = ${sessionScope.user.id};
        var path = "<%=path%>";
        var clientIp = "<%=clientIp%>";

    </script>
</head>
<body>
<!-- 查询 -->
<div id="searchPanel" class="easyui-panel" style="padding:10px;" title="查询窗口" iconCls="icon-search" collapsible="true"
     closable="false">
    <table id="searchTable">
        <tr>
            <td>名&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
            <td>
                <input type="text" name="searchExpressName" id="searchExpressName" style="width:100px;"/>
            </td>
            <td>编&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
            <td>
                <input type="text" name="searchExpressCode" id="searchExpressCode" style="width:100px;"/>
            </td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>&nbsp;&nbsp;
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" id="searchResetBtn">重置</a>
            </td>
        </tr>
    </table>
</div>

<!-- 数据显示table -->
<div id="tablePanel" class="easyui-panel" style="padding:1px;top:300px;" title="快递信息列表" iconCls="icon-list"
     collapsible="true" closable="false">
    <table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
</div>
<div id="supplierDlg" class="easyui-dialog" style="width:600px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons" modal="true" collapsible="false" closable="true">
    <form id="supplierFM">
        <table>
            <tr>
                <td style="width: 80px;height: 20px">编号</td>
                <td style="width: 180px;padding:1px">
                    <input name="expressCode" id="expressCode" class="easyui-validatebox"
                           data-options="required:true,validType:'length[2,15]'" style="width: 160px;height: 20px"/>
                </td>
                <td style="width: 60px;height: 20px">名称</td>
                <td style="width:180px;padding:1px;">
                    <input name="expressName" id="expressName" class="easyui-validatebox"
                           data-options="required:true,validType:'length[2,60]'" style="width: 160px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 60px;height: 20px">是否启用</td>
                <td style="width:180px;padding:1px;">
                    <input type="text" name="enabled" id="enabled" data-options="required:true"  style="width:160px; height:20px" editable="false"/>
                </td>
                <td style="width: 60px;height: 20px">排序</td>
                <td style="padding:1px;">
                    <input name="expressOrder" id="expressOrder" class="easyui-numberbox" style="width: 160px;height: 20px"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" id="saveExpress" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="cancelExpress" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#supplierDlg').dialog('close')">取消</a>
</div>

</body>
</html>