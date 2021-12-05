package com.hl.xinguanservice.converter;

import com.hl.common.vo.business.InStockVO;
import com.hl.xinguanservice.entity.business.InStock;
import com.hl.xinguanservice.entity.business.Supplier;
import com.hl.xinguanservice.mapper.SupplierMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangliang
 * @Date 2021/11/26 19:19
 * @Version 1.0
 * @Description
 */
@Component
public class InStockConverter {

    @Autowired
    private SupplierMapper supplierMapper;

    /**
     * 转voList
     * @param inStocks
     * @return
     */
    public List<InStockVO> converterToVOList(List<InStock> inStocks) {
        List<InStockVO> inStockVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(inStocks)){
            for (InStock inStock : inStocks) {
                InStockVO inStockVO = new InStockVO();
                BeanUtils.copyProperties(inStock,inStockVO);
                Supplier supplier = supplierMapper.selectById(inStock.getSupplierId());
                if(supplier!=null){
                    inStockVO.setSupplierName(supplier.getName());
                    inStockVO.setPhone(supplier.getPhone());
                }
                inStockVOS.add(inStockVO);
            }
        }
        return inStockVOS;
    }
}
