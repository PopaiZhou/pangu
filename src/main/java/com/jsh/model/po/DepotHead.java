package com.jsh.model.po;

import java.sql.Timestamp;

@SuppressWarnings("serial")
public class DepotHead implements java.io.Serializable {
    private Long Id;
    private String Type;
    private String SubType;
    private Depot ProjectId;
    private String DefaultNumber;
    private String Number;
    private String OperPersonName;
    private Timestamp CreateTime;
    private Timestamp OperTime;
    private Customer OrganId;
    private Person HandsPersonId;
    private String Salesman;  //业务员（可以多个）[2][3]
    private Account AccountId;
    private Double ChangeAmount;
    private String AccountIdList; //多账户ID列表 [2][3]
    private String AccountMoneyList; //多账户金额列表 [{"[2]",22},{"[3]",33}]
    private Double Discount; //优惠率  0.10
    private Double DiscountMoney; //优惠金额 10
    private Double DiscountLastMoney; //优惠后金额  90
    private Double OtherMoney; //销售或采购费用 100
    private String OtherMoneyList; //销售或采购费用涉及项目Id数组（包括快递、招待等）[2][3]
    private String OtherMoneyItem; //销售费用涉及项目（包括快递、招待等） [{"[2]",22},{"[3]",33}]
    private Integer AccountDay; //结算天数
    private Depot AllocationProjectId;
    private Double TotalPrice;
    private String PayType;
    private Boolean Status = false; //单据状态
    private Boolean CheckStatus =false;//审核状态
    private String CheckOperName;//审核人名字
    private String Remark;
    private Boolean SendStatus = false; //发货状态
    private Double Weight;
    private Double Freight;
    private String Express;
    private String ExpressNumber;
    private String Contacts;
    private String Phonenum;
    private String SendPersonName;
    /**
     * 省
     */
    private String state;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String street;
    private String address = "";

    public DepotHead() {

    }

    public DepotHead(Long Id) {
        this.Id = Id;
    }

    public DepotHead(String type, String subType, Depot projectId, String defaultNumber, String number, String operPersonName, Timestamp createTime,
                     Timestamp operTime, Customer organId, Person handsPersonId, String salesman, String accountIdList, String accountMoneyList,
                     Double discount, Double discountMoney, Double discountLastMoney, Double otherMoney, String otherMoneyItem, Integer accountDay,
                     Account accountId, Double changeAmount, Depot allocationProjectId, Double totalPrice, String payType, Boolean status, String remark) {
        super();
        Type = type;
        SubType = subType;
        ProjectId = projectId;
        DefaultNumber = defaultNumber;
        Number = number;
        OperPersonName = operPersonName;
        CreateTime = createTime;
        OperTime = operTime;
        OrganId = organId;
        HandsPersonId = handsPersonId;
        Salesman = salesman;
        AccountIdList = accountIdList;
        AccountMoneyList = accountMoneyList;
        Discount = discount;
        DiscountMoney = discountMoney;
        DiscountLastMoney = discountLastMoney;
        OtherMoney = otherMoney;
        OtherMoneyItem = otherMoneyItem;
        AccountDay = accountDay;
        AccountId = accountId;
        ChangeAmount = changeAmount;
        AllocationProjectId = allocationProjectId;
        TotalPrice = totalPrice;
        PayType = payType;
        Status = status;
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

    public String getSubType() {
        return SubType;
    }

    public void setSubType(String subType) {
        SubType = subType;
    }

    public Depot getProjectId() {
        return ProjectId;
    }

    public void setProjectId(Depot projectId) {
        ProjectId = projectId;
    }

    public String getDefaultNumber() {
        return DefaultNumber;
    }

    public void setDefaultNumber(String defaultNumber) {
        DefaultNumber = defaultNumber;
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getOperPersonName() {
        return OperPersonName;
    }

    public void setOperPersonName(String operPersonName) {
        OperPersonName = operPersonName;
    }

    public Timestamp getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Timestamp createTime) {
        CreateTime = createTime;
    }

    public Timestamp getOperTime() {
        return OperTime;
    }

    public void setOperTime(Timestamp operTime) {
        OperTime = operTime;
    }

    public Customer getOrganId() {
        return OrganId;
    }

    public void setOrganId(Customer organId) {
        OrganId = organId;
    }

    public Person getHandsPersonId() {
        return HandsPersonId;
    }

    public void setHandsPersonId(Person handsPersonId) {
        HandsPersonId = handsPersonId;
    }

    public Account getAccountId() {
        return AccountId;
    }

    public void setAccountId(Account accountId) {
        AccountId = accountId;
    }

    public Double getChangeAmount() {
        return ChangeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        ChangeAmount = changeAmount;
    }

    public Depot getAllocationProjectId() {
        return AllocationProjectId;
    }

    public void setAllocationProjectId(Depot allocationProjectId) {
        AllocationProjectId = allocationProjectId;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getPayType() {
        return PayType;
    }

    public void setPayType(String payType) {
        PayType = payType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSalesman() {
        return Salesman;
    }

    public void setSalesman(String salesman) {
        Salesman = salesman;
    }

    public String getAccountIdList() {
        return AccountIdList;
    }

    public void setAccountIdList(String accountIdList) {
        AccountIdList = accountIdList;
    }

    public String getAccountMoneyList() {
        return AccountMoneyList;
    }

    public void setAccountMoneyList(String accountMoneyList) {
        AccountMoneyList = accountMoneyList;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double discount) {
        Discount = discount;
    }

    public Double getDiscountMoney() {
        return DiscountMoney;
    }

    public void setDiscountMoney(Double discountMoney) {
        DiscountMoney = discountMoney;
    }

    public Double getDiscountLastMoney() {
        return DiscountLastMoney;
    }

    public void setDiscountLastMoney(Double discountLastMoney) {
        DiscountLastMoney = discountLastMoney;
    }

    public Double getOtherMoney() {
        return OtherMoney;
    }

    public void setOtherMoney(Double otherMoney) {
        OtherMoney = otherMoney;
    }

    public String getOtherMoneyList() {
        return OtherMoneyList;
    }

    public void setOtherMoneyList(String otherMoneyList) {
        OtherMoneyList = otherMoneyList;
    }

    public String getOtherMoneyItem() {
        return OtherMoneyItem;
    }

    public void setOtherMoneyItem(String otherMoneyItem) {
        OtherMoneyItem = otherMoneyItem;
    }

    public Integer getAccountDay() {
        return AccountDay;
    }

    public void setAccountDay(Integer accountDay) {
        AccountDay = accountDay;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    /**
     * Getter method for property <tt>SendStatus</tt>.
     *
     * @return property value of SendStatus
     */
    public Boolean getSendStatus() {
        return SendStatus;
    }

    /**
     * Setter method for property <tt>SendStatus</tt>.
     *
     * @param sendStatus value to be assigned to property SendStatus
     */
    public void setSendStatus(Boolean sendStatus) {
        SendStatus = sendStatus;
    }

    /**
     * Getter method for property <tt>Weight</tt>.
     *
     * @return property value of Weight
     */
    public Double getWeight() {
        return Weight;
    }

    /**
     * Setter method for property <tt>Weight</tt>.
     *
     * @param weight value to be assigned to property Weight
     */
    public void setWeight(Double weight) {
        Weight = weight;
    }

    /**
     * Getter method for property <tt>Freight</tt>.
     *
     * @return property value of Freight
     */
    public Double getFreight() {
        return Freight;
    }

    /**
     * Setter method for property <tt>Freight</tt>.
     *
     * @param freight value to be assigned to property Freight
     */
    public void setFreight(Double freight) {
        Freight = freight;
    }

    /**
     * Getter method for property <tt>Express</tt>.
     *
     * @return property value of Express
     */
    public String getExpress() {
        return Express;
    }

    /**
     * Setter method for property <tt>Express</tt>.
     *
     * @param express value to be assigned to property Express
     */
    public void setExpress(String express) {
        Express = express;
    }

    /**
     * Getter method for property <tt>ExpressNumber</tt>.
     *
     * @return property value of ExpressNumber
     */
    public String getExpressNumber() {
        return ExpressNumber;
    }

    /**
     * Setter method for property <tt>ExpressNumber</tt>.
     *
     * @param expressNumber value to be assigned to property ExpressNumber
     */
    public void setExpressNumber(String expressNumber) {
        ExpressNumber = expressNumber;
    }

    /**
     * Getter method for property <tt>Contacts</tt>.
     *
     * @return property value of Contacts
     */
    public String getContacts() {
        return Contacts;
    }

    /**
     * Setter method for property <tt>Contacts</tt>.
     *
     * @param contacts value to be assigned to property Contacts
     */
    public void setContacts(String contacts) {
        Contacts = contacts;
    }

    /**
     * Getter method for property <tt>Phonenum</tt>.
     *
     * @return property value of Phonenum
     */
    public String getPhonenum() {
        return Phonenum;
    }

    /**
     * Setter method for property <tt>Phonenum</tt>.
     *
     * @param phonenum value to be assigned to property Phonenum
     */
    public void setPhonenum(String phonenum) {
        Phonenum = phonenum;
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
     * Getter method for property <tt>SendPersonName</tt>.
     *
     * @return property value of SendPersonName
     */
    public String getSendPersonName() {
        return SendPersonName;
    }

    /**
     * Setter method for property <tt>SendPersonName</tt>.
     *
     * @param sendPersonName value to be assigned to property SendPersonName
     */
    public void setSendPersonName(String sendPersonName) {
        SendPersonName = sendPersonName;
    }
    /**
     * Getter method for property <tt>CheckOperName</tt>.
     *
     * @return property value of CheckOperName
     */
    public String getCheckOperName() {
        return CheckOperName;
    }

    /**
     * Setter method for property <tt>CheckOperName</tt>.
     *
     * @param checkOperName value to be assigned to property CheckOperName
     */
    public void setCheckOperName(String checkOperName) {
        CheckOperName = checkOperName;
    }

    /**
     * Getter method for property <tt>CheckStatus</tt>.
     *
     * @return property value of CheckStatus
     */
    public Boolean getCheckStatus() {
        return CheckStatus;
    }

    /**
     * Setter method for property <tt>CheckStatus</tt>.
     *
     * @param checkStatus value to be assigned to property CheckStatus
     */
    public void setCheckStatus(Boolean checkStatus) {
        CheckStatus = checkStatus;
    }
}