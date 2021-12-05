package com.hl.xinguanservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.common.enums.system.UserTypeEnum;
import com.hl.common.error.SystemCodeEnum;
import com.hl.common.error.SystemException;
import com.hl.common.vo.system.DepartmentVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.converter.DepartmentConverter;
import com.hl.xinguanservice.entity.system.Department;
import com.hl.xinguanservice.entity.system.User;
import com.hl.xinguanservice.mapper.DepartmentMapper;
import com.hl.xinguanservice.mapper.UserMapper;
import com.hl.xinguanservice.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {


    @Autowired
    private UserMapper userMapper;


    @Override
    public List<DepartmentVO> findAllVO() {
        List<Department> departments = baseMapper.selectList(null);
        //转vo
        List<DepartmentVO> departmentVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(departments)) {
            for (Department department : departments) {
                DepartmentVO d = new DepartmentVO();
                BeanUtils.copyProperties(department, d);
                System.out.println(d);

                d.setTotal(userMapper.selectCount(new QueryWrapper<User>()
                        .eq("department_Id",department.getId())
                        .ne("type",0)));
                departmentVOS.add(d);
            }
        }
        return departmentVOS;
    }

    /**
     * 部门列表
     *
     * @param pageNum
     * @param pageSize
     * @param departmentVO
     * @return
     */
    @Override
    public PageVO<DepartmentVO> findDepartmentList(Integer pageNum, Integer pageSize, DepartmentVO departmentVO) {
        //创建page对象
        Page<Department> deptPage = new Page<>(pageNum,pageSize);
        //构建条件
        QueryWrapper<Department> wrapper = new QueryWrapper<>();
        if (departmentVO.getName() != null && !"".equals(departmentVO.getName())) {
            wrapper.like("name",departmentVO.getName());
        }
        //转vo
        List<DepartmentVO> departmentVOS = new ArrayList<>();
        baseMapper.selectPage(deptPage,wrapper);
        List<Department> departments = deptPage.getRecords();
        if (!CollectionUtils.isEmpty(departments)) {
            for (Department department : departments) {
                DepartmentVO d = new DepartmentVO();
                BeanUtils.copyProperties(department, d);
                QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("department_id",department.getId());
                wrapper.ne("type",UserTypeEnum.SYSTEM_ADMIN.getTypeCode());
                d.setTotal(userMapper.selectCount(queryWrapper));
                departmentVOS.add(d);
            }
        }
        return new PageVO<>(deptPage.getTotal(), departmentVOS);
    }

    /**
     * 添加部门
     * @param departmentVO
     */
    @Override
    public void addDept(DepartmentVO departmentVO) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentVO,department);
        department.setCreateTime(new Date());
        department.setModifiedTime(new Date());
        baseMapper.insert(department);
    }

    /**
     * 删除部门信息
     * @param id
     */
    @Override
    public void deleteDept(String id) throws SystemException {
        Department department = baseMapper.selectById(id);
        if(department==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要删除的部门不存在");
        }
        baseMapper.deleteById(id);
    }

    /**
     * 编辑部门
     * @param id
     * @return
     */
    @Override
    public DepartmentVO editDept(String id) throws SystemException {
        Department department = baseMapper.selectById(id);
        if(department==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"编辑的部门不存在");
        }
        return DepartmentConverter.converterToDepartment(department);
    }

    /**
     * 更新部门
     * @param id
     * @param departmentVO
     */
    @Override
    public void updateDept(String id, DepartmentVO departmentVO) throws SystemException {
        Department dbDepartment = baseMapper.selectById(id);
        if(dbDepartment==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要更新的部门不存在");
        }
        Department department = new Department();
        BeanUtils.copyProperties(departmentVO,department);
        department.setId(id);
        department.setModifiedTime(new Date());
        baseMapper.updateById(department);
    }


    @Override
    public List<Department> findAllDept() {
        return baseMapper.selectList(null);
    }
}
