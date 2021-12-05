package com.hl.xinguanservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.common.error.BusinessCodeEnum;
import com.hl.common.error.BusinessException;
import com.hl.common.vo.business.HealthVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.entity.business.Health;
import com.hl.xinguanservice.entity.system.User;
import com.hl.xinguanservice.mapper.HealthMapper;
import com.hl.xinguanservice.service.HealthService;
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
 * @since 2021-11-28
 */
@Service
public class HealthServiceImpl extends ServiceImpl<HealthMapper, Health> implements HealthService {


    /**
     * 健康上报
     * @param healthVO
     */
    @Override
    public void report(HealthVO healthVO) throws BusinessException {
        Health report = isReport(healthVO.getUserId());
        if(report!=null) {
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "今日已经打卡,无法重复打卡！");
        }
        Health health = new Health();
        BeanUtils.copyProperties(healthVO,health);
        health.setCreateTime(new Date());
        baseMapper.insert(health);
    }

    /**
     * 今日是否已报备
     * @param id
     * @return
     */
    @Override
    public Health isReport(String id) {
        List<Health> health=baseMapper.isReport(id);
        if(health.size()>0){
            return  health.get(0);
        }
        return null;
    }

    /**
     * 签到历史记录
     * @return
     */
    @Override
    public PageVO<Health> history(String id, Integer pageNum, Integer pageSize) {
        //创建page对象
        Page<Health> healthPage = new Page<>(pageNum,pageSize);
        //构建条件
        QueryWrapper<Health> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        wrapper.eq("user_id",id);
        baseMapper.selectPage(healthPage,wrapper);
        return new PageVO<>(healthPage.getTotal(),healthPage.getRecords());
    }
}
