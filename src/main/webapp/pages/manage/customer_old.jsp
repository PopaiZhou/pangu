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
    <script src="<%=path %>/js/pages/manage/supplier.js"></script>
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
                <input type="text" name="searchSupplier" id="searchSupplier" style="width:100px;"/>
            </td>
            <td>&nbsp;</td>
            <td>手机号码：</td>
            <td>
                <input type="text" name="searchTelephone" id="searchTelephone" style="width:100px;"/>
            </td>
            <td>&nbsp;</td>
            <td>联系电话：</td>
            <td>
                <input type="text" name="searchPhonenum" id="searchPhonenum" style="width:100px;"/>
            </td>
            <td>&nbsp;</td>
            <td id="searchDescLabel">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
            <td>
                <input type="text" name="searchDesc" id="searchDesc" style="width:100px;"/>
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
<div id="supplierDlg" class="easyui-dialog" style="width:580px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons" modal="true" collapsible="false" closable="true">
    <form id="supplierFM">
        <table>
            <tr>
                <td style="width: 80px;height: 20px">名称</td>
                <td style="width: 180px;padding:1px">
                    <input name="supplier" id="supplier" class="easyui-validatebox"
                           data-options="required:true,validType:'length[2,30]'" style="width: 160px;height: 20px"/>
                </td>
                <td style="width: 60px;height: 20px">联系人</td>
                <td style="width:180px;padding:1px;">
                    <input name="contacts" id="contacts" class="easyui-validatebox" style="width: 160px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td>手机号码</td>
                <td style="padding:1px;">
                    <input name="telephone" id="telephone" class="easyui-validatebox"
                           style="width: 160px;height: 20px"/>
                </td>
                <td>电子邮箱</td>
                <td style="padding:1px">
                    <input name="email" id="email" class="easyui-validatebox" validType="email"
                           style="width: 160px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td>联系电话</td>
                <td style="padding:1px;">
                    <input name="phonenum" id="phonenum" class="easyui-validatebox" style="width: 160px;height: 20px"/>
                </td>
                <td>传真</td>
                <td style="padding:1px">
                    <input name="fax" id="fax" class="easyui-validatebox" style="width: 160px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td>期初应收</td>
                <td style="padding:1px">
                    <input name="BeginNeedGet" id="BeginNeedGet" type="text" class="easyui-numberbox"
                           data-options="min:0,precision:2" style="width: 160px;height: 20px"/>
                </td>
                <td>期初应付</td>
                <td style="padding:1px">
                    <input name="BeginNeedPay" id="BeginNeedPay" type="text" class="easyui-numberbox"
                           data-options="min:0,precision:2" style="width: 160px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td>累计应收</td>
                <td style="padding:1px">
                    <input name="AllNeedGet" id="AllNeedGet" type="text" style="width: 160px;height: 20px"
                           readonly="readonly"/>
                </td>
                <td>累计应付</td>
                <td style="padding:1px">
                    <input name="AllNeedPay" id="AllNeedPay" type="text" style="width: 160px;height: 20px"
                           readonly="readonly"/>
                </td>
            </tr>
            <tr>
                <td>纳税人识别号</td>
                <td style="padding:1px">
                    <input name="taxNum" id="taxNum" class="easyui-validatebox" style="width: 160px;height: 20px"/>
                </td>
                <td>税率</td>
                <td style="padding:1px">
                    <input name="taxRate" id="taxRate" class="easyui-validatebox" style="width: 160px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td>开户行</td>
                <td style="padding:1px">
                    <input name="bankName" id="bankName" class="easyui-validatebox" style="width: 160px;height: 20px"/>
                </td>
                <td>账号</td>
                <td style="padding:1px">
                    <input name="accountNumber" id="accountNumber" class="easyui-validatebox"
                           style="width: 160px;height: 20px"/>
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
                <td>详细地址</td>
                <td style="padding:1px" colspan="3">
                    <input name="address" id="address" class="easyui-validatebox" style="width: 408px;height: 20px"/>
                </td>
            </tr>
            <tr>
                <td>备注</td>
                <td style="padding:1px" colspan="3">
                    <textarea name="description" id="description" rows="2" cols="2" style="width: 408px;"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" id="saveSupplier" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="cancelSupplier" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#supplierDlg').dialog('close')">取消</a>
</div>

<!-- 导入excel表格 -->
<div id="importExcelDlg" style="padding:10px 20px">
    <form id="importExcelFM" method="post" enctype="multipart/form-data"
          action="<%=path%>/supplier/importExcelCustomer.action">
        <div class="fitem" style="padding:5px">
            <label>文件名称&nbsp;&nbsp;</label>
            <input name="supplierFile" id="supplierFile" type="file" style="width: 230px;height: 20px"/>
        </div>
        <div class="fitem" style="padding:5px">
            <label><b>导入注意</b>&nbsp;&nbsp;</label><span>（预收款、期初应收、期初应付、税率均为数值且要大于0；另外期初应收、期初应付不能同时输入）</span>
        </div>
        <div class="fitem" style="padding:5px;display: none;">
            <label>是否审查&nbsp;&nbsp;</label>
            <select id="isCheck" name="isCheck" style="width: 230px;height: 20px">
                <option value="0">是</option>
                <option value="1" selected="selected">否</option>
            </select>
        </div>
        <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
        <div id="dlg-buttons5">
            <a href="javascript:void(0)" id="saveimport" class="easyui-linkbutton" iconCls="icon-ok">导入</a>
            <a href="javascript:void(0)" id="cancelimport" class="easyui-linkbutton" iconCls="icon-cancel"
               onclick="javascript:$('#importExcelDlg').dialog('close')">取消</a>
        </div>
    </form>
</div>
</body>
</html>