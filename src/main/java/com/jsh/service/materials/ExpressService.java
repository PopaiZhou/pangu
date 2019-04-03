package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.ExpressIDAO;
import com.jsh.model.po.Express;

public class ExpressService extends BaseService<Express> implements ExpressIService{

    @SuppressWarnings("unused")
    private ExpressIDAO expressDao;

    @Override
    protected Class<Express> getEntityClass() {
        return Express.class;
    }

    /**
     * Setter method for property <tt>expressDAO</tt>.
     *
     * @param expressDao value to be assigned to property expressDAO
     */
    public void setExpressDao(ExpressIDAO expressDao) {
        this.expressDao = expressDao;
    }
}
