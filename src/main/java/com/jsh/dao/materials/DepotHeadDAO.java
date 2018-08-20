package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.DepotHead;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import com.jsh.util.SearchConditionUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

public class DepotHeadDAO extends BaseDAO<DepotHead> implements DepotHeadIDAO {
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<DepotHead> getEntityClass() {
        return DepotHead.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void find(PageUtil<DepotHead> pageUtil, String maxid) throws JshException {
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("select max(Id) as Id from DepotHead depotHead where 1=1 " + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void findAllMoney(PageUtil<DepotHead> pageUtil, Integer supplierId, String type, String subType, String mode) throws JshException {
        Query query;
        String modeName = "";
        if (mode.equals("实际")) {
            modeName = "ChangeAmount";
        } else if (mode.equals("合计")) {
            modeName = "DiscountLastMoney";
        }
        query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery("select sum(" + modeName + ") as allMoney from DepotHead depotHead where Type='" + type + "' and SubType = '" + subType + "' and OrganId =" + supplierId + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        pageUtil.setPageList(query.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void batchSetStatus(Boolean status, String depotHeadIDs) {
        String sql = "update jsh_depothead d set d.Status=" + status + " where d.id in (" + depotHeadIDs + ")";
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
    }

    @Override
    public void batchSetCheck(Boolean status, String depotHeadIDs, String checkOperName) {
        String sql = "update jsh_depothead d set d.CheckStatus=" + status + ",d.CheckOperName='"+checkOperName+"' where d.id in (" + depotHeadIDs + ")";
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql);
        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findInDetail(PageUtil pageUtil, String beginTime, String endTime, String type, Long pid, String dids, Long oId) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select dh.Number,m.`name`,m.Model,di.UnitPrice,di.OperNumber,di.AllPrice,s.supplier,d.dName," +
                "date_format(dh.OperTime, '%Y-%m-%d'), concat(dh.SubType,dh.Type) as newType " +
                "from jsh_depothead dh inner join jsh_depotitem di on di.HeaderId=dh.id " +
                "inner join jsh_material m on m.id=di.MaterialId " +
                "inner join jsh_supplier s on s.id=dh.OrganId " +
                "inner join (select id,name as dName from jsh_depot) d on d.id=di.DepotId " +
                "where dh.OperTime >='" + beginTime + "' and dh.OperTime <='" + endTime + "' ");
        if (oId != null) {
            queryString.append(" and dh.OrganId = " + oId);
        }
        if (pid != null) {
            queryString.append(" and di.DepotId=" + pid);
        } else {
            queryString.append(" and di.DepotId in (" + dids + ")");
        }
        if (type != null && !type.equals("")) {
            queryString.append(" and dh.Type='" + type + "'");
        }
        queryString.append(" ORDER BY OperTime DESC,Number desc");
        Query query;
        query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        // 分页查询
        int pageNo = pageUtil.getCurPage();
        int pageSize = pageUtil.getPageSize();
        if (0 != pageNo && 0 != pageSize) {
            query.setFirstResult((pageNo - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        pageUtil.setPageList(query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findInOutMaterialCount(PageUtil pageUtil, String beginTime, String endTime, String type, Long pid, String dids, Long oId) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select di.MaterialId, m.mName,m.Model,m.categoryName, ");
        //数量汇总
        queryString.append(" (select sum(jdi.BasicNumber) numSum from jsh_depothead jdh INNER JOIN jsh_depotitem jdi " +
                "on jdh.id=jdi.HeaderId where jdi.MaterialId=di.MaterialId " +
                " and jdh.type='" + type + "' and jdh.OperTime >='" + beginTime + "' and jdh.OperTime <='" + endTime + "'");
        if (oId != null) {
            queryString.append(" and jdh.OrganId = " + oId);
        }
        if (pid != null) {
            queryString.append(" and jdi.DepotId=" + pid);
        } else {
            queryString.append(" and jdi.DepotId in (" + dids + ")");
        }
        queryString.append(" ) numSum, ");
        //金额汇总
        queryString.append(" (select sum(jdi.AllPrice) priceSum from jsh_depothead jdh INNER JOIN jsh_depotitem jdi " +
                "on jdh.id=jdi.HeaderId where jdi.MaterialId=di.MaterialId " +
                " and jdh.type='" + type + "' and jdh.OperTime >='" + beginTime + "' and jdh.OperTime <='" + endTime + "'");
        if (oId != null) {
            queryString.append(" and jdh.OrganId = " + oId);
        }
        if (pid != null) {
            queryString.append(" and jdi.DepotId=" + pid);
        } else {
            queryString.append(" and jdi.DepotId in (" + dids + ")");
        }
        queryString.append(" ) priceSum ");

        queryString.append(" from jsh_depothead dh INNER JOIN jsh_depotitem di on dh.id=di.HeaderId " +
                " INNER JOIN (SELECT jsh_material.id,jsh_material.name mName, Model,jsh_materialcategory.`Name` categoryName from jsh_material INNER JOIN jsh_materialcategory on jsh_material.CategoryId=jsh_materialcategory.Id) m " +
                " on m.Id=di.MaterialId where dh.type='" + type + "' and dh.OperTime >='" + beginTime + "' and dh.OperTime <='" + endTime + "' ");
        if (oId != null) {
            queryString.append(" and dh.OrganId = " + oId);
        }
        if (pid != null) {
            queryString.append(" and di.DepotId=" + pid);
        } else {
            queryString.append(" and di.DepotId in (" + dids + ")");
        }
        queryString.append(" GROUP BY di.MaterialId,m.mName,m.Model,m.categoryName ");
        Query query;
        query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        // 分页查询
        int pageNo = pageUtil.getCurPage();
        int pageSize = pageUtil.getPageSize();
        if (0 != pageNo && 0 != pageSize) {
            query.setFirstResult((pageNo - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        pageUtil.setPageList(query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findMaterialsListByHeaderId(PageUtil pageUtil, Long headerId) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select group_concat(concat(jsh_material.`Name`,' ',jsh_material.Model)) as mName from jsh_depotitem inner join jsh_material " +
                " on jsh_depotitem.MaterialId = jsh_material.Id where jsh_depotitem.HeaderId =" + headerId);
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findProductListByHeaderId(PageUtil pageUtil, Long headerId) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select group_concat(concat(jsh_product.`productId`,' ',jsh_product.productName)) as mName from jsh_depotitem inner join jsh_product " +
                " on jsh_depotitem.MaterialId = jsh_product.Id where jsh_depotitem.HeaderId =" + headerId);
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId, String supType) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select dh.Number,concat(dh.SubType,dh.Type) as newType,dh.DiscountLastMoney,dh.ChangeAmount,s.supplier,date_format(dh.OperTime,'%Y-%m-%d %H:%i:%S') as oTime from jsh_depothead dh " +
                "inner join jsh_supplier s on s.id=dh.OrganId where s.type='" + supType + "' and dh.SubType!='其它' " +
                "and dh.OperTime >='" + beginTime + "' and dh.OperTime<='" + endTime + "' ");
        if (organId != null && !organId.equals("")) {
            queryString.append(" and dh.OrganId='" + organId + "' ");
        }
        queryString.append("UNION ALL " +
                "select ah.BillNo,ah.Type as newType,ah.TotalPrice,ah.ChangeAmount,s.supplier,date_format(ah.BillTime,'%Y-%m-%d %H:%i:%S') as oTime from jsh_accounthead ah " +
                "inner join jsh_supplier s on s.id=ah.OrganId where s.type='" + supType + "' " +
                "and ah.BillTime >='" + beginTime + "' and ah.BillTime<='" + endTime + "' ");
        if (organId != null && !organId.equals("")) {
            queryString.append(" and ah.OrganId='" + organId + "' ");
        }
        queryString.append(" ORDER BY oTime");
        Query query;
        query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setTotalCount(query.list().size());
        // 分页查询
        int pageNo = pageUtil.getCurPage();
        int pageSize = pageUtil.getPageSize();
        if (0 != pageNo && 0 != pageSize) {
            query.setFirstResult((pageNo - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        pageUtil.setPageList(query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findCustomerStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT dh.Number,'出货' AS newType,s.customerName,date_format(dh.OperTime,'%Y-%m-%d') AS oTime,c.productName,");
        queryString.append("d.templateName,b.MUnit,b.OperNumber,b.UnitPrice,b.AllPrice FROM jsh_depothead dh");
        queryString.append(" inner join jsh_customer s on s.id=dh.OrganId ");
        queryString.append(" LEFT JOIN jsh_depotitem b on b.HeaderId = dh.Id ");
        queryString.append(" LEFT JOIN jsh_product c on b.MaterialId = c.id ");
        queryString.append(" LEFT JOIN jsh_template d on d.id = b.TemplateId ");
        //我只查已发货的订单 SendStatus=1
        queryString.append(" WHERE dh.OrganId='").append(organId).append("' and dh.OperTime >='").append(beginTime).append("' and dh.OperTime<='").append(endTime).append("' and dh.SendStatus=1");
        queryString.append(" order by DefaultNumber ");
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findSalesManStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT dh.Number,'出货' AS newType,s.customerName,date_format(dh.OperTime,'%Y-%m-%d') AS oTime,c.productName,");
        queryString.append("d.templateName,b.MUnit,b.OperNumber,b.UnitPrice,b.AllPrice FROM jsh_depothead dh");
        queryString.append(" inner join jsh_customer s on s.id=dh.OrganId ");
        queryString.append(" LEFT JOIN jsh_depotitem b on b.HeaderId = dh.Id ");
        queryString.append(" LEFT JOIN jsh_product c on b.MaterialId = c.id ");
        queryString.append(" LEFT JOIN jsh_template d on d.id = b.TemplateId ");
        //我只查已发货的订单 SendStatus=1
        queryString.append(" WHERE dh.Salesman='").append(organId).append("' and dh.OperTime >='").append(beginTime).append("' and dh.OperTime<='").append(endTime).append("' and dh.SendStatus=1");
        queryString.append(" order by DefaultNumber ");
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }

    /**
     * 供应商对账单--订单明细
     * @param pageUtil
     * @param beginTime
     * @param endTime
     * @param organId
     * @throws JshException
     */
    @Override
    @SuppressWarnings("unchecked")
    public void findSupplierStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT dh.Number,date_format(dh.OperTime, '%Y-%m-%d') AS oTime,c.productName,d.templateName,a.MUnit,a.OperNumber,a.TaxUnitPrice FROM jsh_depotitem a ");
        queryString.append("LEFT JOIN jsh_depothead dh on a.HeaderId = dh.Id ");
        queryString.append("LEFT JOIN jsh_product c ON a.MaterialId = c.id ");
        queryString.append("LEFT JOIN jsh_template d ON d.id = a.TemplateId ");
        //我只查已发货的订单 SendStatus=1
        queryString.append(" WHERE a.DepotId='").append(organId).append("' and dh.OperTime >='").append(beginTime).append("' and dh.OperTime<='").append(endTime).append("' and dh.SendStatus=1");
        queryString.append(" order by DefaultNumber ");
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findCustomerStatementTemplate(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT c.templateName, sum(a.OperNumber) as OperNumber, sum(a.AllPrice) as AllPrice ");
        queryString.append("FROM jsh_depotitem a LEFT JOIN jsh_depothead b ON a.HeaderId = b.id LEFT JOIN jsh_template c on a.TemplateId = c.id ");
        queryString.append("WHERE b.OrganId = '").append(organId).append("' and b.OperTime >='").append(beginTime).append("' and b.OperTime<='").append(endTime).append("' and b.SendStatus='1'").append(" GROUP BY templateName");

        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findSalesManStatementTemplate(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT c.templateName, sum(a.OperNumber) as OperNumber, sum(a.AllPrice) as AllPrice ");
        queryString.append("FROM jsh_depotitem a LEFT JOIN jsh_depothead b ON a.HeaderId = b.id LEFT JOIN jsh_template c on a.TemplateId = c.id ");
        queryString.append("WHERE b.Salesman = '").append(organId).append("' and b.OperTime >='").append(beginTime).append("' and b.OperTime<='").append(endTime).append("' and b.SendStatus='1'").append(" GROUP BY templateName");

        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void findSupplierStatementTemplate(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        StringBuilder queryString = new StringBuilder();
        queryString.append("SELECT c.templateName,sum(a.OperNumber) AS OperNumber,sum(a.TaxUnitPrice*a.OperNumber) AS AllPrice FROM jsh_depotitem a ");
        queryString.append("LEFT JOIN jsh_depothead b ON a.HeaderId = b.id ");
        queryString.append("LEFT JOIN jsh_template c ON a.TemplateId = c.id ");
        queryString.append("WHERE a.DepotId = '").append(organId).append("' and b.OperTime >='").append(beginTime).append("' and b.OperTime<='").append(endTime).append("' and b.SendStatus='1'").append(" GROUP BY templateName");
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getHeaderIdByMaterial(PageUtil pageUtil, String materialParam, String depotIds) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select dt.HeaderId from jsh_depotitem dt INNER JOIN jsh_material m on dt.MaterialId = m.Id where ( m.`Name` " +
                " like '%" + materialParam + "%' or m.Model like '%" + materialParam + "%') ");
        if (!depotIds.equals("")) {
            queryString.append(" and dt.DepotId in (" + depotIds + ") ");
        }
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getHeaderIdByMaterial(PageUtil pageUtil, String materialParam) throws JshException {
        StringBuffer queryString = new StringBuffer();
        queryString.append("select dt.HeaderId from jsh_depotitem dt INNER JOIN jsh_product m on dt.MaterialId = m.Id where ( m.`productName` " +
                " like '%" + materialParam + "%' or m.productId like '%" + materialParam + "%') ");
        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }

    /**
     * 客户活跃度统计
     * @param pageUtil
     * @param beginTime
     * @param endTime
     * @param organId
     * @param sort
     * @throws JshException
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sumCustomerActivity(PageUtil pageUtil, String beginTime, String endTime, Long organId, String sort) throws JshException {
        StringBuilder queryString = new StringBuilder();
        queryString.append("select count(*) as num,a.OrganId,b.customerNo,b.customerName,b.phonenum,b.type,b.state,b.city,b.street,b.address from jsh_depothead a ");
        queryString.append("LEFT JOIN jsh_customer b on a.OrganId = b.id ");
        queryString.append("WHERE a.OperTime >='").append(beginTime).append("' and a.OperTime<='").append(endTime).append("'");
        if(organId != null){
            queryString.append(" and a.OrganId = '").append(organId).append("' ");
        }
        queryString.append(" GROUP BY OrganId ");

        if(StringUtils.isNotEmpty(sort)){
            queryString.append(" ORDER BY num ").append(sort);
        }

        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(queryString + SearchConditionUtil.getCondition(pageUtil.getAdvSearch()));
        pageUtil.setPageList(query.list());
    }
}
