/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.action.materials;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.enums.CustomerTypeEnum;
import com.jsh.model.po.Basicuser;
import com.jsh.model.po.Customer;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.UserBusiness;
import com.jsh.model.vo.materials.CustomerModel;
import com.jsh.service.basic.UserBusinessIService;
import com.jsh.service.basic.UserIService;
import com.jsh.service.materials.CustomerIService;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujp
 * @version $Id TemplateAction.java, v 0.1 2018-07-15 12:08 Lenovo Exp $$
 */
@SuppressWarnings("serial")
public class CustomerAction extends BaseAction<CustomerModel> {
    public static final String EXCEL = "excel";  //action返回excel结果
    private CustomerIService customerService;
    private UserBusinessIService userBusinessService;
    private UserIService userService;
    private CustomerModel model = new CustomerModel();

    /**
     * 新增客户信息
     */
    public void create(){
        Log.infoFileSync("==================开始调用增加客户信息方法===================");
        Boolean flag = false;
        try {
            Customer customer = new Customer();
            customer.setCustomerNo(model.getCustomerNo());
            customer.setCustomerName(model.getCustomerName());
            customer.setCustomerShort(model.getCustomerShort());
            customer.setContacts(model.getContacts());
            customer.setPhonenum(model.getPhonenum());
            customer.setEmail(model.getEmail());
            customer.setDescription(model.getDescription());

            customer.setIsystem((byte) 1);
            customer.setType(model.getType());
            customer.setEnabled(model.getEnabled());
            customer.setFax(model.getFax());
            customer.setTelephone(model.getTelephone());
            customer.setQq(model.getQq());
            customer.setExpress(model.getExpress());
            customer.setAddress(model.getAddress());
            customer.setTaxNum(model.getTaxNum());
            customer.setBankName(model.getBankName());
            customer.setAccountNumber(model.getAccountNumber());
            customer.setTaxRate(model.getTaxRate() == null ? 0.00 : model.getTaxRate());
            customer.setState(model.getState());
            customer.setCity(model.getCity());
            customer.setStreet(model.getStreet());
            customer.setUser(new Basicuser(Long.parseLong(model.getUserId())));
            customer.setFax("");

            customerService.create(customer);

            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        }catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加客户信息异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>增加客户信息回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "增加客户信息", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "增加客户信息名称为  " + model.getCustomerName() + " " + tipMsg + "！", "增加客户信息" + tipMsg));
        Log.infoFileSync("==================结束调用增加客户信息方法===================");
    }
    /**
     * 查询客户信息列表
     */
    public void findBy(){
        try{
            PageUtil<Customer> pageUtil = new PageUtil<>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());

            customerService.find(pageUtil);

            getSession().put("pageUtilCustomer", pageUtil);

            List<Customer> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for(Customer customer : dataList){
                    JSONObject item = new JSONObject();
                    //收支项目名称
                    item.put("id", customer.getId());
                    //客户信息名称
                    item.put("customerNo", customer.getCustomerNo());
                    item.put("customerName", customer.getCustomerName());
                    item.put("customerShort", customer.getCustomerShort());
                    item.put("contacts", customer.getContacts());
                    item.put("phonenum", customer.getPhonenum());
                    item.put("email", customer.getEmail());
                    item.put("description", customer.getDescription());

                    item.put("telephone", customer.getTelephone());
                    item.put("qq", customer.getQq());
                    item.put("express", customer.getExpress());
                    item.put("address", customer.getAddress());
                    item.put("taxNum", customer.getTaxNum());
                    item.put("bankName", customer.getBankName());
                    item.put("accountNumber", customer.getAccountNumber());
                    item.put("taxRate", customer.getTaxRate());

                    item.put("state",customer.getState());
                    item.put("city",customer.getCity());
                    item.put("street",customer.getStreet());

                    item.put("type", CustomerTypeEnum.getSexEnumByCode(customer.getType()).getName());
                    item.put("typeId", customer.getType());
                    item.put("userId", customer.getUser().getId());
                    item.put("userName", customer.getUser().getUsername());

                    item.put("isystem", customer.getIsystem() == (short) 0 ? "是" : "否");
                    item.put("enabled", customer.getEnabled());
                    item.put("op", customer.getIsystem());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找客户信息信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询客户信息结果异常", e);
        }
    }
    /**
     * 查询客户信息列表
     */
    public void findById(){
        try{
            /**
             * 拼接搜索条件
             */
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("Id_n_eq", model.getId());

            PageUtil<Customer> pageUtil = new PageUtil<>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(condition);

            customerService.find(pageUtil);

            getSession().put("pageUtilCustomer", pageUtil);

            List<Customer> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for(Customer customer : dataList){
                    JSONObject item = new JSONObject();
                    //收支项目名称
                    item.put("id", customer.getId());
                    //客户信息名称
                    item.put("customerNo", customer.getCustomerNo());
                    item.put("customerName", customer.getCustomerName());
                    item.put("customerShort", customer.getCustomerShort());
                    item.put("contacts", customer.getContacts());
                    item.put("phonenum", customer.getPhonenum());
                    item.put("email", customer.getEmail());
                    item.put("description", customer.getDescription());

                    item.put("telephone", customer.getTelephone());
                    item.put("qq", customer.getQq());
                    item.put("express", customer.getExpress());
                    item.put("address", customer.getAddress());
                    item.put("taxNum", customer.getTaxNum());
                    item.put("bankName", customer.getBankName());
                    item.put("accountNumber", customer.getAccountNumber());
                    item.put("taxRate", customer.getTaxRate());

                    item.put("state",customer.getState());
                    item.put("city",customer.getCity());
                    item.put("street",customer.getStreet());

                    item.put("type", CustomerTypeEnum.getSexEnumByCode(customer.getType()).getName());
                    item.put("typeId", customer.getType());
                    item.put("userId", customer.getUser().getId());
                    item.put("userName", customer.getUser().getUsername());

                    item.put("isystem", customer.getIsystem() == (short) 0 ? "是" : "否");
                    item.put("enabled", customer.getEnabled());
                    item.put("op", customer.getIsystem());
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找客户信息信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询客户信息结果异常", e);
        }
    }

    /**
     * 更新客户信息
     *
     * @return
     */
    public void update() {
        Log.infoFileSync("==================开始调用更新客户信息方法===================");
        Boolean flag = false;
        try {
            Customer customer = customerService.get(model.getId());

            customer.setCustomerNo(model.getCustomerNo());
            customer.setCustomerName(model.getCustomerName());
            customer.setCustomerShort(model.getCustomerShort());
            customer.setContacts(model.getContacts());
            customer.setPhonenum(model.getPhonenum());
            customer.setEmail(model.getEmail());
            customer.setDescription(model.getDescription());

            customer.setIsystem(customer.getIsystem());
            customer.setType(model.getType());
            customer.setEnabled(customer.getEnabled());
            customer.setFax("");
            customer.setTelephone(model.getTelephone());
            customer.setQq(model.getQq());
            customer.setExpress(model.getExpress());
            customer.setAddress(model.getAddress());
            customer.setTaxNum(model.getTaxNum());
            customer.setBankName(model.getBankName());
            customer.setAccountNumber(model.getAccountNumber());
            customer.setTaxRate(model.getTaxRate() == null ? 0.00 : model.getTaxRate());
            customer.setState(model.getState());
            customer.setCity(model.getCity());
            customer.setStreet(model.getStreet());
            customer.setUser(new Basicuser(Long.parseLong(model.getUserId())));
            customer.setFax(customer.getFax());


            customerService.update(customer);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>修改客户信息ID为 ： " + model.getCustomerNo() + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>修改客户信息回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新客户信息", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "更新客户信息ID为  " + model.getCustomerNo() + " " + tipMsg + "！", "更新客户信息" + tipMsg));
    }
    /**
     * 批量删除指定ID客户信息
     *
     * @return
     */
    public String batchDelete() {
        try {
            customerService.batchDelete(model.getBatchDeleteIds());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量删除客户信息ID为：" + model.getBatchDeleteIds() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        logService.create(new Logdetails(getUser(), "批量删除客户信息", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量删除客户信息ID为  " + model.getBatchDeleteIds() + " " + tipMsg + "！", "批量删除客户信息" + tipMsg));
        return SUCCESS;
    }
    /**
     * 批量设置状态-启用或者禁用
     *
     * @return
     */
    public String batchSetEnable() {
        try {
            customerService.batchSetEnable(model.getEnabled(), model.getBatchDeleteIds());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量修改状态，客户ID为：" + model.getBatchDeleteIds() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        logService.create(new Logdetails(getUser(), "批量修改客户状态", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量修改状态，客户ID为  " + model.getBatchDeleteIds() + " " + tipMsg + "！", "批量修改客户状态" + tipMsg));
        return SUCCESS;
    }

    /**
     * 导出excel表格
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public String exportExcel() {
        Log.infoFileSync("===================调用导出客户信息action方法exportExcel开始=======================");
        try {
            String sName = "pageUtil" + model.getType();
            PageUtil<Customer> pageUtil = (PageUtil<Customer>) getSession().get(sName);

            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            String isCurrentPage = "allPage";
            model.setFileName(Tools.changeUnicode("report" + System.currentTimeMillis() + ".xls", model.getBrowserType()));
            model.setExcelStream(customerService.exmportExcel(isCurrentPage, pageUtil));
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>调用导出信息action方法exportExcel异常", e);
            model.getShowModel().setMsgTip("export excel exception");
        }
        Log.infoFileSync("===================调用导出客户信息action方法exportExcel结束==================");
        return EXCEL;
    }


    /**
     * 验证客户编号是否重复
     */
    public void checkIsCustomerNoExist() {
        Boolean flag = false;
        try {
            flag = customerService.checkIsNameExist("customerNo", model.getCustomerNo(), "id", model.getId());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查客户编号为：" + model.getCustomerNo() + " ID为： " + model.getId() + " 是否存在异常！");
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>回写检查客户编号为：" + model.getCustomerNo() + " ID为： " + model.getId() + " 是否存在异常！", e);
            }
        }
    }

    /**
     * 查找业务员信息-下拉框
     *
     * @return
     */
    public void findBySelect_temp() {
        try {
            //存放数据json数组
            JSONArray dataArray = new JSONArray();

            Map<String, Object> condition = new HashMap<String, Object>();
            //查询业务经理角色
            condition.put("Type_s_eq","UserRole");
            condition.put("Value_s_like","[11]");

            PageUtil<UserBusiness> pageUtil = new PageUtil<UserBusiness>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(condition);
            userBusinessService.find(pageUtil);
            List<UserBusiness> dataList = pageUtil.getPageList();

            String userIds = "";
            //拼接ids
            if (null != dataList) {
                for(UserBusiness userBusiness : dataList){
                    userIds = userIds + userBusiness.getKeyId() + ",";
                }
                if (!userIds.equals("")) {
                    userIds = userIds.substring(0, userIds.length() - 1);
                }

                //开始使用ids查询用户信息
                Map<String, Object> userCondition = new HashMap<String, Object>();
                userCondition.put("Id_s_in",userIds);

                PageUtil<Basicuser> userPageUtil = new PageUtil<Basicuser>();
                userPageUtil.setPageSize(0);
                userPageUtil.setCurPage(0);
                userPageUtil.setAdvSearch(userCondition);
                userService.find(userPageUtil);
                List<Basicuser> userDataList = userPageUtil.getPageList();
                if (null != userDataList) {
                    for (Basicuser basicuser : userDataList) {
                        JSONObject item = new JSONObject();
                        item.put("id", basicuser.getId());
                        //客户信息名称
                        item.put("sales", basicuser.getUsername());
                        dataArray.add(item);
                    }
                }

            }
            //回写查询结果
            toClient(dataArray.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找客户信息下拉框信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询客户信息下拉框信息结果异常", e);
        }
    }

    /**
     * 单个删除
     */
    public String delete() {
        Log.infoFileSync("====================开始调用单个删除客户信息方法delete()================");
        try {
            customerService.delete(model.getId());
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getId() + "  的客户信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        model.getShowModel().setMsgTip(tipMsg);
        logService.create(new Logdetails(getUser(), "删除客户信息", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "删除客户信息ID为  " + model.getId() + ",名称为  " + model.getCustomerName() + tipMsg + "！", "删除客户信息" + tipMsg));
        Log.infoFileSync("====================结束调用删除客户信息信息方法delete()================");
        return SUCCESS;
    }

    /**
     * 用户对应客户显示
     *
     * @return
     */
    public void findUserCustomer() {
        try {
            PageUtil<Customer> pageUtil = new PageUtil<Customer>();
            pageUtil.setPageSize(500);

            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("userId_s_eq", model.getUserId());
            condition.put("id_s_order", "desc");

            pageUtil.setAdvSearch(condition);
            customerService.find(pageUtil);
            List<Customer> dataList = pageUtil.getPageList();

            //开始拼接json数据
            JSONObject outer = new JSONObject();
            outer.put("id", 1);
            outer.put("text", "客户列表");
            outer.put("state", "open");
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Customer customer : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", customer.getId());
                    item.put("text", customer.getCustomerName());
                    //默认都是勾选上的
                    item.put("checked", true);
                    //结束
                    dataArray.add(item);
                }
            }
            outer.put("children", dataArray);
            //回写查询结果
            toClient("[" + outer.toString() + "]");
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>查找客户异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>回写查询客户结果异常", e);
        }
    }

    /**
     * 批量转移客户
     */
    public void batchTransCustomer(){
        String oldUserId = model.getOldUserId();
        String newUserId = model.getNewUserId();
        String customerIds = model.getCustomerIds();
        Log.infoFileSync("==================开始调用批量转移客户方法===================");
        Boolean flag = false;
        try{
            customerService.batchTransCustomer(oldUserId,newUserId,customerIds);

            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        }catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>批量转移客户异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>批量转移客户回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "批量转移客户", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量转移客户id为：  " + model.getCustomerIds() + "转移前所属业务员编号：  " + model.getOldUserId() + "转移后所属业务员编号：  " + model.getNewUserId()
                + " " + tipMsg + "！", "增加客户信息" + tipMsg));
        Log.infoFileSync("==================结束调用增加客户信息方法===================");
    }
    /**
     * 查询客户信息下拉列表
     */
    public void findBySelect_sup() {
        try {
            /**
             * 拼接搜索条件
             */
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("enabled_s_eq", 1);
            condition.put("id_s_order", "desc");

            PageUtil<Customer> pageUtil = new PageUtil<Customer>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(condition);
            customerService.find(pageUtil);
            List<Customer> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Customer customer : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("id", customer.getId());
                    //客户名称
                    item.put("customerName", customer.getCustomerName());
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找客户信息下拉框信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询客户信息下拉框信息结果异常", e);
        }
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
        condition.put("customerNo_s_like", model.getCustomerNo());
        condition.put("address_s_like", model.getAddress());

        condition.put("state_s_like", model.getState());
        condition.put("city_s_like", model.getCity());

        condition.put("customerName_s_eq", model.getCustomerName());
        condition.put("phonenum_s_like", model.getPhonenum());
        condition.put("type_s_eq", model.getType());
        condition.put("userId_s_eq", model.getUserId());
        condition.put("id_s_order", "desc");
        return condition;
    }
    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    @Override
    public CustomerModel getModel() {
        return model;
    }

    /**
     * Setter method for property <tt>customerService</tt>.
     *
     * @param customerService value to be assigned to property customerService
     */
    public void setCustomerService(CustomerIService customerService) {
        this.customerService = customerService;
    }

    /**
     * Setter method for property <tt>userBusinessService</tt>.
     *
     * @param userBusinessService value to be assigned to property userBusinessService
     */
    public void setUserBusinessService(UserBusinessIService userBusinessService) {
        this.userBusinessService = userBusinessService;
    }

    /**
     * Setter method for property <tt>userService</tt>.
     *
     * @param userService value to be assigned to property userService
     */
    public void setUserService(UserIService userService) {
        this.userService = userService;
    }
}