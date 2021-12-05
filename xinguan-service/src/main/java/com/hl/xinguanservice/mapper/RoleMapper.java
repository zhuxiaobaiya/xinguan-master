package com.hl.xinguanservice.mapper;

import com.hl.xinguanservice.entity.system.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
