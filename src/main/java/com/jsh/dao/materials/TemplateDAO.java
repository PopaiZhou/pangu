/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.dao.materials;

import com.jsh.base.BaseDAO;
import com.jsh.model.po.Template;

/**
 * @author zhoujp
 * @version $Id TemplateDAO.java, v 0.1 2018-07-15 12:17 Lenovo Exp $$
 */
public class TemplateDAO extends BaseDAO<Template> implements TemplateIDAO{

    @Override
    public Class<Template> getEntityClass() {
        return Template.class;
    }
}