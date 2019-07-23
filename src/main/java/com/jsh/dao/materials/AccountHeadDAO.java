package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.AccountHead;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import com.jsh.util.SearchConditionUtil;
import org.hibernate.Query;

/**
 * @author alan
 */
public class AccountHeadDAO extends BaseDAO<AccountHead> implements AccountHeadIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<AccountHead> getEntityClass() {
        return AccountHead.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void find(PageUtil<AccountHead> pageUtil, String maxid) throws JshException {
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("select max(Id) as Id from AccountHead accountHead where 1=1 " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void findAllMoney(PageUtil<AccountHead> pageUtil, Integer supplierId, String type, String mode) throws JshException {
        Query query;
        String modeName = "";
        if (mode.equals("实际")) {
            modeName = "ChangeAmount";
        } else if (mode.equals("合计")) {
            modeName = "TotalPrice";
        }
        query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("select sum(" + modeName + ") as allMoney from AccountHead accountHead where Type='" + type + "' and OrganId =" + supplierId + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void batchDeleteByBillNos(String billNos) throws JshException {
        String sql = "delete from  jsh_accounthead  where BillNo in (" + billNos + ")";
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void findCustomerStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        StringBuilder queryString = new StringBuilder();
        queryString.append("select a.BillTime,b.`Name`,a.TotalPrice,a.Remark ");
        queryString.append("from jsh_accounthead a LEFT JOIN jsh_account b on a.AccountId = b.Id ");
        queryString.append("WHERE a.OrganId = '").append(organId).append("' and a.BillTime >='").append(beginTime).append("' and a.BillTime<='").append(endTime).append("' ");
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }

    @Override
    public void findInoutItemListByHeaderId(PageUtil pageUtil, Long headerId) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select group_concat(concat(jsh_inoutitem.`Name`)) as mName from jsh_accountitem inner join jsh_inoutitem " +
                " on jsh_accountitem.InOutItemId = jsh_inoutitem.Id where jsh_accountitem.HeaderId =" + headerId);
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }
}
