/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.action.basic;

import com.jsh.base.BaseAction;
import com.jsh.base.Log;
import com.jsh.model.po.RegionArea;
import com.jsh.model.vo.basic.RegionAreaModel;
import com.jsh.service.basic.RegionAreaIService;
import com.jsh.util.JshException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author zhoujp
 * @version $Id RegionAreaAction.java, v 0.1 2018-05-04 17:19 Lenovo Exp $$
 * 省、市、区数据
 */
public class RegionAreaAction extends BaseAction<RegionAreaModel> {

    private RegionAreaIService regionAreaService;

    private RegionAreaModel model = new RegionAreaModel();

    @Override
    public RegionAreaModel getModel() {
        return model;
    }

    /**
     * 获取所有省份信息
     */
    public void getAllState(){
        try {
            List<RegionArea> list = regionAreaService.getAllState();
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(CollectionUtils.isNotEmpty(list)){
                for(RegionArea area : list){
                    JSONObject item = new JSONObject();
                    item.put("id", area.getRegionId());
                    item.put("name", area.getName());
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } catch (JshException e) {
            Log.errorFileSync(">>>>>>>>>获取所有省份信息", e);
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>获取所有省份信息异常", e);
        }
    }
    /**
     * 获取2级市县
     */
    public void getCity(){
        String pid = model.getParentId();
        try {
            List<RegionArea> list = regionAreaService.getCity(pid);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(CollectionUtils.isNotEmpty(list)){
                for(RegionArea area : list){
                    JSONObject item = new JSONObject();
                    item.put("id", area.getRegionId());
                    item.put("name", area.getName());
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } catch (JshException e) {
            Log.errorFileSync(">>>>>>>>>获取所有市县信息", e);
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>获取所有市县信息异常", e);
        }
    }

    /**
     * 获取3级地区
     */
    public void getStreet(){
        String pid = model.getParentId();
        try {
            List<RegionArea> list = regionAreaService.getCity(pid);
            //存放数据json数组
            JSONArray dataArray = new JSONArray();
            if(CollectionUtils.isNotEmpty(list)){
                for(RegionArea area : list){
                    JSONObject item = new JSONObject();
                    item.put("id", area.getRegionId());
                    item.put("name", area.getName());
                    dataArray.add(item);
                }
            }
            //回写查询结果
            toClient(dataArray.toString());
        } catch (JshException e) {
            Log.errorFileSync(">>>>>>>>>获取所有地区信息", e);
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>获取所有地区信息异常", e);
        }
    }

    /**
     * Setter method for property <tt>regionAreaService</tt>.
     *
     * @param regionAreaService value to be assigned to property regionAreaService
     */
    public void setRegionAreaService(RegionAreaIService regionAreaService) {
        this.regionAreaService = regionAreaService;
    }
}