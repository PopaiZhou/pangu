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
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author zhoujp
 * @version $Id Template.java, v 0.1 2018-07-14 22:14 Lenovo Exp $$
 */
@Entity
@Table(name = "jsh_template", schema = "jsh_erp", catalog = "")
public class Template implements Serializable{
    private long id;
    private String templateId;
    private String templateName;
    private Date listingDate;
    private String supplierNo;
    private String remarks;
    private Timestamp gmtCreate;
    private Timestamp gmtModify;

    public Template(long id, String templateId, String templateName, Date listingDate, String supplierNo, String remarks, Timestamp gmtCreate, Timestamp gmtModify) {
        this.id = id;
        this.templateId = templateId;
        this.templateName = templateName;
        this.listingDate = listingDate;
        this.supplierNo = supplierNo;
        this.remarks = remarks;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
    }

    public Template() {
    }

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */
    @Id
    @Column(name = "id", nullable = false)
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
     * Getter method for property <tt>templateId</tt>.
     *
     * @return property value of templateId
     */
    @Basic
    @Column(name = "templateId", nullable = true, length = 32)
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
    @Basic
    @Column(name = "templateName", nullable = true, length = 128)
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
    @Basic
    @Column(name = "listingDate", nullable = true)
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
    @Basic
    @Column(name = "supplierNo", nullable = true, length = 32)
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
    @Basic
    @Column(name = "remarks", nullable = true, length = 512)
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
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
    @Basic
    @Column(name = "gmtCreate", nullable = false)
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
    @Column(name = "gmtModify", nullable = false)
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
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Template that = (Template) o;

        if (id != that.id) {
            return false;
        }
        if (templateId != null ? !templateId.equals(that.templateId) : that.templateId != null){
            return false;
        }
        if (templateName != null ? !templateName.equals(that.templateName) : that.templateName != null) {
            return false;
        }
        if (listingDate != null ? !listingDate.equals(that.listingDate) : that.listingDate != null) {
            return false;
        }
        if (supplierNo != null ? !supplierNo.equals(that.supplierNo) : that.supplierNo != null) {
            return false;
        }
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) {
            return false;
        }
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null) {
            return false;
        }
        if (gmtModify != null ? !gmtModify.equals(that.gmtModify) : that.gmtModify != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (templateId != null ? templateId.hashCode() : 0);
        result = 31 * result + (templateName != null ? templateName.hashCode() : 0);
        result = 31 * result + (listingDate != null ? listingDate.hashCode() : 0);
        result = 31 * result + (supplierNo != null ? supplierNo.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtModify != null ? gmtModify.hashCode() : 0);
        return result;
    }
}