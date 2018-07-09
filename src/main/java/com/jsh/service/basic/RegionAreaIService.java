/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.service.basic;

import com.jsh.base.BaseIService;
import com.jsh.model.po.RegionArea;
import com.jsh.util.JshException;

import java.util.List;

/**
 * @author zhoujp
 * @version $Id RegionAreaIService.java, v 0.1 2018-05-04 16:21 Lenovo Exp $$
 */
public interface RegionAreaIService extends BaseIService<RegionArea> {

    /**
     * 查询所有省份级别的信息
     * @return
     */
    public List<RegionArea> getAllState() throws JshException;

    /**
     * 查询2级市县
     */
    public List<RegionArea> getCity(String pid) throws JshException;

    /**
     * 查询3级地区
     */
    public List<RegionArea> getStreet(String pid) throws JshException;
}