package com.hl.xinguanservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.common.error.BusinessCodeEnum;
import com.hl.common.error.BusinessException;
import com.hl.common.utils.CategoryTreeBuilder;
import com.hl.common.utils.ListPageUtils;
import com.hl.common.vo.business.ProductCategoryTreeNodeVO;
import com.hl.common.vo.business.ProductCategoryVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.converter.ProductCategoryConverter;
import com.hl.xinguanservice.entity.business.Product;
import com.hl.xinguanservice.entity.business.ProductCategory;
import com.hl.xinguanservice.entity.system.User;
import com.hl.xinguanservice.mapper.ProductCategoryMapper;
import com.hl.xinguanservice.mapper.ProductMapper;
import com.hl.xinguanservice.service.ProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-26
 */
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Resource
    private ProductMapper productMapper;


    /**
     * 所有商品类别
     * @return
     */
    public List<ProductCategoryVO> findAll() {
        List<ProductCategory> productCategories = baseMapper.selectList(null);
        return ProductCategoryConverter.converterToVOList(productCategories);
    }


    /**
     * 商品类别列表
     * @param pageNum
     * @param pageSize
     * @param ProductCategoryVO
     * @return
     */
    @Override
    public PageVO<ProductCategoryVO> findProductCategoryList(Integer pageNum, Integer pageSize, ProductCategoryVO ProductCategoryVO) {
        //创建page对象
        Page<ProductCategory> productCategoryPage = new Page<>(pageNum,pageSize);
        //构建条件
        QueryWrapper<ProductCategory> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        if (ProductCategoryVO.getName() != null && !"".equals(ProductCategoryVO.getName())) {
            wrapper.like("name",ProductCategoryVO.getName());
        }
        baseMapper.selectPage(productCategoryPage,wrapper);
        List<ProductCategory> productCategories = productCategoryPage.getRecords();
        List<ProductCategoryVO> categoryVOS= ProductCategoryConverter.converterToVOList(productCategories);

        return new PageVO<>(productCategoryPage.getTotal(), categoryVOS);
    }



    /**
     * 添加商品类别
     * @param ProductCategoryVO
     */
    @Override
    public void addProductCategory(ProductCategoryVO ProductCategoryVO) {
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(ProductCategoryVO,productCategory);
        productCategory.setCreateTime(new Date());
        productCategory.setModifiedTime(new Date());
        baseMapper.insert(productCategory);
    }

    /**
     * 删除商品类别
     * @param id
     */
    @Override
    public void deleteProductCategory(String id) throws BusinessException {
        ProductCategory category = baseMapper.selectById(id);
        if(null==category){
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"该分类不存在");
        }else {
            //检查是否存在子分类
            QueryWrapper<ProductCategory> productCategoryQueryWrapper = new QueryWrapper<>();
            productCategoryQueryWrapper.eq("pid",id);
            int childCount=baseMapper.selectCount(productCategoryQueryWrapper);
            if(childCount!=0){
                throw  new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"存在子节点,无法直接删除");
            }
            //检查该分类是否有物资引用
            QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("one_category_id",id);
            queryWrapper.eq("two_category_id",id);
            queryWrapper.eq("three_category_id",id);
            if(productMapper.selectCount(queryWrapper)!=0){
                throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR,"该分类存在物资引用,无法直接删除");
            }
            baseMapper.deleteById(id);
        }
    }

    /**
     * 编辑商品类别
     * @param id
     * @return
     */
    @Override
    public ProductCategoryVO editProductCategory(String id) {
        ProductCategory productCategory = baseMapper.selectById(id);
        return  ProductCategoryConverter.converterToProductCategoryVO(productCategory);
    }

    /**
     * 更新商品类别
     * @param id
     * @param ProductCategoryVO
     */
    @Override
    public void updateProductCategory(String id, ProductCategoryVO ProductCategoryVO) {
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(ProductCategoryVO,productCategory);
        productCategory.setModifiedTime(new Date());
        baseMapper.updateById(productCategory);
    }


    /**
     * 分类树形结构
     * @return
     */
    @Override
    public PageVO<ProductCategoryTreeNodeVO> categoryTree(Integer pageNum, Integer pageSize) {
        List<ProductCategoryVO> productCategoryVOList = findAll();
        List<ProductCategoryTreeNodeVO> nodeVOS=ProductCategoryConverter.converterToTreeNodeVO(productCategoryVOList);
        List<ProductCategoryTreeNodeVO> tree = CategoryTreeBuilder.build(nodeVOS);
        List<ProductCategoryTreeNodeVO> page;
        if(pageSize!=null&&pageNum!=null){
            page= ListPageUtils.page(tree, pageSize, pageNum);
            return new PageVO<>(tree.size(),page);
        }else {
            return new PageVO<>(tree.size(), tree);
        }
    }

    /**
     * 获取父级分类（2级树）
     * @return
     */
    @Override
    public List<ProductCategoryTreeNodeVO> getParentCategoryTree() {
        List<ProductCategoryVO> productCategoryVOList = findAll();
        List<ProductCategoryTreeNodeVO> nodeVOS=ProductCategoryConverter.converterToTreeNodeVO(productCategoryVOList);
        return  CategoryTreeBuilder.buildParent(nodeVOS);
    }

}
