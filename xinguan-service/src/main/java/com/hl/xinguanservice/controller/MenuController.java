package com.hl.xinguanservice.controller;


import com.hl.common.annotation.ControllerEndpoint;
import com.hl.common.error.SystemException;
import com.hl.common.response.ResponseBean;
import com.hl.common.vo.system.MenuNodeVO;
import com.hl.common.vo.system.MenuVO;
import com.hl.xinguanservice.entity.system.Menu;
import com.hl.xinguanservice.service.MenuService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@Api(tags = "系统模块-菜单权限相关接口")
@RequestMapping("/system/menu")
@RestController
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 加载菜单树
     *
     * @return
     */
    @ApiOperation(value = "加载菜单树", notes = "获取所有菜单树，以及展开项")
    @GetMapping("/tree")
    public ResponseBean<Map<String, Object>> tree() {
        List<MenuNodeVO> menuTree = menuService.findMenuTree();
        List<Long> ids = menuService.findOpenIds();
        Map<String, Object> map = new HashMap<>();
        map.put("tree", menuTree);
        map.put("open", ids);
        return ResponseBean.success(map);
    }

    /**
     * 新增菜单/按钮
     *
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "新增菜单/按钮失败", operation = "新增菜单/按钮")
    @ApiOperation(value = "新增菜单")
    @RequiresPermissions({"menu:add"})
    @PostMapping("/add")
    public ResponseBean<Map<String, Object>> add(@RequestBody @Validated MenuVO menuVO) {
        Menu node = menuService.addMenu(menuVO);
        Map<String, Object> map = new HashMap<>();
        map.put("id", node.getId());
        map.put("menuName", node.getMenuName());
        map.put("children", new ArrayList<>());
        map.put("icon", node.getIcon());
        return ResponseBean.success(map);
    }

    /**
     * 删除菜单/按钮
     *
     * @param id
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "删除菜单/按钮失败", operation = "删除菜单/按钮")
    @ApiOperation(value = "删除菜单", notes = "根据id删除菜单节点")
    @RequiresPermissions({"menu:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id) throws SystemException {
        menuService.deleteMenu(id);
        return ResponseBean.success();
    }

    /**
     * 菜单详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "菜单详情", notes = "根据id编辑菜单，获取菜单详情")
    @RequiresPermissions({"menu:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean<MenuVO> edit(@PathVariable Long id) throws SystemException {
        MenuVO menuVO = menuService.editMenu(id);
        return ResponseBean.success(menuVO);
    }

    /**
     * 更新菜单
     *
     * @param id
     * @param menuVO
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "更新菜单失败", operation = "更新菜单")
    @ApiOperation(value = "更新菜单", notes = "根据id更新菜单节点")
    @RequiresPermissions({"menu:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id, @RequestBody @Validated MenuVO menuVO) throws SystemException {
        menuService.updateMenu(id, menuVO);
        return ResponseBean.success();
    }

    /**
     * 导出excel
     * @param response
     */
    @ApiOperation(value = "导出excel", notes = "导出所有菜单的excel表格")
    @PostMapping("excel")
    @RequiresPermissions("menu:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败",operation = "导出菜单excel")
    public void export(HttpServletResponse response) {
        List<Menu> menus = menuService.findAll();
        ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
    }
}

