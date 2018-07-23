<%@page import="com.jsh.util.Tools" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html>
<html>
<head>
    <title>客户信息</title>
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
    <script type="text/javascript" src="<%=path %>/js/pages/manage/city.js"></script>
    <script src="<%=path %>/js/pages/manage/customer.js"></script>
    <script>
        var kid = ${sessionScope.user.id};
        var path = "<%=path%>";
        var clientIp = "<%=clientIp%>";

        $(document).ready(function() {
            initProvince();
        });
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
                <input type="text" name="searchCustomerName" id="searchCustomerName" style="width:100px;"/>
            </td>
            <td>&nbsp;</td>
            <td>联系电话：</td>
            <td>
                <input type="text" name="searchPhonenum" id="searchPhonenum" style="width:100px;"/>
            </td>
            <td>&nbsp;</td>
            <td>客户类型：</td>
            <td>
                <input type="text" name="searchType" id="searchType" style="width:100px;" editable="false"/>
            </td>
            <td>业务经理：</td>
            <td>
                <input type="text" name="searchBasicuser" id="searchBasicuser" style="width:160px;" editable="false"/>
            </td>
            <td>&nbsp;</td>
            <td>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>&nbsp;&nbsp;
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" id="searchResetBtn">重置</a>
            </td>
        </tr>
    </table>
</div>

<!-- 数据显示table -->
<div id="tablePanel" class="easyui-panel" style="padding:1px;top:300px;" title="客户信息列表" iconCls="icon-list"
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
                    <input name="customerNo" id="customerNo" class="easyui-validatebox"
                           data-options="required:true,validType:'length[2,30]'" style="width: 160px;height: 20px"/>
                </td>
                <td style="width: 60px;height: 20px">名称</td>
                <td style="width:180px;padding:1px;">
                    <input name="customerName" id="customerName" class="easyui-validatebox"
                           data-options="required:true,validType:'length[2,60]'" style="width: 160px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 60px;height: 20px">简称</td>
                <td style="padding:1px;">
                    <input name="customerShort" id="customerShort" class="easyui-validatebox" style="width: 160px;height: 20px"/>
                </td>
                <td style="width: 60px;height: 20px">手机号码</td>
                <td style="width:180px;padding:1px;">
                    <input name="telephone" id="telephone" class="easyui-numberbox" style="width: 160px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 60px;height: 20px">联系电话</td>
                <td style="padding:1px;">
                    <input name="phonenum" id="phonenum" class="easyui-validatebox" style="width: 160px;height: 20px"/>
                </td>
                <td style="width: 60px;height: 20px">联系人</td>
                <td style="width:180px;padding:1px;">
                    <input name="contacts" id="contacts" class="easyui-validatebox" style="width: 160px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 60px;height: 20px">客服QQ</td>
                <td style="padding:1px;">
                    <input name="qq" id="qq" class="easyui-numberbox"
                           style="width: 160px;height: 20px"/>
                </td>
                <td style="width: 60px;height: 20px">电子邮箱</td>
                <td style="padding:1px">
                    <input name="email" id="email" class="easyui-validatebox" validType="email"
                           style="width: 160px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 60px;height: 20px">税率</td>
                <td style="padding:1px">
                    <input name="taxRate" id="taxRate" class="easyui-validatebox" style="width: 160px;height: 20px"/>
                </td>
                <td style="width: 60px;height: 20px">纳税人识别号</td>
                <td style="padding:1px">
                    <input name="taxNum" id="taxNum" class="easyui-validatebox" style="width: 160px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td>客户类型</td>
                <td>
                    <input type="text" name="type" id="type" data-options="required:true"  style="width:160px; height:20px" editable="false"/>
                </td>
                <td>业务经理</td>
                <td>
                    <input type="text" name="basicUser" id="basicUser" data-options="required:true"  style="width:160px; height:20px" editable="false"/>
                </td>
            </tr>
            <tr>
                <td style="width: 60px;height: 20px">开户行</td>
                <td style="padding:1px" colspan="3">
                    <input name="bankName" id="bankName" class="easyui-validatebox" style="width: 468px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td style="width: 60px;height: 20px">账号</td>
                <td style="padding:1px">
                    <input name="accountNumber" id="accountNumber" class="easyui-validatebox"
                           style="width: 160px;height: 20px"/>
                </td>
                <td>默认物流</td>
                <td>
                    <input type="text" name="express" id="express" class="easyui-validatebox"
                           style="width:160px;height: 20px" editable="false"/>
                </td>
            </tr>
            <tr>
                <td>所在地址</td>
                <td style="padding:1px">
                    <input id="state" name="state" class="easyui-combobox" style="width:100px;"  data-options="editable:false,prompt: '请选择省份',missingMessage:'请选择省份'" />
                </td>
                <td style="padding:1px">
                    <input id="city" name="city" class="easyui-combobox" style="width:100px;"  data-options="editable:false,prompt: '请选择城市',missingMessage:'请选择城市'" />
                </td>
                <td style="padding:1px">
                    <input id="street" name="street" class="easyui-combobox" style="width:100px;"  data-options="editable:false,prompt: '请选择区县',missingMessage:'请选择区县'" />
                </td>
            </tr>
            <tr>
                <td style="width: 60px;height: 20px">详细地址</td>
                <td style="padding:1px" colspan="3">
                    <input name="address" id="address" class="easyui-validatebox" style="width: 468px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td>备注</td>
                <td style="padding:1px" colspan="3">
                    <textarea name="description" id="description" rows="2" cols="2" style="width: 468px;"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" id="saveCustomer" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="cancelCustomer" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#supplierDlg').dialog('close')">取消</a>
</div>

</body>
</html>