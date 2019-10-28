package com.jsh.action.materials;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Account;
import com.jsh.model.po.AccountHead;
import com.jsh.model.po.AccountItem;
import com.jsh.model.po.Basicuser;
import com.jsh.model.po.Customer;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.Supplier;
import com.jsh.model.vo.materials.AccountHeadModel;
import com.jsh.service.basic.AccountIService;
import com.jsh.service.materials.AccountHeadIService;
import com.jsh.service.materials.AccountItemIService;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 财务表头管理
 * @author jishenghua  qq:752718920
 */
@SuppressWarnings("serial")
public class AccountHeadAction extends BaseAction<AccountHeadModel> {
    private AccountHeadIService accountHeadService;
    private AccountItemIService accountItemService;
    private AccountIService accountService;
    private AccountHeadModel model = new AccountHeadModel();

    /*
     * 获取MaxId
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public String getMaxId() {
        Map<String, List> mapData = model.getShowModel().getMap();
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
            accountHeadService.find(pageUtil, "maxId");
            mapData.put("accountHeadMax", pageUtil.getPageList());
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>查找最大的Id信息异常", e);
            model.getShowModel().setMsgTip("exceptoin");
        }
        return SUCCESS;
    }

    /**
     * 增加财务
     *
     * @return
     */
    public void create() {
        Log.infoFileSync("==================开始调用增加财务信息方法create()===================");
        Boolean flag = false;
        try {
            AccountHead accountHead = new AccountHead();
            accountHead.setType(model.getType());
            if (model.getOrganId() != null) {
                accountHead.setOrganId(new Customer(model.getOrganId()));
            }
            if (model.getHandsPersonId() != null) {
                accountHead.setHandsPersonId(new Basicuser(model.getHandsPersonId()));
            }else{
                accountHead.setHandsPersonId(new Basicuser(getUser().getId()));
            }
            accountHead.setChangeAmount(model.getChangeAmount() == null ? 0 : model.getChangeAmount());
            accountHead.setTotalPrice(model.getTotalPrice());
            if (model.getAccountId() != null) {
                accountHead.setAccountId(new Account(model.getAccountId()));
            }
            accountHead.setBillNo(model.getBillNo());
            try {
                accountHead.setBillTime(new Timestamp(Tools.parse(model.getBillTime(), "yyyy-MM-dd HH:mm:ss").getTime()));
            } catch (ParseException e) {
                Log.errorFileSync(">>>>>>>>>>>>>>>解析购买日期格式异常", e);
            }
            accountHead.setRemark(model.getRemark());
            accountHeadService.create(accountHead);

            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加财务信息异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>增加财务信息回写客户端结果异常", e);
            }
        }

        logService.create(new Logdetails(getUser(), "增加财务", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "增加财务编号为  " + model.getBillNo() + " " + tipMsg + "！", "增加财务" + tipMsg));
        Log.infoFileSync("==================结束调用增加财务方法create()===================");
    }

    /**
     * 增加财务新方法
     * @author zhoujp
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void createNew() {
        Log.infoFileSync("==================开始调用增加财务信息方法create()===================");
        Boolean flag = false;
        try {
            AccountHead accountHead = new AccountHead();
            String subType = model.getSupType();
            //供应商
            if(subType.equalsIgnoreCase("supplier")){
                if(model.getSupplierId() != null){
                    accountHead.setSupplierId(new Supplier(Long.parseLong(model.getSupplierId())));
                }
            }
            //客户
            if(subType.equalsIgnoreCase("organ")){
                if (model.getOrganId() != null) {
                    accountHead.setOrganId(new Customer(model.getOrganId()));
                }
            }
            //业务员
            if(subType.equalsIgnoreCase("user")){
                if (model.getUserId() != null) {
                    accountHead.setUserId(new Basicuser(model.getUserId()));
                }
            }
            accountHead.setType(model.getType());

            if (model.getHandsPersonId() != null) {
                accountHead.setHandsPersonId(new Basicuser(model.getHandsPersonId()));
            }else{
                accountHead.setHandsPersonId(new Basicuser(getUser().getId()));
            }
            accountHead.setChangeAmount(model.getChangeAmount() == null ? 0 : model.getChangeAmount());
            accountHead.setTotalPrice(model.getTotalPrice());
            if (model.getAccountId() != null) {
                accountHead.setAccountId(new Account(model.getAccountId()));
            }
            accountHead.setBillNo(model.getBillNo());
            try {
                accountHead.setBillTime(new Timestamp(Tools.parse(model.getBillTime(), "yyyy-MM-dd HH:mm:ss").getTime()));
            } catch (ParseException e) {
                Log.errorFileSync(">>>>>>>>>>>>>>>解析购买日期格式异常", e);
            }
            accountHead.setRemark(model.getRemark());
            accountHeadService.create(accountHead);
            if("支出".equalsIgnoreCase(model.getType())){
                //如果是支出，需要减掉相关金额
                accountService.subCurrentAmount(model.getAccountId(),model.getTotalPrice());
            }else{
                accountService.addCurrentAmount(model.getAccountId(),model.getTotalPrice());
            }


            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException | JshException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加财务信息异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>增加财务信息回写客户端结果异常", e);
            }
        }

        logService.create(new Logdetails(getUser(), "增加财务", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "增加财务编号为  " + model.getBillNo() + " " + tipMsg + "！", "增加财务" + tipMsg));
        Log.infoFileSync("==================结束调用增加财务方法create()===================");
    }

    /**
     * 删除财务
     *
     * @return
     */
    public String delete() {
        Log.infoFileSync("====================开始调用删除财务信息方法delete()================");
        try {
            accountHeadService.delete(model.getAccountHeadID());
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getAccountHeadID() + "  的财务异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        model.getShowModel().setMsgTip(tipMsg);
        logService.create(new Logdetails(getUser(), "删除财务", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "删除财务ID为  " + model.getAccountHeadID() + " " + tipMsg + "！", "删除财务" + tipMsg));
        Log.infoFileSync("====================结束调用删除财务信息方法delete()================");
        return SUCCESS;
    }

    /**
     * 更新财务
     *
     * @return
     */
    public void update() {
        Boolean flag = false;
        try {
            AccountHead accountHead = accountHeadService.get(model.getAccountHeadID());
            accountHead.setType(model.getType());
            if (model.getOrganId() != null) {
                accountHead.setOrganId(new Customer(model.getOrganId()));
            }
            if (model.getHandsPersonId() != null) {
                accountHead.setHandsPersonId(new Basicuser(model.getHandsPersonId()));
            }
            accountHead.setChangeAmount(model.getChangeAmount() == null ? 0 : model.getChangeAmount());
            accountHead.setTotalPrice(model.getTotalPrice());
            if (model.getAccountId() != null) {
                accountHead.setAccountId(new Account(model.getAccountId()));
            }
            accountHead.setBillNo(model.getBillNo());
            try {
                accountHead.setBillTime(new Timestamp(Tools.parse(model.getBillTime(), "yyyy-MM-dd HH:mm:ss").getTime()));
            } catch (ParseException e) {
                Log.errorFileSync(">>>>>>>>>>>>>>>解析购买日期格式异常", e);
            }
            accountHead.setRemark(model.getRemark());
            accountHeadService.update(accountHead);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>修改财务ID为 ： " + model.getAccountHeadID() + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>修改财务回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新财务", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "更新财务ID为  " + model.getAccountHeadID() + " " + tipMsg + "！", "更新财务" + tipMsg));
    }

    /**
     * 更新财务
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateNew() {
        Boolean flag = false;
        try {
            AccountHead accountHead = accountHeadService.get(model.getAccountHeadID());
            accountHead.setType(model.getType());
            if (model.getOrganId() != null) {
                accountHead.setOrganId(new Customer(model.getOrganId()));
            }
            if (model.getHandsPersonId() != null) {
                accountHead.setHandsPersonId(new Basicuser(model.getHandsPersonId()));
            }
            accountHead.setChangeAmount(model.getChangeAmount() == null ? 0 : model.getChangeAmount());
            accountHead.setTotalPrice(model.getTotalPrice());
            if (model.getAccountId() != null) {
                accountHead.setAccountId(new Account(model.getAccountId()));
            }
            accountHead.setBillNo(model.getBillNo());
            try {
                accountHead.setBillTime(new Timestamp(Tools.parse(model.getBillTime(), "yyyy-MM-dd HH:mm:ss").getTime()));
            } catch (ParseException e) {
                Log.errorFileSync(">>>>>>>>>>>>>>>解析购买日期格式异常", e);
            }
            accountHead.setRemark(model.getRemark());
            //原来的金额
            double oldAmount = model.getPreTotalPrice();
            double newAmount = model.getTotalPrice();
            BigDecimal bigOldAmount = new BigDecimal(oldAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
            BigDecimal bigNewAmount = new BigDecimal(oldAmount).setScale(2,BigDecimal.ROUND_HALF_UP);

            long oldAccountId = model.getPreAccountId();
            long newAccountId = model.getAccountId();
            //如果没有修改对应的收款账户，那么直接更新新的金额即可
            if("支出".equalsIgnoreCase(accountHead.getType())){
                //如果是支出,需要减相关金额
                if(oldAccountId == newAccountId){
                    accountService.subCurrentAmount(newAccountId,bigNewAmount.subtract(bigOldAmount).doubleValue());
                }else{
                    //如果前后收款账户不一致，那么需要先加回去原来的账户金额，再减掉后来新的账户金额
                    //比如原来 支付宝收20元
                    //修改后  微信收40元
                    //那么需要先 加上支付宝20元 再微信减去40元
                    accountService.addCurrentAmount(oldAccountId,oldAmount);
                    //再减去微信的40元
                    accountService.subCurrentAmount(newAccountId,newAmount);
                }
            }else{
                if(oldAccountId == newAccountId){
                    //新金额= 修改后的金额 - 原来的金额
                    //如果原来 20元 后来变成40元 那么新金额 = 40 - 20 = 20元
                    //如果原来 40元 后来变成20元 那么新金额 = 20 - 40 = -20元
                    accountService.addCurrentAmount(newAccountId,bigNewAmount.subtract(bigOldAmount).doubleValue());
                }else{
                    //如果前后收款账户不一致，那么需要先减掉原来的账户金额，再加上后来新的账户金额
                    //比如原来 支付宝收20元
                    //修改后  微信收40元
                    //那么需要先 减去支付宝20元 再微信加上40元
                    accountService.subCurrentAmount(oldAccountId,oldAmount);
                    //再加上微信的40元
                    accountService.addCurrentAmount(newAccountId,newAmount);
                }
            }
            accountHeadService.update(accountHead);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException | JshException e) {
            Log.errorFileSync(">>>>>>>>>>>>>修改财务ID为 ： " + model.getAccountHeadID() + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        }  finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>修改财务回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新财务", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "更新财务ID为  " + model.getAccountHeadID() + " " + tipMsg + "！", "更新财务" + tipMsg));
    }

    /**
     * 批量删除指定ID财务
     *
     * @return
     */
    public String batchDelete() {
        try {
            accountHeadService.batchDelete(model.getAccountHeadIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量删除财务ID为：" + model.getAccountHeadIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }

        logService.create(new Logdetails(getUser(), "批量删除财务", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量删除财务ID为  " + model.getAccountHeadIDs() + " " + tipMsg + "！", "批量删除财务" + tipMsg));
        return SUCCESS;
    }
    /**
     * 批量删除指定ID
     * 新方法
     *
     * @return
     */
    public String batchDeleteNew() {
        try {
            //删除流程
            accountHeadService.batchDeleteByIds(model.getAccountHeadIDs(),model.getType());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量删除财务ID为：" + model.getAccountHeadIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }

        logService.create(new Logdetails(getUser(), "批量删除财务", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量删除财务ID为  " + model.getAccountHeadIDs() + " " + tipMsg + "！", "批量删除财务" + tipMsg));
        return SUCCESS;
    }

    /**
     * 批量删除指定ID财务
     *
     * @return
     */
    public String batchDeleteByBillNos() {
        try {
            accountHeadService.batchDeleteByBillNos(model.getBillNo());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException | JshException e) {
            Log.errorFileSync(">>>>>>>>>>>批量删除财务ID为：" + model.getAccountHeadIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }

        logService.create(new Logdetails(getUser(), "批量删除财务", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量删除财务ID为  " + model.getAccountHeadIDs() + " " + tipMsg + "！", "批量删除财务" + tipMsg));
        return SUCCESS;
    }

    /**
     * 查找财务信息
     *
     * @return
     */
    public void findBy() {
        try {
            //如果有按照项目名称搜索项
            if(StringUtils.isNotEmpty(model.getMaterialsList())){
                Map<String,Object> conditon = new HashMap<>();
                conditon.put("InOutItemId_s_eq", model.getMaterialsList());
                List<AccountItem> itemList = accountItemService.find(conditon);
                List<String> headIds = new ArrayList<>();
                if(itemList.size() > 0){
                    for(AccountItem accountItem : itemList){
                        headIds.add(accountItem.getHeaderId().getId().toString());
                    }
                    model.setMaterialsList(StringUtils.join(headIds.toArray(), ","));
                }
                else{
                    model.setMaterialsList("-1");
                }
            }
            PageUtil<AccountHead> pageUtil = new PageUtil<AccountHead>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            accountHeadService.find(pageUtil);
            List<AccountHead> dataList = pageUtil.getPageList();

            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (AccountHead accountHead : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("Id", accountHead.getId());
                    item.put("Type", accountHead.getType());
                    item.put("OrganId", accountHead.getOrganId() == null ? "" : accountHead.getOrganId().getId());
                    item.put("OrganName", accountHead.getOrganId() == null ? "" : accountHead.getOrganId().getCustomerName());
                    item.put("HandsPersonId", accountHead.getHandsPersonId() == null ? "" : accountHead.getHandsPersonId().getId());
                    item.put("HandsPersonName", accountHead.getHandsPersonId() == null ? "" : accountHead.getHandsPersonId().getUsername());
                    item.put("AccountId", accountHead.getAccountId() == null ? "" : accountHead.getAccountId().getId());
                    item.put("AccountName", accountHead.getAccountId() == null ? "" : accountHead.getAccountId().getName());
                    item.put("BillNo", accountHead.getBillNo());
                    item.put("BillTime", Tools.getCenternTime(accountHead.getBillTime()));
                    item.put("ChangeAmount", accountHead.getChangeAmount() == null ? "" : Math.abs(accountHead.getChangeAmount()));
                    item.put("TotalPrice", accountHead.getTotalPrice() == null ? "" : Math.abs(accountHead.getTotalPrice()));
                    item.put("Remark", accountHead.getRemark() == null ? "" : accountHead.getRemark() );
                    item.put("op", 1);

                    item.put("SupplierId", accountHead.getSupplierId() == null ? "" : accountHead.getSupplierId().getId());
                    item.put("SupplierName", accountHead.getSupplierId() == null ? "" : accountHead.getSupplierId().getSupplier());

                    item.put("UserId", accountHead.getUserId() == null ? "" : accountHead.getUserId().getId());
                    item.put("UserName", accountHead.getUserId() == null ? "" : accountHead.getUserId().getUsername());
                    if(accountHead.getOrganId() != null){
                        item.put("subType", "organ");
                    }
                    if(accountHead.getSupplierId() != null){
                        item.put("subType", "supplier");
                    }
                    if(accountHead.getUserId() != null){
                        item.put("subType", "user");
                    }
                    if(accountHead.getOrganId() == null && accountHead.getSupplierId() ==null && accountHead.getUserId() ==null){
                        item.put("subType", "trans");
                        item.put("UserName", "未知");
                    }
                    if("收款".equalsIgnoreCase(accountHead.getType())){
                        item.put("MaterialsList", "主营业务收入");
                    }else if("互转支出".equalsIgnoreCase(accountHead.getType())){
                        item.put("MaterialsList", "互转支出");
                    }else if("互转收入".equalsIgnoreCase(accountHead.getType())){
                        item.put("MaterialsList", "互转收入");
                    }else{
                        item.put("MaterialsList", findInoutItemListByHeaderId(accountHead.getId()));
                    }

                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找财务信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询财务信息结果异常", e);
        }
    }

    /**
     * 根据编号查询单据信息
     */
    public void getDetailByNumber() {
        try {
            PageUtil<AccountHead> pageUtil = new PageUtil<AccountHead>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getConditionByNumber());
            accountHeadService.find(pageUtil);
            List<AccountHead> dataList = pageUtil.getPageList();
            JSONObject item = new JSONObject();
            if (dataList != null && dataList.get(0) != null) {
                AccountHead accountHead = dataList.get(0);
                item.put("Id", accountHead.getId());
                item.put("OrganId", accountHead.getOrganId() == null ? "" : accountHead.getOrganId().getId());
                item.put("OrganName", accountHead.getOrganId() == null ? "" : accountHead.getOrganId().getCustomerName());
                item.put("HandsPersonId", accountHead.getHandsPersonId() == null ? "" : accountHead.getHandsPersonId().getId());
                item.put("HandsPersonName", accountHead.getHandsPersonId() == null ? "" : accountHead.getHandsPersonId().getUsername());
                item.put("AccountId", accountHead.getAccountId() == null ? "" : accountHead.getAccountId().getId());
                item.put("AccountName", accountHead.getAccountId() == null ? "" : accountHead.getAccountId().getName());
                item.put("BillNo", accountHead.getBillNo());
                item.put("BillTime", Tools.getCenternTime(accountHead.getBillTime()));
                item.put("ChangeAmount", accountHead.getChangeAmount() == null ? "" : Math.abs(accountHead.getChangeAmount()));
                item.put("TotalPrice", accountHead.getTotalPrice() == null ? "" : Math.abs(accountHead.getTotalPrice()));
                item.put("Remark", accountHead.getRemark());
            }
            //回写查询结果
            toClient(item.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找单据信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询单据信息结果异常", e);
        }
    }

    /**
     * 查询单位的累计应收和累计应付，收预付款不计入此处
     *
     * @return
     */
    public void findTotalPay() {
        try {
            JSONObject outer = new JSONObject();
            Double sum = 0.0;
            String getS = model.getSupplierId();
            String supType = model.getSupType(); //单位类型：客户、供应商
            int i = 1;
            if (supType.equals("customer")) { //客户
                i = 1;
            } else if (supType.equals("vendor")) { //供应商
                i = -1;
            }
            //收付款部分
            sum = sum + (allMoney(getS, "付款", "合计") + allMoney(getS, "付款", "实际")) * i;
            sum = sum - (allMoney(getS, "收款", "合计") + allMoney(getS, "收款", "实际")) * i;
            sum = sum + (allMoney(getS, "收入", "合计") - allMoney(getS, "收入", "实际")) * i;
            sum = sum - (allMoney(getS, "支出", "合计") - allMoney(getS, "支出", "实际")) * i;
            outer.put("getAllMoney", sum);
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询结果异常", e);
        }
    }

    /**
     * 保存收款记录
     */
    public void saveAccountDetails(){
        Log.infoFileSync("==================开始调用保存收款记录方法saveAccountDetails()===================");
        Boolean flag = false;
        double totalAmount = 0;
        try {
            if(StringUtils.isNotEmpty(model.getOrganIds())){
                List<String> OrganIds = Arrays.asList(model.getOrganIds().split(","));
                List<String> TotalPrice = Arrays.asList(model.getTotalPrices().split(","));
                List<String> BillNo = Arrays.asList(model.getBillNo().split(","));
                for(int i = 0 ; i < OrganIds.size() ; i ++){
                    AccountHead accountHead = new AccountHead();
                    accountHead.setType(model.getType());
                    accountHead.setOrganId(new Customer(Long.parseLong(OrganIds.get(i))));
                    accountHead.setHandsPersonId(new Basicuser(getUser().getId()));
                    accountHead.setChangeAmount(0.00);
                    accountHead.setTotalPrice(Double.parseDouble(TotalPrice.get(i)));
                    totalAmount = totalAmount + Double.parseDouble(TotalPrice.get(i));
                    accountHead.setAccountId(new Account(model.getAccountId()));
                    accountHead.setBillNo(BillNo.get(i));
                    try {
                        accountHead.setBillTime(new Timestamp(Tools.parse(model.getBillTime(), "yyyy-MM-dd HH:mm:ss").getTime()));
                    } catch (ParseException e) {
                        Log.errorFileSync(">>>>>>>>>>>>>>>解析入库时间格式异常", e);
                    }
                    accountHeadService.create(accountHead);
                }
                //收款，需要在相关账户余额上加上对应金额
                accountService.addCurrentAmount(model.getAccountId(),totalAmount);
            }
            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException | JshException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加财务信息异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>增加财务信息回写客户端结果异常", e);
            }
        }

        logService.create(new Logdetails(getUser(),   "增加财务", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "增加财务编号为  " + model.getBillNo() + " " + tipMsg + "！", "增加财务|收款操作" + tipMsg +"本次收款金额："+totalAmount +",收款账号："+model.getAccountId()));
        Log.infoFileSync("==================开始调用保存收款记录方法saveAccountDetails()===================");
    }

    /**
     * 统计总金额
     *
     * @param type
     * @param mode 合计或者金额
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Double allMoney(String getS, String type, String mode) {
        Log.infoFileSync("getS:" + getS);
        Double allMoney = 0.0;
        String allReturn = "";
        PageUtil<AccountHead> pageUtil = new PageUtil<AccountHead>();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        pageUtil.setAdvSearch(getConditionHead_byEndTime());
        try {
            Integer supplierId = Integer.valueOf(getS);
            accountHeadService.findAllMoney(pageUtil, supplierId, type, mode);
            allReturn = pageUtil.getPageList().toString();
            allReturn = allReturn.substring(1, allReturn.length() - 1);
            if (allReturn.equals("null")) {
                allReturn = "0";
            }
        } catch (JshException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        allMoney = Double.parseDouble(allReturn);
        //返回正数，如果负数也转为正数
        if (allMoney < 0) {
            allMoney = -allMoney;
        }
        return allMoney;
    }

    /**
     * 客户对账单--银行卡收款明细
     */
    public void findCustomerStatementAccount(){
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        try{
            Long organId = model.getOrganId();
            accountHeadService.findCustomerStatementAccount(pageUtil, beginTime, endTime, organId);
            List dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (dataList != null) {
                for (Integer i = 0; i < dataList.size(); i++) {
                    JSONObject item = new JSONObject();
                    Object dl = dataList.get(i); //获取对象
                    Object[] arr = (Object[]) dl; //转为数组

                    item.put("BillTime", Tools.getCurrentMonth((Date)arr[0])); //收款日期
                    item.put("Name", arr[1]); //收款银行
                    item.put("TotalPrice", arr[2]); //收款金额
                    item.put("Remark", arr[3] == null ? "" : arr[3]); //备注

                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        }catch (JshException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
    }
    public String findInoutItemListByHeaderId(Long headerId){
        String allReturn = "";
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
            accountHeadService.findInoutItemListByHeaderId(pageUtil, headerId);
            allReturn = pageUtil.getPageList().toString();
            allReturn = allReturn.substring(1, allReturn.length() - 1);
            if (allReturn.equals("null")) {
                allReturn = "";
            }
        } catch (JshException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        }
        return allReturn;
    }

    private Map<String, Object> getConditionByNumber() {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("BillNo_s_eq", model.getBillNo());
        return condition;
    }

    /**
     * 拼接搜索条件
     *
     * @return
     */
    private Map<String, Object> getCondition() {
        /**
         * 拼接搜索条件
         */
        Map<String, Object> condition = new HashMap<String, Object>();
        {
            condition.put("BillNo_s_like", model.getBillNo());
        }
        //供应商
        if("supplier".equalsIgnoreCase(model.getSupType())){
            //供应商不为null
            condition.put("SupplierId_o_isn", null);
            condition.put("SupplierId_s_eq",model.getSupplierId());
        }
        if("organ".equalsIgnoreCase(model.getSupType())){
            //客户不为null
            condition.put("OrganId_o_isn", null);
            condition.put("OrganId_s_eq",model.getOrganId());
        }
        if("user".equalsIgnoreCase(model.getSupType())){
            //业务员不为null
            condition.put("UserId_o_isn", null);
            condition.put("UserId_s_eq",model.getUserId());
        }
        condition.put("HandsPersonId_s_eq", model.getHandsPersonId());
        condition.put("AccountId_s_eq", model.getAccountId());
        condition.put("Type_s_in", model.getType());
        condition.put("Id_s_in", model.getMaterialsList());
        condition.put("BillTime_s_gteq", model.getBeginTime());
        condition.put("BillTime_s_lteq", model.getEndTime());
        condition.put("Id_s_order", "desc");
        return condition;
    }

    private Map<String, Object> getConditionHead_byEndTime() {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("BillTime_s_lteq", model.getEndTime());
        return condition;
    }

    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    public AccountHeadModel getModel() {
        return model;
    }

    public void setAccountHeadService(AccountHeadIService accountHeadService) {
        this.accountHeadService = accountHeadService;
    }

    /**
     * Setter method for property <tt>accountService</tt>.
     *
     * @param accountService value to be assigned to property accountService
     */
    public void setAccountService(AccountIService accountService) {
        this.accountService = accountService;
    }

    /**
     * Setter method for property <tt>accountItemService</tt>.
     *
     * @param accountItemService value to be assigned to property accountItemService
     */
    public void setAccountItemService(AccountItemIService accountItemService) {
        this.accountItemService = accountItemService;
    }
}
