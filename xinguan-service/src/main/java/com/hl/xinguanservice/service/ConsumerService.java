package com.hl.xinguanservice.service;

import com.hl.common.vo.business.ConsumerVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.entity.business.Consumer;
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
public interface ConsumerService extends IService<Consumer> {


    /**
     * 查询所有物资去向
     * @return
     */
    List<ConsumerVO> findAll();


    /**
     * 物资去向列表
     * @param pageNum
     * @param pageSize
     * @param consumerVO
     * @return
     */
    PageVO<ConsumerVO> findConsumerList(Integer pageNum, Integer pageSize, ConsumerVO consumerVO);

    /**
     * 添加物资去向
     * @param consumerVO
     */
    Consumer addConsumer(ConsumerVO consumerVO);

    /**
     * 删除物资去向
     * @param id
     */
    void deleteConsumer(Long id);

    /**
     * 编辑物资去向
     * @param id
     * @return
     */
    ConsumerVO editConsumer(Long id);

    /**
     * 更新物资去向
     * @param id
     * @param consumerVO
     */
    void updateConsumer(Long id, ConsumerVO consumerVO);


}
