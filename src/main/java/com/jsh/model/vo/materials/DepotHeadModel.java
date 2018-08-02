package com.jsh.model.vo.materials;

import java.io.Serializable;

/**
 * @author alan
 */
@SuppressWarnings("serial")
public class DepotHeadModel implements Serializable {
    private DepotHeadShowModel showModel = new DepotHeadShowModel();

    /**
     * ======开始接受页面参数=================
     **/
    private String Type = "";
    private String SubType = "";
    private Long ProjectId;
    private String DepotIds = "";
    private String DefaultNumber = "";
    private String Number = "";
    private String OperPersonName = "";
    private String OperTime;
    private Long OrganId;
    private Long HandsPersonId;
    private Long AccountId;
    private Double ChangeAmount;
    private Long AllocationProjectId;
    private Double TotalPrice;
    private String PayType = "";
    private String Remark = "";

    private String Salesman;
    private String AccountIdList;
    private String AccountMoneyList;
    private Double Discount;
    private Double DiscountMoney;
    private Double DiscountLastMoney;
    private Double OtherMoney;
    private String OtherMoneyList;
    private String OtherMoneyItem;
    private Integer AccountDay;
    //单据状态
    private Boolean Status = false;
    //查询开始时间
    private String BeginTime;
    //查询结束时间
    private String EndTime;
    //查询月份
    private String MonthTime;
    //单位Id，用于查询单位的应收应付
    private String supplierId;
    //商品参数
    private String MaterialParam;
    //单据id列表
    private String dhIds;
    //单位类型，客户、供应商
    private String supType;

    private String searchStatus;

    private Boolean SendStatus = false; //发货状态
    private Double Weight;
    private Double Freight;
    private String Express;
    private String ExpressNumber;
    private String Contacts;
    private String Phonenum;
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
    private String address;


    /**
     * 分类ID
     */
    private Long depotHeadID = 0L;

    /**
     * 分类IDs 批量操作使用
     */
    private String depotHeadIDs = "";

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

    public DepotHeadShowModel getShowModel() {
        return showModel;
    }

    public void setShowModel(DepotHeadShowModel showModel) {
        this.showModel = showModel;
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

    public Long getProjectId() {
        return ProjectId;
    }

    public void setProjectId(Long projectId) {
        ProjectId = projectId;
    }

    public String getDepotIds() {
        return DepotIds;
    }

    public void setDepotIds(String depotIds) {
        DepotIds = depotIds;
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

    public Long getAccountId() {
        return AccountId;
    }

    public void setAccountId(Long accountId) {
        AccountId = accountId;
    }

    public Double getChangeAmount() {
        return ChangeAmount;
    }

    public void setChangeAmount(Double changeAmount) {
        ChangeAmount = changeAmount;
    }

    public Long getAllocationProjectId() {
        return AllocationProjectId;
    }

    public void setAllocationProjectId(Long allocationProjectId) {
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

    public Long getDepotHeadID() {
        return depotHeadID;
    }

    public void setDepotHeadID(Long depotHeadID) {
        this.depotHeadID = depotHeadID;
    }

    public String getDepotHeadIDs() {
        return depotHeadIDs;
    }

    public void setDepotHeadIDs(String depotHeadIDs) {
        this.depotHeadIDs = depotHeadIDs;
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

    public String getOperTime() {
        return OperTime;
    }

    public void setOperTime(String operTime) {
        OperTime = operTime;
    }

    public String getMonthTime() {
        return MonthTime;
    }

    public void setMonthTime(String monthTime) {
        MonthTime = monthTime;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
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

    public String getMaterialParam() {
        return MaterialParam;
    }

    public void setMaterialParam(String materialParam) {
        MaterialParam = materialParam;
    }

    public String getDhIds() {
        return dhIds;
    }

    public void setDhIds(String dhIds) {
        this.dhIds = dhIds;
    }

    public String getSupType() {
        return supType;
    }

    public void setSupType(String supType) {
        this.supType = supType;
    }

    /**
     * Getter method for property <tt>searchStatus</tt>.
     *
     * @return property value of searchStatus
     */
    public String getSearchStatus() {
        return searchStatus;
    }

    /**
     * Setter method for property <tt>searchStatus</tt>.
     *
     * @param searchStatus value to be assigned to property searchStatus
     */
    public void setSearchStatus(String searchStatus) {
        this.searchStatus = searchStatus;
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
     * @param SendStatus value to be assigned to property SendStatus
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
     * @param Weight value to be assigned to property Weight
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
     * @param Freight value to be assigned to property Freight
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
     * @param Express value to be assigned to property Express
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
     * @param ExpressNumber value to be assigned to property ExpressNumber
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
     * @param Contacts value to be assigned to property Contacts
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
     * @param Phonenum value to be assigned to property Phonenum
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
}
