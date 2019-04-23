package com.jsh.service.materials;

import com.jsh.base.BaseIService;
import com.jsh.model.po.AccountHead;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

public interface AccountHeadIService extends BaseIService<AccountHead> {
    /*
     * 获取MaxId
     */
    void find(PageUtil<AccountHead> accountHead, String maxid) throws JshException;

    void findAllMoney(PageUtil<AccountHead> accountHead, Integer supplierId, String type, String mode) throws JshException;

    /**
     * 批量删除信息
     * 根据订单编号
     */
    void batchDeleteByBillNos(String billNos) throws JshException;

    void batchDeleteByIds(String objIDs,String type);

    void findCustomerStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException;

    void findInoutItemListByHeaderId(PageUtil pageUtil, Long headerId) throws JshException;
}
