package com.jsh.model.po;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public class AccountHead implements java.io.Serializable {
    private Long Id;
    private String Type;
    private Customer OrganId;
    private Supplier SupplierId;
    private Basicuser UserId;
    private Basicuser HandsPersonId;
    private Double ChangeAmount;
    private Double TotalPrice;
    private Account AccountId;
    private String BillNo;
    private Timestamp BillTime;
    private String Remark;

    public AccountHead() {

    }

    public AccountHead(Long Id) {
        this.Id = Id;
    }

    public AccountHead(String type, Customer organId,
                       Basicuser handsPersonId, Double changeAmount, Double totalPrice,
                       Account accountId, String billNo, Timestamp billTime, String remark) {
        super();
        Type = type;
        OrganId = organId;
        HandsPersonId = handsPersonId;
        ChangeAmount = changeAmount;
        TotalPrice = totalPrice;
        AccountId = accountId;
        BillNo = billNo;
        BillTime = billTime;
        Remark = remark;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Customer getOrganId() {
        return OrganId;
    }

    public void setOrganId(Customer organId) {
        OrganId = organId;
    }

    public Basicuser getHandsPersonId() {
        return HandsPersonId;
    }

    public void setHandsPersonId(Basicuser handsPersonId) {
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

    public Account getAccountId() {
        return AccountId;
    }

    public void setAccountId(Account accountId) {
        AccountId = accountId;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public Timestamp getBillTime() {
        return BillTime;
    }

    public void setBillTime(Timestamp billTime) {
        BillTime = billTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    /**
     * Getter method for property <tt>SupplierId</tt>.
     *
     * @return property value of SupplierId
     */
    public Supplier getSupplierId() {
        return SupplierId;
    }

    /**
     * Setter method for property <tt>SupplierId</tt>.
     *
     * @param SupplierId value to be assigned to property SupplierId
     */
    public void setSupplierId(Supplier supplierId) {
        SupplierId = supplierId;
    }

    /**
     * Getter method for property <tt>UserId</tt>.
     *
     * @return property value of UserId
     */
    public Basicuser getUserId() {
        return UserId;
    }

    /**
     * Setter method for property <tt>UserId</tt>.
     *
     * @param UserId value to be assigned to property UserId
     */
    public void setUserId(Basicuser userId) {
        UserId = userId;
    }
}
