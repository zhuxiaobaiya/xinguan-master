package com.hl.xinguanservice.service;

import com.hl.common.error.BusinessException;
import com.hl.common.vo.business.ProductStockVO;
import com.hl.common.vo.business.ProductVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.entity.business.Product;
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
public interface ProductService extends IService<Product> {


    /**
     * 商品列表
     * @param pageNum
     * @param pageSize
     * @param productVO
     * @return
     */
    PageVO<ProductVO> findProductList(Integer pageNum, Integer pageSize, ProductVO productVO);


    /**
     * 添加商品
     * @param productVO
     */
    void addProduct(ProductVO productVO);

    /**
     * 删除商品
     * @param id
     */
    void deleteProduct(String id) throws BusinessException;

    /**
     * 编辑商品
     * @param id
     * @return
     */
    ProductVO editProduct(String id);

    /**
     * 更新商品
     * @param id
     * @param productVO
     */
    void updateProduct(String id, ProductVO productVO);

    /**
     * 移入回收站
     * @param id
     */
    void removeProduct(String id) throws BusinessException;

    /**
     * 从回收站恢复数据
     * @param id
     */
    void backProduct(String id) throws BusinessException;

    /**
     * 物资添加审核
     * @param id
     */
    void publishProduct(String id) throws BusinessException;

    /**
     * 库存列表
     * @param pageNum
     * @param pageSize
     * @param productVO
     * @return
     */
    PageVO<ProductStockVO> findProductStocks(Integer pageNum, Integer pageSize, ProductVO productVO);

    /**
     * 所有库存信息
     * @return
     */
    List<ProductStockVO> findAllStocks(Integer pageNum, Integer pageSize, ProductVO productVO);
}
