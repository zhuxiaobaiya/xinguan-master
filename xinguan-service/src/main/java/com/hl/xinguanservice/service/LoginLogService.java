package com.hl.xinguanservice.service;

import com.hl.common.error.SystemException;
import com.hl.common.vo.system.LoginLogVO;
import com.hl.common.vo.system.PageVO;
import com.hl.common.vo.system.UserVO;
import com.hl.xinguanservice.entity.system.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录日志表 服务类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-22
 */
public interface LoginLogService extends IService<LoginLog> {


    /**
     * 登入日志列表
     * @param pageNum
     * @param pageSize
     * @param loginLogVO
     * @return
     */
    PageVO<LoginLogVO> findLoginLogList(Integer pageNum, Integer pageSize, LoginLogVO loginLogVO);

    /**
     * 添加登入日志
     * @param request
     */
    void add(HttpServletRequest request);

    /**
     * 删除登入日志
     * @param id
     */
    void delete(Long id) throws SystemException;

    /**
     * 批量删除登入日志
     * @param list
     */
    void batchDelete(List<Long> list) throws SystemException;


    /**
     * 用户登入报表
     * @param userVO
     * @return
     */
    List<Map<String, Object>> loginReport(UserVO userVO);
}
