package com.hl.xinguanservice.service;

import com.hl.common.error.BusinessException;
import com.hl.common.vo.business.ProductCategoryTreeNodeVO;
import com.hl.common.vo.business.ProductCategoryVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.entity.business.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-26
 */
public interface ProductCategoryService extends IService<ProductCategory> {


    /**
     * 查询所物资类别
     * @return
     */
    List<ProductCategoryVO> findAll();

    /**
     * 部门列表
     * @param pageNum
     * @param pageSize
     * @param ProductCategoryVO
     * @return
     */
    PageVO<ProductCategoryVO> findProductCategoryList(Integer pageNum, Integer pageSize, ProductCategoryVO ProductCategoryVO);

    /**
     * 添加物资类别
     * @param ProductCategoryVO
     */
    void addProductCategory(ProductCategoryVO ProductCategoryVO);

    /**
     * 删除物资类别
     * @param id
     */
    void deleteProductCategory(String id) throws BusinessException;


    /**
     * 编辑物资类别
     * @param id
     * @return
     */
    ProductCategoryVO editProductCategory(String id);

    /**
     * 更新物资类别
     * @param id
     * @param ProductCategoryVO
     */
    void updateProductCategory(String id, ProductCategoryVO ProductCategoryVO);


    /**
     * 分类树形
     * @return
     */
    PageVO<ProductCategoryTreeNodeVO> categoryTree(Integer pageNum, Integer pageSize);

    /**
     * 获取父级分类（2级树）
     * @return
     */
    List<ProductCategoryTreeNodeVO> getParentCategoryTree();

}
