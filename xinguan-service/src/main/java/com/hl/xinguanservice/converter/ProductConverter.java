package com.hl.xinguanservice.converter;

import com.hl.common.vo.business.ProductVO;
import com.hl.xinguanservice.entity.business.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangliang
 * @Date 2021/11/27 15:04
 * @Version 1.0
 * @Description
 */
public class ProductConverter {
    /**
     * 转VOList
     * @param products
     * @return
     */
    public static List<ProductVO> converterToVOList(List<Product> products) {
        List<ProductVO> productVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(products)){
            for (Product product : products) {
                ProductVO productVO = converterToProductVO(product);
                productVOS.add(productVO);
            }
        }
        return productVOS;
    }

    /**
     * 转VO
     * @param product
     * @return
     */
    public static ProductVO converterToProductVO(Product product) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product,productVO);
        return productVO;
    }
}
