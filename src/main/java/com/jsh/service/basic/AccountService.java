package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.base.Log;
import com.jsh.dao.basic.AccountIDAO;
import com.jsh.model.po.Account;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

import java.math.BigDecimal;

public class AccountService extends BaseService<Account> implements AccountIService {
    @SuppressWarnings("unused")
    private AccountIDAO accountDao;

    public void setAccountDao(AccountIDAO accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    protected Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    public void findAccountInOutList(PageUtil<Account> pageUtil, Long accountId) throws JshException {
        accountDao.findAccountInOutList(pageUtil, accountId);
    }

    @Override
    public void addCurrentAmount(Long accountId,Double TotalPrices) throws JshException {
        Log.infoFileSync("==================开始调用addCurrentAmount() DAO层===================");
        Account account = accountDao.get(accountId);
        BigDecimal bigOldAmount = new BigDecimal(account.getCurrentAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);
        Log.infoFileSync("==================Account原来金额="+bigOldAmount+"===================");
        BigDecimal bigTotalPrices = new BigDecimal(TotalPrices).setScale(2,BigDecimal.ROUND_HALF_UP);
        Log.infoFileSync("==================Account计算金额="+bigTotalPrices+"===================");
        BigDecimal bigNewAmount = bigOldAmount.add(bigTotalPrices).setScale(2,BigDecimal.ROUND_HALF_UP);
        Log.infoFileSync("==================Account计算结果="+bigNewAmount+"===================");
        accountDao.updateCurrentAmount(accountId,bigNewAmount.doubleValue());
        Log.infoFileSync("==================结束调用addCurrentAmount() DAO层===================");
    }

    @Override
    public void subCurrentAmount(Long accountId, Double TotalPrices) throws JshException {
        Log.infoFileSync("==================开始调用subCurrentAmount() DAO层===================");
        Account account = accountDao.get(accountId);
        BigDecimal bigOldAmount = new BigDecimal(account.getCurrentAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);
        Log.infoFileSync("==================Account原来金额="+bigOldAmount+"===================");
        BigDecimal bigTotalPrices = new BigDecimal(TotalPrices).setScale(2,BigDecimal.ROUND_HALF_UP);
        Log.infoFileSync("==================Account计算金额="+bigTotalPrices+"===================");
        BigDecimal bigNewAmount = bigOldAmount.subtract(bigTotalPrices).setScale(2,BigDecimal.ROUND_HALF_UP);
        Log.infoFileSync("==================Account计算结果="+bigNewAmount+"===================");
        accountDao.updateCurrentAmount(accountId,bigNewAmount.doubleValue());
        Log.infoFileSync("==================结束调用subCurrentAmount() DAO层===================");

    }
}
