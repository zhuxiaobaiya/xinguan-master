package com.hl.xinguanservice.service;

import com.hl.common.error.BusinessException;
import com.hl.common.vo.business.OutStockDetailVO;
import com.hl.common.vo.business.OutStockVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.entity.business.OutStock;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-26
 */
public interface OutStockService extends IService<OutStock> {


    /**
     * 出库单列表
     * @param pageNum
     * @param pageSize
     * @param outStockVO
     * @return
     */
    PageVO<OutStockVO> findOutStockList(Integer pageNum, Integer pageSize, OutStockVO outStockVO);


    /**
     * 提交物资发放单
     * @param outStockVO
     */
    void addOutStock(OutStockVO outStockVO) throws BusinessException;

    /**
     * 移入回收站
     * @param id
     */
    void remove(Long id) throws BusinessException;

    /**
     * 删除发放单
     * @param id
     */
    void delete(Long id) throws BusinessException;

    /**
     * 恢复发放单
     * @param id
     */
    void back(Long id) throws BusinessException;

    /**
     * 发放单审核
     * @param id
     */
    void publish(Long id) throws BusinessException;

    /**
     * 发放单详情
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    OutStockDetailVO detail(Long id, Integer pageNum, Integer pageSize) throws BusinessException;

}
