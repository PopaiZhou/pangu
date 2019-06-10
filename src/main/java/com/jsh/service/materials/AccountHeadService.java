package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.base.Log;
import com.jsh.dao.basic.AccountIDAO;
import com.jsh.dao.materials.AccountHeadIDAO;
import com.jsh.dao.materials.AccountItemIDAO;
import com.jsh.model.po.AccountHead;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountHeadService extends BaseService<AccountHead> implements AccountHeadIService {
    @SuppressWarnings("unused")
    private AccountHeadIDAO accountHeadDao;
    private AccountIDAO accountDao;
    private AccountItemIDAO accountItemDao;


    public void setAccountHeadDao(AccountHeadIDAO accountHeadDao) {
        this.accountHeadDao = accountHeadDao;
    }


    @Override
    protected Class<AccountHead> getEntityClass() {
        return AccountHead.class;
    }

    @Override
    public void find(PageUtil<AccountHead> pageUtil, String maxid) throws JshException {
        accountHeadDao.find(pageUtil, maxid);
    }

    @Override
    public void findAllMoney(PageUtil<AccountHead> pageUtil, Integer supplierId, String type, String mode) throws JshException {
        accountHeadDao.findAllMoney(pageUtil, supplierId, type, mode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteByBillNos(String billNos) throws JshException {
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("BillNo_s_in", billNos);
        List<AccountHead> list = accountHeadDao.find(condition);
        Map<Long,Double> sumMap = new HashMap<>();
        for(AccountHead accountHead : list){
            if(sumMap.containsKey(accountHead.getAccountId().getId())){
                Double total = sumMap.get(accountHead.getAccountId().getId());
                total = total + accountHead.getTotalPrice();
                sumMap.put(accountHead.getAccountId().getId(),total);
            }else{
                sumMap.put(accountHead.getAccountId().getId(),accountHead.getTotalPrice());
            }
        }
        for (long key : sumMap.keySet()) {
            Log.infoFileSync("==================AccountHeadService.java batchDeleteByBillNos 退款,减去相关金额key="+key+"|amount="+sumMap.get(key)+"===================");
            //退款,减去相关金额
            accountDao.subCurrentAmount(key,sumMap.get(key));
        }
        accountHeadDao.batchDeleteByBillNos(billNos);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void batchDeleteByIds(String objIDs,String Type) {
        //删除分3步
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("Id_s_in", objIDs);
        List<AccountHead> list = accountHeadDao.find(condition);
        Map<Long,Double> sumMap = new HashMap<>();
        //存一下需要修改的账户金额
        for(AccountHead accountHead : list){
            if(sumMap.containsKey(accountHead.getAccountId().getId())){
                Double total = sumMap.get(accountHead.getAccountId().getId());
                total = total + accountHead.getTotalPrice();
                sumMap.put(accountHead.getAccountId().getId(),total);
            }else{
                sumMap.put(accountHead.getAccountId().getId(),accountHead.getTotalPrice());
            }
        }
        //1 删除jsh_accounthead 财务主表
        accountHeadDao.batchDelete(objIDs);
        //2 删除jsh_accountitem 财务子表
        accountItemDao.batchDeleteByHeaderIds(objIDs);
        //更新余额
        if("收入".equalsIgnoreCase(Type)){
            for (long key : sumMap.keySet()) {
                Log.infoFileSync("==================AccountHeadService.java batchDeleteByIds 收入,加上相关金额key="+key+"|amount="+sumMap.get(key)+"===================");
                accountDao.subCurrentAmount(key,sumMap.get(key));
            }
        }else{
            for (long key : sumMap.keySet()) {
                Log.infoFileSync("==================AccountHeadService.java batchDeleteByIds 支出,减去相关金额key="+key+"|amount="+sumMap.get(key)+"===================");
                accountDao.addCurrentAmount(key,sumMap.get(key));
            }
        }

    }

    @Override
    public void findCustomerStatementAccount(PageUtil pageUtil, String beginTime, String endTime, Long organId) throws JshException {
        accountHeadDao.findCustomerStatementAccount(pageUtil,beginTime,endTime,organId);
    }

    @Override
    public void findInoutItemListByHeaderId(PageUtil pageUtil, Long headerId) throws JshException {
        accountHeadDao.findInoutItemListByHeaderId(pageUtil,headerId);
    }

    /**
     * Setter method for property <tt>accountDao</tt>.
     *
     * @param accountDao value to be assigned to property accountDao
     */
    public void setAccountDao(AccountIDAO accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * Setter method for property <tt>accountItemDao</tt>.
     *
     * @param accountItemDao value to be assigned to property accountItemDao
     */
    public void setAccountItemDao(AccountItemIDAO accountItemDao) {
        this.accountItemDao = accountItemDao;
    }
}
