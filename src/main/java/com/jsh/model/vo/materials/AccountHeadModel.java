package com.jsh.model.vo.materials;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AccountHeadModel implements Serializable {
    private AccountHeadShowModel showModel = new AccountHeadShowModel();

    /**
     * ======开始接受页面参数=================
     **/
    private String Type;
    private Long OrganId;
    private Long UserId;
    private Long HandsPersonId;
    private Double ChangeAmount;
    private Double TotalPrice;
    private Long AccountId;
    private String BillNo;
    private String BillTime;
    private String Remark;
    private String BeginTime; //查询开始时间
    private String EndTime;  //查询结束时间
    private String MonthTime;  //查询月份

    private String supplierId; //单位Id，用于查询单位的收付款

    private String supType; //单位类型，客户、供应商、业务员
    /**
     * 分类ID
     */
    private Long accountHeadID = 0l;

    /**
     * 分类IDs 批量操作使用
     */
    private String accountHeadIDs = "";

    /**
     * 修改之前的金额
     */
    private Double preTotalPrice;
    /**
     * 修改之前的账户编号
     */
    private long preAccountId;

    /**
     * 账户编号ids
     */
    private String OrganIds;
    /**
     * 价格数组
     */
    private String TotalPrices;

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

    public AccountHeadShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(AccountHeadShowModel showModel) {
        this.showModel = showModel;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Long getOrganId() {
        return OrganId;
    }

    public void setOrganId(Long organId) {
        OrganId = organId;
    }

    public Long getHandsPersonId() {
        return HandsPersonId;
    }

    public void setHandsPersonId(Long handsPersonId) {
        HandsPersonId = handsPersonId;
    }

    public Double getChangeAmount() {
        return ChangeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        ChangeAmount = changeAmount;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        TotalPrice = totalPrice;
    }

    public Long getAccountId() {
        return AccountId;
    }

    public void setAccountId(Long accountId) {
        AccountId = accountId;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public String getBillTime() {
        return BillTime;
    }

    public void setBillTime(String billTime) {
        BillTime = billTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getBeginTime() {
        return BeginTime;
    }

    public void setBeginTime(String beginTime) {
        BeginTime = beginTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getMonthTime() {
        return MonthTime;
    }

    public void setMonthTime(String monthTime) {
        MonthTime = monthTime;
    }

    public Long getAccountHeadID() {
        return accountHeadID;
    }

    public void setAccountHeadID(Long accountHeadID) {
        this.accountHeadID = accountHeadID;
    }

    public String getAccountHeadIDs() {
        return accountHeadIDs;
    }

    public void setAccountHeadIDs(String accountHeadIDs) {
        this.accountHeadIDs = accountHeadIDs;
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupType() {
        return supType;
    }

    public void setSupType(String supType) {
        this.supType = supType;
    }

    /**
     * Getter method for property <tt>OrganIds</tt>.
     *
     * @return property value of OrganIds
     */
    public String getOrganIds() {
        return OrganIds;
    }

    /**
     * Setter method for property <tt>OrganIds</tt>.
     *
     * @param OrganIds value to be assigned to property OrganIds
     */
    public void setOrganIds(String organIds) {
        OrganIds = organIds;
    }

    /**
     * Getter method for property <tt>TotalPrices</tt>.
     *
     * @return property value of TotalPrices
     */
    public String getTotalPrices() {
        return TotalPrices;
    }

    /**
     * Setter method for property <tt>TotalPrices</tt>.
     *
     * @param TotalPrices value to be assigned to property TotalPrices
     */
    public void setTotalPrices(String totalPrices) {
        TotalPrices = totalPrices;
    }

    /**
     * Getter method for property <tt>UserId</tt>.
     *
     * @return property value of UserId
     */
    public Long getUserId() {
        return UserId;
    }

    /**
     * Setter method for property <tt>UserId</tt>.
     *
     * @param UserId value to be assigned to property UserId
     */
    public void setUserId(Long userId) {
        UserId = userId;
    }

    /**
     * Getter method for property <tt>preTotalPrice</tt>.
     *
     * @return property value of preTotalPrice
     */
    public Double getPreTotalPrice() {
        return preTotalPrice;
    }

    /**
     * Setter method for property <tt>preTotalPrice</tt>.
     *
     * @param preTotalPrice value to be assigned to property preTotalPrice
     */
    public void setPreTotalPrice(Double preTotalPrice) {
        this.preTotalPrice = preTotalPrice;
    }

    /**
     * Getter method for property <tt>preAccountId</tt>.
     *
     * @return property value of preAccountId
     */
    public long getPreAccountId() {
        return preAccountId;
    }

    /**
     * Setter method for property <tt>preAccountId</tt>.
     *
     * @param preAccountId value to be assigned to property preAccountId
     */
    public void setPreAccountId(long preAccountId) {
        this.preAccountId = preAccountId;
    }
}
