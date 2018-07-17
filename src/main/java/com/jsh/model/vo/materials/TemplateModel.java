/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.model.vo.materials;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author zhoujp
 * @version $Id TemplateModel.java, v 0.1 2018-07-15 12:10 Lenovo Exp $$
 */
public class TemplateModel implements Serializable{
    private static final long serialVersionUID = 5119638833993917751L;

    private TemplateShowModel showModel = new TemplateShowModel();

    private long id;
    private String templateId;
    private String templateName;
    private Date listingDate;
    private String supplierNo;
    private String remarks;
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
     * 版本册IDs 批量操作使用
     */
    private String templateIds = "";

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
     * Getter method for property <tt>templateName</tt>.
     *
     * @return property value of templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Setter method for property <tt>templateName</tt>.
     *
     * @param templateName value to be assigned to property templateName
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * Getter method for property <tt>listingDate</tt>.
     *
     * @return property value of listingDate
     */
    public Date getListingDate() {
        return listingDate;
    }

    /**
     * Setter method for property <tt>listingDate</tt>.
     *
     * @param listingDate value to be assigned to property listingDate
     */
    public void setListingDate(Date listingDate) {
        this.listingDate = listingDate;
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
     * Getter method for property <tt>remarks</tt>.
     *
     * @return property value of remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Setter method for property <tt>remarks</tt>.
     *
     * @param remarks value to be assigned to property remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
     * Getter method for property <tt>templateIds</tt>.
     *
     * @return property value of templateIds
     */
    public String getTemplateIds() {
        return templateIds;
    }

    /**
     * Setter method for property <tt>templateIds</tt>.
     *
     * @param templateIds value to be assigned to property templateIds
     */
    public void setTemplateIds(String templateIds) {
        this.templateIds = templateIds;
    }

    /**
     * Getter method for property <tt>showModel</tt>.
     *
     * @return property value of showModel
     */
    public TemplateShowModel getShowModel() {
        return showModel;
    }

    /**
     * Setter method for property <tt>showModel</tt>.
     *
     * @param showModel value to be assigned to property showModel
     */
    public void setShowModel(TemplateShowModel showModel) {
        this.showModel = showModel;
    }
}