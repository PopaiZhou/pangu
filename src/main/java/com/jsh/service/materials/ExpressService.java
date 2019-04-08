package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.dao.materials.ExpressIDAO;
import com.jsh.model.po.Express;
import com.jsh.util.UrlUtil;

import java.util.HashMap;
import java.util.Map;

import static com.jsh.util.UrlUtil.urlEncoder;

public class ExpressService extends BaseService<Express> implements ExpressIService{

    //电商ID
    private String EBusinessID="1460523";
    //电商加密私钥，快递鸟提供，注意保管，不要泄漏
    private String AppKey="0bf819f9-6763-4f14-bab3-ff31969038c2";
    //正式请求url
    private String ReqURL="http://api.kdniao.com/Ebusiness/EbusinessOrderHandle.aspx";
    //测试请求url
    //private String ReqURL = "http://sandboxapi.kdniao.com:8080/kdniaosandbox/gateway/exterfaceInvoke.json";

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

    @Override
    public void batchSetEnable(Boolean enable, String batchDeleteIds) {
        expressDao.batchSetEnable(enable, batchDeleteIds);
    }

    @Override
    public String getExpressTraces(String expressCode, String expressNumber) {
        String requestData= "{'OrderCode':'','ShipperCode':'" + expressCode + "','LogisticCode':'" + expressNumber + "'}";

        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("RequestData", urlEncoder(requestData, "UTF-8"));
            params.put("EBusinessID", EBusinessID);
            params.put("RequestType", "1002");
            String dataSign=UrlUtil.encrypt(requestData, AppKey, "UTF-8");
            params.put("DataSign", urlEncoder(dataSign, "UTF-8"));
            params.put("DataType", "2");

            return UrlUtil.sendPost(ReqURL, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
