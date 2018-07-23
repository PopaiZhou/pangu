/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.CustomerIDAO;
import com.jsh.dao.materials.TemplateIDAO;
import com.jsh.model.po.Customer;
import com.jsh.model.po.Template;

/**
 * @author zhoujp
 * @version $Id CustomerService.java, v 0.1 2018-07-15 12:15 Lenovo Exp $$
 */
public class CustomerService extends BaseService<Customer> implements CustomerIService{

    private CustomerIDAO customerDao;

    @Override
    protected Class<Customer> getEntityClass() {
        return Customer.class;
    }

    /**
     * Setter method for property <tt>customerDao</tt>.
     *
     * @param customerDao value to be assigned to property customerDao
     */
    public void setCustomerDao(CustomerIDAO customerDao) {
        this.customerDao = customerDao;
    }
}