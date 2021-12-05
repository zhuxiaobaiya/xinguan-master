package com.hl.xinguanservice.service;

import com.hl.common.error.BusinessException;
import com.hl.common.vo.business.InStockDetailVO;
import com.hl.common.vo.business.InStockVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.entity.business.InStock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-26
 */
public interface InStockService extends IService<InStock> {
    /**
     * 入库单列表
     * @param pageNum
     * @param pageSize
     * @param inStockVO
     * @return
     */
    PageVO<InStockVO> findInStockList(Integer pageNum, Integer pageSize, InStockVO inStockVO);


    /**
     * 物资入库
     * @param inStockVO
     */
    void addIntoStock(InStockVO inStockVO) throws BusinessException;

    /**
     * 入库审核
     * @param id
     */
    void publish(Long id) throws BusinessException;

    /**
     * 入库单明细
     * @param id
     * @return
     */
    InStockDetailVO detail(Long id, int pageNo, int pageSize) throws BusinessException;

    /**
     * 删除入库单
     * @param id
     */
    void delete(Long id) throws BusinessException;

    /**
     * 还原从回收站中
     * @param id
     */
    void back(Long id) throws BusinessException;


    /**
     * 移入回收站
     * @param id
     */
    void remove(Long id) throws BusinessException;

}
