package com.jsh.model.vo.materials;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressShowModel implements Serializable {
    private static final long serialVersionUID = -1543172352493970203L;

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
