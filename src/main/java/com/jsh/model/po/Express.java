package com.jsh.model.po;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "jsh_express", schema = "jsh_erp", catalog = "")
public class Express {
    private long id;
    private String expressName;
    private String expressCode;
    private Integer sortOrder;
    private Boolean enabled;
    private Timestamp created;
    private Timestamp updated;

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
     * Getter method for property <tt>expressName</tt>.
     *
     * @return property value of expressName
     */
    @Basic
    @Column(name = "express_name", nullable = true, length = 128)
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
    @Basic
    @Column(name = "express_code", nullable = true, length = 64)
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
    @Basic
    @Column(name = "sort_order", nullable = true)
    public Integer getSortOrder() {
        return sortOrder;
    }

    /**
     * Setter method for property <tt>sortOrder</tt>.
     *
     * @param sortOrder value to be assigned to property sortOrder
     */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
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
     * Getter method for property <tt>created</tt>.
     *
     * @return property value of created
     */
    @Basic
    @Column(name = "created", nullable = true)
    public Timestamp getCreated() {
        return created;
    }

    /**
     * Setter method for property <tt>created</tt>.
     *
     * @param created value to be assigned to property created
     */
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    /**
     * Getter method for property <tt>updated</tt>.
     *
     * @return property value of updated
     */
    @Basic
    @Column(name = "updated", nullable = true)
    public Timestamp getUpdated() {
        return updated;
    }

    /**
     * Setter method for property <tt>updated</tt>.
     *
     * @param updated value to be assigned to property updated
     */
    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Express that = (Express) o;
        return id == that.id &&
                Objects.equals(expressName, that.expressName) &&
                Objects.equals(expressCode, that.expressCode) &&
                Objects.equals(sortOrder, that.sortOrder) &&
                Objects.equals(enabled, that.enabled) &&
                Objects.equals(created, that.created) &&
                Objects.equals(updated, that.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expressName, expressCode, sortOrder, enabled, created, updated);
    }
}
