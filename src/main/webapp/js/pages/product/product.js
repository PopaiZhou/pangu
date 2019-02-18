
$(function(){
    initTableData();
    //初始化供应商列表
    initProductList();
    //绑定各种按钮事件
    initPager();
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
                var rowInfo = rec.id + 'AaBb' + rec.productId +'AaBb' + rec.productName + 'AaBb'+ rec.tId + 'AaBb'+ rec.standard + 'AaBb'+ rec.purchasePrice+ 'AaBb'+ rec.wholesalePrice+ 'AaBb'+ rec.retailPrice;
                if(1 == value)
                {
                    str += '<img title="编辑" src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editProductInfo(\'' + rowInfo + '\');"/>&nbsp;&nbsp;&nbsp;';
                    str += '<img title="删除" src="' + path + '/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteProduct(\'' + rowInfo + '\');"/>';
                }
                return str;
            }
            },
            { title: '编号',field: 'productId',width:100},
            { title: '名称',field: 'productName',width:150},
            { title: '所属版本编号', field: 'templateId',width:100,align:"center"},
            { title: '所属版本名称', field: 'templateName',width:100,align:"center"},
            { title: '进货价', field: 'purchasePrice',width:100},
            { title: '批发商', field: 'wholesalePrice',width:100},
            { title: '零售商', field: 'retailPrice',width:100},
            { title: '所属供应商', field: 'supplier',width:300,align:"center"},
            { title: '规格', field: 'standard',width:200,align:"center"}

        ]],
        toolbar:[
            {
                id:'addProduct',
                text:'增加',
                iconCls:'icon-add',
                handler:function() {
                    addProduct();
                }
            },'-',
            {
                id:'deleteProduct',
                text:'删除',
                iconCls:'icon-remove',
                handler:function() {
                    batDeleteProduct();
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


//初始化产品型号信息
function initProductInfo(pageNo,pageSize){
    $.ajax({
        type: "post",
        url: path + "/product/findBy.action",
        dataType: "json",
        data: ({
            productId : $.trim($("#searchNumber").val()),
            productName : $.trim($("#searchName").val()),
            templateId : $.trim($("#searchTemplate").datebox("getValue")),
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
function initPager() {
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
                initProductInfo(pageNum,pageSize);
            }
        });
    }
    catch (e)
    {
        $.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
    }
}

//新增产品信息
function addProduct() {
    $('#supplierDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加产品信息');
    $(".window-mask").css({ width: webW ,height: webH});
    $("#supplier").focus();
    $('#supplierFM').form('clear');
    id = 0;
    url = path + '/product/create.action';
}
//批量删除产品
function batDeleteProduct() {
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
                url: path + "/product/batchDelete.action",
                dataType: "json",
                async :  false,
                data: ({
                    productIds : ids,
                    clientIp: clientIp
                }),
                success: function (tipInfo)
                {
                    var msg = tipInfo.showModel.msgTip;
                    if(msg == '成功')
                    {
                        $.messager.alert('提示','删除产品型号成功！','info');
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

//初始化产品型号列表信息(下拉列表)
function initProductList() {
    $('#searchTemplate').combobox({
        url: path + "/template/findBySelect_temp.action",
        valueField:'id',
        textField:'templateName'
    });
    $('#templateId').combobox({
        url: path + "/template/findBySelect_temp.action",
        valueField:'id',
        textField:'templateName'
    });
}
//绑定各种按钮事件
function bindEvent(){
    //保存按钮信息
    $("#saveProduct").off("click").on("click",function() {
        if(!$('#supplierFM').form('validate')){
            return;
        }
        else if(checkProductId()){
            return;
        }

        $.ajax({
            url: url,
            type:"post",
            dataType: "json",
            data:{
                productId : $("#productId").val(),
                productName : $("#productName").val(),
                templateId : $("#templateId").datebox("getValue"),
                standard : $("#standard").val(),
                purchasePrice : $("#purchasePrice").val(),
                wholesalePrice : $("#wholesalePrice").val(),
                retailPrice : $("#retailPrice").val(),
                enabled : 1,
                clientIp : clientIp
            },
            success: function(res) {
                if (res) {
                    $('#supplierDlg').dialog('close');
                    var opts = $("#tableData").datagrid('options');
                    initProductInfo(opts.pageNumber,opts.pageSize);
                }
            }
        });
    });

    //搜索处理
    $("#searchBtn").unbind().bind({
        click:function()
        {
            initProductInfo(1,initPageSize);
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
            $("#searchNumber").val("");
            $("#searchName").val("");
            $("#searchTemplate").combobox("setValue","");

            //加载完以后重新初始化
            $("#searchBtn").click();
        }
    });
}
//检查产品编号
function checkProductId() {
    var productId = $.trim($("#productId").val());
    //表示是否存在 true == 存在 false = 不存在
    var flag = false;
    //开始ajax名称检验，不能重名
    if(productId.length > 0)
    {
        $.ajax({
            type:"post",
            url: path + "/product/checkIsProductIdExist.action",
            dataType: "json",
            async :  false,
            data: ({
                id : id,
                productId : productId
            }),
            success: function (tipInfo)
            {
                flag = tipInfo;
                if(tipInfo)
                {
                    $.messager.alert('提示','产品编号已经存在','info');
                    return;
                }
            },
            //此处添加错误处理
            error:function()
            {
                $.messager.alert('提示','检查产品编号是否重复异常，请稍后再试！','error');
                return;
            }
        });
    }
    return flag;
}

//编辑产品信息
function editProductInfo(productInfo) {
    var product = productInfo.split("AaBb");
    var row = {
        productId : product[1],
        productName : product[2],
        templateId : product[3],
        standard : product[4].replace("undefined",""),
        purchasePrice : product[5].replace("undefined","0"),
        wholesalePrice : product[6].replace("undefined","0"),
        retailPrice : product[7].replace("undefined","0"),
        clientIp: clientIp
    };
    $('#supplierDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑产品型号信息');
    $(".window-mask").css({ width: webW ,height: webH});
    $('#supplierFM').form('load',row);
    id = product[0];
    url = path + '/product/update.action?id=' + product[0];
}
//单个删除按钮
function deleteProduct(productInfo) {
    $.messager.confirm('删除确认','确定要删除此条信息吗？',function(r){
        if (r){
            var product = productInfo.split("AaBb");
            $.ajax({
                type:"post",
                url: path + "/product/delete.action",
                dataType: "json",
                data: ({
                    id : product[0],
                    clientIp: clientIp
                }),
                success: function (tipInfo)
                {
                    var msg = tipInfo.showModel.msgTip;
                    if(msg == '成功'){
                        //加载完以后重新初始化
                        $.messager.alert('提示','删除产品型号信息成功！','info');
                        $("#searchBtn").click();
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
        }
    });
}