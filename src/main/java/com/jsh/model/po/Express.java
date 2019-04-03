package com.jsh.model.po;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "jsh_express", schema = "jsh_erp", catalog = "")
public class Express implements Serializable {

    private static final long serialVersionUID = 8547347753009960907L;

    private long id;
    private String expressName;
    private String expressCode;
    private int sortOrder;
    private Boolean enabled = true;

    public Express() {
    }

    public Express(long id) {
        this.id = id;
    }

    public Express(long id, String expressName, String expressCode, int sortOrder, Boolean enabled) {
        this.id = id;
        this.expressName = expressName;
        this.expressCode = expressCode;
        this.sortOrder = sortOrder;
        this.enabled = enabled;
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
}
