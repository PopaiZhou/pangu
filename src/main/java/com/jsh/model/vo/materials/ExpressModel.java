package com.jsh.model.vo.materials;

import java.io.Serializable;

public class ExpressModel implements Serializable {
    private static final long serialVersionUID = -3610181717571729525L;

    private ExpressShowModel showModel = new ExpressShowModel();

    private long id;
    private String expressName;
    private String expressCode;
    private int sortOrder;
    private Boolean enabled = true;
    //快递单号
    private String ExpressNumber;

    private String batchDeleteIds;

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
     * Getter method for property <tt>expressName</tt>.
     *
     * @return property value of expressName
     */
    public String getExpressName() {
        return expressName;
    }

    /**
     * Setter method for property <tt>expressName</tt>.
     *
     * @param expressName value to be assigned to property expressName
     */
    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    /**
     * Getter method for property <tt>expressCode</tt>.
     *
     * @return property value of expressCode
     */
    public String getExpressCode() {
        return expressCode;
    }

    /**
     * Setter method for property <tt>expressCode</tt>.
     *
     * @param expressCode value to be assigned to property expressCode
     */
    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    /**
     * Getter method for property <tt>sortOrder</tt>.
     *
     * @return property value of sortOrder
     */
    public int getSortOrder() {
        return sortOrder;
    }

    /**
     * Setter method for property <tt>sortOrder</tt>.
     *
     * @param sortOrder value to be assigned to property sortOrder
     */
    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
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
    public ExpressShowModel getShowModel() {
        return showModel;
    }

    /**
     * Setter method for property <tt>showModel</tt>.
     *
     * @param showModel value to be assigned to property showModel
     */
    public void setShowModel(ExpressShowModel showModel) {
        this.showModel = showModel;
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
}
