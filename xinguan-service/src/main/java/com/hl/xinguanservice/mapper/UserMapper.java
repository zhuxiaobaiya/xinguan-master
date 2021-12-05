package com.hl.xinguanservice.mapper;

import com.hl.xinguanservice.entity.system.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
