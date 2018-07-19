/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.ProductIDAO;
import com.jsh.model.po.Product;

/**
 * @author zhoujp
 * @version $Id ProductService.java, v 0.1 2018-07-18 21:12 Lenovo Exp $$
 */
public class ProductService extends BaseService<Product> implements ProductIService {

    private ProductIDAO productDao;

    @Override
    protected Class<Product> getEntityClass() {
        return Product.class;
    }

    /**
     * Setter method for property <tt>productDao</tt>.
     *
     * @param productDao value to be assigned to property productDao
     */
    public void setProductDao(ProductIDAO productDao) {
        this.productDao = productDao;
    }
}