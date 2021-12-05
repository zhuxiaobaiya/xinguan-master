package com.hl.xinguanservice.converter;

import com.hl.common.vo.system.DepartmentVO;
import com.hl.xinguanservice.entity.system.Department;
import org.springframework.beans.BeanUtils;

/**
 * @Author huangliang
 * @Date 2021/11/15 22:33
 * @Version 1.0
 * @Description
 */
public class DepartmentConverter {


    /**
     * 转vo
     * @return
     */
    public static DepartmentVO converterToDepartment(Department department){
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(department,departmentVO);
        return departmentVO;
    }
}
