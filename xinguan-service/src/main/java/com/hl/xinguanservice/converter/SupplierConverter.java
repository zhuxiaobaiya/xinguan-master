package com.hl.xinguanservice.converter;

import com.hl.common.vo.business.SupplierVO;
import com.hl.xinguanservice.entity.business.Supplier;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangliang
 * @Date 2021/11/27 16:54
 * @Version 1.0
 * @Description
 */
public class SupplierConverter {
    /**
     * 转voList
     * @param suppliers
     * @return
     */
    public static List<SupplierVO> converterToVOList(List<Supplier> suppliers) {
        List<SupplierVO> supplierVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(suppliers)){
            for (Supplier supplier : suppliers) {
                SupplierVO supplierVO = converterToSupplierVO(supplier);
                supplierVOS.add(supplierVO);
            }
        }
        return supplierVOS;
    }


    /***
     * 转VO
     * @param supplier
     * @return
     */
    public static SupplierVO converterToSupplierVO(Supplier supplier) {
        SupplierVO supplierVO = new SupplierVO();
        BeanUtils.copyProperties(supplier,supplierVO);
        return supplierVO;
    }
}
