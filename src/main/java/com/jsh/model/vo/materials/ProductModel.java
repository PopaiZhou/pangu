/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.model.vo.materials;

import java.io.Serializable;

/**
 * @author zhoujp
 * @version $Id ProductModel.java, v 0.1 2018-07-18 21:15 Lenovo Exp $$
 */
public class ProductModel implements Serializable {
    private static final long serialVersionUID = 1799209926769523476L;

    private ProductShowModel showModel = new ProductShowModel();

    private long id;
    private String productId;
    private String productName;
    private String templateId;
    private String supplierNo;
    /**
     * 规格
     */
    private String standard;
    /**
     * 进货价
     */
    private Double purchasePrice;
    /**
     * 批发价
     */
    private Double wholesalePrice;
    /**
     * 零售价
     */
    private Double retailPrice;
    /**
     * 库存--暂时不用
     */
    private Double inventory;

    /**
     * 重量系数
     */
    private Double weight;

    /**
     * 每页显示的个数
     */
    private int pageSize = 10;

    /**
     * 当前页码
     */
    private int pageNo = 1;

    /**
     * 用户IP，用户记录操作日志
     */
    private String clientIp = "";

    /**
     * 批量删除用
     */
    private String productIds = "";

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    public long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>productId</tt>.
     *
     * @return property value of productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Setter method for property <tt>productId</tt>.
     *
     * @param productId value to be assigned to property productId
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

    /**
     * Getter method for property <tt>productName</tt>.
     *
     * @return property value of productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Setter method for property <tt>productName</tt>.
     *
     * @param productName value to be assigned to property productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Getter method for property <tt>templateId</tt>.
     *
     * @return property value of templateId
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * Setter method for property <tt>templateId</tt>.
     *
     * @param templateId value to be assigned to property templateId
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * Getter method for property <tt>supplierNo</tt>.
     *
     * @return property value of supplierNo
     */
    public String getSupplierNo() {
        return supplierNo;
    }

    /**
     * Setter method for property <tt>supplierNo</tt>.
     *
     * @param supplierNo value to be assigned to property supplierNo
     */
    public void setSupplierNo(String supplierNo) {
        this.supplierNo = supplierNo;
    }

    /**
     * Getter method for property <tt>standard</tt>.
     *
     * @return property value of standard
     */
    public String getStandard() {
        return standard;
    }

    /**
     * Setter method for property <tt>standard</tt>.
     *
     * @param standard value to be assigned to property standard
     */
    public void setStandard(String standard) {
        this.standard = standard;
    }

    /**
     * Getter method for property <tt>purchasePrice</tt>.
     *
     * @return property value of purchasePrice
     */
    public Double getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Setter method for property <tt>purchasePrice</tt>.
     *
     * @param purchasePrice value to be assigned to property purchasePrice
     */
    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * Getter method for property <tt>wholesalePrice</tt>.
     *
     * @return property value of wholesalePrice
     */
    public Double getWholesalePrice() {
        return wholesalePrice;
    }

    /**
     * Setter method for property <tt>wholesalePrice</tt>.
     *
     * @param wholesalePrice value to be assigned to property wholesalePrice
     */
    public void setWholesalePrice(Double wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    /**
     * Getter method for property <tt>retailPrice</tt>.
     *
     * @return property value of retailPrice
     */
    public Double getRetailPrice() {
        return retailPrice;
    }

    /**
     * Setter method for property <tt>retailPrice</tt>.
     *
     * @param retailPrice value to be assigned to property retailPrice
     */
    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    /**
     * Getter method for property <tt>inventory</tt>.
     *
     * @return property value of inventory
     */
    public Double getInventory() {
        return inventory;
    }

    /**
     * Setter method for property <tt>inventory</tt>.
     *
     * @param inventory value to be assigned to property inventory
     */
    public void setInventory(Double inventory) {
        this.inventory = inventory;
    }

    /**
     * Getter method for property <tt>pageSize</tt>.
     *
     * @return property value of pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Setter method for property <tt>pageSize</tt>.
     *
     * @param pageSize value to be assigned to property pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Getter method for property <tt>pageNo</tt>.
     *
     * @return property value of pageNo
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * Setter method for property <tt>pageNo</tt>.
     *
     * @param pageNo value to be assigned to property pageNo
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * Getter method for property <tt>clientIp</tt>.
     *
     * @return property value of clientIp
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * Setter method for property <tt>clientIp</tt>.
     *
     * @param clientIp value to be assigned to property clientIp
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * Getter method for property <tt>showModel</tt>.
     *
     * @return property value of showModel
     */
    public ProductShowModel getShowModel() {
        return showModel;
    }

    /**
     * Setter method for property <tt>showModel</tt>.
     *
     * @param showModel value to be assigned to property showModel
     */
    public void setShowModel(ProductShowModel showModel) {
        this.showModel = showModel;
    }

    /**
     * Getter method for property <tt>productIds</tt>.
     *
     * @return property value of productIds
     */
    public String getProductIds() {
        return productIds;
    }

    /**
     * Setter method for property <tt>productIds</tt>.
     *
     * @param productIds value to be assigned to property productIds
     */
    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }

    /**
     * Getter method for property <tt>weight</tt>.
     *
     * @return property value of weight
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * Setter method for property <tt>weight</tt>.
     *
     * @param weight value to be assigned to property weight
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }
}