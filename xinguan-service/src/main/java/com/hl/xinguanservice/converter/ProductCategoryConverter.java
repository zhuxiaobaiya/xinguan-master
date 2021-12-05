package com.hl.xinguanservice.converter;

import com.hl.common.vo.business.ProductCategoryTreeNodeVO;
import com.hl.common.vo.business.ProductCategoryVO;
import com.hl.xinguanservice.entity.business.ProductCategory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author huangliang
 * @Date 2021/11/26 14:32
 * @Version 1.0
 * @Description
 */
public class ProductCategoryConverter {

    /**
     * 转vo
     * @param productCategory
     * @return
     */
    public static ProductCategoryVO converterToProductCategoryVO(ProductCategory productCategory) {
        ProductCategoryVO productCategoryVO = new ProductCategoryVO();
        BeanUtils.copyProperties(productCategory,productCategoryVO);
        return productCategoryVO;
    }

    /**
     * 转voList
     * @param productCategories
     * @return
     */
    public static List<ProductCategoryVO> converterToVOList(List<ProductCategory> productCategories) {
        List<ProductCategoryVO> productCategoryVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(productCategories)){
            for (ProductCategory productCategory : productCategories) {
                ProductCategoryVO productCategoryVO = new ProductCategoryVO();
                BeanUtils.copyProperties(productCategory,productCategoryVO);
                productCategoryVOS.add(productCategoryVO);
            }
        }
        return productCategoryVOS;
    }

    /**
     * 转树节点
     * @param productCategoryVOList
     * @return
     */
    public static List<ProductCategoryTreeNodeVO> converterToTreeNodeVO(List<ProductCategoryVO> productCategoryVOList) {
        List<ProductCategoryTreeNodeVO> nodes=new ArrayList<>();
        if(!CollectionUtils.isEmpty(productCategoryVOList)){
            for (ProductCategoryVO productCategoryVO : productCategoryVOList) {
                ProductCategoryTreeNodeVO productCategoryTreeNodeVO = new ProductCategoryTreeNodeVO();
                BeanUtils.copyProperties(productCategoryVO,productCategoryTreeNodeVO);
                nodes.add(productCategoryTreeNodeVO);
            }
        }
        return nodes;
    }
}
