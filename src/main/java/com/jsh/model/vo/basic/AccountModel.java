package com.jsh.model.vo.basic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AccountModel implements Serializable {
    private AccountShowModel showModel = new AccountShowModel();

    /**======开始接受页面参数=================**/
    /**
     * 名称
     */
    private String name = "";

    /**
     * 编号
     */
    private String serialNo = "";

    /**
     * 期初金额
     */
    private Double initialAmount;

    /**
     * 当前余额
     */
    private Double currentAmount;

    /**
     * 是否设为默认
     */
    private Boolean isDefault;

    /**
     * 备注
     */
    private String remark = "";

    /**
     * 分类ID
     */
    private Long accountID = 0l;

    /**
     * 分类IDs 批量操作使用
     */
    private String accountIDs = "";

    /**
     * 每页显示的个数
     */
    private int pageSize = 10;

    /**
     * 当前页码
     */
    private int pageNo = 1;

    /**
     * 银行互转 转出账号
     */
    private Long outAccountId;
    /**
     * 银行互转 转出账号名称
     */
    private String outAccountName;
    /**
     * 银行互转 转入账号
     */
    private Long inAccountId;
    /**
     * 银行互转 转入账号名称
     */
    private String inAccountName;

    /**
     * 用户IP，用户记录操作日志
     */
    private String clientIp = "";

    public AccountShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(AccountShowModel showModel) {
        this.showModel = showModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(Double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public String getAccountIDs() {
        return accountIDs;
    }

    public void setAccountIDs(String accountIDs) {
        this.accountIDs = accountIDs;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * Getter method for property <tt>outAccountId</tt>.
     *
     * @return property value of outAccountId
     */
    public Long getOutAccountId() {
        return outAccountId;
    }

    /**
     * Setter method for property <tt>outAccountId</tt>.
     *
     * @param outAccountId value to be assigned to property outAccountId
     */
    public void setOutAccountId(Long outAccountId) {
        this.outAccountId = outAccountId;
    }

    /**
     * Getter method for property <tt>inAccountId</tt>.
     *
     * @return property value of inAccountId
     */
    public Long getInAccountId() {
        return inAccountId;
    }

    /**
     * Setter method for property <tt>inAccountId</tt>.
     *
     * @param inAccountId value to be assigned to property inAccountId
     */
    public void setInAccountId(Long inAccountId) {
        this.inAccountId = inAccountId;
    }

    /**
     * Getter method for property <tt>outAccountName</tt>.
     *
     * @return property value of outAccountName
     */
    public String getOutAccountName() {
        return outAccountName;
    }

    /**
     * Setter method for property <tt>outAccountName</tt>.
     *
     * @param outAccountName value to be assigned to property outAccountName
     */
    public void setOutAccountName(String outAccountName) {
        this.outAccountName = outAccountName;
    }

    /**
     * Getter method for property <tt>inAccountName</tt>.
     *
     * @return property value of inAccountName
     */
    public String getInAccountName() {
        return inAccountName;
    }

    /**
     * Setter method for property <tt>inAccountName</tt>.
     *
     * @param inAccountName value to be assigned to property inAccountName
     */
    public void setInAccountName(String inAccountName) {
        this.inAccountName = inAccountName;
    }
}
