//初始化界面
$(function () {
    initTableData();
    //初始下拉列表
    initSelectList();
    //绑定各种按钮事件
    ininPager();
    bindEvent();
});


var id = 0;
var btnEnableList = getBtnStr(); //获取按钮的权限
var delFlag = false;
//初始化表格数据
function initTableData() {
    var tableToolBar = [];
    //98 新增
    if(btnEnableList && btnEnableList.indexOf(98)>-1){
        tableToolBar.push(
            {
                id:'addExpress',
                text:'增加',
                iconCls:'icon-add',
                handler:function() {
                    addExpress();
                }
            },'-'
        );
    }
    //2 启用禁用
    if(btnEnableList && btnEnableList.indexOf(2)>-1){
        tableToolBar.push(
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
            },'-'
        );
    }

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
                var rowInfo = rec.id + 'AaBb' + rec.expressCode +'AaBb' + rec.expressName + 'AaBb' + rec.sortOrder + 'AaBb' + rec.enabled;
                if(1 == value)
                {
                    str += '<img title="编辑" src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editExpressInfo(\'' + rowInfo + '\');"/>&nbsp;&nbsp;&nbsp;';
                }
                return str;
            }
            },
            { title: '编号',field: 'expressCode',width:70},
            { title: '名称',field: 'expressName',width:100},
            { title: '状态',field: 'enabled',width:70,align:"center",formatter:function(value){
                return value? "启用":"禁用";
            }}
        ]],
        toolbar:tableToolBar,
        onLoadError:function()
        {
            $.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
            return;
        }
    });
}

//初始客户信息信息
function initExpressInfo(pageNo,pageSize){
    $.ajax({
        type: "post",
        url: path + "/express/findBy.action",
        dataType: "json",
        data: ({
            expressName : $.trim($("#searchExpressName").val()),
            expressCode : $.trim($("#searchExpressCode").val()),

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
                initExpressInfo(pageNum,pageSize);
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
    $("#saveExpress").off("click").on("click",function() {
        if(!$('#supplierFM').form('validate')){
            return;
        }
        $.ajax({
            url: url,
            type:"post",
            dataType: "json",
            data:{
                expressCode:$("#expressCode").val(),
                expressName:$("#expressName").val(),
                sortOrder:$("#sortOrder").val(),

                enabled:true,
                clientIp: clientIp
            },
            success: function(res) {
                if (res) {
                    $('#supplierDlg').dialog('close');
                    var opts = $("#tableData").datagrid('options');
                    initExpressInfo(opts.pageNumber,opts.pageSize);
                }
            }
        });
    });

    //搜索处理
    $("#searchBtn").unbind().bind({
        click:function()
        {
            initExpressInfo(1,initPageSize);
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
            $("#searchExpressName").val("");
            $("#searchExpressCode").val("");

            //加载完以后重新初始化
            $("#searchBtn").click();
        }
    });
}

//新增物流信息信息
function addExpress() {
    $('#supplierDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加物流信息');
    $(".window-mask").css({ width: webW ,height: webH});
    $("#supplier").focus();
    $('#supplierFM').form('clear');
    id = 0;
    url = path + '/express/create.action';
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
                    url: path + "/express/batchSetEnable.action",
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
                    url: path + "/express/batchSetEnable.action",
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

//初始化下拉列表信息(下拉列表)
function initSelectList() {
    //初始化 是否启用 下拉框
    $('#enabled').combobox({
        url: path +'/js/pages/manage/enabled.json',
        valueField:'value',
        textField:'name'
    });
}

//编辑客户信息信息
function editExpressInfo(expressInfo) {
    var express = expressInfo.split("AaBb");
    var enabled = "0";
    if(express[4]){
        enabled = "1";
    }
    var row = {
        expressCode : express[1],
        expressName : express[2],
        sortOrder : express[3].replace("undefined",""),
        enabled : enabled,

        clientIp: clientIp
    };
    $('#supplierDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑物流信息');
    $(".window-mask").css({ width: webW ,height: webH});
    $('#supplierFM').form('load',row);
    id = express[0];
    url = path + '/express/update.action?id=' + express[0];
}
