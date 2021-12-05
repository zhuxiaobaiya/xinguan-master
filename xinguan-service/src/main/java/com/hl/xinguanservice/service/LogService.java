package com.hl.xinguanservice.service;

import com.hl.common.error.SystemException;
import com.hl.common.vo.system.LogVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.entity.system.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * <p>
 * 操作日志表 服务类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-24
 */
public interface LogService extends IService<Log> {

    /**
     * 异步保存操作日志
     */
    @Async("CodeAsyncThreadPool")
    void saveLog(Log log);


    /**
     * 删除登入日志
     * @param id
     */
    void delete(Long id) throws SystemException;


    /**
     * 登入日志列表
     * @param pageNum
     * @param pageSize
     * @param logVO
     * @return
     */
    PageVO<LogVO> findLogList(Integer pageNum, Integer pageSize, LogVO logVO);

    /**
     * 批量删除登入日志
     * @param list
     */
    void batchDelete(List<Long> list) throws SystemException;}
