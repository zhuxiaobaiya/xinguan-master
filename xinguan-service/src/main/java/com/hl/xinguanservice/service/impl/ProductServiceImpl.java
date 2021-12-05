package com.hl.xinguanservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.common.error.BusinessCodeEnum;
import com.hl.common.error.BusinessException;
import com.hl.common.vo.business.ProductStockVO;
import com.hl.common.vo.business.ProductVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.converter.ProductConverter;
import com.hl.xinguanservice.entity.business.Product;
import com.hl.xinguanservice.entity.business.ProductStock;
import com.hl.xinguanservice.entity.system.User;
import com.hl.xinguanservice.mapper.ProductMapper;
import com.hl.xinguanservice.mapper.ProductStockMapper;
import com.hl.xinguanservice.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-26
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {



    @Autowired
    private ProductStockMapper productStockMapper;


    /**
     * 商品列表
     * @param pageNum
     * @param pageSize
     * @param productVO
     * @return
     */
    @Override
    public PageVO<ProductVO> findProductList(Integer pageNum, Integer pageSize, ProductVO productVO) {
        //创建page对象
        Page<Product> productPage = new Page<>(pageNum,pageSize);
        //构建条件
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        List<Product> products;
        if (productVO.getStatus() != null) {
            wrapper.eq("status",productVO.getStatus());
        }
        if(productVO.getThreeCategoryId()!=null){
            wrapper.eq("one_category_id",productVO.getOneCategoryId());
            wrapper.eq("two_category_id",productVO.getTwoCategoryId());
            wrapper.eq("three_category_id",productVO.getThreeCategoryId());
            baseMapper.selectPage(productPage,wrapper);
            products = productPage.getRecords();
            List<ProductVO> categoryVOS= ProductConverter.converterToVOList(products);

            return new PageVO<>(productPage.getTotal(), categoryVOS);
        }
        if(productVO.getTwoCategoryId()!=null){
            wrapper.eq("one_category_id",productVO.getOneCategoryId());
            wrapper.eq("two_category_id",productVO.getTwoCategoryId());

            baseMapper.selectPage(productPage,wrapper);
            products = productPage.getRecords();
            List<ProductVO> categoryVOS= ProductConverter.converterToVOList(products);
            return new PageVO<>(productPage.getTotal(), categoryVOS);
        }
        if(productVO.getOneCategoryId()!=null) {
            wrapper.eq("one_category_id",productVO.getOneCategoryId());

            baseMapper.selectPage(productPage,wrapper);
            products = productPage.getRecords();
            List<ProductVO> categoryVOS= ProductConverter.converterToVOList(products);
            return new PageVO<>(productPage.getTotal(), categoryVOS);
        }
        wrapper.orderByAsc("sort");
        if (productVO.getName() != null && !"".equals(productVO.getName())) {
            wrapper.like("name",productVO.getName());
        }

        baseMapper.selectPage(productPage,wrapper);
        products = productPage.getRecords();
        List<ProductVO> categoryVOS= ProductConverter.converterToVOList(products);
        return new PageVO<>(productPage.getTotal(), categoryVOS);
    }

    /**
     * 添加商品
     * @param ProductVO
     */
    @Override
    public void addProduct(ProductVO ProductVO) {
        Product product = new Product();
        BeanUtils.copyProperties(ProductVO,product);
        product.setCreateTime(new Date());
        product.setModifiedTime(new Date());
        @NotNull(message = "分类不能为空") String[] categoryKeys = ProductVO.getCategoryKeys();
        if(categoryKeys.length==3){
            product.setOneCategoryId(categoryKeys[0]);
            product.setTwoCategoryId(categoryKeys[1]);
            product.setThreeCategoryId(categoryKeys[2]);
        }
        product.setStatus(2);//未审核
        product.setPNum(UUID.randomUUID().toString().substring(0,32));
        baseMapper.insert(product);
    }

    /**
     * 删除商品
     * @param id
     */
    @Override
    public void deleteProduct(String id) throws BusinessException {
        Product t = new Product();
        t.setId(id);
        Product product = baseMapper.selectById(t);
        //只有物资处于回收站,或者待审核的情况下可删除
        if(product.getStatus()!=1&&product.getStatus()!=2){
            throw new BusinessException(BusinessCodeEnum.PRODUCT_STATUS_ERROR);
        }else {
            baseMapper.deleteById(id);
        }
    }


    /**
     * 编辑商品
     * @param id
     * @return
     */
    @Override
    public ProductVO editProduct(String id) {
        Product product = baseMapper.selectById(id);
        return ProductConverter.converterToProductVO(product);
    }

    /**
     * 更新商品
     * @param id
     * @param ProductVO
     */
    @Override
    public void updateProduct(String id, ProductVO ProductVO) {
        Product product = new Product();
        BeanUtils.copyProperties(ProductVO,product);
        product.setModifiedTime(new Date());
        @NotNull(message = "分类不能为空") String[] categoryKeys = ProductVO.getCategoryKeys();
        if(categoryKeys.length==3){
            product.setOneCategoryId(categoryKeys[0]);
            product.setTwoCategoryId(categoryKeys[1]);
            product.setThreeCategoryId(categoryKeys[2]);
        }
        baseMapper.updateById(product);
    }

    /**
     * 移入回收站
     * @param id
     */
    @Override
    public void removeProduct(String id) throws BusinessException {
        Product t = new Product();
        t.setId(id);
        Product product = baseMapper.selectById(t);
        if(product.getStatus()!=0){
            throw new BusinessException(BusinessCodeEnum.PRODUCT_STATUS_ERROR);
        }else {
            t.setStatus(1);
            baseMapper.updateById(t);
        }
    }

    /**
     * 从回收站恢复数据
     * @param id
     */
    @Override
    public void backProduct(String id) throws BusinessException {
        Product t = new Product();
        t.setId(id);
        Product product = baseMapper.selectById(t);
        if(product.getStatus()!=1){
            throw new BusinessException(BusinessCodeEnum.PRODUCT_STATUS_ERROR);
        }else {
            t.setStatus(0);
            baseMapper.updateById(t);
        }
    }

    /**
     * 物资审核
     * @param id
     */
    @Override
    public void publishProduct(String id) throws BusinessException {
        Product t = new Product();
        t.setId(id);
        Product product = baseMapper.selectById(t);
        if(product.getStatus()!=2){
            throw new BusinessException(BusinessCodeEnum.PRODUCT_STATUS_ERROR);
        }else {
            t.setStatus(0);
            baseMapper.updateById(t);
        }
    }


    /**
     * 物资库存列表
     * @param pageNum
     * @param pageSize
     * @param productVO
     * @return
     */
    @Override
    public PageVO<ProductStockVO> findProductStocks(Integer pageNum, Integer pageSize, ProductVO productVO) {
        Page<ProductStock> productStockVOPage = new Page<>(pageNum, pageSize);
        List<ProductStockVO> productStockVOList=productStockMapper.findProductStocks(productVO);
        productStockMapper.selectPage(productStockVOPage,null);
        return new PageVO<>(productStockVOPage.getTotal(), productStockVOList);
    }

    /**
     * 所有库存信息
     * @return
     */
    @Override
    public List<ProductStockVO> findAllStocks(Integer pageNum, Integer pageSize, ProductVO productVO) {
        return productStockMapper.findAllStocks(productVO);
    }
}
