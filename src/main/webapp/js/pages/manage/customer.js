//初始化界面
$(function () {
    initTableData();
    //初始化销售员列表
    initSalesList();
    //绑定各种按钮事件
    ininPager();
    bindEvent();
});


var id = 0;
//初始化表格数据
function initTableData() {
    //改变宽度和高度
    $("#searchPanel").panel({width:webW-2});
    $("#tablePanel").panel({width:webW-2});
    $('#tableData').datagrid({
        //title:'单位列表',
        //iconCls:'icon-save',
        //width:700,
        height:heightInfo,
        nowrap: false,
        rownumbers: false,
        //动画效果
        animate:false,
        //选中单行
        singleSelect : true,
        collapsible:false,
        selectOnCheck:false,
        //fitColumns:true,
        //单击行是否选中
        checkOnSelect : false,
        //交替出现背景
        striped : true,
        pagination: true,
        //自动截取数据
        //nowrap : true,
        //loadFilter: pagerFilter,
        pageSize: initPageSize,
        pageList: initPageNum,
        columns:[[
            { field: 'id',width:35,align:"center",checkbox:true},
            { title: '操作',field: 'op',align:"center",width:60,formatter:function(value,rec)
            {
                var str = '';
                var rowInfo = rec.id + 'AaBb' + rec.customerNo +'AaBb' + rec.customerName + 'AaBb' + rec.customerShort + 'AaBb' + rec.contacts +  'AaBb'+ rec.phonenum + 'AaBb'+ rec.email + 'AaBb'+ rec.description
                    + 'AaBb'+ rec.telephone + 'AaBb' + rec.qq + 'AaBb' + rec.express+ 'AaBb' + rec.address
                    + 'AaBb' + rec.taxNum + 'AaBb' + rec.bankName + 'AaBb' + rec.accountNumber + 'AaBb' + rec.taxRate
                    + 'AaBb' + rec.state + 'AaBb' + rec.city + 'AaBb' + rec.street + 'AaBb' + rec.type + 'AaBb' + rec.userId + 'AaBb' + rec.userName + 'AaBb' + rec.typeId
                    + 'AaBb' + rec.isystem + 'AaBb' + rec.enabled;
                if(1 == value)
                {
                    str += '<img title="编辑" src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editCustomerInfo(\'' + rowInfo + '\');"/>&nbsp;&nbsp;&nbsp;';
                    str += '<img title="删除" src="' + path + '/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteCustomer(\'' + rowInfo + '\');"/>';
                }
                return str;
            }
            },
            { title: '编号',field: 'customerNo',width:70},
            { title: '名称',field: 'customerName',width:200},
            { title: '联系人', field: 'contacts',width:70,align:"center"},
            { title: '联系电话', field: 'phonenum',width:100,align:"center"},
            { title: '电子邮箱',field: 'email',width:120,align:"center"},
            { title: '类型', field: 'type',width:100,align:"center"},
            { title: '业务经理',field: 'userName',width:70,align:"center"},
            { title: 'QQ', field: 'qq',width:100,align:"center"},
            { title: '默认物流',field: 'express',width:70,align:"center"},
            { title: '状态',field: 'enabled',width:70,align:"center",formatter:function(value){
                return value? "启用":"禁用";
            }}
        ]],
        toolbar:[
            {
                id:'addCustomer',
                text:'增加',
                iconCls:'icon-add',
                handler:function() {
                    addCustomer();
                }
            },'-',
            {
                id:'deleteCustomer',
                text:'删除',
                iconCls:'icon-remove',
                handler:function() {
                    batDeleteCustomer();
                }
            },'-',
            {
                id:'setEnable',
                text:'启用',
                iconCls:'icon-ok',
                handler:function() {
                    setEnableFun();
                }
            },'-',
            {
                id:'setDisEnable',
                text:'禁用',
                iconCls:'icon-no',
                handler:function() {
                    setDisEnableFun();
                }
            },'-',
            {
                id:'setOutput',
                text:'导出',
                iconCls:'icon-excel',
                handler:function() {
                    setOutputFun();
                }
            }
        ],
        onLoadError:function()
        {
            $.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
            return;
        }
    });
}

//初始客户信息信息
function initCustomerInfo(pageNo,pageSize){
    $.ajax({
        type: "post",
        url: path + "/customer/findBy.action",
        dataType: "json",
        data: ({
            customerNo : $.trim($("#searchCustomerNo").val()),
            customerName : $.trim($("#searchCustomerName").val()),
            address : $.trim($("#searchAddress").val()),
            phonenum : $.trim($("#searchPhonenum").val()),
            type: $("#searchType").datebox("getValue"),
            userId : $.trim($("#searchBasicuser").datebox("getValue")),

            state : $.trim($("#searchState").datebox("getText")),
            city : $.trim($("#searchCity").datebox("getText")),
            
            pageNo:pageNo,
            pageSize:pageSize
        }),
        success: function (res) {
            if (res && res.rows) {
                $("#tableData").datagrid('loadData',res);
            }
        },
        //此处添加错误处理
        error:function() {
            $.messager.alert('查询提示','查询信息异常，请稍后再试！','error');
            return;
        }
    });
}

//分页信息处理
function ininPager() {
    try
    {
        var opts = $("#tableData").datagrid('options');
        var pager = $("#tableData").datagrid('getPager');
        pager.pagination({
            onSelectPage:function(pageNum, pageSize)
            {
                opts.pageNumber = pageNum;
                opts.pageSize = pageSize;
                pager.pagination('refresh',
                    {
                        pageNumber:pageNum,
                        pageSize:pageSize
                    });
                initCustomerInfo(pageNum,pageSize);
            }
        });
    }
    catch (e)
    {
        $.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
    }
}

//绑定各种按钮事件
function bindEvent(){
    //保存按钮信息
    $("#saveCustomer").off("click").on("click",function() {
        if(!$('#supplierFM').form('validate')){
            return;
        }
        else if(checkCustomerNo()){
            return;
        }
        $.ajax({
            url: url,
            type:"post",
            dataType: "json",
            data:{
                customerNo:$("#customerNo").val(),
                customerName:$("#customerName").val(),
                customerShort:$("#customerShort").val(),
                telephone:$("#telephone").val(),
                phonenum:$("#phonenum").val(),
                contacts:$("#contacts").val(),
                qq:$("#qq").val(),
                email:$("#email").val(),
                taxRate:$("#taxRate").val(),
                taxNum:$("#taxNum").val(),
                bankName:$("#bankName").val(),
                accountNumber:$("#accountNumber").val(),
                express:$("#express").val(),
                type:$("#type").combobox('getValue'),
                userId:$("#basicUser").combobox('getValue'),
                text:$("#text").val(),
                state:$('#state').combobox('getText'),
                city:$('#city').combobox('getText'),
                street:$('#street').combobox('getText'),
                address:$('#address').val(),
                description:$("#description").val(),
                enabled:true,
                clientIp: clientIp
            },
            success: function(res) {
                if (res) {
                    $('#supplierDlg').dialog('close');
                    var opts = $("#tableData").datagrid('options');
                    initCustomerInfo(opts.pageNumber,opts.pageSize);
                }
            }
        });
    });

    //搜索处理
    $("#searchBtn").unbind().bind({
        click:function()
        {
            initCustomerInfo(1,initPageSize);
            var opts = $("#tableData").datagrid('options');
            var pager = $("#tableData").datagrid('getPager');
            opts.pageNumber = 1;
            opts.pageSize = initPageSize;
            pager.pagination('refresh',
                {
                    pageNumber:1,
                    pageSize:initPageSize
                });
        }
    });

    $("#searchBtn").click();

    //重置按钮
    $("#searchResetBtn").unbind().bind({
        click:function(){
            $("#searchCustomerNo").val("");
            $("#searchCustomerName").val("");
            $("#searchAddress").val("");
            $("#searchPhonenum").val("");
            $("#searchType").combobox("setValue","");
            $("#searchBasicuser").combobox("setValue","");

            $("#searchState").combobox("setValue","");
            $("#searchCity").combobox("setValue","");

            //加载完以后重新初始化
            $("#searchBtn").click();
        }
    });
}

//新增客户信息信息
function addCustomer() {
    $('#supplierDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加客户信息');
    $(".window-mask").css({ width: webW ,height: webH});
    $("#supplier").focus();
    $('#supplierFM').form('clear');
    id = 0;
    url = path + '/customer/create.action';
}
//批量删除
function batDeleteCustomer() {
    var row = $('#tableData').datagrid('getChecked');
    if(row.length == 0)
    {
        $.messager.alert('删除提示','没有记录被选中！','info');
        return;
    }
    if(row.length > 0){
        $.messager.confirm('删除确认','确定要删除选中的' + row.length + '条信息吗？',function(r){
            if (r){
                var ids = "";
                for(var i = 0;i < row.length; i ++)
                {
                    if(i == row.length-1)
                    {
                        ids += row[i].id;
                        break;
                    }
                    ids += row[i].id + ",";
                }
            }
            $.ajax({
                type:"post",
                url: path + "/customer/batchDelete.action",
                dataType: "json",
                async :  false,
                data: ({
                    batchDeleteIds : ids,
                    clientIp: clientIp
                }),
                success: function (tipInfo)
                {
                    var msg = tipInfo.showModel.msgTip;
                    if(msg == '成功')
                    {
                        $.messager.alert('提示','删除客户信息成功！','info');
                        //加载完以后重新初始化
                        $("#searchBtn").click();
                        $(":checkbox").attr("checked",false);
                    }
                    else{
                        $.messager.alert('删除提示','删除信息失败，请稍后再试！','error');
                    }
                },
                //此处添加错误处理
                error:function()
                {
                    $.messager.alert('删除提示','删除信息异常，请稍后再试！','error');
                    return;
                }
            });
        });
    }
}
//批量启用
function setEnableFun() {
    var row = $('#tableData').datagrid('getChecked');
    if(row.length == 0)
    {
        $.messager.alert('启用提示','没有记录被选中！','info');
        return;
    }
    if(row.length > 0)
    {
        $.messager.confirm('启用确认','确定要启用选中的' + row.length + '条信息吗？',function(r)
        {
            if (r)
            {
                var ids = "";
                for(var i = 0;i < row.length; i ++)
                {
                    if(i == row.length-1)
                    {
                        ids += row[i].id;
                        break;
                    }
                    ids += row[i].id + ",";
                }
                $.ajax({
                    type:"post",
                    url: path + "/customer/batchSetEnable.action",
                    dataType: "json",
                    async :  false,
                    data: ({
                        enabled: true,
                        batchDeleteIds : ids,
                        clientIp: clientIp
                    }),
                    success: function (tipInfo)
                    {
                        var msg = tipInfo.showModel.msgTip;
                        if(msg == '成功')
                        {
                            //加载完以后重新初始化
                            $("#searchBtn").click();
                            $(":checkbox").attr("checked",false);
                        }
                        else
                            $.messager.alert('启用提示','启用信息失败，请稍后再试！','error');
                    },
                    //此处添加错误处理
                    error:function()
                    {
                        $.messager.alert('启用提示','启用信息异常，请稍后再试！','error');
                        return;
                    }
                });
            }
        });
    }
}
//批量禁用
function setDisEnableFun() {
    var row = $('#tableData').datagrid('getChecked');
    if(row.length == 0)
    {
        $.messager.alert('禁用提示','没有记录被选中！','info');
        return;
    }
    if(row.length > 0)
    {
        $.messager.confirm('禁用确认','确定要禁用选中的' + row.length + '条信息吗？',function(r)
        {
            if (r)
            {
                var ids = "";
                for(var i = 0;i < row.length; i ++)
                {
                    if(i == row.length-1)
                    {
                        ids += row[i].id;
                        break;
                    }
                    ids += row[i].id + ",";
                }
                $.ajax({
                    type:"post",
                    url: path + "/customer/batchSetEnable.action",
                    dataType: "json",
                    async :  false,
                    data: ({
                        enabled: false,
                        batchDeleteIds : ids,
                        clientIp: clientIp
                    }),
                    success: function (tipInfo)
                    {
                        var msg = tipInfo.showModel.msgTip;
                        if(msg == '成功')
                        {
                            //加载完以后重新初始化
                            $("#searchBtn").click();
                            $(":checkbox").attr("checked",false);
                        }
                        else
                            $.messager.alert('禁用提示','禁用信息失败，请稍后再试！','error');
                    },
                    //此处添加错误处理
                    error:function()
                    {
                        $.messager.alert('禁用提示','禁用信息异常，请稍后再试！','error');
                        return;
                    }
                });
            }
        });
    }
}

//导出数据
function setOutputFun(){
    window.location.href = path + "/customer/exportExcel.action?browserType=" + getOs() + "&type=Customer";
}

//检查客户编号
function checkCustomerNo() {
    var customerNo = $.trim($("#customerNo").val());
    //表示是否存在 true == 存在 false = 不存在
    var flag = false;
    //开始ajax名称检验，不能重名
    if(customerNo.length > 0)
    {
        $.ajax({
            type:"post",
            url: path + "/customer/checkIsCustomerNoExist.action",
            dataType: "json",
            async :  false,
            data: ({
                id : id,
                customerNo : customerNo
            }),
            success: function (tipInfo)
            {
                flag = tipInfo;
                if(tipInfo)
                {
                    $.messager.alert('提示','客户编号已经存在','info');
                    return;
                }
            },
            //此处添加错误处理
            error:function()
            {
                $.messager.alert('提示','检查客户编号是否重复异常，请稍后再试！','error');
                return;
            }
        });
    }
    return flag;
}
//初始化业务经理列表信息(下拉列表)
function initSalesList() {
    $('#searchBasicuser').combobox({
        url: path + "/customer/findBySelect_temp.action",
        valueField:'id',
        textField:'sales'
    });
    $('#basicUser').combobox({
        url: path + "/customer/findBySelect_temp.action",
        valueField:'id',
        textField:'sales'
    });
    //初始化用户类型下拉框
    $('#searchType').combobox({
        url: path +'/js/pages/manage/customerType.json',
        valueField:'value',
        textField:'name'
    });
    $('#type').combobox({
        url: path +'/js/pages/manage/customerType.json',
        valueField:'value',
        textField:'name'
    });
}

//编辑客户信息信息
function editCustomerInfo(customerInfo) {
    var customer = customerInfo.split("AaBb");
    var row = {
        customerNo : customer[1],
        customerName : customer[2],
        customerShort : customer[3].replace("undefined",""),
        contacts : customer[4].replace("undefined",""),
        phonenum : customer[5].replace("undefined",""),
        email : customer[6].replace("undefined",""),
        description : customer[7].replace("undefined",""),
        telephone : customer[8].replace("undefined",""),
        qq : customer[9].replace("undefined",""),
        express : customer[10].replace("undefined",""),
        address : customer[11].replace("undefined",""),
        taxNum : customer[12].replace("undefined",""),
        bankName : customer[13].replace("undefined",""),
        accountNumber : customer[14].replace("undefined",""),
        taxRate : customer[15].replace("undefined",""),
        state : customer[16].replace("undefined",""),
        city : customer[17].replace("undefined",""),
        street : customer[18].replace("undefined",""),

        basicUser : customer[20].replace("undefined",""),
        type : customer[22].replace("undefined",""),
        enabled : true,
        clientIp: clientIp
    };
    $('#supplierDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑客户信息信息');
    $(".window-mask").css({ width: webW ,height: webH});
    $('#supplierFM').form('load',row);
    id = customer[0];
    url = path + '/customer/update.action?id=' + customer[0];
}

//单个删除客户信息
function deleteCustomer(customerInfo) {
    $.messager.confirm('删除确认','确定要删除此条信息吗？',function(r) {
        if (r) {
            var customer = customerInfo.split("AaBb");
            $.ajax({
                type: "post",
                url: path + "/customer/delete.action",
                dataType: "json",
                data: ({
                    id: customer[0],
                    customerId: customer[1],
                    customerName: customer[2].replace("undefined", ""),
                    clientIp: clientIp
                }),
                success: function (tipInfo) {
                    var msg = tipInfo.showModel.msgTip;
                    if (msg == '成功') {
                        //加载完以后重新初始化
                        $.messager.alert('提示', '删除客户信息成功！', 'info');
                        $("#searchBtn").click();
                    }
                    else {
                        $.messager.alert('删除提示', '删除信息失败，请稍后再试！', 'error');
                    }
                },
                //此处添加错误处理
                error: function () {
                    $.messager.alert('删除提示', '删除信息异常，请稍后再试！', 'error');
                    return;
                }
            });
        }
    });
}