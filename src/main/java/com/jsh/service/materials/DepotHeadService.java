package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.DepotHeadIDAO;
import com.jsh.model.po.DepotHead;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

public class DepotHeadService extends BaseService<DepotHead> implements DepotHeadIService {
    @SuppressWarnings("unused")
    private DepotHeadIDAO depotHeadDao;


    public void setDepotHeadDao(DepotHeadIDAO depotHeadDao) {
        this.depotHeadDao = depotHeadDao;
    }


    @Override
    protected Class<DepotHead> getEntityClass() {
        return DepotHead.class;
    }

    @Override
    public void find(PageUtil<DepotHead> pageUtil, String maxid) throws JshException {
        depotHeadDao.find(pageUtil, maxid);
    }

    @Override
    public void findAllMoney(PageUtil<DepotHead> pageUtil, Integer supplierId, String type, String subType, String mode) throws JshException {
        depotHeadDao.findAllMoney(pageUtil, supplierId, type, subType, mode);
    }

    @Override
    public void batchSetStatus(Boolean status, String depotHeadIDs) {
        depotHeadDao.batchSetStatus(status, depotHeadIDs);
    }

    @Override
    public void batchSetCheck(Boolean status, String depotHeadIDs,String checkOperName) {
        depotHeadDao.batchSetCheck(status, depotHeadIDs,checkOperName);
    }

    @Override
    public void findInDetail(PageUtil pageUtil, String beginTime, String endTime, String type, Long pid, String dids, Long oId) throws JshException {
        depotHeadDao.findInDetail(pageUtil, beginTime, endTime, type, pid, dids, oId);
    }

    @Override
    public void findInOutMaterialCount(PageUtil pageUtil, String beginTime, String endTime, String type, Long pid, String dids, Long oId) throws JshException {
        depotHeadDao.findInOutMaterialCount(pageUtil, beginTime, endTime, type, pid, dids, oId);
    }

    @Override
    public void findMaterialsListByHeaderId(PageUtil pageUtil, Long headerId) throws JshException {
        depotHeadDao.findMaterialsListByHeaderId(pageUtil, headerId);
    }

    @Override
    public void findProductListByHeaderId(PageUtil pageUtil, Long headerId) throws JshException {
        depotHeadDao.findProductListByHeaderId(pageUtil, headerId);
    }

    @Override
    public void findStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId, String supType) throws JshException {
        depotHeadDao.findStatementAccount(pageUtil, beginTime, endTime, organId, supType);
    }

    /**
     * 客户对账单查询
     * @param pageUtil
     * @param beginTime
     * @param endTime
     * @param organId
     * @throws JshException
     */
    @Override
    public void findCustomerStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        depotHeadDao.findCustomerStatementAccount(pageUtil, beginTime, endTime, organId);
    }

    @Override
    public void findSalesManStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        depotHeadDao.findSalesManStatementAccount(pageUtil, beginTime, endTime, organId);
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
    public void findSupplierStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        depotHeadDao.findSupplierStatementAccount(pageUtil, beginTime, endTime, organId);
    }

    @Override
    public void findCustomerStatementTemplate(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        depotHeadDao.findCustomerStatementTemplate(pageUtil, beginTime, endTime, organId);
    }

    @Override
    public void findSalesManStatementTemplate(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        depotHeadDao.findSalesManStatementTemplate(pageUtil, beginTime, endTime, organId);
    }

    @Override
    public void findSupplierStatementTemplate(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        depotHeadDao.findSupplierStatementTemplate(pageUtil, beginTime, endTime, organId);
    }

    @Override
    public void getHeaderIdByMaterial(PageUtil pageUtil, String materialParam, String depotIds) throws JshException {
        depotHeadDao.getHeaderIdByMaterial(pageUtil, materialParam, depotIds);
    }

    @Override
    public void getHeaderIdByMaterial(PageUtil pageUtil, String materialParam) throws JshException {
        depotHeadDao.getHeaderIdByMaterial(pageUtil, materialParam);
    }

    @Override
    public void sumCustomerActivity(PageUtil pageUtil, String beginTime, String endTime, Long organId, String sort) throws JshException {
        depotHeadDao.sumCustomerActivity(pageUtil, beginTime, endTime, organId , sort);
    }
}
