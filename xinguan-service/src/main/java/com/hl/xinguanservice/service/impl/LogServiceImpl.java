package com.hl.xinguanservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.common.error.SystemCodeEnum;
import com.hl.common.error.SystemException;
import com.hl.common.vo.system.LogVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.entity.system.Log;
import com.hl.xinguanservice.entity.system.LoginLog;
import com.hl.xinguanservice.entity.system.User;
import com.hl.xinguanservice.mapper.LogMapper;
import com.hl.xinguanservice.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-24
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {


    /**
     * 保存登入日志
     * @param log
     */
    @Override
    public void saveLog(Log log) {
        baseMapper.insert(log);
    }



    /**
     * 删除操作日志
     * @param id
     */
    @Override
    public void delete(Long id) throws SystemException {
        Log log = baseMapper.selectById(id);
        if(log==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要删除的操作日志不存在");
        }
        baseMapper.deleteById(id);
    }


    @Override
    public PageVO<LogVO> findLogList(Integer pageNum, Integer pageSize, LogVO logVO) {
        //创建page对象
        Page<Log> logVOPage = new Page<>(pageNum,pageSize);
        //构建条件
        QueryWrapper<Log> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        if(logVO.getLocation()!=null&&!"".equals(logVO.getLocation())){
            wrapper.like("location",logVO.getLocation());
        }
        if(logVO.getIp()!=null&&!"".equals(logVO.getIp())){
            wrapper.like("ip",logVO.getIp());
        }
        if(logVO.getUsername()!=null&&!"".equals(logVO.getUsername())){
            wrapper.like("username",logVO.getUsername());
        }
        baseMapper.selectPage(logVOPage,wrapper);
        List<Log> loginLogs = logVOPage.getRecords();
        List<LogVO> logVOS=new ArrayList<>();
        if(!CollectionUtils.isEmpty(loginLogs)){
            for (Log loginLog : loginLogs) {
                LogVO logVO1 = new LogVO();
                BeanUtils.copyProperties(loginLog,logVO1);
                logVOS.add(logVO1);
            }
        }

        return new PageVO<>(logVOPage.getTotal(),logVOS);
    }

    /**
     * 批量删除
     * @param list
     */
    @Override
    public void batchDelete(List<Long> list) throws SystemException {
        for (Long id : list) {
            Log log = baseMapper.selectById(id);
            if(log==null){
                throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"id="+id+",操作日志不存在");
            }
            delete(id);
        }
    }
}
