package com.hl.xinguanservice.service;

import com.hl.common.vo.business.SupplierVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.entity.business.Supplier;
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
public interface SupplierService extends IService<Supplier> {


    /**
     * 查询所有供应商
     * @return
     */
    List<SupplierVO> findAllSupplier();

    /**
     * 供应商列表
     * @param pageNum
     * @param pageSize
     * @param supplierVO
     * @return
     */
    PageVO<SupplierVO> findSupplierList(Integer pageNum, Integer pageSize, SupplierVO supplierVO);



    /**
     * 添加供应商
     * @param supplierVO
     */
    Supplier addSupplier(SupplierVO supplierVO);

    /**
     * 删除供应商
     * @param id
     */
    void deleteSupplier(Long id);

    /**
     * 编辑供应商
     * @param id
     * @return
     */
    SupplierVO editSupplier(Long id);

    /**
     * 更新供应商
     * @param id
     * @param supplierVO
     */
    void updateSupplier(Long id, SupplierVO supplierVO);

}
