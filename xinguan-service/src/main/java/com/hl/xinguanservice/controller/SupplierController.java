package com.hl.xinguanservice.controller;


import com.hl.common.annotation.ControllerEndpoint;
import com.hl.common.response.ResponseBean;
import com.hl.common.vo.business.SupplierVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.service.SupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangliang
 * @since 2021-11-26
 */
@Api(tags = "业务模块-物资来源相关接口")
@RestController
@RequestMapping("/business/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;


    /**
     * 所有来源
     *
     * @return
     */
    @ApiOperation(value = "所有来源", notes = "所有来源列表")
    @GetMapping("/findAll")
    public ResponseBean<List<SupplierVO>> findAll() {
        List<SupplierVO> supplierVOS = supplierService.findAllSupplier();
        return ResponseBean.success(supplierVOS);
    }

    /**
     * 来源列表
     *
     * @return
     */
    @ApiOperation(value = "来源列表", notes = "来源列表,根据来源名模糊查询")
    @GetMapping("/findSupplierList")
    public ResponseBean findSupplierList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize") Integer pageSize,
                                         SupplierVO supplierVO) {
        PageVO<SupplierVO> supplierVOPageVO = supplierService.findSupplierList(pageNum, pageSize, supplierVO);
        return ResponseBean.success(supplierVOPageVO);
    }

    /**
     * 添加来源
     *
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "物资来源添加失败", operation = "物资来源添加")
    @RequiresPermissions({"supplier:add"})
    @ApiOperation(value = "添加来源")
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated SupplierVO supplierVO) {
        supplierService.addSupplier(supplierVO);
        return ResponseBean.success();
    }

    /**
     * 删除来源
     *
     * @param id
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "物资来源删除失败", operation = "物资来源删除")
    @ApiOperation(value = "删除来源", notes = "删除来源信息")
    @RequiresPermissions({"supplier:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseBean.success();
    }

    /**
     * 编辑来源
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑来源", notes = "编辑来源信息")
    @RequiresPermissions({"supplier:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean<SupplierVO> edit(@PathVariable Long id) {
        SupplierVO supplierVO = supplierService.editSupplier(id);
        return ResponseBean.success(supplierVO);
    }

    /**
     * 更新来源
     *
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "物资来源更新失败", operation = "物资来源更新")
    @ApiOperation(value = "更新来源", notes = "更新来源信息")
    @RequiresPermissions({"supplier:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id, @RequestBody @Validated SupplierVO supplierVO) {
        supplierService.updateSupplier(id, supplierVO);
        return ResponseBean.success();
    }
}

