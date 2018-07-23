package com.jsh.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhoujp
 * @version $Id CustomerTypeEnum.java, v 0.1 2018-07-21 18:12 Lenovo Exp $$
 */
public enum CustomerTypeEnum {

    WHOLESALER("wholesaler","批发商"),
    TRADER("trader","零售商");

    private String code;
    private String name;

    CustomerTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    public static CustomerTypeEnum getSexEnumByCode(String code){
        for(CustomerTypeEnum sexEnum : CustomerTypeEnum.values()){
            if(StringUtils.equals(code, sexEnum.getCode())){
                return sexEnum;
            }
        }
        return null;
    }
}