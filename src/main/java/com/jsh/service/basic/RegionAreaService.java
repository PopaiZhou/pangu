/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.service.basic;

import com.jsh.base.BaseService;
import com.jsh.dao.basic.RegionAreaIDAO;
import com.jsh.model.po.RegionArea;
import com.jsh.util.JshException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujp
 * @version $Id RegionAreaService.java, v 0.1 2018-05-04 16:22 Lenovo Exp $$
 */
public class RegionAreaService extends BaseService<RegionArea> implements RegionAreaIService{

    private RegionAreaIDAO regionAreaDao;


    @Override
    protected Class<RegionArea> getEntityClass() {
        return RegionArea.class;
    }

    /**
     * Setter method for property <tt>regionAreaDao</tt>.
     *
     * @param regionAreaDao value to be assigned to property regionAreaDao
     */
    public void setRegionAreaDao(RegionAreaIDAO regionAreaDao) {
        this.regionAreaDao = regionAreaDao;
    }

    /**
     * 查询所有省份
     * conditon 写法
     *          字段_类型_操作
     *          loginame_s_eq  表示 loginame String 类型 == XX值
     * @return
     * @throws JshException
     */
    @Override
    public List<RegionArea> getAllState() throws JshException {
        Map<String,Object> condition = new HashMap<>(0);
        condition.put("parentId_s_eq",0);
        return regionAreaDao.find(condition);
    }

    @Override
    public List<RegionArea> getCity(String pid) throws JshException {
        Map<String,Object> condition = new HashMap<>(0);
        condition.put("parentId_s_eq",pid);
        return regionAreaDao.find(condition);
    }

    @Override
    public List<RegionArea> getStreet(String pid) throws JshException {
        Map<String,Object> condition = new HashMap<>(0);
        condition.put("parentId_s_eq",pid);
        return regionAreaDao.find(condition);
    }
}