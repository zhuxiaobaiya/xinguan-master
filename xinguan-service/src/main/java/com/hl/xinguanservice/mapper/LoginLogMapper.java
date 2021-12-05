package com.hl.xinguanservice.mapper;

import com.hl.common.vo.system.UserVO;
import com.hl.xinguanservice.entity.system.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录日志表 Mapper 接口
 * </p>
 *
 * @author huangliang
 * @since 2021-11-22
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 用户登入报表
     * @param userVO
     * @return
     */
    List<Map<String,Object>> userLoginReport(UserVO userVO);
}
