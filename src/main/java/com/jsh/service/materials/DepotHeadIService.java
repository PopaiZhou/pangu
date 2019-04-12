package com.jsh.service.materials;

import com.jsh.base.BaseIService;
import com.jsh.model.po.DepotHead;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

public interface DepotHeadIService extends BaseIService<DepotHead> {
    /*
     * 获取MaxId
     */
    void find(PageUtil<DepotHead> depotHead, String maxid) throws JshException;

    void findAllMoney(PageUtil<DepotHead> depotHead, Integer supplierId, String type, String subType, String mode) throws JshException;

    void batchSetStatus(Boolean status, Long accountId,String depotHeadIDs);

    void batchSetCheck(Boolean status, String depotHeadIDs,String checkOperName);

    void findInDetail(PageUtil pageUtil, String beginTime, String endTime, String type, Long pid, String dids, Long oId) throws JshException;

    void findInOutMaterialCount(PageUtil pageUtil, String beginTime, String endTime, String type, Long pid, String dids, Long oId) throws JshException;

    void findMaterialsListByHeaderId(PageUtil pageUtil, Long headerId) throws JshException;

    void findProductListByHeaderId(PageUtil pageUtil, Long headerId) throws JshException;

    void findStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId, String supType) throws JshException;

    /**
     * 地区统计
     * @param pageUtil
     * @param beginTime
     * @param endTime
     * @param state
     * @throws JshException
     */
    void findAreaReport(PageUtil pageUtil, String beginTime, String endTime, String state) throws JshException;

    void findAreaReportTemplate(PageUtil pageUtil, String beginTime, String endTime, String state) throws JshException;

    void findCustomerStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException;

    void findSalesManStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException;
    /**
     * 供应商对账单--订单明细
     * @param pageUtil
     * @param beginTime
     * @param endTime
     * @param organId
     * @throws JshException
     */
    void findSupplierStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException;

    void findCustomerStatementTemplate(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException;

    void findSalesManStatementTemplate(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException;

    void findSupplierStatementTemplate(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException;

    void getHeaderIdByMaterial(PageUtil pageUtil, String materialParam, String depotIds) throws JshException;

    void getHeaderIdByMaterial(PageUtil pageUtil, String materialParam) throws JshException;

    void sumCustomerActivity(PageUtil pageUtil, String beginTime, String endTime, Long organId,String sort) throws JshException;
}
