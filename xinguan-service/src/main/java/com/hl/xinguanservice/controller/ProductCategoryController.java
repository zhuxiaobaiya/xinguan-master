package com.hl.xinguanservice.controller;


import com.hl.common.annotation.ControllerEndpoint;
import com.hl.common.error.BusinessException;
import com.hl.common.response.ResponseBean;
import com.hl.common.vo.business.ProductCategoryTreeNodeVO;
import com.hl.common.vo.business.ProductCategoryVO;
import com.hl.common.vo.system.PageVO;
import com.hl.xinguanservice.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物资分类管理
 *
 * @Author huangliang
 * @Since  2021-11-26
 **/
@Api(tags = "业务模块-物资类别相关接口")
@RestController
@RequestMapping("/business/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;


    /**
     * 查询所有分类
     *
     * @return
     */
    @ApiOperation(value = "所有分类")
    @GetMapping("/findAll")
    public ResponseBean findAll() {
        List<ProductCategoryVO> productCategoryVOS = productCategoryService.findAll();
        return ResponseBean.success(productCategoryVOS);
    }


    /**
     * 物资分类列表
     *
     * @return
     */
    @ApiOperation(value = "分类列表", notes = "物资分类列表,根据物资分类名模糊查询")
    @GetMapping("/findProductCategoryList")
    public ResponseBean findProductCategoryList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            ProductCategoryVO productCategoryVO) {

        PageVO<ProductCategoryVO> departmentsList = productCategoryService.findProductCategoryList(pageNum, pageSize, productCategoryVO);
        return ResponseBean.success(departmentsList);
    }

    /**
     * 添加物资分类
     *
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "物资分类添加失败", operation = "物资分类添加")
    @RequiresPermissions({"productCategory:add"})
    @ApiOperation(value = "添加分类")
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated ProductCategoryVO productCategoryVO) {
        productCategoryService.addProductCategory(productCategoryVO);
        return ResponseBean.success();
    }


    /**
     * 删除物资分类
     *
     * @param id
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "物资分类删除失败", operation = "物资分类删除")
    @ApiOperation(value = "删除分类")
    @RequiresPermissions({"productCategory:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable String id) throws BusinessException {
        productCategoryService.deleteProductCategory(id);
        return ResponseBean.success();
    }

    /**
     * 编辑物资分类
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑分类")
    @RequiresPermissions({"productCategory:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable String id) {
        ProductCategoryVO productCategoryVO = productCategoryService.editProductCategory(id);
        return ResponseBean.success(productCategoryVO);
    }

    /**
     * 更新物资分类
     *
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "物资分类更新失败", operation = "物资分类更新")
    @ApiOperation(value = "更新分类")
    @RequiresPermissions({"productCategory:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable String id, @RequestBody @Validated ProductCategoryVO productCategoryVO) {
        productCategoryService.updateProductCategory(id, productCategoryVO);
        return ResponseBean.success();
    }

    /**
     * 分类树形结构(分页)
     *
     * @return
     */
    @ApiOperation(value = "分类树形结构")
    @GetMapping("/categoryTree")
    public ResponseBean categoryTree(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                     @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        PageVO<ProductCategoryTreeNodeVO> pageVO = productCategoryService.categoryTree(pageNum, pageSize);
        return ResponseBean.success(pageVO);
    }

    /**
     * 获取父级分类树：2级树
     *
     * @return
     */
    @ApiOperation(value = "父级分类树")
    @GetMapping("/getParentCategoryTree")
    public ResponseBean getParentCategoryTree() {
        List<ProductCategoryTreeNodeVO> parentTree = productCategoryService.getParentCategoryTree();
        return ResponseBean.success(parentTree);
    }
}

