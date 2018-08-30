/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.model.vo.materials;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author zhoujp
 * @version $Id TemplateModel.java, v 0.1 2018-07-15 12:10 Lenovo Exp $$
 */
public class CustomerModel implements Serializable{

    private static final long serialVersionUID = 5232161392816748516L;

    private CustomerShowModel showModel = new CustomerShowModel();

    private long id;
    private String customerNo;
    private String customerName;
    private String customerShort;
    private String contacts;
    private String phonenum;
    private String email;
    private String description;
    private Byte isystem;
    private String type;
    private Boolean enabled;
    private String fax;
    private String telephone;
    private String qq;
    private String express;
    private String address;
    private String taxNum;
    private String bankName;
    private String accountNumber;
    private Double taxRate;
    private String state;
    private String city;
    private String street;
    private String userId;

    private String batchDeleteIds;

    /**
     * 批量转移客户参数
     */
    private String oldUserId;
    private String newUserId;
    private String customerIds;

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
     * excel导属性
     */
    private String browserType = ""; //浏览器类型
    private String fileName = ""; //文件名称
    private InputStream excelStream;  //输入流，导出excel文件


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
     * Getter method for property <tt>customerNo</tt>.
     *
     * @return property value of customerNo
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * Setter method for property <tt>customerNo</tt>.
     *
     * @param customerNo value to be assigned to property customerNo
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * Getter method for property <tt>customerName</tt>.
     *
     * @return property value of customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter method for property <tt>customerName</tt>.
     *
     * @param customerName value to be assigned to property customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter method for property <tt>customerShort</tt>.
     *
     * @return property value of customerShort
     */
    public String getCustomerShort() {
        return customerShort;
    }

    /**
     * Setter method for property <tt>customerShort</tt>.
     *
     * @param customerShort value to be assigned to property customerShort
     */
    public void setCustomerShort(String customerShort) {
        this.customerShort = customerShort;
    }

    /**
     * Getter method for property <tt>contacts</tt>.
     *
     * @return property value of contacts
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * Setter method for property <tt>contacts</tt>.
     *
     * @param contacts value to be assigned to property contacts
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    /**
     * Getter method for property <tt>phonenum</tt>.
     *
     * @return property value of phonenum
     */
    public String getPhonenum() {
        return phonenum;
    }

    /**
     * Setter method for property <tt>phonenum</tt>.
     *
     * @param phonenum value to be assigned to property phonenum
     */
    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    /**
     * Getter method for property <tt>email</tt>.
     *
     * @return property value of email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter method for property <tt>email</tt>.
     *
     * @param email value to be assigned to property email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter method for property <tt>description</tt>.
     *
     * @return property value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter method for property <tt>description</tt>.
     *
     * @param description value to be assigned to property description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter method for property <tt>isystem</tt>.
     *
     * @return property value of isystem
     */
    public Byte getIsystem() {
        return isystem;
    }

    /**
     * Setter method for property <tt>isystem</tt>.
     *
     * @param isystem value to be assigned to property isystem
     */
    public void setIsystem(Byte isystem) {
        this.isystem = isystem;
    }

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     *
     * @param type value to be assigned to property type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>enabled</tt>.
     *
     * @return property value of enabled
     */
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
     * Getter method for property <tt>fax</tt>.
     *
     * @return property value of fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Setter method for property <tt>fax</tt>.
     *
     * @param fax value to be assigned to property fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Getter method for property <tt>telephone</tt>.
     *
     * @return property value of telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Setter method for property <tt>telephone</tt>.
     *
     * @param telephone value to be assigned to property telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Getter method for property <tt>qq</tt>.
     *
     * @return property value of qq
     */
    public String getQq() {
        return qq;
    }

    /**
     * Setter method for property <tt>qq</tt>.
     *
     * @param qq value to be assigned to property qq
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * Getter method for property <tt>express</tt>.
     *
     * @return property value of express
     */
    public String getExpress() {
        return express;
    }

    /**
     * Setter method for property <tt>express</tt>.
     *
     * @param express value to be assigned to property express
     */
    public void setExpress(String express) {
        this.express = express;
    }

    /**
     * Getter method for property <tt>address</tt>.
     *
     * @return property value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for property <tt>address</tt>.
     *
     * @param address value to be assigned to property address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method for property <tt>taxNum</tt>.
     *
     * @return property value of taxNum
     */
    public String getTaxNum() {
        return taxNum;
    }

    /**
     * Setter method for property <tt>taxNum</tt>.
     *
     * @param taxNum value to be assigned to property taxNum
     */
    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum;
    }

    /**
     * Getter method for property <tt>bankName</tt>.
     *
     * @return property value of bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Setter method for property <tt>bankName</tt>.
     *
     * @param bankName value to be assigned to property bankName
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * Getter method for property <tt>accountNumber</tt>.
     *
     * @return property value of accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Setter method for property <tt>accountNumber</tt>.
     *
     * @param accountNumber value to be assigned to property accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Getter method for property <tt>taxRate</tt>.
     *
     * @return property value of taxRate
     */
    public Double getTaxRate() {
        return taxRate;
    }

    /**
     * Setter method for property <tt>taxRate</tt>.
     *
     * @param taxRate value to be assigned to property taxRate
     */
    public void setTaxRate(Double taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * Getter method for property <tt>state</tt>.
     *
     * @return property value of state
     */
    public String getState() {
        return state;
    }

    /**
     * Setter method for property <tt>state</tt>.
     *
     * @param state value to be assigned to property state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Getter method for property <tt>city</tt>.
     *
     * @return property value of city
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter method for property <tt>city</tt>.
     *
     * @param city value to be assigned to property city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Getter method for property <tt>street</tt>.
     *
     * @return property value of street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Setter method for property <tt>street</tt>.
     *
     * @param street value to be assigned to property street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Getter method for property <tt>userId</tt>.
     *
     * @return property value of userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Setter method for property <tt>userId</tt>.
     *
     * @param userId value to be assigned to property userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
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
     * Getter method for property <tt>batchDeleteIds</tt>.
     *
     * @return property value of batchDeleteIds
     */
    public String getBatchDeleteIds() {
        return batchDeleteIds;
    }

    /**
     * Setter method for property <tt>batchDeleteIds</tt>.
     *
     * @param batchDeleteIds value to be assigned to property batchDeleteIds
     */
    public void setBatchDeleteIds(String batchDeleteIds) {
        this.batchDeleteIds = batchDeleteIds;
    }

    /**
     * Getter method for property <tt>showModel</tt>.
     *
     * @return property value of showModel
     */
    public CustomerShowModel getShowModel() {
        return showModel;
    }

    /**
     * Setter method for property <tt>showModel</tt>.
     *
     * @param showModel value to be assigned to property showModel
     */
    public void setShowModel(CustomerShowModel showModel) {
        this.showModel = showModel;
    }

    /**
     * Getter method for property <tt>browserType</tt>.
     *
     * @return property value of browserType
     */
    public String getBrowserType() {
        return browserType;
    }

    /**
     * Setter method for property <tt>browserType</tt>.
     *
     * @param browserType value to be assigned to property browserType
     */
    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    /**
     * Getter method for property <tt>fileName</tt>.
     *
     * @return property value of fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter method for property <tt>fileName</tt>.
     *
     * @param fileName value to be assigned to property fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter method for property <tt>excelStream</tt>.
     *
     * @return property value of excelStream
     */
    public InputStream getExcelStream() {
        return excelStream;
    }

    /**
     * Setter method for property <tt>excelStream</tt>.
     *
     * @param excelStream value to be assigned to property excelStream
     */
    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    /**
     * Getter method for property <tt>oldUserId</tt>.
     *
     * @return property value of oldUserId
     */
    public String getOldUserId() {
        return oldUserId;
    }

    /**
     * Setter method for property <tt>oldUserId</tt>.
     *
     * @param oldUserId value to be assigned to property oldUserId
     */
    public void setOldUserId(String oldUserId) {
        this.oldUserId = oldUserId;
    }

    /**
     * Getter method for property <tt>newUserId</tt>.
     *
     * @return property value of newUserId
     */
    public String getNewUserId() {
        return newUserId;
    }

    /**
     * Setter method for property <tt>newUserId</tt>.
     *
     * @param newUserId value to be assigned to property newUserId
     */
    public void setNewUserId(String newUserId) {
        this.newUserId = newUserId;
    }

    /**
     * Getter method for property <tt>customerIds</tt>.
     *
     * @return property value of customerIds
     */
    public String getCustomerIds() {
        return customerIds;
    }

    /**
     * Setter method for property <tt>customerIds</tt>.
     *
     * @param customerIds value to be assigned to property customerIds
     */
    public void setCustomerIds(String customerIds) {
        this.customerIds = customerIds;
    }
}