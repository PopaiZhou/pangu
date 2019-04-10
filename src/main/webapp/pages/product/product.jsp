<%@page import="com.jsh.util.Tools" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html>
<html>
<head>
    <title>产品型号管理</title>
    <meta charset="utf-8">
    <!-- 指定以IE8的方式来渲染 -->
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path %>/js/easyui-1.3.5/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="<%=path %>/css/common.css"/>
    <link type="text/css" rel="stylesheet" href="<%=path %>/css/in_out.css"/>
    <script src="<%=path %>/js/jquery-1.8.0.min.js"></script>
    <script src="<%=path %>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
    <script src="<%=path %>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
    <script src="<%=path %>/js/My97DatePicker/WdatePicker.js"></script>
    <script src="<%=path %>/js/common/common.js"></script>
    <script src="<%=path %>/js/pages/product/product.js"></script>
    <script>
        var kid = ${sessionScope.user.id};
        var path = "<%=path%>";
        var clientIp = "<%=clientIp%>";
    </script>
</head>
<body>
<!-- 查询 -->
<div id="searchPanel" class="easyui-panel" style="padding:3px;" title="查询窗口" iconCls="icon-search" collapsible="true"
     closable="false">
    <table id="searchTable">
        <tr>
            <td>型号编号：</td>
            <td>
                <input type="text" name="searchNumber" id="searchNumber" style="width:100px;"/>
            </td>
            <td>型号名称：</td>
            <td>
                <input type="text" name="searchName" id="searchName" placeholder="型号" style="width:160px;"/>
            </td>
            <td>所属版本：</td>
            <td>
                <input type="text" name="searchTemplate" id="searchTemplate" style="width:200px;" editable="false"/>
            </td>
            <td>&nbsp;</td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>&nbsp;
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" id="searchResetBtn">重置</a>
            </td>
        </tr>
    </table>
</div>

<!-- 数据显示table -->
<div id="tablePanel" class="easyui-panel" style="padding:1px; top:300px;" title="型号列表" iconCls="icon-list"
     collapsible="true" closable="false">
    <table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
</div>

<!--产品信息Form-->
<div id="supplierDlg" class="easyui-dialog" style="width:620px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons" modal="true" collapsible="false" closable="true">
    <form id="supplierFM">
        <table>
            <tr>
                <td style="width: 80px;height: 20px">型号编号:</td>
                <td style="width: 180px;padding:1px">
                    <input name="productId" id="productId" class="easyui-validatebox"
                           data-options="required:true,validType:'length[2,30]'" style="width: 200px;height: 20px"/>
                </td>

                <td style="width: 60px;height: 20px">型号名称:</td>
                <td style="width:180px;padding:1px;">
                    <input name="productName" id="productName" class="easyui-validatebox"
                           data-options="required:true,validType:'length[2,120]'" style="width: 200px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 60px;height: 20px">所属版本名称:</td>
                <td>
                    <input type="text" name="templateId" id="templateId" data-options="required:true" editable="false" style="width:200px;"/>
                </td>
                <td style="width: 60px;height: 20px">规格:</td>
                <td style="width:180px;padding:1px;">
                    <input name="standard" id="standard" class="easyui-validatebox" style="width: 200px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 60px;height: 20px">重量系数:</td>
                <td style="width:180px;padding:1px;">
                    <input name="weight" id="weight" type="text" class="easyui-numberbox"
                           data-options="required:true,min:0,precision:2" style="width: 200px;height: 20px"/>
                </td>
                <td style="width: 60px;height: 20px">进货价:</td>
                <td style="width:180px;padding:1px;">
                    <input name="purchasePrice" id="purchasePrice" type="text" class="easyui-numberbox"
                           data-options="min:0,precision:2" style="width: 200px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 60px;height: 20px">批发商:</td>
                <td style="width:180px;padding:1px;">
                    <input name="wholesalePrice" id="wholesalePrice" type="text" class="easyui-numberbox"
                           data-options="min:0,precision:2" style="width: 200px;height: 20px"/>
                </td>
                <td style="width: 60px;height: 20px">零售商:</td>
                <td style="width:180px;padding:1px;">
                    <input name="retailPrice" id="retailPrice" type="text" class="easyui-numberbox"
                           data-options="min:0,precision:2" style="width: 200px;height: 20px"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" id="saveProduct" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="cancelProduct" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#supplierDlg').dialog('close')">取消</a>
</div>
</body>
</html>
