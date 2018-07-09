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
 * @version $Id RegionArea.java, v 0.1 2018-05-04 16:13 Lenovo Exp $$
 */
@Entity
@Table(name = "jsh_region_area", schema = "jsh_erp", catalog = "")
public class RegionArea implements java.io.Serializable{
    private long id;
    private String regionId;
    private String name;
    private String parentId;
    private String parentName;
    private String areacode;
    private String zipcode;
    private String depth;
    private String areaNameEn;
    private String parentAreaNameEn;
    private String isoCode;
    private Timestamp gmtCreate;
    private Timestamp gmtModify;


    public RegionArea() {
    }

    public RegionArea(long id, String regionId, String name, String parentId, String parentName, String areacode, String zipcode, String depth, String areaNameEn, String parentAreaNameEn, String isoCode, Timestamp gmtCreate, Timestamp gmtModify) {
        this.id = id;
        this.regionId = regionId;
        this.name = name;
        this.parentId = parentId;
        this.parentName = parentName;
        this.areacode = areacode;
        this.zipcode = zipcode;
        this.depth = depth;
        this.areaNameEn = areaNameEn;
        this.parentAreaNameEn = parentAreaNameEn;
        this.isoCode = isoCode;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
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
     * Getter method for property <tt>regionId</tt>.
     *
     * @return property value of regionId
     */
    @Basic
    @Column(name = "region_id")
    public String getRegionId() {
        return regionId;
    }

    /**
     * Setter method for property <tt>regionId</tt>.
     *
     * @param regionId value to be assigned to property regionId
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>parentId</tt>.
     *
     * @return property value of parentId
     */
    @Basic
    @Column(name = "parent_id")
    public String getParentId() {
        return parentId;
    }

    /**
     * Setter method for property <tt>parentId</tt>.
     *
     * @param parentId value to be assigned to property parentId
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * Getter method for property <tt>parentName</tt>.
     *
     * @return property value of parentName
     */
    @Basic
    @Column(name = "parent_name")
    public String getParentName() {
        return parentName;
    }

    /**
     * Setter method for property <tt>parentName</tt>.
     *
     * @param parentName value to be assigned to property parentName
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * Getter method for property <tt>areacode</tt>.
     *
     * @return property value of areacode
     */
    @Basic
    @Column(name = "areacode")
    public String getAreacode() {
        return areacode;
    }

    /**
     * Setter method for property <tt>areacode</tt>.
     *
     * @param areacode value to be assigned to property areacode
     */
    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    /**
     * Getter method for property <tt>zipcode</tt>.
     *
     * @return property value of zipcode
     */
    @Basic
    @Column(name = "zipcode")
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Setter method for property <tt>zipcode</tt>.
     *
     * @param zipcode value to be assigned to property zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * Getter method for property <tt>depth</tt>.
     *
     * @return property value of depth
     */
    @Basic
    @Column(name = "depth")
    public String getDepth() {
        return depth;
    }

    /**
     * Setter method for property <tt>depth</tt>.
     *
     * @param depth value to be assigned to property depth
     */
    public void setDepth(String depth) {
        this.depth = depth;
    }

    /**
     * Getter method for property <tt>areaNameEn</tt>.
     *
     * @return property value of areaNameEn
     */
    @Basic
    @Column(name = "area_name_en")
    public String getAreaNameEn() {
        return areaNameEn;
    }

    /**
     * Setter method for property <tt>areaNameEn</tt>.
     *
     * @param areaNameEn value to be assigned to property areaNameEn
     */
    public void setAreaNameEn(String areaNameEn) {
        this.areaNameEn = areaNameEn;
    }

    /**
     * Getter method for property <tt>parentAreaNameEn</tt>.
     *
     * @return property value of parentAreaNameEn
     */
    @Basic
    @Column(name = "parent_area_name_en")
    public String getParentAreaNameEn() {
        return parentAreaNameEn;
    }

    /**
     * Setter method for property <tt>parentAreaNameEn</tt>.
     *
     * @param parentAreaNameEn value to be assigned to property parentAreaNameEn
     */
    public void setParentAreaNameEn(String parentAreaNameEn) {
        this.parentAreaNameEn = parentAreaNameEn;
    }

    /**
     * Getter method for property <tt>isoCode</tt>.
     *
     * @return property value of isoCode
     */
    @Basic
    @Column(name = "iso_code")
    public String getIsoCode() {
        return isoCode;
    }

    /**
     * Setter method for property <tt>isoCode</tt>.
     *
     * @param isoCode value to be assigned to property isoCode
     */
    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    /**
     * Getter method for property <tt>gmtCreate</tt>.
     *
     * @return property value of gmtCreate
     */
    @Basic
    @Column(name = "gmt_create")
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
    @Column(name = "gmt_modify")
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

        RegionArea that = (RegionArea) o;

        if (id != that.id) return false;
        if (regionId != null ? !regionId.equals(that.regionId) : that.regionId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (parentName != null ? !parentName.equals(that.parentName) : that.parentName != null) return false;
        if (areacode != null ? !areacode.equals(that.areacode) : that.areacode != null) return false;
        if (zipcode != null ? !zipcode.equals(that.zipcode) : that.zipcode != null) return false;
        if (depth != null ? !depth.equals(that.depth) : that.depth != null) return false;
        if (areaNameEn != null ? !areaNameEn.equals(that.areaNameEn) : that.areaNameEn != null) return false;
        if (parentAreaNameEn != null ? !parentAreaNameEn.equals(that.parentAreaNameEn) : that.parentAreaNameEn != null)
            return false;
        if (isoCode != null ? !isoCode.equals(that.isoCode) : that.isoCode != null) return false;
        if (gmtCreate != null ? !gmtCreate.equals(that.gmtCreate) : that.gmtCreate != null) return false;
        if (gmtModify != null ? !gmtModify.equals(that.gmtModify) : that.gmtModify != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (regionId != null ? regionId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (parentName != null ? parentName.hashCode() : 0);
        result = 31 * result + (areacode != null ? areacode.hashCode() : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        result = 31 * result + (depth != null ? depth.hashCode() : 0);
        result = 31 * result + (areaNameEn != null ? areaNameEn.hashCode() : 0);
        result = 31 * result + (parentAreaNameEn != null ? parentAreaNameEn.hashCode() : 0);
        result = 31 * result + (isoCode != null ? isoCode.hashCode() : 0);
        result = 31 * result + (gmtCreate != null ? gmtCreate.hashCode() : 0);
        result = 31 * result + (gmtModify != null ? gmtModify.hashCode() : 0);
        return result;
    }
}