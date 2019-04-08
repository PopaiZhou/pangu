package com.jsh.service.materials;

import com.jsh.base.BaseIService;
import com.jsh.model.po.Express;

public interface ExpressIService extends BaseIService<Express> {

    public void batchSetEnable(Boolean enable, String batchDeleteIds);

    public String getExpressTraces(String expressCode,String expressNumber);
}
