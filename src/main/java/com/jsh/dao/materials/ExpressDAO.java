package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Express;
import org.hibernate.Query;

public class ExpressDAO extends BaseDAO<Express> implements ExpressIDAO {
    @Override
    public void batchSetEnable(Boolean enable, String batchDeleteIds) {
        String sql = "update jsh_express s set s.enabled=" + enable + " where s.id in (" + batchDeleteIds + ")";
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
    }
}
