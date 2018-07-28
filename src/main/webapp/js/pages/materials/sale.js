var amountNum = "DD"; //单据编号开头字符
var defaultAccountId = 0; //默认账户id
var listSubType;
var mPropertyList = ""; //商品属性列表
var editIndex = undefined;
var customerId = "";//客户编号
var customerType = "";//客户类型

$(function(){
    initTableData();
    //初始化客户信息下拉列表
    initCustomerList();
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
            { field: 'Id',width:35,align:"center",checkbox:true},
            { title: '操作',field: 'op',align:"center",width:90,
                formatter:function(value,rec) {
                    var str = '';
                    var rowInfo = rec.Id + 'AaBb' + rec.ProjectId+ 'AaBb' + rec.Number+ 'AaBb' + rec.OperPersonName
                        + 'AaBb' + rec.OperTime+ 'AaBb' + rec.OrganId+ 'AaBb' + rec.HandsPersonId
                        + 'AaBb' + rec.AccountId+ 'AaBb' + rec.ChangeAmount+ 'AaBb' + rec.Remark
                        + 'AaBb' + rec.ProjectName+ 'AaBb' + rec.OrganName+ 'AaBb' + rec.HandsPersonName
                        + 'AaBb' + rec.AccountName + 'AaBb' + rec.TotalPrice + 'AaBb' + rec.AllocationProjectId
                        + 'AaBb' + rec.AllocationProjectName + 'AaBb' + rec.payType + 'AaBb' + rec.Salesman
                        + 'AaBb' + rec.Discount + 'AaBb' + rec.DiscountMoney + 'AaBb' + rec.DiscountLastMoney
                        + 'AaBb' + rec.AccountIdList + 'AaBb' + rec.AccountMoneyList
                        + 'AaBb' + rec.OtherMoney + 'AaBb' + rec.OtherMoneyList + 'AaBb' + rec.OtherMoneyItem + 'AaBb' + rec.AccountDay;
                    if(1 == value) {
                        var orgId = rec.OrganId? rec.OrganId:0;
                        str += '<img title="查看" src="' + path + '/js/easyui-1.3.5/themes/icons/list.png" style="cursor: pointer;" onclick="showDepotHead(\'' + rowInfo + '\');"/>&nbsp;&nbsp;&nbsp;';
                        str += '<img title="编辑" src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editDepotHead(\'' + rowInfo + '\''+',' + rec.Status + ');"/>&nbsp;&nbsp;&nbsp;';
                        str += '<img title="删除" src="' + path + '/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteDepotHead('+ rec.Id +',' + orgId +',' + rec.TotalPrice+',' + rec.Status + ');"/>';
                    }
                    return str;
                }
            },
            { title: '客户名称', field: 'OrganName',width:120},
            { title: '单据编号',field: 'Number',width:130},
            { title: '商品信息',field: 'MaterialsList',width:180,formatter:function(value){
                return value.replace(",","，");
            }
            },
            { title: '单据日期 ',field: 'OperTime',width:130},
            { title: '操作员',field: 'OperPersonName',width:60},
            { title: '金额合计',field: 'TotalPrice',width:60},
            { title: '含税合计',field: 'TotalTaxLastMoney',width:60,formatter:function(value,rec){
                return (rec.DiscountMoney + rec.DiscountLastMoney).toFixed(2);
            }
            },
            { title: '优惠后金额',field: 'DiscountLastMoney',width:80},
            { title: '收款',field: 'ChangeAmount',width:50},
            { title: '状态',field: 'Status', width:70,align:"center",formatter:function(value){
                return value ? "<span style='color:green;'>已审核</span>":"<span style='color:red;'>未审核</span>";
            }
            }
        ]],
        toolbar:[
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

    orgDepotHead = "";
    depotHeadID = 0;
    initTableData_material("add"); //商品列表
    reject(); //撤销下、刷新商品列表
    $("#addOrgan").off("click").on("click",function(){
        $('#supplierDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加供应商');
    });
    url = path + '/depotHead/create.action';

    //零售单据修改收款时，自动计算找零
    if(listSubType == "零售" || listSubType == "零售退货") {
        $("#payType").val("现付");
        $("#OrganId").combobox("setValue", orgDefaultId); //自动默认选择非会员
        // 鼠标点下时清空选择项
        $("#OrganId").next().find("input").off("mousedown").on("mousedown",function(){
            $("#OrganId").combobox("setValue", "");
        });
        //当会员卡号长度超过10位后，自动点击下拉框，用于兼容刷卡器
        $("#OrganId").next().find("input").off("keyup").on("keyup",function(){
            var self = this;
            if($(this).val().length === 10){
                setTimeout(function(){
                    $(".combo-panel .combobox-item-selected").click();
                    //更新付款类型，加载会员的预付款的金额
                    for(var i=0; i<orgDefaultList.length; i++){
                        var rec = orgDefaultList[i];
                        if(rec.supplier == $(self).val()){
                            var option = "";
                            if(rec.supplier !== "非会员" && rec.advanceIn >0){
                                option = '<option value="预付款">预付款(' + rec.advanceIn + ')</option>';
                                option += '<option value="现付">现付</option>';
                            }
                            else {
                                option += '<option value="现付">现付</option>';
                            }
                            $("#payType").empty().append(option);
                        }
                    }
                },1000);
            }
        });
        var getAmount = $("#depotHeadFM .get-amount");
        var changeAmount = $("#depotHeadFM .change-amount");
        var backAmount = $("#depotHeadFM .back-amount");
        getAmount.val(0); changeAmount.val(0); backAmount.val(0); //时间初始化
        getAmount.off("keyup").on("keyup",function() {
            if(changeAmount.val()){
                backAmount.val((getAmount.val()-changeAmount.val()).toFixed(2));
            }
        });
    }
}

//初始化表格数据-商品列表-编辑状态
function initTableData_material(type,TotalPrice){
    var body,footer,input; //定义表格和文本框
    var ratio = 1; //比例-品名专用
    var ratioDepot = 1; //比例-仓库用
    var monthTime = getNowFormatMonth();
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
                            var opts = $(this).combobox('options');
                            return row[opts.textField].indexOf(q) >-1;
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
                                        //版本编号下拉框赋值
                                        target.combobox('setValue',tId);
                                        //规格赋值
                                        body.find("[field='Unit']").find(input).val(standard);
                                        //批发价赋值
                                        body.find("[field='WholesalePrice']").find(input).val(wholesalePrice);
                                        //零售价赋值
                                        body.find("[field='RetailPrice']").find(input).val(retailPrice);

                                        //价格回填
                                        if(customerType == '批发商'){
                                            //规格赋值
                                            body.find("[field='UnitPrice']").find(input).val(wholesalePrice);
                                        }else{
                                            body.find("[field='UnitPrice']").find(input).val(retailPrice);
                                        }
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
            { title: '规格',field: 'Unit',editor:'validatebox',width:60},
            { title: '单价(m)',field: 'UnitPrice',editor:'validatebox',width:60},
            { title: '数量',field: 'OperNumber',editor:'validatebox',width:60},
            { title: '总价',field: 'AllPrice',editor:'validatebox',width:75},
            { title: '备注',field: 'Remark',editor:'validatebox',width:120},
            { title: '批发价',field: 'WholesalePrice',editor:'validatebox',width:120},
            { title: '零售价',field: 'RetailPrice',editor:'validatebox',width:120}
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
            var AllPrice = 0;
            var TaxLastMoney = 0;
            var DiscountMoney = $("#DiscountMoney").val()-0; //优惠金额
            var DiscountLastMoney = $("#DiscountLastMoney").val()-0; //优惠后金额
            if(type === "edit") {
                AllPrice = TotalPrice;
                TaxLastMoney = DiscountMoney + DiscountLastMoney;
            }
            var array = [];
            array.push({
                "AllPrice": AllPrice,
                "TaxLastMoney": TaxLastMoney
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
                        var rows = $("#materialData").datagrid("getRows"); //这段代码是获取当前页的所有行
                        if(endEditing()){
                            var grid = $('#materialData');
                            for(var i = 0 ; i <rows.length;i++){
                                if (customerType == '批发商') {
                                    EasyUIDataGrid.setFieldValue('UnitPrice',rows[i].WholesalePrice,i,grid);
                                } else {
                                    EasyUIDataGrid.setFieldValue('UnitPrice',rows[i].RetailPrice,i,grid);
                                }
                            }
                        }
                    }
                },
                error: function() {
                    $.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
                }
            });
        }
    });
}
//新增
function append(){
    //第一步，判断是否选中客户
    customerId = $('#OrganId').combobox('getValue');
    if(customerId == ''){
        $.messager.alert('提示','请选择客户','info');
        return;
    }
    if (endEditing()) {
        $('#materialData').datagrid('appendRow', {});
        editIndex = $('#materialData').datagrid('getRows').length - 1;
        $('#materialData').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
        autoReckon();
    }
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

}