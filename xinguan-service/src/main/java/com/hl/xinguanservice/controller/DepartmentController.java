package com.hl.xinguanservice.controller;


import com.hl.common.annotation.ControllerEndpoint;
import com.hl.common.error.SystemException;
import com.hl.common.response.ResponseBean;
import com.hl.common.vo.system.DepartmentVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.entity.system.Department;
import com.hl.xinguanservice.service.DepartmentService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@Api(tags = "系统模块-部门相关接口")
@RestController
@RequestMapping("/system/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    /**
     * 所有部门
     *
     * @return
     */
    @ApiOperation(value = "所有部门")
    @GetMapping("/findAll")
    public ResponseBean<List<DepartmentVO>> findAll() {
        List<DepartmentVO> departmentVOS = departmentService.findAllVO();
        return ResponseBean.success(departmentVOS);
    }

    /**
     * 部门列表
     *
     * @return
     */
    @ApiOperation(value = "部门列表", notes = "部门列表,根据部门名模糊查询")
    @GetMapping("/findDepartmentList")
    public ResponseBean<PageVO<DepartmentVO>> findDepartmentList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(value = "pageSize") Integer pageSize,
                                                                 DepartmentVO departmentVO) {
        PageVO<DepartmentVO> departmentsList = departmentService.findDepartmentList(pageNum, pageSize, departmentVO);
        return ResponseBean.success(departmentsList);
    }

    /**
     * 添加部门
     *
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "添加部门失败", operation = "添加部门")
    @RequiresPermissions({"department:add"})
    @ApiOperation(value = "添加部门")
    @PostMapping("/add")
    public ResponseBean addz(@RequestBody @Validated DepartmentVO departmentVO) {
        departmentService.addDept(departmentVO);
        return ResponseBean.success();
    }

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "删除部门失败", operation = "删除部门")
    @ApiOperation(value = "删除部门")
    @RequiresPermissions({"department:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable String id) throws SystemException {
        departmentService.deleteDept(id);
        return ResponseBean.success();
    }

    /**
     * 编辑部门
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑部门")
    @RequiresPermissions({"department:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable String id) throws SystemException {
        DepartmentVO departmentVO = departmentService.editDept(id);
        return ResponseBean.success(departmentVO);
    }

    /**
     * 更新部门
     *
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "更新部门失败", operation = "更新部门")
    @ApiOperation(value = "更新部门")
    @RequiresPermissions({"department:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable String id, @RequestBody @Validated DepartmentVO departmentVO) throws SystemException {
        departmentService.updateDept(id, departmentVO);
        return ResponseBean.success();
    }

    /**
     * 导出excel
     * @param response
     */
    @ApiOperation(value = "导出excel", notes = "导出所有部门的excel表格")
    @PostMapping("/excel")
    @RequiresPermissions("department:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败",operation = "导出部门excel")
    public void export(HttpServletResponse response) {
        List<Department> departments = departmentService.findAllDept();
        ExcelKit.$Export(Department.class, response).downXlsx(departments, false);
    }
}

