/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Product;

/**
 * @author zhoujp
 * @version $Id ProductDAO.java, v 0.1 2018-07-18 21:05 Lenovo Exp $$
 */
public class ProductDAO extends BaseDAO<Product> implements ProductIDAO {
    @Override
    public Class<Product> getEntityClass() {
        return Product.class;
    }
}