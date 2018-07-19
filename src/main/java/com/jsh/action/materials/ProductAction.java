/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.action.materials;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.Product;
import com.jsh.model.po.Supplier;
import com.jsh.model.po.Template;
import com.jsh.model.vo.materials.ProductModel;
import com.jsh.service.materials.ProductIService;
import com.jsh.service.materials.TemplateIService;
import com.jsh.util.PageUtil;
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
 * @version $Id ProductAction.java, v 0.1 2018-07-18 21:14 Lenovo Exp $$
 */
public class ProductAction extends BaseAction<ProductModel>{

    private ProductIService productService;
    private TemplateIService templateService;
    private ProductModel model = new ProductModel();


    /**
     * 新增产品信息
     */
    public void create(){
        Log.infoFileSync("==================开始调用产品型号方法===================");
        Boolean flag = false;
        try {
            Product product = new Product();
            product.setProductId(model.getProductId());
            product.setProductName(model.getProductName());
            product.setTemplateId(new Template(Long.parseLong(model.getTemplateId())));
            product.setSupplierNo(templateService.get(Long.parseLong(model.getTemplateId())).getSupplierNo());
            product.setStandard(model.getStandard());
            product.setPurchasePrice(model.getPurchasePrice());
            product.setWholesalePrice(model.getWholesalePrice());
            product.setRetailPrice(model.getRetailPrice());

            productService.create(product);

            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        }catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加产品型号异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>增加产品型号回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "增加产品型号", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "增加产品型号名称为  " + model.getProductName() + " " + tipMsg + "！", "增加产品型号" + tipMsg));
        Log.infoFileSync("==================结束调用增加产品型号方法===================");
    }
    /**
     * 更新
     */
    public void update() {
        Log.infoFileSync("==================开始调用更新产品型号方法===================");
        Product product = productService.get(model.getId());
        Boolean flag = false;
        try{
            product.setProductId(model.getProductId());
            product.setProductName(model.getProductName());
            product.setTemplateId(new Template(Long.parseLong(model.getTemplateId())));
            product.setSupplierNo(templateService.get(Long.parseLong(model.getTemplateId())).getSupplierNo());
            product.setStandard(model.getStandard());
            product.setPurchasePrice(model.getPurchasePrice());
            product.setWholesalePrice(model.getWholesalePrice());
            product.setRetailPrice(model.getRetailPrice());
            productService.update(product);

            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        }catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>更新产品型号异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>更新产品型号回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新版本型号", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "更新版本型号名称为  " + model.getProductName() + " " + tipMsg + "！", "更新版本型号" + tipMsg));
        Log.infoFileSync("==================结束调用更新版本型号方法===================");
    }
    /**
     * 查询
     * @return
     */
    public void findBy(){
        try{
            PageUtil<Product> pageUtil = new PageUtil<>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());

            productService.find(pageUtil);

            List<Product> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for(Product product : dataList){
                    JSONObject item = new JSONObject();
                    //收支项目名称
                    item.put("id",product.getId());
                    item.put("productId", product.getProductId());
                    item.put("productName", product.getProductName());
                    item.put("tId", product.getTemplateId().getId());
                    item.put("templateId", product.getTemplateId().getTemplateId());
                    item.put("templateName", product.getTemplateId().getTemplateName());
                    item.put("supplier", product.getSupplierNo().getSupplier());
                    item.put("standard",product.getStandard());
                    item.put("purchasePrice",product.getPurchasePrice());
                    item.put("wholesalePrice",product.getWholesalePrice());
                    item.put("retailPrice",product.getRetailPrice());
                    item.put("op","1");
                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找产品信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询产品结果异常", e);
        }
    }

    /**
     * 单个删除
     */
    public String delete() {
        Log.infoFileSync("====================开始调用单个删除产品型号信息方法delete()================");
        try {
            productService.delete(model.getId());
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>删除ID为 " + model.getId() + "  的产品异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        model.getShowModel().setMsgTip(tipMsg);
        logService.create(new Logdetails(getUser(), "删除产品型号", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "删除产品型号ID为  " + model.getId() + ",名称为  " + model.getProductName() + tipMsg + "！", "删除产品型号" + tipMsg));
        Log.infoFileSync("====================结束调用删除产品信息方法delete()================");
        return SUCCESS;
    }
    /**
     * 批量删除指定ID产品
     *
     * @return
     */
    public String batchDelete() {
        try {
            productService.batchDelete(model.getProductIds());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量删除产品型号ID为：" + model.getProductIds() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        logService.create(new Logdetails(getUser(), "批量删除产品型号", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量删除产品型号ID为  " + model.getProductIds() + " " + tipMsg + "！", "批量删除产品型号" + tipMsg));
        return SUCCESS;
    }

    /**
     * 验证产品编号是否重复
     */
    public void checkIsProductIdExist() {
        Boolean flag = false;
        try {
            flag = productService.checkIsNameExist("productId", model.getProductId(), "id", model.getId());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>检查产品型号名称为：" + model.getProductId() + " ID为： " + model.getId() + " 是否存在异常！");
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>回写检查产品型号名称为：" + model.getProductId() + " ID为： " + model.getId() + " 是否存在异常！", e);
            }
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
        condition.put("productId_s_like", model.getProductId());
        condition.put("productName_s_like", model.getProductName());
        condition.put("templateId_s_like", model.getTemplateId());
        condition.put("id_s_order", "desc");
        return condition;
    }

    //=============以下spring注入以及Model驱动公共方法，与Action处理无关==================
    @Override
    public ProductModel getModel() {
        return model;
    }

    /**
     * Setter method for property <tt>productService</tt>.
     *
     * @param productService value to be assigned to property productService
     */
    public void setProductService(ProductIService productService) {
        this.productService = productService;
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