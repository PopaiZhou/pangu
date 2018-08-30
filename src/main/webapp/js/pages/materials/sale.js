var amountNum = "SLKSQ"; //单据编号开头字符
var defaultAccountId = 0; //默认账户id
var mPropertyList = ""; //商品属性列表
var editIndex = undefined;
var customerId = "";//客户编号
var customerType = "";//客户类型
var Salesman = "";//所属业务员
var depotString = ""; //店铺id列表
var depotHeadID = 0;
var depotHeadMaxId=null; //获取最大的Id
var listType = '下单';
var listSubType = '销售';
var preTotalPrice = 0; //前一次加载的金额
var oldNumber = ""; //编辑前的单据编号
var oldId = 0; //编辑前的单据Id
var accountList; //账户列表
var url;
var isChangePrice = false;//是否可以修改价格 true表示可以更改
var btnEnableList = getBtnStr(); //获取按钮的权限
var modifyShow = false; //单条编辑按钮是否展示
var deleteShow = false; //单条删除按钮是否暂时
var opeWith = 45;
var mulSelete = false; //是否多选
var priceHidden = false; //是否隐藏价格

$(function(){
    //显示保存按钮
    $("#saveDepotHead").show();
    //隐藏打印按钮
    $("#printDepotHead").hide();
    
    //获取角色权限--判断是否是管理员登录
    //getRole();
    initTableData();
    //初始化客户信息下拉列表
    initCustomerList();
    //初始化账户信息
    initSystemData_account();
    //初始化账户信息
    initSelectInfo_account();
    //绑定操作事件
    bindEvent();
});

//初始化表格数据
function initTableData() {
    var tableToolBar = [];
    // 7是新增删除
    if(btnEnableList && btnEnableList.indexOf(7)>-1){
        modifyShow = true;
        deleteShow = true;
        opeWith = 90;
        tableToolBar.push(
        {
            id:'addTemplate',
                text:'增加',
            iconCls:'icon-add',
            handler:function() {
            addDepotHead();
        }
        },'-',
        {
            id:'deleteTemplate',
            text:'删除',
            iconCls:'icon-remove',
            handler:function() {
                batDeleteDepotHead();
            }
        },'-');
    }
    //6是收款退款
    if(btnEnableList && btnEnableList.indexOf(6)>-1){
        //根据订单金额查询
        $("#spanTotalPrice").show();
        $("#searchTotalPrice").show();
        tableToolBar.push(
            {
                id:'receipt',
                text:'收款',
                iconCls:'icon-ok',
                handler:function() {
                    receipt();
                }
            },'-',
            {
                id:'refund',
                text:'退款',
                iconCls:'icon-undo',
                handler:function() {
                    refund();
                }
            },'-'
        );
    }
    //如果允许的按钮列表中存在就显示，3-代表审核|反审核的权限
    if(btnEnableList && btnEnableList.indexOf(3)>-1){
        tableToolBar.push({
                id:'okDepotHead',
                text:'审核',
                iconCls:'icon-ok',
                handler:function() {
                    setStatusFun();
                }
            },'-',
            {
                id:'undoDepotHead',
                text:'反审核',
                iconCls:'icon-undo',
                handler:function() {
                    setUnStatusFun();
                }
            },'-');
    }
    //9是打印出库单
    if(btnEnableList && btnEnableList.indexOf(8)>-1){
        mulSelete = true;
        priceHidden = true;
        tableToolBar.push(
            {
                id:'printOut',
                text:'打印出库单',
                iconCls:'icon-print',
                handler:function() {
                    printOut();
                }
            },'-'
        );
    }
    //8是发货
    if(btnEnableList && btnEnableList.indexOf(8)>-1){
        mulSelete = true;
        priceHidden = true;
        tableToolBar.push(
            {
                id:'ship',
                text:'发货',
                iconCls:'icon-send2',
                handler:function() {
                    ship();
                }
            },'-'
        );
    }
    //4是打印
    if(btnEnableList && btnEnableList.indexOf(4)>-1){
        tableToolBar.push(
            {
                id:'print',
                text:'打印',
                iconCls:'icon-print',
                handler:function() {
                    print();
                }
            },'-'
        );
    }
    //10是更改价格权限
    if(btnEnableList && btnEnableList.indexOf(10)>-1){
        isChangePrice = true;
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
        //fitColumns:true,

        //单击行是否选中 这2个控制是否可以多选
        checkOnSelect : mulSelete,
        selectOnCheck : mulSelete,

        //交替出现背景
        striped : true,
        pagination: true,
        //自动截取数据
        //nowrap : true,
        //loadFilter: pagerFilter,
        pageSize: initPageSize,
        pageList: initPageNum,
        columns:[[
            { field: 'Id',width:35,align:"center",checkbox:true},
            { title: '操作',field: 'op',align:"center",width:opeWith,
                formatter:function(value,rec) {
                    var str = '';
                    var rowInfo = rec.Id + 'AaBb' + rec.Number+ 'AaBb' + rec.OperTime+ 'AaBb'
                        + rec.OrganId+ 'AaBb' + rec.Remark + 'AaBb' + rec.OrganName+ 'AaBb' + rec.TotalPrice + 'AaBb' + rec.Salesman + 'AaBb' + rec.SalesmanId
                        + 'AaBb' + rec.Express + 'AaBb' + rec.ExpressNumber + 'AaBb' + rec.Contacts + 'AaBb' + rec.Phonenum + 'AaBb'
                        + rec.state + 'AaBb' + rec.city + 'AaBb' + rec.street + 'AaBb' + rec.address + 'AaBb' + rec.Weight + 'AaBb' + rec.Freight + 'AaBb' + rec.CreateTime;
                    if(1 == value) {
                        var orgId = rec.OrganId? rec.OrganId:0;
                        str += '<img title="查看" src="' + path + '/js/easyui-1.3.5/themes/icons/list.png" style="cursor: pointer;" onclick="showDepotHead(\'' + rowInfo + '\');"/>&nbsp;&nbsp;&nbsp;';
                        if(modifyShow){
                            str += '<img title="编辑" src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editDepotHead(\'' + rowInfo + '\''+',' + rec.Status + ');"/>&nbsp;&nbsp;&nbsp;';
                        }
                        if(deleteShow){
                            str += '<img title="删除" src="' + path + '/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteDepotHead('+ rec.Id +',' + orgId +',' + rec.TotalPrice+',' + rec.Status + ');"/>';
                        }
                    }
                    return str;
                }
            },
            { title: '客户名称', field: 'OrganName',width:120},
            { title: '单据编号',field: 'Number',width:130},
            { title: '商品信息',field: 'MaterialsList',width:230,formatter:function(value){
                return value.replace(/,/g,"，");
            }
            },
            { title: '下单日期 ',field: 'CreateTime',width:150},
            { title: '发货日期 ',field: 'OperTime',width:150},
            { title: '操作员',field: 'OperPersonName',width:120},
            { title: '金额合计',field: 'TotalPrice',width:120,hidden : priceHidden},
            { title: '收款状态',field: 'Status', width:100,align:"center",formatter:function(value){
                return value ? "<span style='color:green;'>已收款</span>":"<span style='color:red;'>未收款</span>";
                }
            },
            { title: '审核状态',field: 'CheckStatus', width:100,align:"center",formatter:function(value){
                return value ? "<span style='color:green;'>已审核</span>":"<span style='color:red;'>未审核</span>";
                }
            },
            { title: '审核员',field: 'CheckOperName',width:100},
            { title: '发货状态',field: 'SendStatus', width:100,align:"center",formatter:function(value){
                return value ? "<span style='color:green;'>已发货</span>":"<span style='color:red;'>未发货</span>";
                }
            },
            { title: '发货员',field: 'SendPersonName',width:100},
            { title: '是否过期',field: 'Expired',width:100,hidden:true}
        ]],
        rowStyler: function (index, row) {
            if(row.Expired){
                return 'background-color:#F78181;';
            }

        },
        toolbar:tableToolBar, 
        onLoadError:function()
        {
            $.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
            return;
        }
    });
}

//新增信息
function addDepotHead(){
    $("#clientIp").val(clientIp);
    $('#depotHeadFM').form('clear');
    var thisDateTime = getNowFormatDateTime(); //当前时间
    $("#OperTime").val(thisDateTime);
    var thisNumber = getNowFormatDateNum(); //根据时间生成编号
    var thisDate = getNowFormatDate(); //当前日期
    var beginTime = thisDate + " 00:00:00";
    var endTime = thisDate + " 23:59:59";
    //生成单据编号
    $.ajax({
        type: "post",
        url: path + "/depotHead/buildNumber.action",
        data: {
            Type: '',
            SubType: '',
            BeginTime: beginTime,
            EndTime: endTime
        },
        success:function(res){
            if(res){
                res = JSON.parse(res);
                var defaultNumber = res.DefaultNumber;
                var thisDateTwo = getNowFormatDateTwo(); //当前日期
                var newNumber = amountNum + thisDateTwo + defaultNumber;
                $("#Number").val(newNumber).attr("data-defaultNumber",newNumber);
            }
        },
        error:function(){
            $.messager.alert('提示','生成单据编号失败！','error');
        }
    });
    //初始化优惠率、优惠金额、优惠后金额、本次付|收款、本次欠款 为0
    $("#Discount").val(0);
    $("#DiscountMoney").val(0);
    $("#DiscountLastMoney").val(0);
    $("#ChangeAmount").val(0);
    $("#Debt").val(0);
    $("#AccountId").val(defaultAccountId); //初始化默认的账户Id
    $('#depotHeadDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加订单');
    $(".window-mask").css({ width: webW ,height: webH});

    depotHeadID = 0;
    initTableData_material("add"); //商品列表
    reject(); //撤销下、刷新商品列表
    $("#addOrgan").off("click").on("click",function(){
        $('#supplierDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加供应商');
    });
    url = path + '/depotHead/create.action';
}

//批量删除单据信息
function batDeleteDepotHead(){
    var row = $('#tableData').datagrid('getChecked');
    if(row.length == 0)
    {
        $.messager.alert('删除提示','没有记录被选中！','info');
        return;
    }
    if(row.length > 0)
    {
        $.messager.confirm('删除确认','确定要删除选中的' + row.length + '条单据信息吗？',function(r)
        {
            if (r)
            {
                var ids = "";
                for(var i = 0;i < row.length; i++)
                {
                    if(row[i].Status){
                        $.messager.alert('删除提示','存在已收款的订单，不能删除！','info');
                        return;
                    }
                    if(i == row.length-1)
                    {
                        ids += row[i].Id;
                        break;
                    }
                    //alert(row[i].id);
                    ids += row[i].Id + ",";
                }
                //批量删除
                $.ajax({
                    type:"post",
                    url: path + "/depotHead/batchDelete.action",
                    dataType: "json",
                    async :  false,
                    data: ({
                        depotHeadIDs : ids,
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
                            $.messager.alert('删除提示','删除单据信息失败，请稍后再试！','error');
                    },
                    //此处添加错误处理
                    error:function()
                    {
                        $.messager.alert('删除提示','删除单据信息异常，请稍后再试！','error');
                        return;
                    }
                });
            }
        });
    }
}

//删除单据信息 -- 单条删除
function deleteDepotHead(depotHeadID, thisOrganId, totalPrice, status){
    if(status) {
        $.messager.alert('删除提示','已收款的单据不能删除！','warning');
        return;
    }
    $.messager.confirm('删除确认','确定要删除此单据信息吗？',function(r) {
        if (r) {
            $.ajax({
                type:"post",
                url: path + "/depotHead/delete.action",
                dataType: "json",
                data: ({
                    depotHeadID : depotHeadID,
                    clientIp: clientIp
                }),
                success: function (tipInfo)
                {
                    var msg = tipInfo.showModel.msgTip;
                    if(msg == '成功')
                    {
                        //加载完以后重新初始化
                        $("#searchBtn").click();
                    }
                    else
                        $.messager.alert('删除提示','删除单据信息失败，请稍后再试！','error');
                },
                //此处添加错误处理
                error:function()
                {
                    $.messager.alert('删除提示','删除单据信息异常，请稍后再试！','error');
                    return;
                }
            });
        }
    });
}

//编辑信息
function editDepotHead(depotHeadTotalInfo, status){
    if(status) {
        $.messager.alert('编辑提示','已收款的单据不能编辑！','warning');
        return;
    }
    var depotHeadInfo = depotHeadTotalInfo.split("AaBb");
    $("#clientIp").val(clientIp);
    $("#Number").val(depotHeadInfo[1]).attr("data-defaultNumber",depotHeadInfo[1]);
    $("#OperTime").val(depotHeadInfo[19]);
    $('#OrganId').combobox('setValue', depotHeadInfo[3]);
    $("#Remark").val(depotHeadInfo[4]);

    var TotalPrice = depotHeadInfo[6];
    preTotalPrice = depotHeadInfo[6]; //记录前一次合计金额，用于扣预付款
    oldNumber = depotHeadInfo[1]; //记录编辑前的单据编号
    oldId = depotHeadInfo[0]; //记录单据Id
    Salesman = depotHeadInfo[8];


    $("#Express").val(depotHeadInfo[9]); //物流公司
    $("#ExpressNumber").val(depotHeadInfo[10]); //物流编号
    $("#Contacts").val(depotHeadInfo[11]); //联系人
    $("#Phonenum").val(depotHeadInfo[12]); //联系号码
    $('#state').combobox('setValue', depotHeadInfo[13]);
    $('#city').combobox('setValue', depotHeadInfo[14]);
    $('#street').combobox('setValue', depotHeadInfo[15]);
    $("#address").val(depotHeadInfo[16]); //联系号码

    $("#Weight").val(depotHeadInfo[17]); //重量
    $("#Freight").val(depotHeadInfo[18]); //运费预估


    $('#depotHeadDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑订单明细');
    $(".window-mask").css({ width: webW ,height: webH});
    depotHeadID = depotHeadInfo[0];

    initTableData_material("edit",TotalPrice); //商品列表
    reject(); //撤销下、刷新商品列表
    url = path + '/depotHead/update.action?depotHeadID=' + depotHeadInfo[0];
}

//初始化表格数据-商品列表-编辑状态
function initTableData_material(type,TotalPrice){
    //显示保存按钮
    $("#saveDepotHead").show();
    //隐藏打印按钮
    $("#printDepotHead").hide();

    var body,footer,input; //定义表格和文本框
    $('#materialData').datagrid({
        height:245,
        rownumbers: false,
        //动画效果
        animate:false,
        //选中单行
        singleSelect : true,
        collapsible:false,
        selectOnCheck:false,
        //单击行是否选中
        checkOnSelect : false,
        pagination: false,
        //交替出现背景
        striped : true,
        showFooter: true,
        //loadFilter: pagerFilter,
        onClickRow: onClickRow,
        columns:[[
            { field: 'Id',width:35,align:"center",checkbox:true},
            { title: '型号',field: 'MaterialId',width:230,
                formatter:function(value,row,index){
                    return row.MaterialName;
                },
                editor:{
                    type:'combobox',
                    options:{
                        valueField:'id',
                        textField:'productName',
                        method:'post',
                        url: path + "/product/findBySelect.action",
                        panelWidth: 300, //下拉框的宽度
                        //全面模糊匹配，过滤字段
                        filter: function(q, row){
                            q = q.toUpperCase();
                            var opts = $(this).combobox('options');
                            return row[opts.textField].toUpperCase().indexOf(q) >-1;
                        },
                        onBeforeLoad: function(param){
                            param.mpList = mPropertyList; //商品属性
                        },
                        onSelect:function(rec){
                            //进行版本名称回填
                            body =$("#depotHeadFM .datagrid-body");
                            footer =$("#depotHeadFM .datagrid-footer");
                            input = ".datagrid-editable-input";
                            var productId = body.find("[field='MaterialId']").find(".combo-value").val();

                            var row = $('#materialData').datagrid('getSelected');
                            var rowIndex = $('#materialData').datagrid('getRowIndex',row);//获取行号
                            var target = $('#materialData').datagrid('getEditor', {'index':rowIndex,'field':'DepotId'}).target;
                            $.ajax({
                                url: path + "/product/findById.action",
                                type: "get",
                                dataType: "json",
                                data: {
                                    "id": productId
                                },
                                success: function (res) {
                                    if(res && res.rows && res.rows[0]) {
                                        var tId = res.rows[0].tId;//版本册编号
                                        var standard = res.rows[0].standard;//规格

                                        var wholesalePrice = res.rows[0].wholesalePrice;//批发价
                                        var retailPrice = res.rows[0].retailPrice;//零售价
                                        var purchasePrice = res.rows[0].purchasePrice;//进货价
                                        var supplierNo = res.rows[0].supplierNo;//供应商编号
                                        //版本编号下拉框赋值
                                        target.combobox('setValue',tId);
                                        //规格赋值
                                        body.find("[field='Unit']").find(input).val(standard);
                                        //批发价赋值
                                        body.find("[field='WholesalePrice']").find(input).val(wholesalePrice);
                                        //零售价赋值
                                        body.find("[field='RetailPrice']").find(input).val(retailPrice);
                                        //进货价
                                        body.find("[field='TaxUnitPrice']").find(input).val(purchasePrice);
                                        //供应商编号
                                        body.find("[field='AnotherDepotId']").find(input).val(supplierNo);
                                        //获取数量
                                        var OperNumber = body.find("[field='OperNumber']").find(input).val();

                                        var UnitPrice = 0;
                                        //价格回填
                                        if(customerType == '批发商'){
                                            UnitPrice = wholesalePrice;
                                            //价格赋值
                                            body.find("[field='UnitPrice']").find(input).val(wholesalePrice);
                                            //总价赋值
                                            body.find("[field='AllPrice']").find(input).val((wholesalePrice * OperNumber).toFixed(2));
                                        }else{
                                            UnitPrice = retailPrice;
                                            body.find("[field='UnitPrice']").find(input).val(retailPrice);
                                            //总价赋值
                                            body.find("[field='AllPrice']").find(input).val((retailPrice * OperNumber).toFixed(2));
                                        }
                                        statisticsFun(body,UnitPrice,OperNumber,footer);
                                    }
                                },
                                error: function() {
                                    $.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
                                }
                            });

                        }
                    }
                }
            },
            { title: '版本名称', field: 'DepotId', editor: 'validatebox', width: 90,
                formatter: function (value, row, index) {
                    return row.DepotName;
                },
                editor: {
                    type: 'combobox',
                    options: {
                        valueField: 'id',
                        textField: 'templateName',
                        method: 'get',
                        url: path + "/template/findBySelect_temp.action",
                        onSelect:function(rec){
                            var row = $('#materialData').datagrid('getSelected');
                            var rowIndex = $('#materialData').datagrid('getRowIndex',row);//获取行号
                            body =$("#depotHeadFM .datagrid-body");
                            footer =$("#depotHeadFM .datagrid-footer");
                            input = ".datagrid-editable-input";
                            var tempId = body.find("[field='DepotId']").find(".combo-value").val();


                            var target = $('#materialData').datagrid('getEditor', {'index':rowIndex,'field':'MaterialId'}).target;
                            target.combobox('clear'); //清除原来的数据
                            var url = path + "/product/findByTemplateId.action?id="+tempId;
                            target.combobox('reload', url);//联动下拉列表重载

                        }
                    }
                }
            },
            { title: '规格',field: 'Unit',editor:'validatebox',width:60},
            { title: '单价(m)',field: 'UnitPrice',editor:'validatebox',width:60},
            { title: '数量',field: 'OperNumber',editor:'validatebox',width:60},
            { title: '总价',field: 'AllPrice',editor:'validatebox',width:75},
            { title: '备注',field: 'Remark',editor:'validatebox',width:360},
            { title: '批发价',field: 'WholesalePrice',editor:'validatebox',width:120,hidden:true},
            { title: '零售价',field: 'RetailPrice',editor:'validatebox',width:120,hidden:true},
            { title: '进货价',field: 'TaxUnitPrice',editor:'validatebox',width:120,hidden:true},
            { title: '供应商编号',field: 'AnotherDepotId',editor:'validatebox',width:120,hidden:true}
        ]],
        toolbar:[
            {
                id:'append',
                text:'新增',
                iconCls:'icon-add',
                handler:function()
                {
                    append(); //新增
                }
            },
            {
                id:'delete',
                text:'删除',
                iconCls:'icon-remove',
                handler:function()
                {
                    removeit(); //删除
                }
            },
            {
                id:'reject',
                text:'撤销',
                iconCls:'icon-undo',
                handler:function()
                {
                    reject(); //撤销
                }
            }
        ],
        onLoadError:function()
        {
            $.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
            return;
        }
    });
    $.ajax({
        type:"post",
        url: path + '/depotItem/findBy.action?HeaderId=' + depotHeadID,
        data: {
            mpList: mPropertyList
        },
        dataType: "json",
        success: function (res) {
            var allOperNum = 0;
            if(res && res.rows) {
                var myRows = res.rows;
                for(var i=0; i<myRows.length; i++){
                    var num = myRows[i].OperNumber;
                    allOperNum = allOperNum + num;
                }
            }
            var AllPrice = 0;
            if(type === "edit") {
                AllPrice = TotalPrice;
            }
            var array = [];
            array.push({
                "Unit" : "合计",
                "OperNumber" : allOperNum,
                "AllPrice": AllPrice
            });
            res.footer = array;
            $("#materialData").datagrid('loadData',res);

        },
        error:function() {
            $.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
        }
    });
}

//单击
function onClickRow(index) {
    if (editIndex != index) {
        if (endEditing()) {
            $('#materialData').datagrid('selectRow', index).datagrid('beginEdit', index);
            editIndex = index;

            var thisTarget = $('#materialData').datagrid('getEditor', {'index':index,'field':'DepotId'}).target;
            var tempId = thisTarget.combobox('getValue');
            var target = $('#materialData').datagrid('getEditor', {'index':index,'field':'MaterialId'}).target;
            var url = path + "/product/findByTemplateId.action?id="+tempId;
            target.combobox('reload', url);//联动下拉列表重载

            // isChangePrice == true 标识可以更改价格
            // !isChangePrice == false 标识不可以更改价格
            if(!isChangePrice){
                var vareditor = $('#materialData').datagrid('getEditor', { index:index, field:'UnitPrice'});
                vareditor.target.prop('readonly', true);
            }
            autoReckon();
        } else {
            $('#materialData').datagrid('selectRow', editIndex);
        }
    }
}
//撤销
function reject() {
    $('#materialData').datagrid('rejectChanges');
    editIndex = undefined;
}
//客户信息(下拉列表)
function initCustomerList() {
    $('#OrganId').combobox({
        url: path + "/customer/findBySelect_sup.action",
        valueField:'id',
        textField:'customerName'
    });
    //初始化 收款状态搜索 下拉框
    $('#searchState').combobox({
        url: path +'/js/pages/manage/orderStatus.json',
        valueField:'value',
        textField:'name'
    });
    //初始化 审核状态搜索 下拉框
    $('#searchCheck').combobox({
        url: path +'/js/pages/manage/checkStatus.json',
        valueField:'value',
        textField:'name'
    });

    //初始化 发货状态搜索 下拉框
    $('#searchSendStatus').combobox({
        url: path +'/js/pages/manage/orderSendStatus.json',
        valueField:'value',
        textField:'name'
    });

    $("#AccountId").attr("data-accountArr");

    $('#OrganId').combobox({
        onSelect: function () {
            //获取客户类型  是批发商 还是零售商
            $.ajax({
                url: path + "/customer/findById.action",
                type: "get",
                dataType: "json",
                data: {
                    "id": $('#OrganId').combobox('getValue')
                },
                success: function (res) {
                    if(res && res.rows && res.rows[0]) {
                        customerType = res.rows[0].type;
                        //所属业务员
                        Salesman = res.rows[0].userId;

                        //默认物流公司
                        $("#Express").val(res.rows[0].express);
                        //默认收货人
                        $("#Contacts").val(res.rows[0].contacts);
                        //默认收货号码
                        $("#Phonenum").val(res.rows[0].phonenum);
                        //默认详细地址
                        $("#address").val(res.rows[0].address);

                        $('#state').combobox('setValue',res.rows[0].state);
                        $('#city').combobox('setValue',res.rows[0].city);
                        $('#street').combobox('setValue',res.rows[0].street);

                        var rows = $("#materialData").datagrid("getRows"); //这段代码是获取当前页的所有行
                        if(endEditing()){
                            var grid = $('#materialData');
                            var tempAllPrice = 0;
                            for(var i = 0 ; i <rows.length;i++){
                                if (customerType == '批发商') {
                                    //将批发价赋值给单价
                                    EasyUIDataGrid.setFieldValue('UnitPrice',rows[i].WholesalePrice,i,grid);
                                    //重新计算总价
                                    var allPrice = rows[i].WholesalePrice * rows[i].OperNumber;
                                    tempAllPrice = tempAllPrice + allPrice;
                                    //将新计算的总价重新赋值
                                    EasyUIDataGrid.setFieldValue('AllPrice',allPrice,i,grid);
                                } else {
                                    //将零售价赋值给单价
                                    EasyUIDataGrid.setFieldValue('UnitPrice',rows[i].RetailPrice,i,grid);
                                    //重新计算总价
                                    var allPrice = rows[i].RetailPrice * rows[i].OperNumber;
                                    tempAllPrice = tempAllPrice + allPrice;
                                    //将新计算的总价重新赋值
                                    EasyUIDataGrid.setFieldValue('AllPrice',allPrice,i,grid);
                                }
                            }
                            var footer =$("#depotHeadFM .datagrid-footer");
                            footer.find("[field='AllPrice']").find("div").text((tempAllPrice).toFixed(2)); //金额的合计
                        }
                    }
                },
                error: function() {
                    $.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
                }
            });
        },
        filter: function(q, row){
            q = q.toUpperCase();
            var opts = $(this).combobox('options');
            return row[opts.textField].toUpperCase().indexOf(q) >-1;
        }
    });
}
//新增
function append(){
    //第一步，判断是否选中客户
    customerId = $('#OrganId').combobox('getValue');
    if(customerId == ''){
        $.messager.alert('提示','请选择客户','warning');
        return;
    }
    if (endEditing()) {
        $('#materialData').datagrid('appendRow', {});
        editIndex = $('#materialData').datagrid('getRows').length - 1;
        $('#materialData').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);

        if(!isChangePrice){
            var vareditor = $('#materialData').datagrid('getEditor', { index:editIndex, field:'UnitPrice'});
            vareditor.target.prop('readonly', true);
        }
        autoReckon();
    }
}
//删除
function removeit(){
    var row = $('#materialData').datagrid('getChecked');
    if(row.length > 0){
        for(var i = 0;i < row.length; i++){
            var rowIndex = $('#materialData').datagrid('getRowIndex', row[i]);
            $('#materialData').datagrid('cancelEdit', rowIndex)
                .datagrid('deleteRow', rowIndex);
        }
    }
    if (editIndex == undefined) { return }
    $('#materialData').datagrid('cancelEdit', editIndex)
        .datagrid('deleteRow', editIndex);
    editIndex = undefined;

}
//结束编辑
function endEditing() {
    if (editIndex == undefined) { return true }
    if ($('#materialData').datagrid('validateRow', editIndex)) {
        //版本名称
        var edDepot = $('#materialData').datagrid('getEditor', {index:editIndex,field:'DepotId'});
        var DepotName = $(edDepot.target).combobox('getText');
        $('#materialData').datagrid('getRows')[editIndex]['DepotName'] = DepotName;
        //型号
        var edMaterial = $('#materialData').datagrid('getEditor', {index:editIndex,field:'MaterialId'});
        var MaterialName = $(edMaterial.target).combobox('getText');
        $('#materialData').datagrid('getRows')[editIndex]['MaterialName'] = MaterialName;
        $('#materialData').datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}
//自动计算事件
function autoReckon() {
    //延时绑定事件
    setTimeout(function(){
        var body =$("#depotHeadFM .datagrid-body");
        var footer =$("#depotHeadFM .datagrid-footer");
        var input = ".datagrid-editable-input";

        //修改数量，自动计算总价
        body.find("[field='OperNumber']").find(input).off("keyup").on("keyup",function(){
            var UnitPrice = body.find("[field='UnitPrice']").find(input).val(); //单价
            var OperNumber =$(this).val()-0; //数量
            body.find("[field='AllPrice']").find(input).val((UnitPrice*OperNumber).toFixed(2)); //金额
            statisticsFun(body,UnitPrice,OperNumber,footer);
        });
        //在可以更改价格状态下，才会去自动计算
        if(isChangePrice){
            //修改单价，自动计算总价
            body.find("[field='UnitPrice']").find(input).off("keyup").on("keyup",function(){
                var UnitPrice = body.find("[field='UnitPrice']").find(input).val(); //单价
                var OperNumber =$(this).val()-0; //数量
                body.find("[field='AllPrice']").find(input).val((UnitPrice*OperNumber).toFixed(2)); //金额
                statisticsFun(body,UnitPrice,OperNumber,footer);
            });
        }
    });
}

//最底下合计方法
function statisticsFun(body,UnitPrice,OperNumber,footer){
    var TotalPrice = 0;

    var totalNum = 0;
    //金额的合计
    body.find("[field='AllPrice']").each(function(){
        if($(this).find("div").text()!==""){
            TotalPrice = TotalPrice + parseFloat($(this).find("div").text().toString());
        }
    });
    body.find("[field='OperNumber']").each(function(){
        if($(this).find("div").text()!==""){
            totalNum = totalNum + parseFloat($(this).find("div").text().toString());
        }
    });
    TotalPrice = TotalPrice + UnitPrice*OperNumber;

    totalNum = totalNum + OperNumber;
    footer.find("[field='AllPrice']").find("div").text((TotalPrice).toFixed(0)); //金额的合计
    footer.find("[field='OperNumber']").find("div").text((totalNum).toFixed(2)); //金额的合计
    footer.find("[field='Unit']").find("div").text("合计"); //金额的合计

    //重量赋值
    $("#Weight").val((totalNum * 0.8).toFixed(2));//赋值
}

//检查单据编号是否存在
function checkDepotHeadNumber() {
    var thisNumber = $.trim($("#Number").val());
    //表示是否存在 true == 存在 false = 不存在
    var flag = false;
    //开始ajax名称检验，不能重名
    if(thisNumber.length > 0 &&( oldNumber.length ==0 || thisNumber != oldNumber))
    {
        $.ajax({
            type:"post",
            url: path + "/depotHead/checkIsNumberExist.action",
            dataType: "json",
            async :  false,
            data: ({
                DepotHeadID : oldId,
                Number : thisNumber
            }),
            success: function (tipInfo)
            {
                flag = tipInfo;
                if(tipInfo)
                {
                    $.messager.alert('提示','抱歉，该单据编号已经存在','warning');
                    return;
                }
            },
            //此处添加错误处理
            error:function()
            {
                $.messager.alert('提示','检查单据编号是否存在异常，请稍后再试！','error');
                return;
            }
        });
    }
    return flag;
}
//判断
function CheckData(type) {
    append();
    removeit();
    var change = $('#materialData').datagrid('getChanges').length;
    if(type =="add" && !change) {
        $.messager.alert('提示','请输入明细信息！','warning');
        return false;
    }
    var row = $('#materialData').datagrid('getRows');
    if(!row.length){
        $.messager.alert('提示',"请输入明细信息！",'info');
        return false;
    }
    var totalRowNum = "";
    for (var i = 0; i < row.length; i++) {
        if (row[i].DepotId == "" || row[i].MaterialId == "" || row[i].OperNumber == "" || row[i].UnitPrice === "" || row[i].AllPrice === "") {
            totalRowNum += (i + 1) + "、";
        }
    }
    if (totalRowNum != "") {
        var totalRowNum = totalRowNum.substring(0, totalRowNum.length - 1);
        $.messager.alert('提示',"第" + totalRowNum + "行数据填写不完整！",'info');
        return false;
    }
    return true;
}


function funSaveDepotHead() {
    if (!$('#depotHeadFM').form('validate')) {
        return;
    } else {
        //如果初始编号被修改了，就要判断单据编号是否存在
        if ($.trim($("#Number").val()) != $('#Number').attr("data-defaultNumber")) {
            //调用查询单据编号是否重名的方法
            if (checkDepotHeadNumber()) {
                return;
            }
        }
        //进行明细的校验
        if (depotHeadID == 0) {
            //新增模式下
            if (!CheckData("add")) {
                return;
            }
        }
        else {
            //编辑模式下
            if (!CheckData("edit")) {
                return;
            }
        }
        var OrganId = null;
        if($('#OrganId').length){
            OrganId = $('#OrganId').combobox('getValue');
        }
        var TotalPrice = $("#depotHeadFM .datagrid-footer [field='AllPrice'] div").text();
        $.ajax({
            type:"post",
            url: url,
            dataType: "json",
            async :  false,
            data: ({
                Type : listType,
                SubType : listSubType,
                ProjectId : '',
                AllocationProjectId : '',
                DefaultNumber: $.trim($("#Number").attr("data-defaultNumber")),//初始编号
                Number: $.trim($("#Number").val()),
                OperTime: $("#OperTime").val(),
                OrganId: OrganId,
                HandsPersonId: '',
                Salesman: Salesman, //业务人员
                AccountId: '',//所属账户编号
                ChangeAmount: '', //付款/收款
                TotalPrice: TotalPrice, //合计
                PayType: '', //现付/预付款
                Remark: $.trim($("#Remark").val()),
                AccountIdList: '', //账户列表-多账户
                AccountMoneyList: '', //账户金额列表-多账户
                Discount: '',//优惠率
                DiscountMoney: '',//优惠金额
                DiscountLastMoney: '',//优惠后金额
                OtherMoney: '', //采购费用、销售费用
                OtherMoneyList: '', //支出项目列表-涉及费用
                OtherMoneyItem: '', //支出项目金额列表-涉及费用
                AccountDay: '', //结算天数
                Weight: $("#Weight").val(), //重量
                Freight: $("#Freight").val(), //运费预估
                Express: $("#Express").val(), //默认物流
                ExpressNumber: $("#ExpressNumber").val(), //物流单号
                Contacts: $("#Contacts").val(), //联系人
                Phonenum: $("#Phonenum").val(), //联系电话
                state:$('#state').combobox('getText'),
                city:$('#city').combobox('getText'),
                street:$('#street').combobox('getText'),
                address: $("#address").val(), //详细地址
                clientIp: clientIp
            }),
            success: function (tipInfo)
            {
                if(tipInfo){
                    function closeDialog(){
                        //$('#depotHeadDlg').dialog('close');
                        $("#saveDepotHead").hide();
                        $("#printDepotHead").show();
                        
                        var opts = $("#tableData").datagrid('options');
                        showDepotHeadDetails(opts.pageNumber,opts.pageSize);
                    }
                    //保存明细记录
                    if(depotHeadID ==0)
                    {
                        getMaxId(); //查找最大的Id
                        accept(depotHeadMaxId,closeDialog); //新增
                    }
                    else
                    {
                        acceptModify(depotHeadID,closeDialog); //修改
                    }
                }
            },
            //此处添加错误处理
            error:function()
            {
                $.messager.alert('提示','保存信息异常，请稍后再试！','error');
                return;
            }
        });
    }
}

//绑定操作事件
function bindEvent(){
    showDepotHeadDetails(1,initPageSize); //初始化时自动查询
    //搜索处理
    $("#searchBtn").off("click").on("click",function(){
        showDepotHeadDetails(1,initPageSize);
        var opts = $("#tableData").datagrid('options');
        var pager = $("#tableData").datagrid('getPager');
        opts.pageNumber = 1;
        opts.pageSize = initPageSize;
        pager.pagination('refresh',
            {
                pageNumber:1,
                pageSize:initPageSize
            });
    });
    //重置按钮
    $("#searchResetBtn").unbind().bind({
        click:function(){
            $("#searchNumber").val("");
            $("#searchCustomerNo").val("");
            $("#searchCustomerName").val("");
            $("#searchMaterial").val("");
            $("#searchState").combobox("setValue","");
            $("#searchSendStatus").combobox("setValue","");
            $("#searchCheck").combobox("setValue","");
            $("#searchBeginTime").val("");
            $("#searchEndTime").val("");

            $("#searchSendBeginTime").val("");
            $("#searchSendEndTime").val("");

            $("#searchTotalPrice").val("");
            //加载完以后重新初始化
            $("#searchBtn").click();
        }
    });
    //保存信息
    $('#saveDepotHead').off("click").on("click", function () {
        funSaveDepotHead();
    });
    //打印信息
    $('#printDepotHead').off("click").on("click", function () {
        PrintOrder($("#Number").val(),path,systemNo)
    });

    //发货信息 保存按钮
    $('#saveDepotHeadSendDlg').off("click").on("click", function () {
        var row = $('#tableData').datagrid('getChecked');

        var express = $("#ExpressSend").val();
        if(express == ''){
            $.messager.alert('提示','请填写物流公司！','info');
            return;
        }
        var num = $("#ExpressNumberSend").val();
        $.ajax({
            type:"post",
            url: path + "/depotHead/sendGoods.action",
            dataType: "json",
            async :  false,
            data: ({
                depotHeadID : row[0].Id,
                Express : express,//物流公司
                ExpressNumber : num,//物流单号
                Weight : $("#Weight").val(),//重量
                Freight : $("#Freight").val(),//运费预估
                OperTime : getNowFormatDateTime(),
                clientIp: clientIp
            }),
            success: function (tipInfo)
            {
                if(tipInfo){
                    $('#depotHeadSendDlg').dialog('close');
                    var opts = $("#tableData").datagrid('options');
                    showDepotHeadDetails(opts.pageNumber,opts.pageSize);
                }
            },
            //此处添加错误处理
            error:function()
            {
                $.messager.alert('提示','保存信息异常，请稍后再试！','error');
                return;
            }
        });
    });

    //选择收款账户 保存按钮
    $('#saveReceiptDlg').off("click").on("click", function () {
        if(!$('#AccountId').val()){
            $.messager.alert('提示','请选择结算账户！','warning');
            return;
        }
        var row = $('#tableData').datagrid('getChecked');
        var OrganIds = "";
        var TotalPrices = "";
        var BillNo = "";
        var ids = "";
        for(var i = 0;i < row.length; i ++)
        {
            if(i == row.length-1)
            {
                OrganIds += row[i].OrganId;
                BillNo += row[i].Number;
                TotalPrices += row[i].TotalPrice;
                ids += row[i].Id;
                break;
            }
            OrganIds += row[i].OrganId + ",";
            BillNo += row[i].Number + ",";
            TotalPrices += row[i].TotalPrice + ",";
            ids += row[i].Id + ",";
        }
        //开始保存收款明细
        $.ajax({
            type:"post",
            url: path + "/accountHead/saveAccountDetails.action",
            dataType: "json",
            async :  false,
            data: ({
                Type : "收款",
                OrganIds : OrganIds,//客户编号
                TotalPrices : TotalPrices,//价格数组
                AccountId : $('#AccountId').val(),//收款账户
                BillNo : BillNo,//单据编号
                BillTime : getNowFormatDateTime(),
                clientIp : clientIp
            }),
            success: function (res)
            {
                if(res)
                {
                    $.ajax({
                        type:"post",
                        url: path + "/depotHead/batchSetStatus.action",
                        dataType: "json",
                        async :  false,
                        data: ({
                            Status: true,
                            DepotHeadIDs : ids,
                            clientIp: clientIp
                        }),
                        success: function (tipInfo)
                        {
                            var msg = tipInfo.showModel.msgTip;
                            if(msg == '成功')
                            {
                                $.messager.alert('提示','确认收款成功！','info');
                                //关闭当前对话框
                                $('#receiptDlg').dialog('close');
                                //加载完以后重新初始化
                                $("#searchBtn").click();
                                $(":checkbox").attr("checked",false);
                            }
                            else{
                                $.messager.alert('提示','确认收款失败，请稍后再试！','error');
                            }
                        },
                        //此处添加错误处理
                        error:function()
                        {
                            $.messager.alert('提示','确认收款异常，请稍后再试！','error');
                            return;
                        }
                    });
                }
                else
                    $.messager.alert('提示','确认收款失败，请稍后再试！','error');
            },
            //此处添加错误处理
            error:function()
            {
                $.messager.alert('提示','确认收款异常，请稍后再试！','error');
                return;
            }
        });
    });

    //初始化键盘enter事件
    $(document).keydown(function (event) {
        //兼容 IE和firefox 事件
        var e = window.event || event;
        var k = e.keyCode || e.which || e.charCode;
        //兼容 IE,firefox 兼容
        var obj = e.srcElement ? e.srcElement : e.target;
        //绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件 ---遗留问题 enter键效验 对话框会关闭问题
        if (k == "13" && (obj.id == "State" || obj.id == "Number")) {
            $("#saveDepotHead").click();
        }
        //搜索按钮添加快捷键
        if (k == "13" && (obj.id == "searchState" || obj.id == "searchSendStatus" || obj.id == "searchNumber" || obj.id == "searchMaterial" || obj.id == "searchCustomerNo"
            || obj.id == "searchCustomerName"|| obj.id == "searchCheck" )) {
            $("#searchBtn").click();
        }
    });
}


//初始化首页
function showDepotHeadDetails(pageNo,pageSize){
    var materialParam = $.trim($("#searchMaterial").val());
    $.ajax({
        type:"post",
        url: path + "/depotHead/getHeaderIdByMaterial.action",
        dataType: "json",
        data: ({
            MaterialParam: materialParam,
            DepotIds: depotString
        }),
        success: function (res) {
            if(res) {
                var ids = res.ret;
                if(ids){
                    $.ajax({
                        type: "post",
                        url: path + "/depotHead/findBy.action",
                        dataType: "json",
                        data: ({
                            Type: listType,
                            SubType: listSubType,
                            searchStatus: $("#searchState").datebox("getValue"),
                            searchSendStatus: $("#searchSendStatus").datebox("getValue"),
                            searchCheckStatus : $("#searchCheck").datebox("getValue"),

                            customerName: $.trim($("#searchCustomerName").val()),
                            customerNo: $.trim($("#searchCustomerNo").val()),

                            searchTotalPrice : $.trim($("#searchTotalPrice").val()),

                            Number: $.trim($("#searchNumber").val()),
                            BeginTime: $("#searchBeginTime").val(),
                            EndTime: $("#searchEndTime").val(),

                            SendBeginTime: $("#searchSendBeginTime").val(),
                            SendEndTime: $("#searchSendEndTime").val(),
                            
                            dhIds: ids,
                            pageNo: pageNo,
                            pageSize: pageSize
                        }),
                        success: function (data) {
                            $("#tableData").datagrid('loadData', data);
                        },
                        //此处添加错误处理
                        error: function () {
                            $.messager.alert('查询提示', '查询数据后台异常，请稍后再试！', 'error');
                            return;
                        }
                    });
                }
                else {
                    $("#tableData").datagrid('loadData', []);
                }
            }
        },
        //此处添加错误处理
        error:function() {
            $.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
            return;
        }
    });
}
//获取MaxId
function getMaxId(){
    var depotHeadMax=null;
    $.ajax({
        type:"post",
        url: path + "/depotHead/getMaxId.action",
        //设置为同步
        async:false,
        dataType: "json",
        success: function (systemInfo)
        {
            if(systemInfo)
            {
                depotHeadMax = systemInfo.showModel.map.depotHeadMax;
                var msgTip = systemInfo.showModel.msgTip;
                if(msgTip == "exceptoin")
                {
                    $.messager.alert('提示','查找最大的Id异常,请与管理员联系！','error');
                    return;
                }
            }
            else
            {
                depotHeadMax=null;
            }
        }
    });

    if(depotHeadMax !=null)
    {
        if(depotHeadMax.length>0)
        {
            depotHeadMaxId=depotHeadMax[0];
        }
    }
}
//新增时候--保存
function accept(accepId,fun) {
    var inserted = $("#materialData").datagrid('getChanges', "inserted");
    var deleted = $("#materialData").datagrid('getChanges', "deleted");
    var updated = $("#materialData").datagrid('getChanges', "updated");
    $.ajax({
        type: "post",
        url: path + "/depotItem/saveDetials.action",
        data: {
            Inserted : JSON.stringify(inserted),
            Deleted : JSON.stringify(deleted),
            Updated : JSON.stringify(updated),
            HeaderId : accepId,
            clientIp : clientIp
        },
        success: function (tipInfo)
        {
            if (tipInfo) {
                $.messager.alert('提示','保存成功！','info');
            }
            else {
                $.messager.alert('提示', '保存失败！', 'error');
            }
            fun && fun();
        },
        error: function (XmlHttpRequest, textStatus, errorThrown)
        {
            $.messager.alert('提示',XmlHttpRequest.responseText,'error');
            fun && fun();
        }
    });
    if (endEditing()) {
        $('#materialData').datagrid('acceptChanges');
    }
}

//新增时候--保存
function acceptModify(accepId,fun) {
    var rows = $("#materialData").datagrid('getRows');
    $.ajax({
        type: "post",
        url: path + "/depotItem/saveDetialsByUpdate.action",
        data: {
            Inserted : JSON.stringify(rows),
            HeaderId : accepId,
            clientIp : clientIp
        },
        success: function (tipInfo)
        {
            if (tipInfo) {
                $.messager.alert('提示','保存成功！','info');
            }
            else {
                $.messager.alert('提示', '保存失败！', 'error');
            }
            fun && fun();
        },
        error: function (XmlHttpRequest, textStatus, errorThrown)
        {
            $.messager.alert('提示',XmlHttpRequest.responseText,'error');
            fun && fun();
        }
    });
    if (endEditing()) {
        $('#materialData').datagrid('acceptChanges');
    }
}

//查看订单明细
function showDepotHead(depotHeadTotalInfo){
    var depotHeadInfo = depotHeadTotalInfo.split("AaBb");
    depotHeadID = depotHeadInfo[0];
    $("#NumberShow").text(depotHeadInfo[1]);//单据编号单据日期
    $("#OperTimeShow").text(depotHeadInfo[19]);//下单日期
    $("#RemarkShow").text(depotHeadInfo[4]);//单据备注
    $('#OrganIdShow').text(depotHeadInfo[5]);//客户
    var TotalPrice = depotHeadInfo[6];
    $("#SalesmanShow").text(depotHeadInfo[7]); //销售人员

    $("#ExpressShow").text(depotHeadInfo[9]); //物流公司
    $("#ExpressNumberShow").text(depotHeadInfo[10]); //运单号码
    $("#ContactsShow").text(depotHeadInfo[11]); //收货人
    $("#PhonenumShow").text(depotHeadInfo[12]); //收货号码

    $("#WeightShow").text(depotHeadInfo[17]); //重量
    $("#FreightShow").text(depotHeadInfo[18]); //运费预估

    $("#AddressShow").text(depotHeadInfo[13]+depotHeadInfo[14]+depotHeadInfo[15]+depotHeadInfo[16]); //收货地址

    $('#depotHeadDlgShow').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/list.png"/>&nbsp;查看订单明细');
    $(".window-mask").css({ width: webW ,height: webH});
    initTableData_material_show(TotalPrice); //商品列表-查看状态
}
//初始化表格数据-商品列表-查看状态
function initTableData_material_show(TotalPrice){
    var isShowAnotherDepot = true; //显示对方仓库,true为隐藏,false为显示
    var anotherDepotHeadName = ""; //对方仓库的列的标题
    var isShowTaxColumn = false; //是否显示税率相关的列,true为隐藏,false为显示
    var isShowMaterialTypeColumn = true; //是否显示商品类型相关的列,true为隐藏,false为显示

    $('#materialDataShow').datagrid({
        height:245,
        rownumbers: true,
        //动画效果
        animate:false,
        //选中单行
        singleSelect : true,
        collapsible:false,
        selectOnCheck:false,
        pagination: false,
        //交替出现背景
        striped : true,
        showFooter: true,
        onClickRow: onClickRow,
        columns:[[
            { title: '版本名称',field: 'TemplateName',width:230},
            { title: '型号',field: 'ProductName',width:230},
            { title: '规格',field: 'Unit',editor:'validatebox',width:60},
            { title: '单价',field: 'UnitPrice',editor:'validatebox',width:60,hidden : priceHidden },
            { title: '数量',field: 'OperNumber',editor:'validatebox',width:60},
            { title: '总价',field: 'AllPrice',editor:'validatebox',width:75,hidden : priceHidden},
            { title: '备注',field: 'Remark',editor:'validatebox',width:120}
        ]],
        onLoadError:function() {
            $.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
            return;
        }
    });
    $.ajax({
        type:"post",
        url: path + '/depotItem/findBy.action?HeaderId=' + depotHeadID,
        data: {
            mpList: mPropertyList
        },
        dataType: "json",
        success: function (res) {
            var allOperNum = 0;
            if(res && res.rows) {
                var myRows = res.rows;
                for(var i=0; i<myRows.length; i++){
                    var num = myRows[i].OperNumber;
                    allOperNum = allOperNum + num;
                }
            }
            var AllPrice = TotalPrice;
            var array = [];
            array.push({
                "Unit" : "合计",
                "OperNumber" : allOperNum,
                "AllPrice": AllPrice
            });
            res.footer = array;
            $("#materialDataShow").datagrid('loadData',res);
        },
        error:function() {
            $.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
        }
    });
}

//批量确认收款按钮
function receipt() {
    var row = $('#tableData').datagrid('getChecked');
    if(row.length == 0) {
        $.messager.alert('确认收款提示','没有记录被选中！','info');
        return;
    }
    if(row.length > 0) {
        $.messager.confirm('确认收款','确定要确认选中的' + row.length + '条信息吗？',function(r)
        {
            if (r)
            {
                for(var i = 0;i < row.length; i ++)
                {
                    if(row[i].Status){
                        $.messager.alert('收款提示','存在已收款的订单，不能重复操作！','info');
                        return;
                    }
                }
                $('#receiptDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/comment.png"/>&nbsp;选择收款账户');
            }
        });
    }
}

//批量 退款 按钮
function refund() {
    var row = $('#tableData').datagrid('getChecked');
    if(row.length == 0) {
        $.messager.alert('确认退款提示','没有记录被选中！','info');
        return;
    }
    if(row.length > 0) {
        for(var i = 0;i < row.length; i ++){
            if(!row[i].Status){
                $.messager.alert('退款提示','存在未收款的订单，请核对！','info');
                return;
            }
            if(row[i].CheckStatus){
                $.messager.alert('退款提示','存在已审核的订单，不能进行退款操作，请核对！','info');
                return;
            }
        }
        $.messager.confirm('确认退款','确定要确认选中的' + row.length + '条信息吗？',function(r)
        {
            if (r)
            {
                var ids = "";
                var BillNos = "";
                for(var i = 0;i < row.length; i ++)
                {

                    if(i == row.length-1)
                    {
                        ids += row[i].Id;
                        BillNos += "'"+row[i].Number+"'";
                        break;
                    }
                    ids += row[i].Id + ",";
                    BillNos += "'"+row[i].Number + "',";
                }
                $.ajax({
                    type:"post",
                    url: path + "/depotHead/batchSetStatus.action",
                    dataType: "json",
                    async :  false,
                    data: ({
                        Status: false,
                        DepotHeadIDs : ids,
                        clientIp: clientIp
                    }),
                    success: function (tipInfo)
                    {
                        var msg = tipInfo.showModel.msgTip;
                        if(msg == '成功')
                        {
                            $.ajax({
                                type:"post",
                                url: path + "/accountHead/batchDeleteByBillNos.action",
                                dataType: "json",
                                async :  false,
                                data: ({
                                    BillNo : BillNos,
                                    clientIp: clientIp
                                }),
                                success: function (res)
                                {
                                    if(res)
                                    {
                                        //加载完以后重新初始化
                                        $("#searchBtn").click();
                                        $(":checkbox").attr("checked",false);
                                    }
                                    else{
                                        $.messager.alert('提示','确认退款失败，请稍后再试！','error');
                                    }
                                },
                                //此处添加错误处理
                                error:function()
                                {
                                    $.messager.alert('提示','确认退款异常，请稍后再试！','error');
                                    return;
                                }
                            });
                        }
                    },
                    //此处添加错误处理
                    error:function()
                    {
                        $.messager.alert('提示','确认退款异常，请稍后再试！','error');
                        return;
                    }
                });
            }
        });
    }
}

//打印
function print() {
    var row = $('#tableData').datagrid('getChecked');
    if(row.length == 0) {
        $.messager.alert('提示','没有记录被选中！','info');
        return;
    }
    if(row.length >= 2) {
        $.messager.alert('提示','请选择一条单据进行打印！','info');
        return;
    }
    //如果是需要隐藏价格的。那么订货单不能让他打印，不然就看到价格了
    if(priceHidden && !row[0].SendStatus){
        $.messager.alert('提示','未发货的订单暂时不能打印！','info');
        return;
    }
    //如果是发货单
    if(row[0].SendStatus){
        CreateNewFormPageSend('发货单', $('#tableData'), path,systemNo);
    }else{
        CreateNewFormPageOrder('订货单', $('#tableData'), path,systemNo);
    }

}
//打印出库单
function printOut() {
    var row = $('#tableData').datagrid('getChecked');
    if(row.length == 0) {
        $.messager.alert('提示','没有记录被选中！','info');
        return;
    }
    if(row.length >= 2) {
        $.messager.alert('提示','请选择一条单据进行打印！','info');
        return;
    }
    CreateNewFormPageSend('出库单', $('#tableData'), path,systemNo);
}


//发货功能
function ship() {
    var row = $('#tableData').datagrid('getChecked');
    if(row.length == 0) {
        $.messager.alert('提示','没有记录被选中！','info');
        return;
    }
    if(row.length >= 2) {
        $.messager.alert('提示','请选择一条单据进行操作！','info');
        return;
    }
    if(!row[0].Status){
        $.messager.alert('提示','该单据未收款，不能发货！','info');
        return;
    }
    if(!row[0].CheckStatus){
        $.messager.alert('提示','该单据审核，不能发货！','info');
        return;
    }
    if(row[0].SendStatus){
        $.messager.alert('提示','该单据以发货不能重复发货！','info');
        return;
    }
    $("#ExpressSend").val(row[0].Express);


    $('#depotHeadSendDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/comment.png"/>&nbsp;填写发货信息');

}

//批量审核
function setStatusFun() {
    var row = $('#tableData').datagrid('getChecked');
    if(row.length == 0) {
        $.messager.alert('审核提示','没有记录被选中！','info');
        return;
    }
    if(row.length > 0) {
        $.messager.confirm('审核确认','确定要审核选中的' + row.length + '条信息吗？',function(r)
        {
            if (r)
            {
                var ids = "";
                for(var i = 0;i < row.length; i ++)
                {
                    if(!row[i].Status){
                        $.messager.alert('提示','未收款的单子无法进行审核！','info');
                        return;
                    }
                    if(i == row.length-1)
                    {
                        ids += row[i].Id;
                        break;
                    }
                    ids += row[i].Id + ",";
                }
                $.ajax({
                    type:"post",
                    url: path + "/depotHead/batchSetCheck.action",
                    dataType: "json",
                    async :  false,
                    data: ({
                        CheckStatus: true,
                        DepotHeadIDs : ids,
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
                            $.messager.alert('审核提示','审核信息失败，请稍后再试！','error');
                    },
                    //此处添加错误处理
                    error:function()
                    {
                        $.messager.alert('审核提示','审核信息异常，请稍后再试！','error');
                        return;
                    }
                });
            }
        });
    }
}

//批量反审核
function setUnStatusFun() {
    var row = $('#tableData').datagrid('getChecked');
    if(row.length == 0)
    {
        $.messager.alert('反审核提示','没有记录被选中！','info');
        return;
    }
    if(row.length > 0)
    {
        $.messager.confirm('反审核确认','确定要反审核选中的' + row.length + '条信息吗？',function(r)
        {
            if (r)
            {
                var ids = "";
                for(var i = 0;i < row.length; i ++)
                {
                    if(row[i].SendStatus){
                        $.messager.alert('提示','已发货不能反审核！','info');
                        return;
                    }
                    if(i == row.length-1)
                    {
                        ids += row[i].Id;
                        break;
                    }
                    ids += row[i].Id + ",";
                }
                $.ajax({
                    type:"post",
                    url: path + "/depotHead/batchSetCheck.action",
                    dataType: "json",
                    async :  false,
                    data: ({
                        CheckStatus: false,
                        DepotHeadIDs : ids,
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
                        else{
                            $.messager.alert('反审核提示','反审核信息失败，请稍后再试！','error');
                        }
                    },
                    //此处添加错误处理
                    error:function()
                    {
                        $.messager.alert('反审核提示','反审核信息异常，请稍后再试！','error');
                        return;
                    }
                });
            }
        });
    }
}

//获取账户信息
function initSystemData_account(){
    $.ajax({
        type:"post",
        url: path + "/account/getAccount.action",
        //设置为同步
        async:false,
        dataType: "json",
        success: function (systemInfo)
        {
            accountList = systemInfo.showModel.map.accountList;
            var msgTip = systemInfo.showModel.msgTip;
            if(msgTip == "exceptoin")
            {
                $.messager.alert('提示','查找账户信息异常,请与管理员联系！','error');
                return;
            }
        }
    });
}
//获取账户信息
function initSelectInfo_account(){
    var options = "";
    if(accountList !=null){
        for(var i = 0 ;i < accountList.length;i++) {
            var account = accountList[i];
            options += '<option value="' + account.id + '" data-currentAmount="' + account.currentAmount + '">' + account.name + '</option>';
            if(account.isDefault) {
                defaultAccountId = account.id; //给账户赋值默认id
            }
        }
        $("#AccountId").empty().append(options);
    }
}