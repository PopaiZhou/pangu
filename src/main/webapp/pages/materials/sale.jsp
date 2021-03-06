<%@page import="com.jsh.util.Tools" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html>
<html>
<head>
    <title>销售下单</title>
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
    <script src="<%=path %>/js/print/print.js"></script>
    <script src="<%=path %>/js/common/dataGridUtils.js"></script>
    <script src="<%=path %>/js/pages/materials/sale.js"></script>
    <script type="text/javascript" src="<%=path %>/js/pages/manage/city.js"></script>
    <script>
        var kid = ${sessionScope.user.id};
        var systemName = "${sessionScope.user.username}";
        var systemNo = "${sessionScope.user.userno}";
        var path = "<%=path%>";
        var clientIp = "<%=clientIp%>";
        $(document).ready(function() {
            initProvince();
        });
    </script>
</head>
<body>
<!-- 查询 -->
<div id="searchPanel" class="easyui-panel" style="padding:3px;" title="查询窗口" iconCls="icon-search" collapsible="true"
     closable="false">
    <table id="searchTable">
        <tr>
            <td>单据编号：</td>
            <td>
                <input type="text" name="searchNumber" id="searchNumber" style="width:100px;"/>
            </td>
            <td>客户编号：</td>
            <td>
                <input type="text" name="searchCustomerNo" id="searchCustomerNo" style="width:100px;"/>
            </td>
            <td>客户名称：</td>
            <td>
                <input type="text" name="searchCustomerName" id="searchCustomerName" style="width:100px;"/>
            </td>
            <td>商品信息：</td>
            <td>
                <input type="text" name="searchMaterial" id="searchMaterial" placeholder="名称，型号" style="width:100px;"/>
            </td>
            <td id="spanTotalPrice" hidden>订单金额：</td>
            <td>
                <input type="text" class="easyui-numberbox" data-options="min:0,precision:2" name="searchTotalPrice" id="searchTotalPrice" style="width:100px;" hidden/>
            </td>
        </tr>
        <tr>
            <td>收款状态:</td>
            <td>
                <select name="searchState" id="searchState" style="width:100px; height:20px">
                </select>
            </td>
            <td>审核状态:</td>
            <td>
                <select name="searchCheck" id="searchCheck" style="width:100px; height:20px">
                </select>
            </td>
            <td>发货状态:</td>
            <td>
                <select name="searchSendStatus" id="searchSendStatus" style="width:100px; height:20px">
                </select>
            </td>
            <td>收货人：</td>
            <td>
                <input type="text" name="searchContacts" id="searchContacts" style="width:100px;"/>
            </td>
            <td>运单号码：</td>
            <td>
                <input type="text" name="searchExpressNumber" id="searchExpressNumber" style="width:100px;"/>
            </td>
        </tr>
        <tr>
            <td>下单日期：</td>
            <td>
                <input type="text" name="searchBeginTime" id="searchBeginTime"
                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="txt Wdate" style="width:150px;"/>
            </td>
            <td colspan="2">-
                <input type="text" name="searchEndTime" id="searchEndTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                       class="txt Wdate" style="width:150px;"/>
            </td>

            <td>发货日期：</td>
            <td>
                <input type="text" name="searchSendBeginTime" id="searchSendBeginTime"
                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="txt Wdate" style="width:150px;"/>
            </td>
            <td colspan="2">-
                <input type="text" name="searchSendEndTime" id="searchSendEndTime" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                       class="txt Wdate" style="width:150px;"/>
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
<div id="tablePanel" class="easyui-panel" style="padding:1px; top:300px;" title="订单列表" iconCls="icon-list"
     collapsible="true" closable="false">
    <table id="tableData" style="top:300px;border-bottom-color:#FFFFFF"></table>
</div>

<div id="depotHeadDlg" class="easyui-dialog" style="width:1200px;padding:10px 20px;top:20px"
     closed="true" buttons="#dlg-buttons" modal="true" cache="false" collapsible="false" closable="true">
    <form id="depotHeadFM" method="post" novalidate>
        <table>
            <tr>
                <td style="width:60px;">客户：</td>
                <td style="padding:5px">
                    <input id="OrganId" name="OrganId" style="width:130px;"/>
                </td>
                <td style="width:80px;">下单日期：</td>
                <td style="padding:5px">
                    <input type="text" name="OperTime" id="OperTime"
                           onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="txt Wdate"
                           style="width:140px;"/>
                </td>
                <td style="width:70px;" name="NumberLabel" id="NumberLabel">订单编号：</td>
                <td style="padding:5px">
                    <input name="Number" id="Number" class="easyui-validatebox"
                           data-options="required:true,validType:'length[2,30]'" style="width: 140px;"/>
                </td>
                <td style="width:100px;"></td>
            </tr>
            <tr>
                <td colspan="9">
                    <!-- 商品列表table -->
                    <table id="materialData" style="top:100px;border-bottom-color:#FFFFFF"></table>
                </td>
            </tr>
            <tr>
                <td colspan="9">
                    <textarea name="Remark" id="Remark" rows="2" cols="2" placeholder="暂无备注信息"
                              style="width: 1130px; height:35px;"></textarea>
                </td>
            </tr>
            <tr>
                <td>物流公司：</td>
                <td style="padding:5px">
                    <input id="ExpressCode" name="ExpressCode" class="easyui-validatebox" style="width:120px;"/>
                </td>
                <td>物流备注：</td>
                <td style="padding:5px">
                    <input id="Express" name="Express" class="easyui-validatebox" style="width:120px;"/>
                </td>
                <td>运单号码：</td>
                <td style="padding:5px">
                    <input id="ExpressNumber" name="ExpressNumber" class="easyui-validatebox" style="width:130px;"/>
                </td>
            </tr>
            <tr>
                <td>重量：</td>
                <td style="padding:5px">
                    <input id="Weight" name="Weight" class="easyui-validatebox" style="width:130px;"/>
                </td>
                <td>运费预估：</td>
                <td style="padding:5px">
                    <input id="Freight" name="Freight" data-options="required:true" class="easyui-validatebox" style="width:130px;"/>
                </td>
                <td>收货人：</td>
                <td style="padding:5px">
                    <input id="Contacts" name="Contacts" class="easyui-validatebox" style="width:120px;"/>
                </td>
                <td>收货号码：</td>
                <td style="padding:5px">
                    <input id="Phonenum" name="Phonenum" class="easyui-validatebox" style="width:120px;"/>
                </td>
            </tr>
            <tr>
                <td>所在地址：</td>
                <td style="padding:5px;width:100px;">
                    <input id="state" name="state" class="easyui-combobox" style="width:100px;"  data-options="editable:false,prompt: '请选择省份',missingMessage:'请选择省份'" />
                </td>
                <td style="padding:1px">
                    <input id="city" name="city" class="easyui-combobox" style="width:100px;"  data-options="editable:false,prompt: '请选择城市',missingMessage:'请选择城市'" />
                </td>
                <td style="padding:1px">
                    <input id="street" name="street" class="easyui-combobox" style="width:100px;"  data-options="editable:false,prompt: '请选择区县',missingMessage:'请选择区县'" />
                </td>
                <td>详细地址：</td>
                <td style="padding:1px" colspan="3">
                    <input name="address" id="address" class="easyui-validatebox" style="width: 468px;height: 20px"/>
                </td>
            </tr>

        </table>
        <input type="hidden" name="clientIp" id="clientIp" value="<%=clientIp %>"/>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" id="saveDepotHead" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="cancelDepotHead" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#depotHeadDlg').dialog('close')">取消</a>
    <a href="javascript:void(0)" id="printDepotHead" class="easyui-linkbutton" iconCls="icon-print">打印</a>
</div>
<div id="depotHeadDlgShow" class="easyui-dialog" style="width:1200px;padding:10px 20px;top:20px"
     closed="true" modal="true" cache="false" collapsible="false" closable="true">
    <table>
        <tr>
            <td style="width:60px;">客户编号：</td>
            <td style="padding:5px;width:130px;">
                <span id="OrganNoShow"></span>
            </td>
            <td style="width:60px;">客户：</td>
            <td style="padding:5px;width:130px;">
                <span id="OrganIdShow"></span>
            </td>
            <td style="width:70px;">业务经理：</td>
            <td style="padding:5px;width:130px;">
                <span id="SalesmanShow"></span>
            </td>
        </tr>
        <tr>
            <td style="width:70px;">单据编号：</td>
            <td style="padding:5px;width:140px;">
                <span id="NumberShow"></span>
            </td>
            <td style="width:80px;">下单日期：</td>
            <td style="padding:5px;width:140px;">
                <span id="OperTimeShow"></span>
            </td>

            <td style="width:80px;">发货日期：</td>
            <td style="padding:5px;width:140px;">
                <span id="SendTimeShow"></span>
            </td>
        </tr>
        <tr>
            <td colspan="9" style="width: 1130px;">
                <!-- 商品列表table -->
                <table id="materialDataShow" style="top:100px;border-bottom-color:#FFFFFF"></table>
            </td>
        </tr>
        <tr>
            <td style="width:60px;">单据备注：</td>
            <td colspan="8" style="height:35px;">
                <span id="RemarkShow" style="width: 1070px; height:35px;"></span>
            </td>
        </tr>
        <tr>
            <td style="width:60px;">物流公司：</td>
            <td style="padding:5px;width:130px;">
                <span id="ExpressShow"></span>
            </td>
            <td style="width:60px;">运单号码：</td>
            <td style="padding:5px;width:130px;">
                <span id="ExpressNumberShow"></span>
            </td>
            <td style="width:60px;">收货人：</td>
            <td style="padding:5px;width:130px;">
                <span id="ContactsShow"></span>
            </td>
            <td style="width:60px;">收货号码：</td>
            <td style="padding:5px;width:130px;">
                <span id="PhonenumShow"></span>
            </td>
        </tr>
        <tr>
            <td style="width:60px;">重量：</td>
            <td style="padding:5px;width:130px;">
                <span id="WeightShow"></span>
            </td>
            <td style="width:60px;">运费预估：</td>
            <td style="padding:5px;width:130px;">
                <span id="FreightShow"></span>
            </td>
            <td style="width:60px;">收货地址：</td>
            <td colspan="8" style="height:35px;">
                <span id="AddressShow" style="width: 1070px; height:35px;"></span>
            </td>
        </tr>
    </table>
</div>
<div id="depotHeadSendDlg" class="easyui-dialog" style="width:550px;padding:10px 20px;top:80px"
     closed="true" modal="true" buttons="#sendDlgButtons" cache="false" collapsible="false" closable="true">
    <form id="depotHeadSendFM" method="post" novalidate>
        <table>
            <tr>
                <td style="width:60px;">物流公司：</td>
                <td style="padding:5px">
                    <input id="ExpressSend" name="ExpressSend" style="width:130px;"/>
                </td>
                <td style="width:60px;">运单号码：</td>
                <td style="padding:5px">
                    <input id="ExpressNumberSend" name="ExpressNumberSend" style="width:200px;"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="sendDlgButtons">
    <a href="javascript:void(0)" id="saveDepotHeadSendDlg" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="cancelDepotHeadSendDlg" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#depotHeadSendDlg').dialog('close')">取消</a>
</div>

<!--收款账户列表 -->
<div id="receiptDlg" class="easyui-dialog" style="width:550px;padding:10px 20px;top:80px"
     closed="true" modal="true" buttons="#receiptDlgButtons" cache="false" collapsible="false" closable="true">
    <form id="receiptFM" method="post" novalidate>
        <table>
            <tr>
                <td>结算账户：</td>
                <td style="padding:5px">
                    <select name="AccountId" id="AccountId" style="width:240px;"></select>
                    <img class="many-account-ico" src="<%=path%>/js/easyui-1.3.5/themes/icons/filelist.jpg"
                         style="display: none;"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="receiptDlgButtons">
    <a href="javascript:void(0)" id="saveReceiptDlg" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <a href="javascript:void(0)" id="cancelReceiptDlg" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#receiptDlg').dialog('close')">取消</a>
</div>


<!--快递跟踪 -->
<div id="expressDlg" class="easyui-dialog" style="width:550px;padding:10px 20px;top:80px"
     closed="true" modal="true" buttons="#expressDlgButtons" cache="false" collapsible="false" closable="true">
    <form id="expressFM" method="post" novalidate>
        <table>
            <tr>
                <td style="width:60px;">运单号码：</td>
                <td style="padding:5px">
                    <span id="ExpressNumberCheckShow"></span>
                </td>
                <td style="width:60px;">物流公司：</td>
                <td style="padding:5px">
                    <span id="ExpressCompanyCheckShow"></span>
                </td>
            </tr>
            <tr>
                <td style="width:60px;">签收状态：</td>
                <td style="padding:5px">
                    <span id="ExpressStateCheckShow"></span>
                </td>
            </tr>
        </table>
        <table id="expressData" style="top:300px;border-bottom-color:#FFFFFF"></table>
    </form>
</div>
<div id="expressDlgButtons">
    <a href="javascript:void(0)" id="cancelExpressDlg" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#expressDlg').dialog('close')">取消</a>
</div>

</body>
</html>