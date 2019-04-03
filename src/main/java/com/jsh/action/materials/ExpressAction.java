package com.jsh.action.materials;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Express;
import com.jsh.model.vo.materials.ExpressModel;
import com.jsh.service.materials.ExpressIService;
import com.jsh.util.PageUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("serial")
public class ExpressAction extends BaseAction<ExpressModel> {
    private ExpressModel model = new ExpressModel();

    private ExpressIService expressService;

    /**
     * Setter method for property <tt>expressService</tt>.
     *
     * @param expressService value to be assigned to property expressService
     */
    public void setExpressService(ExpressIService expressService) {
        this.expressService = expressService;
    }

    /**
     * 查询物流信息列表
     */
    public void findBy(){
        try{
            PageUtil<Express> pageUtil = new PageUtil<>();
            pageUtil.setPageSize(model.getPageSize());
            pageUtil.setCurPage(model.getPageNo());
            pageUtil.setAdvSearch(getCondition());

            expressService.find(pageUtil);

            List<Express> dataList = pageUtil.getPageList();
            JSONObject outer = new JSONObject();
            outer.put("total", pageUtil.getTotalCount());
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for(Express express : dataList){
                    JSONObject item = new JSONObject();

                    item.put("id", express.getId());
                    item.put("expressName", express.getExpressName());
                    item.put("expressCode", express.getExpressCode());
                    item.put("sortOrder", express.getSortOrder());

                    dataArray.add(item);
                }
            }
            outer.put("rows", dataArray);
            //回写查询结果
            toClient(outer.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找物流信息信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写物流信息结果异常", e);
        }
    }

    /**
     * 拼接搜索条件
     *
     * @return
     */
    private Map<String, Object> getCondition() {
        /**
         * 拼接搜索条件
         */
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("expressName_s_like", model.getExpressName());
        condition.put("expressCode_s_like", model.getExpressCode());
        condition.put("sortOrder_s_order", "desc");
        return condition;
    }

    @Override
    public ExpressModel getModel() {
        return model;
    }
}
