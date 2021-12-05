package com.hl.xinguanservice.service;

import com.hl.common.error.SystemException;
import com.hl.common.vo.system.DepartmentVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.entity.system.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
public interface DepartmentService extends IService<Department> {

    /**
     * 所有部门
     * @return
     */
    List<DepartmentVO> findAllVO();

    /**
     * 部门列表
     * @param pageNum
     * @param pageSize
     * @param departmentVO
     * @return
     */
    PageVO<DepartmentVO> findDepartmentList(Integer pageNum, Integer pageSize, DepartmentVO departmentVO);

    /**
     * 添加部门
     * @param departmentVO
     */
    void addDept(DepartmentVO departmentVO);

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    void deleteDept(String id) throws SystemException;

    /**
     * 编辑部门
     * @param id
     * @return
     */
    DepartmentVO editDept(String id) throws SystemException;

    /**
     * 更新部门
     *
     * @return
     */
    void updateDept(String id, DepartmentVO departmentVO) throws SystemException;

    /**
     * 全部部门
     * @return
     */
    List<Department> findAllDept();
}
