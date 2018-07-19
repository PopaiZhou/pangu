/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.model.po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author zhoujp
 * @version $Id Product.java, v 0.1 2018-07-18 20:59 Lenovo Exp $$
 */
@Entity
@Table(name = "jsh_product", schema = "jsh_erp", catalog = "")
public class Product {
    private long id;
    private String productId;
    private String productName;
    private Template templateId;
    private Supplier supplierNo;
    private String standard;
    private Double purchasePrice;
    private Double wholesalePrice;
    private Double retailPrice;
    private Double inventory;
    private Boolean enabled;
    private Timestamp gmtCreate;
    private Timestamp gmtModify;

    public Product(long id, String productId, String productName, Template templateId, Supplier supplierNo, String standard, Double purchasePrice, Double wholesalePrice, Double retailPrice, Double inventory, Boolean enabled, Timestamp gmtCreate, Timestamp gmtModify) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.templateId = templateId;
        this.supplierNo = supplierNo;
        this.standard = standard;
        this.purchasePrice = purchasePrice;
        this.wholesalePrice = wholesalePrice;
        this.retailPrice = retailPrice;
        this.inventory = inventory;
        this.enabled = enabled;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
    }

    public Product() {
    }

    /**
     * Getter method for property <tt>templateId</tt>.
     *
     * @return property value of templateId
     */
    public Template getTemplateId() {
        return templateId;
    }

    /**
     * Setter method for property <tt>templateId</tt>.
     *
     * @param templateId value to be assigned to property templateId
     */
    public void setTemplateId(Template templateId) {
        this.templateId = templateId;
    }

    /**
     * Getter method for property <tt>supplierNo</tt>.
     *
     * @return property value of supplierNo
     */
    public Supplier getSupplierNo() {
        return supplierNo;
    }

    /**
     * Setter method for property <tt>supplierNo</tt>.
     *
     * @param supplierNo value to be assigned to property supplierNo
     */
    public void setSupplierNo(Supplier supplierNo) {
        this.supplierNo = supplierNo;
    }
    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    @Id
    @Column(name = "id")
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
    @Basic
    @Column(name = "productId")
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
    @Basic
    @Column(name = "productName")
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
     * Getter method for property <tt>standard</tt>.
     *
     * @return property value of standard
     */
    @Basic
    @Column(name = "standard")
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
    @Basic
    @Column(name = "purchasePrice")
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
    @Basic
    @Column(name = "wholesalePrice")
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
    @Basic
    @Column(name = "retailPrice")
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
    @Basic
    @Column(name = "inventory")
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
     * Getter method for property <tt>enabled</tt>.
     *
     * @return property value of enabled
     */
    @Basic
    @Column(name = "enabled")
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * Setter method for property <tt>enabled</tt>.
     *
     * @param enabled value to be assigned to property enabled
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
    @Basic
    @Column(name = "gmtCreate")
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    /**
     * Setter method for property <tt>gmtCreate</tt>.
     *
     * @param gmtCreate value to be assigned to property gmtCreate
     */
    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * Getter method for property <tt>gmtModify</tt>.
     *
     * @return property value of gmtModify
     */
    @Basic
    @Column(name = "gmtModify")
    public Timestamp getGmtModify() {
        return gmtModify;
    }

    /**
     * Setter method for property <tt>gmtModify</tt>.
     *
     * @param gmtModify value to be assigned to property gmtModify
     */
    public void setGmtModify(Timestamp gmtModify) {
        this.gmtModify = gmtModify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product that = (Product) o;

        if (id != that.id) return false;
        if (productId != null ? !productId.equals(that.productId) : that.productId != null) return false;
        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        if (templateId != null ? !templateId.equals(that.templateId) : that.templateId != null) return false;
        if (supplierNo != null ? !supplierNo.equals(that.supplierNo) : that.supplierNo != null) return false;
        if (standard != null ? !standard.equals(that.standard) : that.standard != null) return false;
        if (purchasePrice != null ? !purchasePrice.equals(that.purchasePrice) : that.purchasePrice != null)
            return false;
        if (wholesalePrice != null ? !wholesalePrice.equals(that.wholesalePrice) : that.wholesalePrice != null)
            return false;
        if (retailPrice != null ? !retailPrice.equals(that.retailPrice) : that.retailPrice != null) return false;
        if (inventory != null ? !inventory.equals(that.inventory) : that.inventory != null) return false;
        if (enabled != null ? !enabled.equals(that.enabled) : that.enabled != null) return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null) return false;
        if (gmtModify != null ? !gmtModify.equals(that.gmtModify) : that.gmtModify != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (templateId != null ? templateId.hashCode() : 0);
        result = 31 * result + (supplierNo != null ? supplierNo.hashCode() : 0);
        result = 31 * result + (standard != null ? standard.hashCode() : 0);
        result = 31 * result + (purchasePrice != null ? purchasePrice.hashCode() : 0);
        result = 31 * result + (wholesalePrice != null ? wholesalePrice.hashCode() : 0);
        result = 31 * result + (retailPrice != null ? retailPrice.hashCode() : 0);
        result = 31 * result + (inventory != null ? inventory.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtModify != null ? gmtModify.hashCode() : 0);
        return result;
    }
}