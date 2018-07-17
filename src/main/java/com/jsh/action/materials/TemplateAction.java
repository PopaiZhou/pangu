/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.action.materials;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.Supplier;
import com.jsh.model.po.Template;
import com.jsh.model.vo.materials.TemplateModel;
import com.jsh.service.materials.TemplateIService;
import com.jsh.util.PageUtil;
import com.jsh.util.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.time.DateUtils;
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
public class TemplateAction extends BaseAction<TemplateModel> {
    private TemplateIService templateService;
    private TemplateModel model = new TemplateModel();

    /**
     * 新增版本册
     */
    public void create(){
        Log.infoFileSync("==================开始调用增加版本册方法===================");
        Boolean flag = false;
        try {
            Template template = new Template();
            template.setTemplateId(model.getTemplateId());
            template.setTemplateName(model.getTemplateName());
            template.setListingDate(model.getListingDate());
            template.setSupplierNo(new Supplier(Long.parseLong(model.getSupplierNo())));
            template.setRemarks(model.getRemarks());

            templateService.create(template);

            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        }catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加版本册异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>增加版本册回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "增加版本册", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "增加版本册名称为  " + model.getTemplateName() + " " + tipMsg + "！", "增加版本册" + tipMsg));
        Log.infoFileSync("==================结束调用增加版本册方法===================");
    }

    /**
     * 查询版本册列表
     */
    public void findBy(){
        try{
            PageUtil<Template> pageUtil = new PageUtil<>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());

            templateService.find(pageUtil);

            List<Template> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for(Template template : dataList){
                    JSONObject item = new JSONObject();
                    //收支项目名称
                    item.put("id",template.getId());
                    item.put("templateId", template.getTemplateId());
                    item.put("templateName", template.getTemplateName());
                    item.put("listingDate", template.getListingDate() == null ? "" : Tools.getCurrentMonth(template.getListingDate()));
                    item.put("supplierNo", template.getSupplierNo().getId());
                    item.put("supplierName", template.getSupplierNo().getSupplier());
                    item.put("remarks",template.getRemarks());
                    item.put("op","1");
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } /*catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找版本册信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询版本册结果异常", e);
        }*/
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 批量删除指定ID版本册
     *
     * @return
     */
    public String batchDelete() {
        try {
            templateService.batchDelete(model.getTemplateIds());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量删除版本册ID为：" + model.getTemplateIds() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        logService.create(new Logdetails(getUser(), "批量删除版本册", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量删除版本册ID为  " + model.getTemplateIds() + " " + tipMsg + "！", "批量删除版本册" + tipMsg));
        return SUCCESS;
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
        condition.put("templateId_s_like", model.getTemplateId());
        condition.put("templateName_s_like", model.getTemplateName());
        condition.put("supplierNo_s_like", model.getSupplierNo());
        condition.put("listingDate_s_gteq", model.getBeginTime());
        condition.put("listingDate_s_lteq", model.getEndTime());
        condition.put("id_s_order", "desc");
        return condition;
    }

    /**
     * 验证版本册编号是否重复
     */
    public void checkIsTemplateIdExist() {
        Boolean flag = false;
        try {
            flag = templateService.checkIsNameExist("templateId", model.getTemplateId(), "id", model.getId());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查供应商名称为：" + model.getTemplateId() + " ID为： " + model.getId() + " 是否存在异常！");
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>回写检查供应商名称为：" + model.getTemplateId() + " ID为： " + model.getId() + " 是否存在异常！", e);
            }
        }
    }


    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    @Override
    public TemplateModel getModel() {
        return model;
    }

    /**
     * Setter method for property <tt>templateService</tt>.
     *
     * @param templateService value to be assigned to property templateService
     */
    public void setTemplateService(TemplateIService templateService) {
        this.templateService = templateService;
    }
}