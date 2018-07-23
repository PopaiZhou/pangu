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
import java.util.Map;

/**
 * @author zhoujp
 * @version $Id Customer.java, v 0.1 2018-07-19 15:59 Lenovo Exp $$
 */
@Entity
@Table(name = "jsh_customer", schema = "jsh_erp", catalog = "")
public class Customer {
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
    private Basicuser user;


    //----------以下属性导入exel表格使用--------------------
    /**
     * 类型 right--正确 warn--警告  wrong--错误
     */
    private Map<Integer, String> cellInfo;

    /**
     * 行号
     */
    private Integer rowLineNum;

    /**
     * Getter method for property <tt>user</tt>.
     *
     * @return property value of user
     */
    public Basicuser getUser() {
        return user;
    }

    /**
     * Setter method for property <tt>user</tt>.
     *
     * @param user value to be assigned to property user
     */
    public void setUser(Basicuser user) {
        this.user = user;
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
     * Getter method for property <tt>customerNo</tt>.
     *
     * @return property value of customerNo
     */
    @Basic
    @Column(name = "customerNo", nullable = true, length = 32)
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
    @Basic
    @Column(name = "customerName", nullable = false, length = 255)
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
    @Basic
    @Column(name = "customerShort", nullable = true, length = 255)
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
    @Basic
    @Column(name = "contacts", nullable = true, length = 100)
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
    @Basic
    @Column(name = "phonenum", nullable = true, length = 30)
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
    @Basic
    @Column(name = "email", nullable = true, length = 50)
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
    @Basic
    @Column(name = "description", nullable = true, length = 500)
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
    @Basic
    @Column(name = "isystem", nullable = true)
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
    @Basic
    @Column(name = "type", nullable = true, length = 20)
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
    @Basic
    @Column(name = "enabled", nullable = true)
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
    @Basic
    @Column(name = "fax", nullable = true, length = 30)
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
    @Basic
    @Column(name = "telephone", nullable = true, length = 30)
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
    @Basic
    @Column(name = "qq", nullable = true, length = 30)
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
    @Basic
    @Column(name = "express", nullable = true, length = 30)
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
    @Basic
    @Column(name = "address", nullable = true, length = 50)
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
    @Basic
    @Column(name = "taxNum", nullable = true, length = 50)
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
    @Basic
    @Column(name = "bankName", nullable = true, length = 50)
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
    @Basic
    @Column(name = "accountNumber", nullable = true, length = 50)
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
    @Basic
    @Column(name = "taxRate", nullable = true, precision = 0)
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
    @Basic
    @Column(name = "state", nullable = true, length = 32)
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
    @Basic
    @Column(name = "city", nullable = true, length = 32)
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
    @Basic
    @Column(name = "street", nullable = true, length = 64)
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer that = (Customer) o;

        if (id != that.id) return false;
        if (customerNo != null ? !customerNo.equals(that.customerNo) : that.customerNo != null) return false;
        if (customerName != null ? !customerName.equals(that.customerName) : that.customerName != null) return false;
        if (customerShort != null ? !customerShort.equals(that.customerShort) : that.customerShort != null)
            return false;
        if (contacts != null ? !contacts.equals(that.contacts) : that.contacts != null) return false;
        if (phonenum != null ? !phonenum.equals(that.phonenum) : that.phonenum != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (isystem != null ? !isystem.equals(that.isystem) : that.isystem != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (enabled != null ? !enabled.equals(that.enabled) : that.enabled != null) return false;
        if (fax != null ? !fax.equals(that.fax) : that.fax != null) return false;
        if (telephone != null ? !telephone.equals(that.telephone) : that.telephone != null) return false;
        if (qq != null ? !qq.equals(that.qq) : that.qq != null) return false;
        if (express != null ? !express.equals(that.express) : that.express != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (taxNum != null ? !taxNum.equals(that.taxNum) : that.taxNum != null) return false;
        if (bankName != null ? !bankName.equals(that.bankName) : that.bankName != null) return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (taxRate != null ? !taxRate.equals(that.taxRate) : that.taxRate != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (street != null ? !street.equals(that.street) : that.street != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (customerNo != null ? customerNo.hashCode() : 0);
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (customerShort != null ? customerShort.hashCode() : 0);
        result = 31 * result + (contacts != null ? contacts.hashCode() : 0);
        result = 31 * result + (phonenum != null ? phonenum.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (isystem != null ? isystem.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (enabled != null ? enabled.hashCode() : 0);
        result = 31 * result + (fax != null ? fax.hashCode() : 0);
        result = 31 * result + (telephone != null ? telephone.hashCode() : 0);
        result = 31 * result + (qq != null ? qq.hashCode() : 0);
        result = 31 * result + (express != null ? express.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (taxNum != null ? taxNum.hashCode() : 0);
        result = 31 * result + (bankName != null ? bankName.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (taxRate != null ? taxRate.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    /**
     * Getter method for property <tt>cellInfo</tt>.
     *
     * @return property value of cellInfo
     */
    public Map<Integer, String> getCellInfo() {
        return cellInfo;
    }

    /**
     * Setter method for property <tt>cellInfo</tt>.
     *
     * @param cellInfo value to be assigned to property cellInfo
     */
    public void setCellInfo(Map<Integer, String> cellInfo) {
        this.cellInfo = cellInfo;
    }

    /**
     * Getter method for property <tt>rowLineNum</tt>.
     *
     * @return property value of rowLineNum
     */
    public Integer getRowLineNum() {
        return rowLineNum;
    }

    /**
     * Setter method for property <tt>rowLineNum</tt>.
     *
     * @param rowLineNum value to be assigned to property rowLineNum
     */
    public void setRowLineNum(Integer rowLineNum) {
        this.rowLineNum = rowLineNum;
    }
}