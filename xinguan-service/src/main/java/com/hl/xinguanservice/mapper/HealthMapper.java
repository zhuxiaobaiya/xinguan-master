package com.hl.xinguanservice.mapper;

import com.hl.xinguanservice.entity.business.Health;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangliang
 * @since 2021-11-28
 */
public interface HealthMapper extends BaseMapper<Health> {
    /**
     * 今日是否打卡
     * @param id
     * @return
     */
    @Select("select * from biz_health where create_time < (CURDATE()+1) " +
            " and create_time>CURDATE() and user_id=#{id}")
    List<Health> isReport(@Param("id") String id);
}
