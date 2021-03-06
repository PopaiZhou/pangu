package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.AccountItem;

public class AccountItemDAO extends BaseDAO<AccountItem> implements AccountItemIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<AccountItem> getEntityClass() {
        return AccountItem.class;
    }

    @Override
    public void batchDeleteByHeaderIds(String objIDs) {
        this.getHibernateTemplate().bulkUpdate("delete from " + getEntityClass().getName() + " where HeaderId in (" + objIDs + ")");
    }
}
