/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.model.vo.basic;

import java.io.Serializable;

/**
 * @author zhoujp
 * @version $Id RegionAreaModel.java, v 0.1 2018-05-04 17:13 Lenovo Exp $$
 */
public class RegionAreaModel implements Serializable{
    private static final long serialVersionUID = -4514070393840831909L;

    /**
     * 省市区编号
     */
    private String regionId;
    /**
     * 省市区名称
     */
    private String name;
    /**
     * 上级编号
     */
    private String parentId;
    /**
     * 上级名称
     */
    private String parentName;

    /**
     * Getter method for property <tt>regionId</tt>.
     *
     * @return property value of regionId
     */
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
}