
$(function(){
    initTableData();
    ininPager();
    initTemplateInfo();
    //初始化供应商列表
    initSupplierList();
    //绑定各种按钮事件
    bindEvent();
});


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
                var rowInfo = rec.id + 'AaBb' + rec.supplier +'AaBb' + rec.contacts + 'AaBb'+ rec.phonenum + 'AaBb'+ rec.email + 'AaBb'+ rec.BeginNeedGet + 'AaBb'+ rec.BeginNeedPay + 'AaBb' + rec.isystem + 'AaBb' + rec.description+ 'AaBb' + rec.type
                    + 'AaBb' + rec.fax + 'AaBb' + rec.telephone + 'AaBb' + rec.address + 'AaBb' + rec.taxNum + 'AaBb' + rec.bankName + 'AaBb' + rec.accountNumber + 'AaBb' + rec.taxRate + 'AaBb' + rec.state + 'AaBb' + rec.city + 'AaBb' + rec.street
                    + 'AaBb' + rec.supplierNo + 'AaBb' + rec.supplierShort;
                if(1 == value)
                {
                    str += '<img title="编辑" src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editTemplateInfo(\'' + rowInfo + '\');"/>&nbsp;&nbsp;&nbsp;';
                    str += '<img title="删除" src="' + path + '/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteTemplate(\'' + rowInfo + '\');"/>';
                }
                return str;
            }
            },
            { title: '编号',field: 'templateId',width:100},
            { title: '名称',field: 'templateName',width:200},
            { title: '上市日期', field: 'listingDate',width:100,align:"center"},
            { title: '供应商编号', field: 'supplierNo',width:150,align:"center"},
            { title: '备注', field: 'remarks',width:512,align:"center"}
        ]],
        toolbar:[
            {
                id:'addTemplate',
                text:'增加',
                iconCls:'icon-add',
                handler:function() {
                    addSuppler();
                }
            },'-',
            {
                id:'deleteTemplate',
                text:'删除',
                iconCls:'icon-remove',
                handler:function() {
                    batDeleteTemplate();
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


//初始化版本册信息
function initTemplateInfo(pageNo,pageSize){
    $.ajax({
        type: "post",
        url: path + "/template/findBy.action",
        dataType: "json",
        data: ({
            pageNo:pageNo,
            pageSize:pageSize
        }),
        success: function (res) {
            if (res && res.rows) {
                $("#tableData").datagrid('loadData',res.rows);
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
                initTemplateInfo(pageNum,pageSize);
            }
        });
    }
    catch (e)
    {
        $.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
    }
}

//新增模版信息
function addSuppler() {
    $('#supplierDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加版本册信息');
    $(".window-mask").css({ width: webW ,height: webH});
    $("#supplier").focus();
    $('#supplierFM').form('clear');
    orgSupplier = "";
    supplierID = 0;
    url = path + '/template/create.action';
}
//批量删除版本册
function batDeleteTemplate() {
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
                url: path + "/template/batchDelete.action",
                dataType: "json",
                async :  false,
                data: ({
                    templateIds : ids,
                    clientIp: clientIp
                }),
                success: function (tipInfo)
                {
                    var msg = tipInfo.showModel.msgTip;
                    if(msg == '成功')
                    {
                        $.messager.alert('提示','删除版本册成功！','info');
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

//初始化供应商列表信息(下拉列表)
function initSupplierList() {
    $('#searchSupplier').combobox({
        url: path + "/supplier/findBySelect_sup.action",
        valueField:'id',
        textField:'supplier'
    });
    $('#supplierNo').combobox({
        url: path + "/supplier/findBySelect_sup.action",
        valueField:'id',
        textField:'supplier'
    });
}
//绑定各种按钮事件
function bindEvent(){
    //保存信息
    $("#saveTemplate").off("click").on("click",function() {
        if(!$('#supplierFM').form('validate')){
            return;
        }
        else if(checkTemplateId()){
            return;
        }
        var listingDate = $("#listingDate").datebox("getValue");
        if(!IsDate(listingDate)){
            $.messager.alert('提示','上市日期格式不正确','info');
            return;
        }
        $.ajax({
            url: url,
            type:"post",
            dataType: "json",
            data:{
                templateId : $("#templateId").val(),
                templateName : $("#templateName").val(),
                listingDate : listingDate,
                supplierNo : $("#supplierNo").datebox("getValue"),
                remarks : $("#remarks").val(),
                enabled : 1,
                clientIp : clientIp
            },
            success: function(res) {
                if (res) {
                    $.messager.alert('提示','新增版本册成功！','info');
                    $('#supplierDlg').dialog('close');
                    var opts = $("#tableData").datagrid('options');
                    initTemplateInfo(opts.pageNumber,opts.pageSize);
                }
            }
        });
    });
}
//检查版本册编号
function checkTemplateId() {
    var templateId = $.trim($("#templateId").val());
    //表示是否存在 true == 存在 false = 不存在
    var flag = false;
    //开始ajax名称检验，不能重名
    if(templateId.length > 0)
    {
        $.ajax({
            type:"post",
            url: path + "/template/checkIsTemplateIdExist.action",
            dataType: "json",
            async :  false,
            data: ({
                id : 0,
                templateId : templateId
            }),
            success: function (tipInfo)
            {
                flag = tipInfo;
                if(tipInfo)
                {
                    $.messager.alert('提示','版本册编号已经存在','info');
                    return;
                }
            },
            //此处添加错误处理
            error:function()
            {
                $.messager.alert('提示','检查版本册编号是否重复异常，请稍后再试！','error');
                return;
            }
        });
    }
    return flag;
}