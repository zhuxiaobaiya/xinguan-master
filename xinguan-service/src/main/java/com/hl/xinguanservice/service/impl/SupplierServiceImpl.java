package com.hl.xinguanservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.common.vo.business.SupplierVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.converter.SupplierConverter;
import com.hl.xinguanservice.entity.business.Supplier;
import com.hl.xinguanservice.entity.system.User;
import com.hl.xinguanservice.mapper.SupplierMapper;
import com.hl.xinguanservice.service.SupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<SupplierVO> findAllSupplier() {
        List<Supplier> suppliers = baseMapper.selectList(null);
        return SupplierConverter.converterToVOList(suppliers);
    }

    /**
     * 供应商列表
     * @param pageNum
     * @param pageSize
     * @param supplierVO
     * @return
     */
    @Override
    public PageVO<SupplierVO> findSupplierList(Integer pageNum, Integer pageSize, SupplierVO supplierVO) {
        //创建page对象
        Page<Supplier> supplierPage = new Page<>(pageNum,pageSize);
        //构建条件
        QueryWrapper<Supplier> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        if (supplierVO.getName() != null && !"".equals(supplierVO.getName())) {
            wrapper.like("name", supplierVO.getName());
        }
        if (supplierVO.getContact() != null && !"".equals(supplierVO.getContact())) {
            wrapper.like("contact", supplierVO.getContact());
        }
        if (supplierVO.getAddress() != null && !"".equals(supplierVO.getAddress())) {
            wrapper.like("address",supplierVO.getAddress());
        }
        baseMapper.selectPage(supplierPage,wrapper);
        List<Supplier> suppliers = supplierPage.getRecords();
        List<SupplierVO> categoryVOS= SupplierConverter.converterToVOList(suppliers);
        return new PageVO<>(supplierPage.getTotal(), categoryVOS);
    }


    /**
     * 添加供应商
     * @param SupplierVO
     */
    @Override
    public Supplier addSupplier(SupplierVO SupplierVO) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(SupplierVO,supplier);
        supplier.setCreateTime(new Date());
        supplier.setModifiedTime(new Date());
        baseMapper.insert(supplier);
        return supplier;
    }

    /**
     * 删除供应商
     * @param id
     */
    @Override
    public void deleteSupplier(Long id) {
        baseMapper.deleteById(id);
    }

    /**
     * 编辑供应商
     * @param id
     * @return
     */
    @Override
    public SupplierVO editSupplier(Long id) {
        Supplier supplier = baseMapper.selectById(id);
        return SupplierConverter.converterToSupplierVO(supplier);
    }

    /**
     * 更新供应商
     * @param id
     * @param SupplierVO
     */
    @Override
    public void updateSupplier(Long id, SupplierVO SupplierVO) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(SupplierVO,supplier);
        supplier.setModifiedTime(new Date());
        baseMapper.updateById(supplier);
    }
}
