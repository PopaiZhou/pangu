/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.TemplateIDAO;
import com.jsh.model.po.Template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujp
 * @version $Id TemplateService.java, v 0.1 2018-07-15 12:15 Lenovo Exp $$
 */
public class TemplateService extends BaseService<Template> implements TemplateIService{

    private TemplateIDAO templateDao;

    @Override
    protected Class<Template> getEntityClass() {
        return Template.class;
    }
    /**
     * Setter method for property <tt>templateDao</tt>.
     *
     * @param templateDao value to be assigned to property templateDao
     */
    public void setTemplateDao(TemplateIDAO templateDao) {
        this.templateDao = templateDao;
    }
}