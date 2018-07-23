/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.service.materials;

import com.jsh.base.BaseIService;
import com.jsh.model.po.Customer;
import com.jsh.model.po.Supplier;
import com.jsh.model.po.Template;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;

import java.io.InputStream;

/**
 * @author zhoujp
 * @version $Id CustomerIService.java, v 0.1 2018-07-15 12:14 Lenovo Exp $$
 */
public interface CustomerIService extends BaseIService<Customer> {
    public void batchSetEnable(Boolean enable, String batchDeleteIds);

    /**
     * 导出excel方法
     * @param isAllPage
     * @param pageUtil
     * @return
     * @throws JshException
     */
    public InputStream exmportExcel(String isAllPage, PageUtil<Customer> pageUtil) throws JshException;
}