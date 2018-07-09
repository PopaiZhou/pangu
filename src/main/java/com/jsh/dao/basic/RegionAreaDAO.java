/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.dao.basic;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Logdetails;
import com.jsh.model.po.RegionArea;

/**
 * @author zhoujp
 * @version $Id RegionAreaDAO.java, v 0.1 2018-05-04 16:18 Lenovo Exp $$
 */
public class RegionAreaDAO extends BaseDAO<RegionArea> implements RegionAreaIDAO{
    /**
     * 设置dao映射基类
     *
     * @return
     */
    @Override
    public Class<RegionArea> getEntityClass() {
        return RegionArea.class;
    }
}