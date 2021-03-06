package com.jsh.action.materials;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.enums.CustomerTypeEnum;
import com.jsh.model.po.Account;
import com.jsh.model.po.Basicuser;
import com.jsh.model.po.Customer;
import com.jsh.model.po.Depot;
import com.jsh.model.po.DepotHead;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.Person;
import com.jsh.model.vo.materials.DepotHeadModel;
import com.jsh.service.basic.UserIService;
import com.jsh.service.materials.CustomerIService;
import com.jsh.service.materials.DepotHeadIService;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * 单据表头管理
 * @author
 */
@SuppressWarnings("serial")
public class DepotHeadAction extends BaseAction<DepotHeadModel> {
    private DepotHeadIService depotHeadService;
    private UserIService userService;
    private CustomerIService customerService;
    private DepotHeadModel model = new DepotHeadModel();

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
            depotHeadService.find(pageUtil, "maxId");
            mapData.put("depotHeadMax", pageUtil.getPageList());
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>查找最大的Id信息异常", e);
            model.getShowModel().setMsgTip("exceptoin");
        }
        return SUCCESS;
    }

    /**
     * 增加单据
     *
     * @return
     */
    public void create() {
        Log.infoFileSync("==================开始调用增加单据信息信息方法create()===================");
        Boolean flag = false;
        try {
            DepotHead depotHead = new DepotHead();
            depotHead.setType(model.getType());
            depotHead.setSubType(model.getSubType());
            if (model.getProjectId() != null) {
                depotHead.setProjectId(new Depot(model.getProjectId()));
            }
            //构造新的编号
            String dNumber = model.getDefaultNumber();
            String number = dNumber.substring(0, 12); //截取前缀
            String beginTime = Tools.getNow() + " 00:00:00";
            String endTime = Tools.getNow() + " 23:59:59";
            String newNumber = buildNumberFun(model.getType(), model.getSubType(), beginTime, endTime);  //从数据库查询最新的编号+1,这样能防止重复
            String allNewNumber = number + newNumber;

            //判断当前编号是否已经使用
            PageUtil<DepotHead> pageUtil = new PageUtil<DepotHead>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getConditionByNumber());
            depotHeadService.find(pageUtil);
            List<DepotHead> dataList = pageUtil.getPageList();
            if(dataList.size() > 0){
                //证明编号已经在使用了
                String oldNumber = model.getNumber();
                String newGenNumber = "";
                oldNumber = oldNumber.substring(0,oldNumber.length()- 4);
                //重新生成
                newGenNumber = oldNumber + buildNumberFun(model.getType(), model.getSubType(), beginTime, endTime);
                depotHead.setNumber(newGenNumber); //一直从前端文本框里面获取
            }else{
                depotHead.setNumber(model.getNumber()); //一直从前端文本框里面获取
            }
            depotHead.setDefaultNumber(allNewNumber); //初始编号，一直都从后台取值

            depotHead.setOperPersonName(getUser().getUsername());
            depotHead.setCreateTime(new Timestamp(System.currentTimeMillis()));
            depotHead.setOperTime(new Timestamp(System.currentTimeMillis()));
            /*try {
                depotHead.setOperTime(new Timestamp(Tools.parse(model.getOperTime(), "yyyy-MM-dd HH:mm:ss").getTime()));
            } catch (ParseException e) {
                Log.errorFileSync(">>>>>>>>>>>>>>>解析购买日期格式异常", e);
            }*/
            if (model.getOrganId() != null) {
                depotHead.setOrganId(new Customer(model.getOrganId()));
            }
            if (model.getHandsPersonId() != null) {
                depotHead.setHandsPersonId(new Person(model.getHandsPersonId()));
            }
            if (model.getSalesman() != null) {
                depotHead.setSalesman(model.getSalesman().toString());
            }
            if (model.getAccountId() != null) {
                depotHead.setAccountId(new Account(model.getAccountId()));
            }
            depotHead.setChangeAmount(model.getChangeAmount());
            depotHead.setAccountIdList(model.getAccountIdList());
            depotHead.setAccountMoneyList(model.getAccountMoneyList());
            depotHead.setDiscount(model.getDiscount());
            depotHead.setDiscountMoney(model.getDiscountMoney());
            depotHead.setDiscountLastMoney(model.getDiscountLastMoney());
            depotHead.setOtherMoney(model.getOtherMoney());
            depotHead.setOtherMoneyList(model.getOtherMoneyList());
            depotHead.setOtherMoneyItem(model.getOtherMoneyItem());
            depotHead.setAccountDay(model.getAccountDay());
            if (model.getAllocationProjectId() != null) {
                depotHead.setAllocationProjectId(new Depot(model.getAllocationProjectId()));
            }
            depotHead.setTotalPrice(model.getTotalPrice());
            depotHead.setPayType(model.getPayType());
            depotHead.setStatus(false);

            depotHead.setRemark(model.getRemark());
            depotHead.setExpress(model.getExpress());
            depotHead.setExpressNumber(model.getExpressNumber());
            depotHead.setContacts(model.getContacts());
            depotHead.setPhonenum(model.getPhonenum());
            depotHead.setState(model.getState());
            depotHead.setCity(model.getCity());
            depotHead.setStreet(model.getStreet());
            depotHead.setAddress(model.getAddress());
            depotHead.setSendStatus(false);//发货状态
            depotHead.setWeight(model.getWeight());
            depotHead.setFreight(model.getFreight());
            depotHead.setExpressCode(model.getExpressCode());
            depotHeadService.create(depotHead);

            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加单据信息异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>增加单据信息回写客户端结果异常", e);
            }
        }

        logService.create(new Logdetails(getUser(), "增加单据", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "增加单据编号为  " + model.getNumber() + " " + tipMsg + "！", "增加单据" + tipMsg));
        Log.infoFileSync("==================结束调用增加单据方法create()===================");
    }

    /**
     * 删除单据
     *
     * @return
     */
    public String delete() {
        Log.infoFileSync("====================开始调用删除单据信息方法delete()================");
        try {
            depotHeadService.delete(model.getDepotHeadID());
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getDepotHeadID() + "  的单据异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        model.getShowModel().setMsgTip(tipMsg);
        logService.create(new Logdetails(getUser(), "删除单据", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "删除单据ID为  " + model.getDepotHeadID() + " " + tipMsg + "！", "删除单据" + tipMsg));
        Log.infoFileSync("====================结束调用删除单据信息方法delete()================");
        return SUCCESS;
    }

    /**
     * 更新单据
     *
     * @return
     */
    public void update() {
        Log.infoFileSync("====================开始调用更新单据信息方法update()================");
        Boolean flag = false;
        try {
            DepotHead depotHead = depotHeadService.get(model.getDepotHeadID());
            depotHead.setType(model.getType());
            depotHead.setSubType(model.getSubType());
            if (model.getProjectId() != null) {
                depotHead.setProjectId(new Depot(model.getProjectId()));
            }
            depotHead.setNumber(model.getNumber());
            depotHead.setOperPersonName(getUser().getUsername());
            /*try {
                depotHead.setOperTime(new Timestamp(Tools.parse(model.getOperTime(), "yyyy-MM-dd HH:mm:ss").getTime()));
            } catch (ParseException e) {
                Log.errorFileSync(">>>>>>>>>>>>>>>解析入库时间格式异常", e);
            }*/
            depotHead.setOperTime(new Timestamp(System.currentTimeMillis()));
            if (model.getOrganId() != null) {
                depotHead.setOrganId(new Customer(model.getOrganId()));
            }
            if (model.getHandsPersonId() != null) {
                depotHead.setHandsPersonId(new Person(model.getHandsPersonId()));
            }
            depotHead.setSalesman(model.getSalesman());
            if (model.getAccountId() != null) {
                depotHead.setAccountId(new Account(model.getAccountId()));
            } else {
                depotHead.setAccountId(null);
            }
            depotHead.setChangeAmount(model.getChangeAmount());
            depotHead.setAccountIdList(model.getAccountIdList());
            depotHead.setAccountMoneyList(model.getAccountMoneyList());
            depotHead.setDiscount(model.getDiscount());
            depotHead.setDiscountMoney(model.getDiscountMoney());
            depotHead.setDiscountLastMoney(model.getDiscountLastMoney());
            depotHead.setOtherMoney(model.getOtherMoney());
            depotHead.setOtherMoneyList(model.getOtherMoneyList());
            depotHead.setOtherMoneyItem(model.getOtherMoneyItem());
            depotHead.setAccountDay(model.getAccountDay());
            if (model.getAllocationProjectId() != null) {
                depotHead.setAllocationProjectId(new Depot(model.getAllocationProjectId()));
            }
            depotHead.setTotalPrice(model.getTotalPrice());
            depotHead.setPayType(model.getPayType());
            depotHead.setStatus(false);
            depotHead.setRemark(model.getRemark());

            depotHead.setExpress(model.getExpress());
            depotHead.setExpressNumber(model.getExpressNumber());
            depotHead.setContacts(model.getContacts());
            depotHead.setPhonenum(model.getPhonenum());
            depotHead.setState(model.getState());
            depotHead.setCity(model.getCity());
            depotHead.setStreet(model.getStreet());
            depotHead.setAddress(model.getAddress());
            depotHead.setWeight(model.getWeight());
            depotHead.setFreight(model.getFreight());
            //发货状态
            depotHead.setSendStatus(false);
            depotHead.setExpressCode(model.getExpressCode());

            depotHeadService.update(depotHead);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>修改单据ID为 ： " + model.getDepotHeadID() + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>修改单据回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新单据", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "更新单据ID为  " + model.getDepotHeadID() + " " + tipMsg + "！", "更新单据" + tipMsg));
        Log.infoFileSync("====================结束调用更新单据信息方法update()================");
    }

    /**
     * 发货
     *
     * @return
     */
    public void sendGoods() {
        Log.infoFileSync("====================开始调用发货方法sendGoods()================");
        Boolean flag = false;
        try {
            DepotHead depotHead = depotHeadService.get(model.getDepotHeadID());
            //物流公司
            depotHead.setExpress(model.getExpress());
            //物流编号
            depotHead.setExpressNumber(model.getExpressNumber());
            //发货状态
            depotHead.setSendStatus(true);
            //发货员
            depotHead.setSendPersonName(getUser().getUsername());
            //操作时间
            /*try {
                depotHead.setOperTime(new Timestamp(Tools.parse(model.getOperTime(), "yyyy-MM-dd HH:mm:ss").getTime()));
            } catch (ParseException e) {
                Log.errorFileSync(">>>>>>>>>>>>>>>解析入库时间格式异常", e);
            }*/
            depotHead.setOperTime(new Timestamp(System.currentTimeMillis()));

            depotHeadService.update(depotHead);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>发货ID为 ： " + model.getDepotHeadID() + "发货失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>发货异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "发货", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "发货ID为  " + model.getDepotHeadID() + " " + tipMsg + "！", "发货成功" + tipMsg));
        Log.infoFileSync("====================开始调用发货方法sendGoods()================");
    }

    /**
     * 批量删除指定ID单据
     *
     * @return
     */
    public String batchDelete() {
        try {
            depotHeadService.batchDelete(model.getDepotHeadIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量删除单据ID为：" + model.getDepotHeadIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }

        logService.create(new Logdetails(getUser(), "批量删除单据", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量删除单据ID为  " + model.getDepotHeadIDs() + " " + tipMsg + "！", "批量删除单据" + tipMsg));
        return SUCCESS;
    }

    /**
     * 批量设置状态-已收款-未收款
     *
     * @return
     */
    public String batchSetStatus() {
        try {
            depotHeadService.batchSetStatus(model.getStatus(),model.getAccountId(), model.getDepotHeadIDs());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量修改状态，单据ID为：" + model.getDepotHeadIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }

        logService.create(new Logdetails(getUser(), "批量修改单据状态", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量修改状态，单据ID为  " + model.getDepotHeadIDs() + " " + tipMsg + "！", "批量修改单据状态" + tipMsg));
        return SUCCESS;
    }

    /**
     * 批量审核-反审核
     *
     * @return
     */
    public String batchSetCheck() {
        try {
            depotHeadService.batchSetCheck(model.getCheckStatus(), model.getDepotHeadIDs(),getUser().getUsername());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量修改状态，单据ID为：" + model.getDepotHeadIDs() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }

        logService.create(new Logdetails(getUser(), "批量修改单据状态", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量修改状态，单据ID为  " + model.getDepotHeadIDs() + " " + tipMsg + "！", "批量修改单据状态" + tipMsg));
        return SUCCESS;
    }

    /**
     * 检查单据编号是否存在
     */
    public void checkIsNumberExist() {
        Boolean flag = false;
        try {
            flag = depotHeadService.checkIsNameExist("Number", model.getNumber(), "Id", model.getDepotHeadID());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查单据编号为：" + model.getNumber() + " ID为： " + model.getDepotHeadID() + " 是否存在出现异常！");
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>回写检查单据编号为：" + model.getNumber() + " ID为： " + model.getDepotHeadID() + " 是否存在出现异常！", e);
            }
        }
    }

    /**
     * 单据编号生成接口，规则：查找当前类型单据下的当天最大的单据号，并加1
     */
    public void buildNumber() {
        try {
            String beginTime = model.getBeginTime();
            String endTime = model.getEndTime();
            String newNumber = buildNumberFun(model.getType(), model.getSubType(), beginTime, endTime);
            JSONObject outer = new JSONObject();
            outer.put("DefaultNumber", newNumber);
            //回写查询结果
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>单据编号生成异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写单据编号生成接口异常", e);
        }
    }

    /**
     * 查找单据编号
     *
     * @return
     */
    public String buildNumberFun(String type, String subType, String beginTime, String endTime) {
        String newNumber = "1200"; //新编号
        try {
            PageUtil<DepotHead> pageUtil = new PageUtil<DepotHead>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(buildNumberCondition(type, subType, beginTime, endTime));
            depotHeadService.find(pageUtil);
            List<DepotHead> dataList = pageUtil.getPageList();
            //存放数据json数组
            if (null != dataList && dataList.size() > 0) {
                DepotHead depotHead = dataList.get(0);
                if (depotHead != null) {
                    String number = depotHead.getNumber(); //最大的单据编号
                    if (number != null) {
                        Integer lastNumber = Integer.parseInt(number.substring(number.length()- 4, number.length())); //末四尾
                        lastNumber = lastNumber + 1;
                        Integer nLen = lastNumber.toString().length();
                        if (nLen == 1) {
                            newNumber = "000" + lastNumber.toString();
                        } else if (nLen == 2) {
                            newNumber = "00" + lastNumber.toString();
                        } else if (nLen == 3) {
                            newNumber = "0" + lastNumber.toString();
                        } else if (nLen == 4) {
                            newNumber = lastNumber.toString();
                        }
                    }
                }
            }
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>单据编号生成异常", e);
        }
        return newNumber;
    }

    /**
     * 根据材料信息获取
     */
    public void getHeaderIdByMaterial() {
        try {
            String materialParam = model.getMaterialParam(); //商品参数
            JSONObject outer = new JSONObject();
            if (StringUtils.isEmpty(materialParam)) {
                outer.put("ret", "");
                //回写查询结果
                toClient(outer.toString());
            }else{
                PageUtil pageUtil = new PageUtil();
                pageUtil.setPageSize(0);
                pageUtil.setCurPage(0);
                depotHeadService.getHeaderIdByMaterial(pageUtil, materialParam);

                String allReturn = "";
                List dataList = pageUtil.getPageList();
                if (dataList != null) {
                    for (Integer i = 0; i < dataList.size(); i++) {
                        Object dl = dataList.get(i); //获取对象
                        allReturn = allReturn + dl.toString() + ",";
                    }
                }
                if(StringUtils.isNotEmpty(allReturn)){
                    allReturn = allReturn.substring(0, allReturn.length() - 1);
                }
                if (allReturn.equals("null")) {
                    allReturn = "";
                }
                outer.put("ret", allReturn);
                //回写查询结果
                toClient(outer.toString());
            }
        } catch (JshException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询单据信息结果异常", e);
        }
    }

    /**
     * 查找单据信息
     *
     * @return
     */
    public void findBy() {
        try {
            if(StringUtils.isNotEmpty(model.getCustomerNo()) || StringUtils.isNotEmpty(model.getCustomerName())){
                String customerIds = "";
                Map<String, Object> customerCondition = new HashMap<String, Object>();
                customerCondition.put("customerNo_s_like", model.getCustomerNo());
                customerCondition.put("customerName_s_like", model.getCustomerName());

                PageUtil<Customer> customerPageUtil = new PageUtil<Customer>();
                customerPageUtil.setPageSize(0);
                customerPageUtil.setCurPage(0);
                customerPageUtil.setAdvSearch(customerCondition);
                customerService.find(customerPageUtil);
                List<Customer> customerList = customerPageUtil.getPageList();
                if(null != customerList){
                    for(Customer customer : customerList){
                        customerIds += customer.getId() + ",";
                    }
                }
                if(StringUtils.isNotEmpty(customerIds)){
                    customerIds = customerIds.substring(0, customerIds.lastIndexOf(","));
                    model.setCustomerIds(customerIds);
                }
            }
            PageUtil<DepotHead> pageUtil = new PageUtil<DepotHead>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());
            depotHeadService.find(pageUtil);
            List<DepotHead> dataList = pageUtil.getPageList();

            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (DepotHead depotHead : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("Id", depotHead.getId());
                    item.put("ProjectId", depotHead.getProjectId() == null ? "" : depotHead.getProjectId().getId());
                    item.put("ProjectName", depotHead.getProjectId() == null ? "" : depotHead.getProjectId().getName());
                    item.put("Number", depotHead.getNumber());
                    item.put("OperPersonName", depotHead.getOperPersonName());
                    item.put("CreateTime", Tools.getCenternTime(depotHead.getCreateTime()));
                    if(depotHead.getSendStatus()){
                        item.put("OperTime", Tools.getCenternTime(depotHead.getOperTime()));
                    }else{
                        item.put("OperTime", null);
                    }
                    item.put("OrganId", depotHead.getOrganId() == null ? "" : depotHead.getOrganId().getId());
                    item.put("OrganNo", depotHead.getOrganId() == null ? "" : depotHead.getOrganId().getCustomerNo());
                    item.put("OrganName", depotHead.getOrganId() == null ? "" : depotHead.getOrganId().getCustomerName());
                    item.put("HandsPersonId", depotHead.getHandsPersonId() == null ? "" : depotHead.getHandsPersonId().getId());
                    Basicuser user = userService.get(Long.parseLong(depotHead.getSalesman()));
                    item.put("Salesman", user == null ? "" : user.getUsername());
                    item.put("SalesmanId", user == null ? "" : user.getId());
                    item.put("SalesmanNo", user == null ? "" : user.getUserno());
                    item.put("HandsPersonName", depotHead.getHandsPersonId() == null ? "" : depotHead.getHandsPersonId().getName());
                    item.put("AccountId", depotHead.getAccountId() == null ? "" : depotHead.getAccountId().getId());
                    item.put("AccountName", depotHead.getAccountId() == null ? "" : depotHead.getAccountId().getName());
                    item.put("ChangeAmount", depotHead.getChangeAmount() == null ? "" : Math.abs(depotHead.getChangeAmount()));
                    item.put("AccountIdList", depotHead.getAccountIdList());
                    item.put("AccountMoneyList", depotHead.getAccountMoneyList());
                    item.put("Discount", depotHead.getDiscount());
                    item.put("DiscountMoney", depotHead.getDiscountMoney());
                    item.put("DiscountLastMoney", depotHead.getDiscountLastMoney());
                    item.put("OtherMoney", depotHead.getOtherMoney());
                    item.put("OtherMoneyList", depotHead.getOtherMoneyList()); //id列表
                    item.put("OtherMoneyItem", depotHead.getOtherMoneyItem()); //money列表
                    item.put("AccountDay", depotHead.getAccountDay()); //结算天数
                    item.put("AllocationProjectId", depotHead.getAllocationProjectId() == null ? "" : depotHead.getAllocationProjectId().getId());
                    item.put("AllocationProjectName", depotHead.getAllocationProjectId() == null ? "" : depotHead.getAllocationProjectId().getName());
                    item.put("TotalPrice", depotHead.getTotalPrice() == null ? "" : depotHead.getTotalPrice());
                    item.put("payType", depotHead.getPayType() == null ? "" : depotHead.getPayType());
                    item.put("Status", depotHead.getStatus());
                    item.put("SendStatus", depotHead.getSendStatus());
                    item.put("Remark", depotHead.getRemark());

                    item.put("CheckStatus", depotHead.getCheckStatus());
                    item.put("CheckOperName", depotHead.getCheckOperName());

                    item.put("Express", depotHead.getExpress());
                    item.put("ExpressNumber", depotHead.getExpressNumber());
                    item.put("Contacts", depotHead.getContacts());
                    item.put("Phonenum", depotHead.getPhonenum());
                    item.put("state", depotHead.getState());
                    item.put("city", depotHead.getCity());
                    item.put("street", depotHead.getStreet());
                    item.put("address", depotHead.getAddress());

                    //重量
                    item.put("Weight", depotHead.getWeight());
                    //运费预估
                    item.put("Freight", depotHead.getFreight());
                    //发货员
                    item.put("SendPersonName", depotHead.getSendPersonName());

                    //item.put("MaterialsList", findMaterialsListByHeaderId(depotHead.getId()));
                    item.put("MaterialsList", findProductListByHeaderId(depotHead.getId()));
                    //是否过期
                    if(depotHead.getSendStatus()){
                        item.put("Expired",false);
                    }else{
                        item.put("Expired",Tools.isExpired(depotHead.getCreateTime()));
                    }
                    item.put("op", 1);
                    item.put("ExpressCode",depotHead.getExpressCode());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找单据信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询单据信息结果异常", e);
        }
    }

    /**
     * 根据编号查询单据信息
     */
    public void getDetailByNumber() {
        try {
            PageUtil<DepotHead> pageUtil = new PageUtil<DepotHead>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(getConditionByNumber());
            depotHeadService.find(pageUtil);
            List<DepotHead> dataList = pageUtil.getPageList();
            JSONObject item = new JSONObject();
            if (dataList != null && dataList.get(0) != null) {
                DepotHead depotHead = dataList.get(0);
                item.put("Id", depotHead.getId());
                item.put("ProjectId", depotHead.getProjectId() == null ? "" : depotHead.getProjectId().getId());
                item.put("ProjectName", depotHead.getProjectId() == null ? "" : depotHead.getProjectId().getName());
                item.put("Number", depotHead.getNumber());
                item.put("OperPersonName", depotHead.getOperPersonName());
                item.put("CreateTime", Tools.getCenternTime(depotHead.getCreateTime()));
                item.put("OperTime", Tools.getCenternTime(depotHead.getOperTime()));
                item.put("OrganId", depotHead.getOrganId() == null ? "" : depotHead.getOrganId().getId());
                item.put("OrganName", depotHead.getOrganId() == null ? "" : depotHead.getOrganId().getCustomerName());
                item.put("HandsPersonId", depotHead.getHandsPersonId() == null ? "" : depotHead.getHandsPersonId().getId());
                Basicuser user = userService.get(Long.parseLong(depotHead.getSalesman()));
                item.put("Salesman", user == null ? "" : user.getUsername());
                item.put("SalesmanId", user == null ? "" : user.getId());
                item.put("SalesmanNo", user == null ? "" : user.getUserno());
                item.put("HandsPersonName", depotHead.getHandsPersonId() == null ? "" : depotHead.getHandsPersonId().getName());
                item.put("AccountId", depotHead.getAccountId() == null ? "" : depotHead.getAccountId().getId());
                item.put("AccountName", depotHead.getAccountId() == null ? "" : depotHead.getAccountId().getName());
                item.put("ChangeAmount", depotHead.getChangeAmount() == null ? "" : Math.abs(depotHead.getChangeAmount()));
                item.put("AccountIdList", depotHead.getAccountIdList());
                item.put("AccountMoneyList", depotHead.getAccountMoneyList());
                item.put("Discount", depotHead.getDiscount());
                item.put("DiscountMoney", depotHead.getDiscountMoney());
                item.put("DiscountLastMoney", depotHead.getDiscountLastMoney());
                item.put("OtherMoney", depotHead.getOtherMoney());
                item.put("OtherMoneyList", depotHead.getOtherMoneyList()); //id列表
                item.put("OtherMoneyItem", depotHead.getOtherMoneyItem()); //money列表
                item.put("AccountDay", depotHead.getAccountDay()); //结算天数
                item.put("AllocationProjectId", depotHead.getAllocationProjectId() == null ? "" : depotHead.getAllocationProjectId().getId());
                item.put("AllocationProjectName", depotHead.getAllocationProjectId() == null ? "" : depotHead.getAllocationProjectId().getName());
                item.put("TotalPrice", depotHead.getTotalPrice() == null ? "" : Math.abs(depotHead.getTotalPrice()));
                item.put("payType", depotHead.getPayType() == null ? "" : depotHead.getPayType());
                item.put("Status", depotHead.getStatus());
                item.put("SendStatus", depotHead.getSendStatus());
                item.put("Remark", depotHead.getRemark());

                item.put("CheckStatus", depotHead.getCheckStatus());
                item.put("CheckOperName", depotHead.getCheckOperName());

                item.put("Express", depotHead.getExpress());
                item.put("ExpressNumber", depotHead.getExpressNumber());
                item.put("Contacts", depotHead.getContacts());
                item.put("Phonenum", depotHead.getPhonenum());
                item.put("state", depotHead.getState());
                item.put("city", depotHead.getCity());
                item.put("street", depotHead.getStreet());
                item.put("address", depotHead.getAddress());

                //重量
                item.put("Weight", depotHead.getWeight());
                //运费预估
                item.put("Freight", depotHead.getFreight());
                //发货员
                item.put("SendPersonName", depotHead.getSendPersonName());

                item.put("ExpressCode",depotHead.getExpressCode());
                //item.put("MaterialsList", findMaterialsListByHeaderId(depotHead.getId()));
                item.put("MaterialsList", findProductListByHeaderId(depotHead.getId()));
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
     * 查找单据_根据月份(报表)
     *
     * @return
     */
    public void findByMonth() {
        try {
            PageUtil<DepotHead> pageUtil = new PageUtil<DepotHead>();
            pageUtil.setPageSize(1000);
            pageUtil.setCurPage(1);
            pageUtil.setAdvSearch(getConditionHead());
            depotHeadService.find(pageUtil);
            List<DepotHead> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            String headId = "";
            if (null != dataList) {
                for (DepotHead depotHead : dataList) {
                    headId = headId + depotHead.getId() + ",";
                }
            }
            if (headId != "") {
                headId = headId.substring(0, headId.lastIndexOf(","));
            }
            outer.put("HeadIds", headId);
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找单据信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询单据信息结果异常", e);
        }
    }

    /**
     * 查找统计信息_根据礼品卡(报表)
     *
     * @return
     */
    public void findGiftReport() {
        try {
            PageUtil<DepotHead> pageUtil_in = new PageUtil<DepotHead>();
            pageUtil_in.setPageSize(0);
            pageUtil_in.setCurPage(0);
            pageUtil_in.setAdvSearch(getConditionHead_Gift_In());
            depotHeadService.find(pageUtil_in);
            List<DepotHead> dataList_in = pageUtil_in.getPageList();
            JSONObject outer = new JSONObject();
            String headId = "";
            if (null != dataList_in) {
                for (DepotHead depotHead : dataList_in) {
                    headId = headId + depotHead.getId() + ",";
                }
                PageUtil<DepotHead> pageUtil_out = new PageUtil<DepotHead>();
                pageUtil_out.setPageSize(0);
                pageUtil_out.setCurPage(0);
                pageUtil_out.setAdvSearch(getConditionHead_Gift_Out());
                depotHeadService.find(pageUtil_out);
                List<DepotHead> dataList_out = pageUtil_out.getPageList();
                if (null != dataList_out) {
                    for (DepotHead depotHead : dataList_out) {
                        headId = headId + depotHead.getId() + ",";
                    }
                }
            }
            if (headId != "") {
                headId = headId.substring(0, headId.lastIndexOf(","));
            }
            outer.put("HeadIds", headId);
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找单据信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询单据信息结果异常", e);
        }
    }

    /**
     * 查询单位的累计应收和累计应付，零售不能计入
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
            //进销部分
            sum = sum - (allMoney(getS, "入库", "采购", "合计") - allMoney(getS, "入库", "采购", "实际")) * i;
            sum = sum - (allMoney(getS, "入库", "销售退货", "合计") - allMoney(getS, "入库", "销售退货", "实际")) * i;
            sum = sum + (allMoney(getS, "出库", "销售", "合计") - allMoney(getS, "出库", "销售", "实际")) * i;
            sum = sum + (allMoney(getS, "出库", "采购退货", "合计") - allMoney(getS, "出库", "采购退货", "实际")) * i;
            outer.put("getAllMoney", sum);
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询结果异常", e);
        }
    }


    /**
     * 统计总金额
     *
     * @param type
     * @param subType
     * @param mode    合计或者金额
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Double allMoney(String getS, String type, String subType, String mode) {
        Log.infoFileSync("getS:" + getS);
        Double allMoney = 0.0;
        String allReturn = "";
        PageUtil<DepotHead> pageUtil = new PageUtil<DepotHead>();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        pageUtil.setAdvSearch(getConditionHead_byEndTime());
        try {
            Integer supplierId = Integer.valueOf(getS);
            depotHeadService.findAllMoney(pageUtil, supplierId, type, subType, mode);
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
     * 入库出库明细接口
     */
    public void findInDetail() {
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        Long pid = model.getProjectId();
        String dids = model.getDepotIds();
        Long oId = model.getOrganId();
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        String type = model.getType();
        try {
            depotHeadService.findInDetail(pageUtil, beginTime, endTime, type, pid, dids, oId);
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
                    item.put("number", arr[0]); //单据编号
                    item.put("materialName", arr[1]); //商品名称
                    item.put("materialModel", arr[2]); //商品型号
                    item.put("unitPrice", arr[3]); //单价
                    item.put("operNumber", arr[4]); //入库出库数量
                    item.put("allPrice", arr[5]); //金额
                    item.put("supplierName", arr[6]); //供应商
                    item.put("depotName", arr[7]); //仓库
                    item.put("operTime", arr[8]); //入库出库日期
                    item.put("type", arr[9]); //入库出库日期
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (JshException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
    }

    /**
     * 入库出库统计接口
     */
    public void findInOutMaterialCount() {
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        Long pid = model.getProjectId();
        String dids = model.getDepotIds();
        Long oId = model.getOrganId();
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        String type = model.getType();
        try {
            depotHeadService.findInOutMaterialCount(pageUtil, beginTime, endTime, type, pid, dids, oId);
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
                    item.put("MaterialId", arr[0]); //商品Id
                    item.put("mName", arr[1]); //商品名称
                    item.put("Model", arr[2]); //商品型号
                    item.put("categoryName", arr[3]); //商品类型
                    item.put("numSum", arr[4]); //数量
                    item.put("priceSum", arr[5]); //金额
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (JshException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
    }

    private String findMaterialsListByHeaderId(Long headerId) {
        String allReturn = "";
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
            depotHeadService.findMaterialsListByHeaderId(pageUtil, headerId);
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

    public String findProductListByHeaderId(Long headerId) {
        String allReturn = "";
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(0);
        pageUtil.setCurPage(0);
        try {
            depotHeadService.findProductListByHeaderId(pageUtil, headerId);
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

    /**
     * 对账单接口
     */
    public void findStatementAccount() {
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        Long organId = model.getOrganId();
        String supType = model.getSupType(); //单位类型：客户、供应商
        try {
            depotHeadService.findStatementAccount(pageUtil, beginTime, endTime, organId, supType);
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
                    item.put("number", arr[0]); //单据编号
                    item.put("type", arr[1]); //类型
                    String type = arr[1].toString();
                    Double p1 = 0.0;
                    Double p2 = 0.0;
                    if (arr[2] != null) {
                        p1 = Double.parseDouble(arr[2].toString());
                    }
                    if (arr[3] != null) {
                        p2 = Double.parseDouble(arr[3].toString());
                    }
                    Double allPrice = 0.0;
                    if (p1 < 0) {
                        p1 = -p1;
                    }
                    if (p2 < 0) {
                        p2 = -p2;
                    }
                    if (type.equals("采购入库")) {
                        allPrice = -(p1 - p2);
                    } else if (type.equals("销售退货入库")) {
                        allPrice = -(p1 - p2);
                    } else if (type.equals("销售出库")) {
                        allPrice = p1 - p2;
                    } else if (type.equals("采购退货出库")) {
                        allPrice = p1 - p2;
                    } else if (type.equals("付款")) {
                        allPrice = p1 + p2;
                    } else if (type.equals("收款")) {
                        allPrice = -(p1 + p2);
                    } else if (type.equals("收入")) {
                        allPrice = p1 - p2;
                    } else if (type.equals("支出")) {
                        allPrice = -(p1 - p2);
                    }
                    item.put("discountLastMoney", p1); //金额
                    item.put("changeAmount", p2); //金额
                    item.put("allPrice", String.format("%.2f", allPrice)); //计算后的金额
                    item.put("supplierName", arr[4]); //供应商
                    item.put("operTime", arr[5]); //入库出库日期
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (JshException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询信息结果异常", e);
        }
    }

    /**
     * 地区统计
     */
    public void findAreaReport() {
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        try{
            String state = model.getState();
            depotHeadService.findAreaReport(pageUtil, beginTime, endTime, state);
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

                    item.put("number", arr[0]); //单据编号
                    item.put("type", arr[1]); //类型
                    item.put("customerName",arr[2]);//客户名称
                    item.put("oTime", arr[3]); //日期
                    item.put("productName", arr[4]); //产品名称
                    item.put("templateName", arr[5]); //版本明细
                    item.put("mUnit", arr[6]); //规格
                    item.put("operNumber", arr[7]); //数量
                    item.put("unitPrice", arr[8]); //单价
                    item.put("allPrice", arr[9]); //总价
                    item.put("customerNo",arr[10]);//客户编号
                    item.put("state",arr[11]);//省
                    item.put("city",arr[12]);//市

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

    /**
     * 地区统计--版本册
     */
    public void findAreaReportTemplate(){
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        try{
            String state = model.getState();
            depotHeadService.findAreaReportTemplate(pageUtil, beginTime, endTime, state);
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

                    item.put("templateName", arr[0]); //版本种类
                    item.put("OperNumber", arr[1]); //数量
                    item.put("AllPrice", arr[2]); //合计金额

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

    /**
     *  新的客户对账单
     */
    public void findCustomerStatementAccount() {
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        try{
            Long organId = model.getOrganId();
            depotHeadService.findCustomerStatementAccount(pageUtil, beginTime, endTime, organId);
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

                    item.put("number", arr[0]); //单据编号
                    item.put("type", arr[1]); //类型
                    item.put("customerName",arr[2]);//客户名称
                    item.put("oTime", arr[3]); //日期
                    item.put("productName", arr[4]); //产品名称
                    item.put("templateName", arr[5]); //版本明细
                    item.put("mUnit", arr[6]); //规格
                    item.put("operNumber", arr[7]); //数量
                    item.put("unitPrice", arr[8]); //单价
                    item.put("allPrice", arr[9]); //总价
                    item.put("customerNo",arr[10]);//客户编号
                    item.put("state",arr[11]);//省
                    item.put("city",arr[12]);//市

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

    /**
     * 业务员对账单--订单明细
     */
    public void findSalesManStatementAccount() {
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        try{
            Long organId = model.getOrganId();
            depotHeadService.findSalesManStatementAccount(pageUtil, beginTime, endTime, organId);
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

                    item.put("number", arr[0]); //单据编号
                    item.put("type", arr[1]); //类型
                    item.put("customerName",arr[2]);//客户名称
                    item.put("oTime", arr[3]); //日期
                    item.put("productName", arr[4]); //产品名称
                    item.put("templateName", arr[5]); //版本明细
                    item.put("mUnit", arr[6]); //规格
                    item.put("operNumber", arr[7]); //数量
                    item.put("unitPrice", arr[8]); //单价
                    item.put("allPrice", arr[9]); //总价
                    item.put("customerNo",arr[10]);//客户编号
                    item.put("state",arr[11]);//省
                    item.put("city",arr[12]);//市

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

    /**
     *  新的客户对账单 版本分类统计
     */
    public void findCustomerStatementTemplate(){
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        try{
            Long organId = model.getOrganId();
            depotHeadService.findCustomerStatementTemplate(pageUtil, beginTime, endTime, organId);
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

                    item.put("templateName", arr[0]); //版本种类
                    item.put("OperNumber", arr[1]); //数量
                    item.put("AllPrice", arr[2]); //合计金额

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

    /**
     * 业务员对账单--版本归类
     */
    public void findSalesManStatementTemplate(){
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        try{
            Long organId = model.getOrganId();
            depotHeadService.findSalesManStatementTemplate(pageUtil, beginTime, endTime, organId);
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

                    item.put("templateName", arr[0]); //版本种类
                    item.put("OperNumber", arr[1]); //数量
                    item.put("AllPrice", arr[2]); //合计金额

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

    /**
     * 新的供应商对账单 版本分类统计
     */
    public void findSupplierStatementTemplate(){
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        try{
            Long organId = model.getOrganId();
            depotHeadService.findSupplierStatementTemplate(pageUtil, beginTime, endTime, organId);
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

                    item.put("templateName", arr[0]); //版本种类
                    item.put("OperNumber", arr[1]); //数量
                    item.put("AllPrice", arr[2]); //合计金额

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

    /**
     * 新的供应商对账单
     */
    public void findSupplierStatementAccount() {
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        try{
            Long organId = model.getOrganId();
            depotHeadService.findSupplierStatementAccount(pageUtil, beginTime, endTime, organId);
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

                    item.put("number", arr[0]); //单据编号
                    item.put("oTime", arr[1]); //日期
                    item.put("productName", arr[2]); //产品名称
                    item.put("templateName", arr[3]); //版本明细
                    item.put("mUnit", arr[4]); //规格
                    item.put("operNumber", arr[5]); //数量
                    item.put("taxUnitPrice", arr[6]); //进货价
                    item.put("allPrice", (double)arr[5]*(double)arr[6]); //总价

                    item.put("customerNo", arr[7]); //客户编号
                    item.put("customerName", arr[8]); //客户姓名
                    item.put("state", arr[9]); //省
                    item.put("city", arr[10]); //市

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

    /**
     * 统计客户活跃度
     */
    public void sumCustomerActivity(){
        PageUtil pageUtil = new PageUtil();
        pageUtil.setPageSize(model.getPageSize());
        pageUtil.setCurPage(model.getPageNo());
        String beginTime = model.getBeginTime();
        String endTime = model.getEndTime();
        try{
            Long organId = model.getOrganId();
            String sort = model.getSort();
            depotHeadService.sumCustomerActivity(pageUtil, beginTime, endTime, organId,sort);
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

                    item.put("number", arr[0]); //下单数量
                    item.put("customerNo", arr[2]); //客户编号
                    item.put("customerName", arr[3]); //客户姓名
                    item.put("phonenum", arr[4]); //手机号码
                    item.put("type", CustomerTypeEnum.getSexEnumByCode((String)arr[5]).getName()); //客户类型
                    item.put("address", (String)arr[6]+arr[7]+arr[8]+arr[9]); //手机号码

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

    private Map<String, Object> getConditionByNumber() {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("Number_s_eq", model.getNumber());
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
        condition.put("Type_s_eq", model.getType());
        condition.put("SubType_s_eq", model.getSubType());
        condition.put("Number_s_like", model.getNumber());
        condition.put("Status_n_eq", model.getSearchStatus());

        //收件人
        condition.put("Contacts_s_eq", model.getSearchContacts());
        //运单号码
        condition.put("ExpressNumber_s_eq", model.getSearchExpressNumber());

        condition.put("CheckStatus_n_eq", model.getSearchCheckStatus());
        condition.put("OrganId_s_in", model.getCustomerIds());
        condition.put("SendStatus_n_eq", model.getSearchSendStatus());
        condition.put("Id_s_in", model.getDhIds());

        condition.put("CreateTime_s_gteq", model.getBeginTime());
        condition.put("CreateTime_s_lteq", model.getEndTime());

        condition.put("OperTime_s_gteq", model.getSendBeginTime());
        condition.put("OperTime_s_lteq", model.getSendEndTime());

        //如果是有订单金额  只搜索1个月之内的订单数据
        if(null != model.getSearchTotalPrice()){
            condition.put("TotalPrice_s_gteq", model.getSearchTotalPrice()-10);
            condition.put("TotalPrice_s_lteq", model.getSearchTotalPrice()+10);


            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c = Calendar.getInstance();
            //过去一月
            c.setTime(new Date());
            c.add(Calendar.MONTH, -1);
            Date m = c.getTime();
            String begin = format.format(m);

            String end = format.format(new Date());

            condition.put("CreateTime_s_gteq", begin);
            condition.put("CreateTime_s_lteq", end);
        }

        condition.put("Id_s_order", "desc");
        return condition;
    }

    private Map<String, Object> buildNumberCondition(String type, String subType, String beginTime, String endTime) {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("Type_s_eq", type);
        condition.put("SubType_s_eq", subType);
        condition.put("OperTime_s_gteq", beginTime);
        condition.put("OperTime_s_lteq", endTime);
        condition.put("Id_s_order", "desc");
        return condition;
    }

    private Map<String, Object> getConditionHead() {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("OperTime_s_lteq", model.getMonthTime() + "-31 00:00:00");
        return condition;
    }

    private Map<String, Object> getConditionHead_Gift_In() {
        Map<String, Object> condition = new HashMap<String, Object>();
        return condition;
    }

    private Map<String, Object> getConditionHead_Gift_Out() {
        Map<String, Object> condition = new HashMap<String, Object>();
        if (model.getProjectId() != null) {
            condition.put("ProjectId_n_eq", model.getProjectId());
        }
        return condition;
    }

    private Map<String, Object> getConditionHead_byEndTime() {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("OperTime_s_lteq", model.getEndTime());
        return condition;
    }

    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    @Override
    public DepotHeadModel getModel() {
        return model;
    }

    public void setDepotHeadService(DepotHeadIService depotHeadService) {
        this.depotHeadService = depotHeadService;
    }

    /**
     * Setter method for property <tt>userService</tt>.
     *
     * @param userService value to be assigned to property userService
     */
    public void setUserService(UserIService userService) {
        this.userService = userService;
    }

    /**
     * Setter method for property <tt>customerService</tt>.
     *
     * @param customerService value to be assigned to property customerService
     */
    public void setCustomerService(CustomerIService customerService) {
        this.customerService = customerService;
    }
}
