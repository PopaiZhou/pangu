/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.dao.materials;

import com.jsh.base.BaseIDAO;
import com.jsh.model.po.Customer;

/**
 * @author zhoujp
 * @version $Id CustomerIDAO.java, v 0.1 2018-07-15 12:17 Lenovo Exp $$
 */
public interface CustomerIDAO extends BaseIDAO<Customer> {
    public void batchSetEnable(Boolean enable, String batchDeleteIds);

    public void batchTransCustomer(String oldId, String newId, String customerIds);
}