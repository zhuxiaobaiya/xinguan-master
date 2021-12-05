package com.hl.xinguanservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.common.vo.business.ConsumerVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.converter.ConsumerConverter;
import com.hl.xinguanservice.entity.business.Consumer;
import com.hl.xinguanservice.entity.system.User;
import com.hl.xinguanservice.mapper.ConsumerMapper;
import com.hl.xinguanservice.service.ConsumerService;
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
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer> implements ConsumerService {




    /**
     * 查询所有
     * @return
     */
    @Override
    public List<ConsumerVO> findAll() {
        List<Consumer> consumers = baseMapper.selectList(null);
        return ConsumerConverter.converterToVOList(consumers);
    }


    /**
     * 供应商列表
     * @param pageNum
     * @param pageSize
     * @param consumerVO
     * @return
     */
    @Override
    public PageVO<ConsumerVO> findConsumerList(Integer pageNum, Integer pageSize, ConsumerVO consumerVO) {
        //创建page对象
        Page<Consumer> consumerPage = new Page<>(pageNum,pageSize);
        //构建条件
        QueryWrapper<Consumer> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        if (consumerVO.getName() != null && !"".equals(consumerVO.getName())) {
            wrapper.like("name",consumerVO.getName());
        }
        if (consumerVO.getAddress() != null && !"".equals(consumerVO.getAddress())) {
            wrapper.like("address",consumerVO.getAddress());
        }
        if (consumerVO.getContact() != null && !"".equals(consumerVO.getContact())) {
            wrapper.like("contact",consumerVO.getContact());
        }
        baseMapper.selectPage(consumerPage,wrapper);
        List<Consumer> consumers = consumerPage.getRecords();
        List<ConsumerVO> categoryVOS= ConsumerConverter.converterToVOList(consumers);
        return new PageVO<>(consumerPage.getTotal(), categoryVOS);
    }

    /**
     * 添加供应商
     * @param ConsumerVO
     */
    @Override
    public Consumer addConsumer(ConsumerVO ConsumerVO) {
        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(ConsumerVO,consumer);
        consumer.setCreateTime(new Date());
        consumer.setModifiedTime(new Date());
        baseMapper.insert(consumer);
        return consumer;
    }

    /**
     * 删除供应商
     * @param id
     */
    @Override
    public void deleteConsumer(Long id) {
        baseMapper.deleteById(id);
    }

    /**
     * 编辑供应商
     * @param id
     * @return
     */
    @Override
    public ConsumerVO editConsumer(Long id) {
        Consumer consumer = baseMapper.selectById(id);
        return  ConsumerConverter.converterToConsumerVO(consumer);
    }

    /**
     * 更新供应商
     * @param id
     * @param ConsumerVO
     */
    @Override
    public void updateConsumer(Long id, ConsumerVO ConsumerVO) {
        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(ConsumerVO,consumer);
        consumer.setModifiedTime(new Date());
        baseMapper.updateById(consumer);
    }

}
