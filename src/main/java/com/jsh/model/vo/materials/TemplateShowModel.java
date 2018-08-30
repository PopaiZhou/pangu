/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.model.vo.materials;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujp
 * @version $Id TemplateShowModel.java, v 0.1 2018-07-17 15:58 Lenovo Exp $$
 */
public class TemplateShowModel implements Serializable{
    private static final long serialVersionUID = 3443399592132503315L;
    /**
     * 提示信息
     */
    private String msgTip = "";

    /**
     * 系统数据
     */
    @SuppressWarnings("rawtypes")
    private Map<String, List> map = new HashMap<String, List>();

    public String getMsgTip() {
        return msgTip;
    }

    public void setMsgTip(String msgTip) {
        this.msgTip = msgTip;
    }

    @SuppressWarnings("rawtypes")
    public Map<String, List> getMap() {
        return map;
    }

    @SuppressWarnings("rawtypes")
    public void setMap(Map<String, List> map) {
        this.map = map;
    }
}