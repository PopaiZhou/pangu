	var editIndex = undefined;
	//初始化界面
	$(function(){
		var amountNum = ""; //单据编号开头字符
		var payTypeTitle = "";//收入 支出
		var itemType = true; //隐藏当前列
		var inOrOut = ""; //链接类型为收入或者支出
		getType();
		//初始化表格数据
		initTableData();
		initSystemData_account(); //账户数据
		initSelectInfo_account(); //账户信息
		//分页信息
		ininPager();
		//初始化下拉框信息
		initSelect();
		bindEvent();//绑定操作事件
		$("#searchBtn").click();
	});
	//根据单据名称获取类型
	function getType(){
		listTitle = $("#tablePanel").prev().text();
		if(listTitle === "收入单列表"){
			listType = "收入";
			searchType = '\'收入\',\'收款\'';
			itemType = false; //显示当前列
			payTypeTitle = "收入项目";
			amountNum = "SR";
			inOrOut = "in";
		}
		else if(listTitle === "支出单列表"){
			listType = "支出";
			searchType = '\'支出\'';
			itemType = false; //显示当前列
			payTypeTitle = "支出项目";
			amountNum = "ZC";
			inOrOut = "out";
		}
	}


	//初始化表格数据
	function initTableData(){
		$('#tableData').datagrid({
			//width:700,
			height:heightInfo,
			rownumbers: false,
			//动画效果
			animate:false,
			//选中单行
			singleSelect : true,
			collapsible:false,
			selectOnCheck:false,
			nowrap: false,//是否在同一行显示数据，默认为true 表示同一行显示
			//fitColumns:true,
			//单击行是否选中
			//checkOnSelect : false,
			pagination: true,
			//交替出现背景
			striped : true,
			//loadFilter: pagerFilter,
			pageSize: 5,
			pageList: initPageNum,
			columns:[[
				{ field: 'Id',width:35,align:"center",checkbox:true},
				{ title: '操作',field: 'op',align:"center",width:90,formatter:function(value,rec) {
						var str = '';
						var rowInfo = rec.Id + 'AaBb' + rec.BillNo+ 'AaBb' + rec.BillTime+ 'AaBb' + rec.Remark
							+ 'AaBb' + rec.AccountId+ 'AaBb' + rec.AccountName + 'AaBb' + rec.OrganId + 'AaBb' + rec.OrganName
							+ 'AaBb' + rec.HandsPersonId + 'AaBb' + rec.HandsPersonName + 'AaBb' + rec.ChangeAmount + 'AaBb' + rec.TotalPrice
							+ 'AaBb' + rec.SupplierId + 'AaBb' + rec.SupplierName + 'AaBb' + rec.UserId + 'AaBb' + rec.UserName + 'AaBb' + rec.subType;
						if(1 == value)
						{
							var orgId =  rec.OrganId ?  rec.OrganId : 0;
							if('收款' != rec.Type){
								str += '<img title="查看" src="' + path + '/js/easyui-1.3.5/themes/icons/list.png" style="cursor: pointer;" onclick="showAccountHead(\'' + rowInfo + '\');"/>&nbsp;&nbsp;&nbsp;';
								str += '<img title="编辑" src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png" style="cursor: pointer;" onclick="editAccountHead(\'' + rowInfo + '\');"/>&nbsp;&nbsp;&nbsp;';
								str += '<img title="删除" src="' + path + '/js/easyui-1.3.5/themes/icons/edit_remove.png" style="cursor: pointer;" onclick="deleteAccountHead('+ rec.Id +',' + orgId +',' + rec.TotalPrice + ');"/>';
							}
						}
						return str;
					}
				},
				{ title: '单据时间 ',field: 'BillTime',width:140},
				{ title: '项目名称',field: 'MaterialsList',width:180,formatter:function(value){
						return value.replace(/,/g,"，");
					}
				},
				{ title: '往来类型',field: 'subType',width:60,formatter:function(value){
						if(value === "organ"){
							return "客户";
						}
						if(value === "supplier"){
							return "供应商";
						}
						if(value === "user"){
							return "业务员";
						}
					}
				},
				{ title: '往来单位',field: 'SupplierName',width:80,
					styler: function(value,row,index){
						if(value === "" ){
							$("td[field='SupplierName']").hide();
							return 'color:red;display:none';
						}else{
							$("td[field='SupplierName']").show();
							return value;
						}
					}
				},
				{ title: '往来单位',field: 'OrganName',width:80,
					styler: function(value,row,index){
						if(value === "" ){
							$("td[field='OrganName']").hide();
							return 'color:red;display:none';
						}else{
							$("td[field='OrganName']").show();
							return value;
						}
					}
				},
				{ title: '往来单位',field: 'UserName',width:80,
					styler: function(value,row,index){
						if(value === "" ){
							$("td[field='UserName']").hide();
							return 'color:red;display:none';
						}else{
							$("td[field='UserName']").show();
							return value;
						}
					}
				},
				{ title: '单据编号',field: 'BillNo',width:140},
				{ title: '经手人',field: 'HandsPersonName',width:80},
				{ title: '合计',field: 'TotalPrice',width:80},
				{ title: '收入账户',field: 'AccountName',width:100},
				{ title: '备注',field: 'Remark',width:100}
			]],
			toolbar:[
				{
					id:'addAccountHead',
					text:'增加',
					iconCls:'icon-add',
					handler:function()
					{
						addAccountHead();
					}
				},
				{
					id:'deleteAccountHead',
					text:'删除',
					iconCls:'icon-remove',
					handler:function()
					{
						batDeleteAccountHead();
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
	//分页信息处理
	function ininPager(){
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
					showAccountHeadDetails(pageNum,pageSize);
				}
			});
		}
		catch (e)
		{
			$.messager.alert('异常处理提示',"分页信息异常 :  " + e.name + ": " + e.message,'error');
		}
	}
	function showAccountHeadDetails(pageNo,pageSize){
		$.ajax({
			type:"post",
			url: path + "/accountHead/findBy.action",
			dataType: "json",
			data: ({
				Type: searchType,
				BillNo:$.trim($("#searchBillNo").val()),
				BeginTime:$("#searchBeginTime").val(),
				EndTime:$("#searchEndTime").val(),
				pageNo:pageNo,
				pageSize:pageSize
			}),
			success: function (data)
			{
				$("#tableData").datagrid('loadData',data);
			},
			//此处添加错误处理
			error:function()
			{
				$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
				return;
			}
		});
	}

	//增加
	function addAccountHead(){
		$("#clientIp").val(clientIp);
		$('#accountHeadFM').form('clear');
		var thisDateTime = getNowFormatDateTime(); //当前时间
		$("#BillTime").val(thisDateTime);
		var thisNumber = getNowFormatDateNum(); //根据时间生成编号
		$("#BillNo").val(amountNum + thisNumber).focus();
		var addTitle = listTitle.replace("列表","信息");
		$('#accountHeadDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/edit_add.png"/>&nbsp;增加' + addTitle);
		$(".window-mask").css({ width: webW ,height: webH});

		orgAccountHead = "";
		accountHeadID = 0;
		initTableData_account("add"); //明细列表
		reject(); //撤销下、刷新材料列表
		url = path + '/accountHead/createNew.action';
	}
	//初始化表格数据-明细列表-编辑状态
	function initTableData_account(type,TotalPrice){
		$('#accountData').datagrid({
			height:280,
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
				{ title: payTypeTitle,field: 'InOutItemId',width:230,hidden:itemType,
					formatter:function(value,row,index){
						return row.InOutItemName;
					},
					editor:{
						type:'combobox',
						options:{
							valueField:'Id',
							textField:'InOutItemName',
							method:'get',
							url: path + "/inOutItem/findBySelect.action?type=" + inOrOut
						}
					}
				},
				{ title: '金额',field: 'EachAmount',editor:'validatebox',width:70},
				{ title: '备注',field: 'Remark',editor:'validatebox',width:150}
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
			url: path + '/accountItem/findBy.action?HeaderId=' + accountHeadID,
			dataType: "json",
			success: function (res) {
				var EachAmount = 0;
				if(type === "edit") {
					EachAmount = TotalPrice;
				}
				var array = [];
				array.push({
					"EachAmount": EachAmount
				});
				res.footer = array;
				$("#accountData").datagrid('loadData',res);
			},
			error:function() {
				$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
			}
		});
	}
	//新增
	function append()  {
		if(!$('#Type').combobox('getValue')){
			$.messager.alert('提示','请选择往来类型！','warning');
			return;
		}
		if($('#Type').combobox('getValue') === 'supplier'){
			if(!$('#SupplierId').combobox('getValue')){
				$.messager.alert('提示','请选择供应商！','warning');
				return;
			}
		}
		if($('#Type').combobox('getValue') === 'organ'){
			if(!$('#OrganId').combobox('getValue')){
				$.messager.alert('提示','请选择客户！','warning');
				return;
			}
		}
		if($('#Type').combobox('getValue') === 'user'){
			if(!$('#UserId').combobox('getValue')){
				$.messager.alert('提示','请选择业务员！','warning');
				return;
			}
		}

		if (endEditing()) {
			$('#accountData').datagrid('appendRow', {});
			editIndex = $('#accountData').datagrid('getRows').length - 1;
			$('#accountData').datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
			autoReckon();
		}
	}
	//单击
	function onClickRow(index) {
		if (editIndex != index) {
			if (endEditing()) {
				$('#accountData').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
				editIndex = index;
				autoReckon();
			} else {
				$('#accountData').datagrid('selectRow', editIndex);
			}
		}
	}
	//撤销
	function reject() {
		$('#accountData').datagrid('rejectChanges');
		editIndex = undefined;
	}
	//删除
	function removeit() {
		if (editIndex == undefined) { return }
		$('#accountData').datagrid('cancelEdit', editIndex)
			.datagrid('deleteRow', editIndex);
		editIndex = undefined;
	}

	//初始化各下拉框信息信息
	function initSelect(){
		//初始化 收款状态搜索 下拉框
		$('#Type').combobox({
			url: path +'/js/pages/financial/financialType.json',
			valueField:'value',
			textField:'name',
			onSelect:function(record){
				//供应商，显示供应商下拉列表
				if(record.value === 'supplier'){
					//隐藏客户信息
					$("#OrganIdLabel").hide();
					$("#OrganIdName").hide();
					//显示供应商信息
					$("#SupplierIdLabel").show();
					$("#SupplierIdName").show();
					//隐藏业务员信息
					$("#UserIdLabel").hide();
					$("#UserIdName").hide();
				}else if(record.value === 'organ'){
					//客户，显示客户下拉列表
					$("#OrganIdLabel").show();
					$("#OrganIdName").show();
					//隐藏供应商
					$("#SupplierIdLabel").hide();
					$("#SupplierIdName").hide();
					//隐藏业务员
					$("#UserIdLabel").hide();
					$("#UserIdName").hide();
				}else if(record.value === 'user'){
					//客户，隐藏客户下拉列表
					$("#OrganIdLabel").hide();
					$("#OrganIdName").hide();
					//隐藏供应商
					$("#SupplierIdLabel").hide();
					$("#SupplierIdName").hide();
					//显示业务员
					$("#UserIdLabel").show();
					$("#UserIdName").show();
				}
			}
		});
		//客户下拉框
		$('#OrganId').combobox({
			url: path + "/customer/findBySelect_sup.action",
			valueField:'id',
			textField:'customerName',
			filter: function (q, row) {
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) > -1;
			}
		});
		//供应商
		$('#SupplierId').combobox({
			url: path + "/supplier/findBySelect_sup.action",
			valueField:'id',
			textField:'supplier',
			filter: function (q, row) {
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) > -1;
			}
		});
		//业务员
		$('#UserId').combobox({
			url: path + "/user/findBySelect_sup.action",
			valueField:'id',
			textField:'username',
			filter: function (q, row) {
				var opts = $(this).combobox('options');
				return row[opts.textField].indexOf(q) > -1;
			}
		});
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
		if(accountList !=null)
		{
			options = "";
			for(var i = 0 ;i < accountList.length; i++)
			{
				var account = accountList[i];
				options += '<option value="' + account.id + '">' + account.name + '</option>';
			}
			$("#AccountId").empty().append(options);
		}
	}

	//结束编辑
	function endEditing() {
		var edField = "";
		if(!itemType){
			edField = "InOutItemId";
			edName = "InOutItemName";
		}
		else {
			edField = "AccountId";
			edName = "AccountName";
		}
		if (editIndex == undefined) { return true }
		if ($('#accountData').datagrid('validateRow', editIndex)) {
			var ed = $('#accountData').datagrid('getEditor', {index: editIndex, field: edField});
			var textName = $(ed.target).combobox('getText');
			$('#accountData').datagrid('getRows')[editIndex][edName] = textName;
			$('#accountData').datagrid('endEdit', editIndex);
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
			var body =$("#accountHeadFM .datagrid-body");
			var footer =$("#accountHeadFM .datagrid-footer");
			var input = ".datagrid-editable-input";

			//修改金额，自动计算单价和合计
			body.find("[field='EachAmount']").find(input).off("keyup").on("keyup",function(){
				var TotalPrice = 0;
				var EachAmount =$(this).val()-0; //金额
				body.find("[field='EachAmount']").each(function(){
					if($(this).find("div").text()!==""){
						TotalPrice = TotalPrice + parseFloat($(this).find("div").text().toString());
					}
				});
				TotalPrice = TotalPrice + EachAmount;
				footer.find("[field='EachAmount']").find("div").text((TotalPrice).toFixed(2));
				$("#ChangeAmount").val((TotalPrice).toFixed(2));
			});
		},500);
	}


	//绑定操作事件
	function bindEvent(){
		//搜索处理
		$("#searchBtn").unbind().bind({
			click:function()
			{
				showAccountHeadDetails(1,initPageSize);
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
		//重置按钮
		$("#searchResetBtn").unbind().bind({
			click:function(){
				$("#searchBillNo").val("");
				$("#searchBeginTime").val("");
				$("#searchEndTime").val("");
				//加载完以后重新初始化
				$("#searchBtn").click();
			}
		});

		//保存信息
		$("#saveAccountHead").unbind().bind({
			click:function()
			{
				if(!$('#accountHeadFM').form('validate')){
					return;
				}else{
					if(listTitle === "收入单列表"){
						if(!$('#AccountId').val()){
							$.messager.alert('提示','请选择收款账户！','warning');
							return;
						}
					}
					var ChangeAmount = $.trim($("#ChangeAmount").val());
					var TotalPrice = $("#accountHeadFM .datagrid-footer [field='EachAmount'] div").text();
					var OrganId = $('#OrganId').combobox('getValue');
					var SupplierId = $('#SupplierId').combobox('getValue');
					var UserId = $('#UserId').combobox('getValue');
					//保存单位信息
					$.ajax({
						type:"post",
						url: url,
						dataType: "json",
						async :  false,
						data: ({
							Type: listType,
							supType : $("#Type").combobox('getValue'),
							BillNo : $.trim($("#BillNo").val()),
							BillTime : $.trim($("#BillTime").val()),
							AccountId: $.trim($("#AccountId").val()),
							ChangeAmount: ChangeAmount, //付款/收款/优惠/实付
							TotalPrice: TotalPrice, //合计
							OrganId: OrganId, //客户
							supplierId : SupplierId, //供应商
							UserId : UserId, //业务员
							HandsPersonId: $.trim($("#HandsPersonId").val()),
							Remark: $.trim($("#Remark").val()),
							clientIp: clientIp
						}),
						success: function (tipInfo)
						{
							if(tipInfo)
							{
								//保存明细记录
								if(accountHeadID ==0)
								{
									getMaxId(); //查找最大的Id
									accept(accountHeadMaxId,listType); //新增
								}
								else
								{
									accept(accountHeadID,listType); //修改
								}

								$('#accountHeadDlg').dialog('close');
								var opts = $("#tableData").datagrid('options');
								showAccountHeadDetails(opts.pageNumber,opts.pageSize);
							}
							else
							{
								$.messager.show({
									title: '错误提示',
									msg: '保存信息失败，请稍后重试!'
								});
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
		});
		//初始化键盘enter事件
		$(document).keydown(function(event)
		{
			//兼容 IE和firefox 事件
			var e = window.event || event;
			var k = e.keyCode||e.which||e.charCode;
			//兼容 IE,firefox 兼容
			var obj = e.srcElement ? e.srcElement : e.target;
			//绑定键盘事件为 id是指定的输入框才可以触发键盘事件 13键盘事件 ---遗留问题 enter键效验 对话框会关闭问题
			if(k == "13"&&(obj.id=="BillNo"||obj.id=="BillTime"))
			{
				$("#saveAccountHead").click();
			}
			//搜索按钮添加快捷键
			if(k == "13"&&(obj.id=="searchBillNo"))
			{
				$("#searchBtn").click();
			}
		});
	}
	//保存
	function accept(accepId,listType) {
		append();
		removeit();
		if ($("#accountData").datagrid('getChanges').length) {
			if (!CheckData())
				return false;
			var inserted = $("#accountData").datagrid('getChanges', "inserted");
			var deleted = $("#accountData").datagrid('getChanges', "deleted");
			var updated = $("#accountData").datagrid('getChanges', "updated");
			$.ajax({
				type: "post",
				url: path + "/accountItem/saveDetials.action",
				data: {
					Inserted: JSON.stringify(inserted),
					Deleted: JSON.stringify(deleted),
					Updated: JSON.stringify(updated),
					HeaderId: accepId,
					ListType: listType,
					clientIp: clientIp
				},
				success: function (tipInfo)
				{
					if (tipInfo) {
						$.messager.alert('提示','保存成功！','info');
					}
					else
						$.messager.alert('提示','保存失败！','error');

				},
				error: function (XmlHttpRequest, textStatus, errorThrown)
				{
					$.messager.alert('提示',XmlHttpRequest.responseText,'error');
				}
			});
		}
		if (endEditing()) {
			$('#accountData').datagrid('acceptChanges');
		}
	}
	//判断
	function CheckData() {
		var row = $('#accountData').datagrid('getRows');
		var totalRowNum = "";
		for (var i = 0; i < row.length; i++) {
			if(!itemType){
				if (row[i].InOutItemId == "") {
					totalRowNum += (i + 1) + "、";
				}
			}
			else{
				if (row[i].AccountId == "") {
					totalRowNum += (i + 1) + "、";
				}
			}
		}
		if (totalRowNum != "") {
			var totalRowNum = totalRowNum.substring(0, totalRowNum.length - 1);
			$.messager.alert('提示',"第" + totalRowNum + "行数据填写不完整！",'info');
			return false;
		}
		return true;
	}
	//获取MaxId
	function getMaxId(){
		var accountHeadMax=null;
		$.ajax({
			type:"post",
			url: path + "/accountHead/getMaxId.action",
			//设置为同步
			async:false,
			dataType: "json",
			success: function (systemInfo)
			{
				if(systemInfo)
				{
					accountHeadMax = systemInfo.showModel.map.accountHeadMax;
					var msgTip = systemInfo.showModel.msgTip;
					if(msgTip == "exceptoin")
					{
						$.messager.alert('提示','查找最大的Id异常,请与管理员联系！','error');
						return;
					}
				}
				else
				{
					accountHeadMax=null;
				}
			}
		});

		if(accountHeadMax !=null)
		{
			if(accountHeadMax.length>0)
			{
				accountHeadMaxId=accountHeadMax[0];
			}
		}
	}
	//查看信息
	function showAccountHead(accountHeadTotalInfo){
		var accountHeadInfo = accountHeadTotalInfo.split("AaBb");
		var subType = accountHeadInfo[16];
		//供应商
		if(subType === 'supplier'){
			$('#TypeShow').text('供应商');
			$('#OrganIdShow').text(accountHeadInfo[13]);
		}
		//客户
		if(subType === 'organ'){
			$('#TypeShow').text('客户');
			$('#OrganIdShow').text(accountHeadInfo[7]);
		}
		//供应商
		if(subType === 'user'){
			$('#TypeShow').text('业务员');
			$('#OrganIdShow').text(accountHeadInfo[15]);
		}

		$("#BillNoShow").text(accountHeadInfo[1]);
		$("#BillTimeShow").text(accountHeadInfo[2]);
		$("#RemarkShow").text(accountHeadInfo[3]);
		$("#AccountIdShow").text(accountHeadInfo[5]);

		$("#HandsPersonIdShow").text(accountHeadInfo[9]);
		$("#ChangeAmountShow").text(accountHeadInfo[10]);
		var TotalPrice = accountHeadInfo[11];
		var showTitle = listTitle.replace("列表","信息");
		$('#accountHeadDlgShow').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/list.png"/>&nbsp;查看' + showTitle);
		$(".window-mask").css({ width: webW ,height: webH});

		accountHeadID = accountHeadInfo[0];
		initTableData_account_show(TotalPrice); //明细列表-查看状态
	}
	//编辑信息
	function editAccountHead(accountHeadTotalInfo){
		var accountHeadInfo = accountHeadTotalInfo.split("AaBb");
		var subType = accountHeadInfo[16];
		$("#Type").combobox({disabled: true});
		$('#Type').combobox('setValue', subType);
		//供应商
		if(subType === 'supplier'){
			//隐藏客户信息
			$("#OrganIdLabel").hide();
			$("#OrganIdName").hide();
			//显示供应商信息
			$("#SupplierIdLabel").show();
			$("#SupplierIdName").show();
			//隐藏业务员信息
			$("#UserIdLabel").hide();
			$("#UserIdName").hide();

			$("#SupplierId").combobox({disabled: true});
			$('#SupplierId').combobox('setValue', accountHeadInfo[12]);
		}
		//客户
		if(subType === 'organ'){
			//隐藏客户信息
			$("#OrganIdLabel").show();
			$("#OrganIdName").show();
			//显示供应商信息
			$("#SupplierIdLabel").hide();
			$("#SupplierIdName").hide();
			//隐藏业务员信息
			$("#UserIdLabel").hide();
			$("#UserIdName").hide();

			$("#OrganId").combobox({disabled: true});
			$('#OrganId').combobox('setValue', accountHeadInfo[6]);
		}
		//供应商
		if(subType === 'user'){
			//隐藏客户信息
			$("#OrganIdLabel").hide();
			$("#OrganIdName").hide();
			//显示供应商信息
			$("#SupplierIdLabel").hide();
			$("#SupplierIdName").hide();
			//隐藏业务员信息
			$("#UserIdLabel").show();
			$("#UserIdName").show();

			$("#UserId").combobox({disabled: true});
			$('#UserId').combobox('setValue', accountHeadInfo[14]);
		}
		$("#clientIp").val(clientIp);
		$("#BillNo").val(accountHeadInfo[1]);
		$("#BillTime").val(accountHeadInfo[2]);
		$("#Remark").val(accountHeadInfo[3]);
		$("#AccountId").val(accountHeadInfo[4]);
		$("#HandsPersonId").val(accountHeadInfo[8]);
		$("#ChangeAmount").val(accountHeadInfo[10]);
		var TotalPrice = accountHeadInfo[11];
		preTotalPrice = accountHeadInfo[11]; //记录前一次合计金额，用于收预付款
		var editTitle = listTitle.replace("列表","信息");
		$('#accountHeadDlg').dialog('open').dialog('setTitle','<img src="' + path + '/js/easyui-1.3.5/themes/icons/pencil.png"/>&nbsp;编辑' + editTitle);
		$(".window-mask").css({ width: webW ,height: webH});
		accountHeadID = accountHeadInfo[0];

		initTableData_account("edit",TotalPrice); //明细列表
		reject(); //撤销下、刷新列表
		url = path + '/accountHead/updateNew.action?accountHeadID=' + accountHeadInfo[0]+'&preTotalPrice='+preTotalPrice+'&preAccountId='+accountHeadInfo[4];
	}
	//初始化表格数据-明细列表-查看状态
	function initTableData_account_show(TotalPrice){
		$('#accountDataShow').datagrid({
			height:280,
			rownumbers: true,
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
			columns:[[
				{ title: payTypeTitle, field: 'InOutItemName', width:230, hidden:itemType},
				{ title: '金额',field: 'EachAmount',width:70},
				{ title: '备注',field: 'Remark',width:150}
			]],
			onLoadError:function()
			{
				$.messager.alert('页面加载提示','页面加载异常，请稍后再试！','error');
				return;
			}
		});
		$.ajax({
			type:"post",
			url: path + '/accountItem/findBy.action?HeaderId=' + accountHeadID,
			dataType: "json",
			success: function (res) {
				var EachAmount = TotalPrice;
				var array = [];
				array.push({
					"EachAmount": EachAmount
				});
				res.footer = array;
				$("#accountDataShow").datagrid('loadData',res);
			},
			error:function() {
				$.messager.alert('查询提示','查询数据后台异常，请稍后再试！','error');
			}
		});
	}
	//删除财务信息
	function deleteAccountHead(accountHeadID, thisOrganId, totalPrice){
		$.messager.confirm('删除确认','确定要删除此财务信息吗？',function(r)
		{
			if (r)
			{
				$.ajax({
					type:"post",
					url: path + "/accountHead/batchDeleteNew.action",
					dataType: "json",
					data: ({
						accountHeadIDs : accountHeadID,
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
							$.messager.alert('删除提示','删除财务信息失败，请稍后再试！','error');
					},
					//此处添加错误处理
					error:function()
					{
						$.messager.alert('删除提示','删除财务信息异常，请稍后再试！','error');
						return;
					}
				});
			}
		});
	}
	//批量删除财务信息
	function batDeleteAccountHead(){
		var row = $('#tableData').datagrid('getChecked');
		if(row.length == 0)
		{
			$.messager.alert('删除提示','没有记录被选中！','info');
			return;
		}
		if(row.length > 0)
		{
			$.messager.confirm('删除确认','确定要删除选中的' + row.length + '条财务信息吗？',function(r)
			{
				if (r)
				{
					var ids = "";
					for(var i = 0;i < row.length; i ++) {
						if(row[i].Type === "收款"){
							$.messager.alert('删除提示','版本销售收入不能进行删除，请在下单模块处理！','warning');
							return;
						}
						if(i == row.length-1)
						{
							ids += row[i].Id;
							break;
						}
						ids += row[i].Id + ",";
					}
					//批量删除
					$.ajax({
						type:"post",
						url: path + "/accountHead/batchDeleteNew.action",
						dataType: "json",
						async :  false,
						data: ({
							accountHeadIDs : ids,
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
								$.messager.alert('删除提示','删除财务信息失败，请稍后再试！','error');
						},
						//此处添加错误处理
						error:function()
						{
							$.messager.alert('删除提示','删除财务信息异常，请稍后再试！','error');
							return;
						}
					});
				}
			});
		}
	}