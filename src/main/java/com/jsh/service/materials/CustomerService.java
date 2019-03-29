/**
 * Zentech-Inc
 * Copyright (C) 2018 All Rights Reserved.
 */
package com.jsh.service.materials;

import com.jsh.base.BaseService;
import com.jsh.base.Log;
import com.jsh.dao.materials.CustomerIDAO;
import com.jsh.enums.CustomerTypeEnum;
import com.jsh.model.po.Customer;
import com.jsh.util.JshException;
import com.jsh.util.PageUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author zhoujp
 * @version $Id CustomerService.java, v 0.1 2018-07-15 12:15 Lenovo Exp $$
 */
public class CustomerService extends BaseService<Customer> implements CustomerIService{

    private CustomerIDAO customerDao;

    @Override
    public void batchSetEnable(Boolean enable, String batchDeleteIds) {
        customerDao.batchSetEnable(enable, batchDeleteIds);
    }

    @Override
    public void batchTransCustomer(String oldId, String newId, String customerIds) {
        customerDao.batchTransCustomer(oldId,newId,customerIds);
    }

    @Override
    public InputStream exmportExcel(String isAllPage, PageUtil<Customer> pageUtil) throws JshException {
        try {
            if ("currentPage".equals(isAllPage)) {
                customerDao.find(pageUtil);
            } else {
                pageUtil.setCurPage(0);
                pageUtil.setPageSize(0);
                customerDao.find(pageUtil);
            }
            //将OutputStream转化为InputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            putDataOnOutputStream(out, pageUtil.getPageList());
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>>导出信息为excel表格异常", e);
            throw new JshException("导出信息为excel表格异常", e);
        }
    }

    /**
     * 生成excel表格
     *
     * @param os
     */
    @SuppressWarnings("deprecation")
    private void putDataOnOutputStream(OutputStream os, List<Customer> dataList) {
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(os);
            WritableSheet sheet = workbook.createSheet("信息报表", 0);
            //增加列头
            String[] colunmName = {"编号","全称", "简称", "联系人", "联系电话", "电子邮箱", "客户类型", "手机", "QQ", "默认物流", "省", "市", "区县", "地址", "纳税人识别号", "开户行", "账号", "税率","业务经理","状态"};
            for (int i = 0; i < colunmName.length; i++) {
                sheet.setColumnView(i, 10);
                sheet.addCell(new Label(i, 0, colunmName[i]));
            }
            if (null != dataList && dataList.size() > 0) {
                int i = 1;
                for (Customer customer : dataList) {
                    int j = 0;
                    Map<Integer, String> cellInfo = customer.getCellInfo();
                    sheet.addCell(new Label(j++, i, customer.getCustomerNo()));
                    sheet.addCell(new Label(j++, i, customer.getCustomerName()));
                    sheet.addCell(new Label(j++, i, customer.getCustomerShort() == null ? "" : customer.getCustomerShort()));
                    sheet.addCell(new Label(j++, i, customer.getContacts() == null ? "" : customer.getContacts()));
                    sheet.addCell(new Label(j++, i, customer.getPhonenum() == null ? "" : customer.getPhonenum()));
                    sheet.addCell(new Label(j++, i, customer.getEmail() == null ? "" : customer.getEmail()));

                    sheet.addCell(new Label(j++, i, customer.getType() == null ? "未知" : CustomerTypeEnum.getSexEnumByCode(customer.getType()).getName()));
                    sheet.addCell(new Label(j++, i, customer.getTelephone() == null ? "" : customer.getTelephone()));
                    sheet.addCell(new Label(j++, i, customer.getQq() == null ? "" : customer.getQq()));
                    sheet.addCell(new Label(j++, i, customer.getExpress() == null ? "" : customer.getExpress()));
                    sheet.addCell(new Label(j++, i, customer.getState() == null ? "" : customer.getState()));
                    sheet.addCell(new Label(j++, i, customer.getCity() == null ? "" : customer.getCity()));
                    sheet.addCell(new Label(j++, i, customer.getStreet() == null ? "" : customer.getStreet()));
                    sheet.addCell(new Label(j++, i, customer.getAddress() == null ? "" : customer.getAddress()));

                    sheet.addCell(new Label(j++, i, customer.getTaxNum() == null ? "" : customer.getTaxNum()));
                    sheet.addCell(new Label(j++, i, customer.getBankName() == null ? "" : customer.getBankName()));
                    sheet.addCell(new Label(j++, i, customer.getAccountNumber() == null ? "" : customer.getAccountNumber()));
                    sheet.addCell(new Label(j++, i, customer.getTaxRate() == null ? "" : customer.getTaxRate()+""));

                    sheet.addCell(new Label(j++, i, customer.getUser() == null ? "" : customer.getUser().getUsername()));
                    sheet.addCell(new Label(j++, i, customer.getEnabled() ? "启用" : "禁用"));
                    i++;
                }
            }
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            Log.errorFileSync(">>>>>>>>>>>>>>>>>>>>>>>导出信息为excel表格异常", e);
        }
    }

    @Override
    protected Class<Customer> getEntityClass() {
        return Customer.class;
    }

    /**
     * Setter method for property <tt>customerDao</tt>.
     *
     * @param customerDao value to be assigned to property customerDao
     */
    public void setCustomerDao(CustomerIDAO customerDao) {
        this.customerDao = customerDao;
    }


}