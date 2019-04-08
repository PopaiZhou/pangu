package com.jsh.action.materials;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.Basicuser;
import com.jsh.model.po.Customer;
import com.jsh.model.po.Express;
import com.jsh.model.po.Logdetails;
import com.jsh.model.vo.materials.ExpressModel;
import com.jsh.service.materials.ExpressIService;
import com.jsh.util.PageUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import java.io.IOException;
import java.sql.Timestamp;
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
     * 新增物流信息
     */
    public void create(){
        Log.infoFileSync("==================开始调用增加物流信息方法===================");
        boolean flag = false;
        try {
            Express express = new Express();
            express.setExpressCode(model.getExpressCode());
            express.setExpressName(model.getExpressName());
            express.setEnabled(model.getEnabled());
            express.setSortOrder(model.getSortOrder());

            expressService.create(express);

            //========标识位===========
            flag = true;
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        }catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>增加物流信息异常", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(Boolean.toString(flag));
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>增加物流信息回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "增加物流信息", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "增加物流信息名称|编号为  " + model.getExpressName()+"|"+model.getExpressCode() + " " + tipMsg + "！", "增加物流信息" + tipMsg));
        Log.infoFileSync("==================结束调用增加物流信息方法===================");
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
                    item.put("enabled", express.getEnabled());
                    item.put("op","1");

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
        condition.put("sortOrder_s_order", "asc");
        return condition;
    }

    /**
     * 批量设置状态-启用或者禁用
     *
     * @return
     */
    public String batchSetEnable() {
        try {
            expressService.batchSetEnable(model.getEnabled(), model.getBatchDeleteIds());
            model.getShowModel().setMsgTip("成功");
            //记录操作日志使用
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>批量修改状态，物流ID为：" + model.getBatchDeleteIds() + "信息异常", e);
            tipMsg = "失败";
            tipType = 1;
        }
        logService.create(new Logdetails(getUser(), "批量修改物流状态", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "批量修改物流状态，物流ID为  " + model.getBatchDeleteIds() + " " + tipMsg + "！", "批量修改物流信息状态" + tipMsg));
        return SUCCESS;
    }

    /**
     * 更新物流信息
     *
     * @return
     */
    public void update() {
        Log.infoFileSync("==================开始调用更新物流信息方法===================");
        Boolean flag = false;
        try {
            Express express = expressService.get(model.getId());

            express.setExpressCode(model.getExpressCode());
            express.setExpressName(model.getExpressName());
            express.setEnabled(model.getEnabled());
            express.setSortOrder(model.getSortOrder());

            expressService.update(express);

            flag = true;
            tipMsg = "成功";
            tipType = 0;
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>修改物流信息ID为 ： " + model.getId() + "信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } finally {
            try {
                toClient(flag.toString());
            } catch (IOException e) {
                Log.errorFileSync(">>>>>>>>>>>>修改物流信息回写客户端结果异常", e);
            }
        }
        logService.create(new Logdetails(getUser(), "更新物流信息", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "更新物流信息ID为  " + model.getId() + " " + tipMsg + "！", "更新物流信息" + tipMsg));
    }

    /**
     * 查询物流信息下拉列表
     */
    public void findBySelect_sup() {
        try {
            /**
             * 拼接搜索条件
             */
            Map<String, Object> condition = new HashMap<String, Object>();
            condition.put("enabled_s_eq", 1);
            condition.put("sort_order_s_order", "ASC");

            PageUtil<Express> pageUtil = new PageUtil<Express>();
            pageUtil.setPageSize(0);
            pageUtil.setCurPage(0);
            pageUtil.setAdvSearch(condition);
            expressService.find(pageUtil);
            List<Express> dataList = pageUtil.getPageList();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if (null != dataList) {
                for (Express express : dataList) {
                    JSONObject item = new JSONObject();
                    item.put("expressCode", express.getExpressCode());
                    //客户名称
                    item.put("expressName", express.getExpressName());
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>查找物流信息下拉框信息异常", e);
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询物流信息下拉框信息结果异常", e);
        }
    }

    /**
     * 查询物流信息
     *
     * @return
     */
    public void checkExpress() {
        Log.infoFileSync("==================开始调用查询物流信息方法===================");
        Boolean flag = false;
        try {
            String result = expressService.getExpressTraces(model.getExpressCode(),model.getExpressNumber());
            Log.infoFileSync("==================查询物流信息返回结果===================result ="+result);
            if(StringUtils.isNotEmpty(result)){
                JSONObject json = JSONObject.fromObject(result);
                json.put("ExpressName",model.getExpressName());
                if(json.getBoolean("Success")){
                    flag = true;
                    tipMsg = "成功";
                    tipType = 0;
                    json.put("StateName",transStatus(json.getString("State")));

                    //回写查询结果
                    toClient(json.toString());
                    return;
                }else {
                    flag = false;
                    tipMsg = "失败";
                    tipType = 1;
                    toClient(flag.toString());
                    return;
                }
            }else{
                toClient(flag.toString());
                return;
            }
        } catch (DataAccessException e) {
            Log.errorFileSync(">>>>>>>>>>>>>查询物流信息物流单号为 ： " + model.getExpressName()+"| 物流代码" + model.getExpressCode() + ",信息失败", e);
            flag = false;
            tipMsg = "失败";
            tipType = 1;
        } catch (IOException e) {
            Log.errorFileSync(">>>>>>>>>回写查询物流信息结果异常", e);
        }
        logService.create(new Logdetails(getUser(), "调用第三方快递鸟查询物流信息", model.getClientIp(),
                new Timestamp(System.currentTimeMillis())
                , tipType, "调用第三方快递鸟查询物流信息ID为  " + model.getId() + " " + tipMsg + "！", "更新物流信息" + tipMsg));
    }

    private String transStatus(String State){
        switch (State){
            case "0" :
                return "无轨迹";
            case "1" :
                return "已揽件";
            case "2" :
                return "在途中";
            case "3" :
                return "签收";
            case "4" :
                return "问题件";
            default:
                return "未知";
        }

    }

    @Override
    public ExpressModel getModel() {
        return model;
    }
}
