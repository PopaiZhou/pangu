/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Customer;

/**
 * @author zhoujp
 * @version $Id CustomerDAO.java, v 0.1 2018-07-15 12:17 Lenovo Exp $$
 */
public class CustomerDAO extends BaseDAO<Customer> implements CustomerIDAO{

    @Override
    public Class<Customer> getEntityClass() {
        return Customer.class;
    }
}