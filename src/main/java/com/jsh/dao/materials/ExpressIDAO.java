package com.jsh.dao.materials;

import com.jsh.base.BaseIDAO;
import com.jsh.model.po.Express;

public interface ExpressIDAO extends BaseIDAO<Express> {

    public void batchSetEnable(Boolean enable, String batchDeleteIds);
}
