package com.hl.xinguanservice.service;

import com.hl.common.error.BusinessException;
import com.hl.common.vo.business.HealthVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.entity.business.Health;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-28
 */
public interface HealthService extends IService<Health> {

    /**
     * 健康上报
     * @param healthVO
     */
    void report(HealthVO healthVO) throws BusinessException;

    /**
     * 签到记录
     * @return
     */
    PageVO<Health> history(String id, Integer pageNum, Integer pageSize);

    /**
     * 今日是否已经报备
     * @param id
     * @return
     */
    Health isReport(String id);

}
