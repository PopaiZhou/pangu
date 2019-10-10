package com.jsh.dao.basic;

import com.jsh.base.BaseIDAO;
import com.jsh.model.po.Account;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

import java.math.BigDecimal;

public interface AccountIDAO extends BaseIDAO<Account> {

    public void findAccountInOutList(PageUtil<Account> pageUtil, Long accountId) throws JshException;

    public void updateCurrentAmount(Long accountId, BigDecimal totalPrices) throws JshException;

    public void subCurrentAmount(Long accountId,BigDecimal totalPrices);

    public void addCurrentAmount(Long accountId,BigDecimal totalPrices);

}
