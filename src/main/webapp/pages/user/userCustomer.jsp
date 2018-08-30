<%@page import="com.jsh.util.Tools" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String clientIp = Tools.getLocalIp(request);
%>
<!DOCTYPE html>
<html>
<head>
    <title>用户对应客户</title>
    <meta charset="utf-8">
    <!-- 指定以IE8的方式来渲染 -->
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
    <link rel="shortcut icon" href="<%=path%>/images/favicon.ico" type="image/x-icon"/>
    <script type="text/javascript" src="<%=path%>/js/jquery-1.8.0.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path%>/js/easyui-1.3.5/themes/default/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>/js/easyui-1.3.5/themes/icon.css"/>
    <link type="text/css" rel="stylesheet" href="<%=path%>/css/common.css"/>
    <script type="text/javascript" src="<%=path%>/js/easyui-1.3.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=path%>/js/easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=path%>/js/common/common.js"></script>
</head>
<body>
<!-- 数据显示table -->
<div style="padding-bottom: 10px;">
    <a id="btnOK" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
    <td>业务经理</td> &nbsp; &nbsp;<input type="text" name="basicUser" id="basicUser" style="width:120px" editable="false"/>
</div>
<div>
    <ul id="tt"></ul>
</div>

<script type="text/javascript">
    var url_id = getUrlParam('id');  //获取传值id（用户id）
    var type = "UserCustomer";
    var url;//定义链接地址
    function GetNode(ctype) {
        var node = $('#tt').tree('getChecked');
        var cnodes = '';
        var pnodes = '';

        var prevNode = ''; //保存上一步所选父节点
        for (var i = 0; i < node.length; i++) {

            if ($('#tt').tree('isLeaf', node[i].target)) {
                cnodes += node[i].id + ',';

                var pnode = $('#tt').tree('getParent', node[i].target); //获取当前节点的父节点
                if (prevNode != pnode.id) //保证当前父节点与上一次父节点不同
                {
                    pnodes +=  pnode.id + ',';
                    prevNode = pnode.id; //保存当前节点
                }
            }
        }
        cnodes = cnodes.substring(0, cnodes.length - 1);
        pnodes = pnodes.substring(0, pnodes.length - 1);

        if (ctype == 'child') {
            return cnodes;
        }
        else {
            return pnodes
        }
        ;
    };

    //初始化业务经理列表信息(下拉列表)
    function initSalesList() {
        $('#basicUser').combobox({
            url: '<%=path%>/customer/findBySelect_temp.action',
            valueField:'id',
            textField:'sales'
        });
    }

    $(function () {
        initSalesList();
        $('#tt').tree({
            url: '<%=path%>/customer/findUserCustomer.action?userId=' + url_id,
            animate: true,
            checkbox: true
        });


        $("#btnOK").click(
            function () {
                var newUserId = $.trim($("#basicUser").datebox("getValue"));
                if(newUserId == ''){
                    $.messager.alert('选择提示','请选择需要转移的业务员！','info');
                    return;
                }
                if(newUserId == url_id){
                    $.messager.alert('选择提示','不能进行自身转移！','info');
                    return;
                }
                if (confirm("您确定要保存吗？")) {

                    $.ajax({
                        type: "post",
                        url: "<%=path %>/customer/batchTransCustomer.action",
                        data: {
                            oldUserId : url_id,
                            newUserId : newUserId,
                            customerIds : GetNode('child'),
                            clientIp: '<%=clientIp %>'
                        },
                        dataType: "json",
                        async: false,
                        success: function (tipInfo) {
                            if (tipInfo) {
                                self.parent.$.colorbox.close();
                                alert("操作成功！");
                            }
                            else
                                alert(tipInfo);
                        }
                    });
                }
            }
        );

    });
</script>
</body>
</html>