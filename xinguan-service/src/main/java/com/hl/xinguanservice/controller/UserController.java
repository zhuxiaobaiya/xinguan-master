package com.hl.xinguanservice.controller;


import com.hl.common.annotation.ControllerEndpoint;
import com.hl.common.error.SystemException;
import com.hl.common.response.ResponseBean;
import com.hl.common.vo.system.*;
import com.hl.xinguanservice.converter.RoleConverter;
import com.hl.xinguanservice.entity.dto.UserLoginDTO;
import com.hl.xinguanservice.entity.system.Role;
import com.hl.xinguanservice.entity.system.User;
import com.hl.xinguanservice.service.LoginLogService;
import com.hl.xinguanservice.service.RoleService;
import com.hl.xinguanservice.service.UserService;

import com.wuwenze.poi.ExcelKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@RestController
@RequestMapping("/system/user")
@Validated
@Api(tags = "系统模块-用户相关接口")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LoginLogService loginLogService;
    /**
     * 用户登入
     *
     * @return
     */
    @ApiOperation(value = "用户登入", notes = "接收参数用户名和密码,登入成功后,返回JWTToken")
    @PostMapping("/login")
    public ResponseBean<String> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) throws SystemException {
        String token=userService.login(userLoginDTO.getUsername(),userLoginDTO.getPassword());
        loginLogService.add(request);
        return ResponseBean.success(token);
    }

    /**
     * 用户列表
     *
     * @return
     */
    @ApiOperation(value = "用户列表", notes = "模糊查询用户列表")
    @GetMapping("/findUserList")
    public ResponseBean<PageVO<UserVO>> findUserList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "6") Integer pageSize,
                                                     UserVO userVO) {
        PageVO<UserVO> userList = userService.findUserList(pageNum, pageSize, userVO);
        return ResponseBean.success(userList);
    }

    /**
     * 用户信息
     *
     * @return
     */
    @ApiOperation(value = "用户信息", notes = "用户登入信息")
    @GetMapping("/info")
    public ResponseBean<UserInfoVO> info() throws SystemException {
        UserInfoVO userInfoVO =userService.info();
        return ResponseBean.success(userInfoVO);
    }

    /**
     * 加载菜单
     *
     * @return
     */
    @ApiOperation(value = "加载菜单", notes = "用户登入后,根据角色加载菜单树")
    @GetMapping("/findMenu")
    public ResponseBean<List<MenuNodeVO>> findMenu() {
        List<MenuNodeVO> menuTreeVOS = userService.findMenu();
        return ResponseBean.success(menuTreeVOS);
    }


    /**
     * 添加用户信息
     * @param userVO
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "添加用户失败", operation = "添加用户")
    @ApiOperation(value = "添加用户", notes = "添加用户信息")
    @RequiresPermissions({"user:add"})
    @PostMapping("/add")
    public ResponseBean addUser(@RequestBody @Validated UserVO userVO) throws SystemException {
        userService.addUser(userVO);
        return ResponseBean.success();
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "删除用户失败", operation = "删除用户")
    @RequiresPermissions({"user:delete"})
    @ApiOperation(value = "删除用户", notes = "删除用户信息，根据用户ID")
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable String id) throws SystemException {
        userService.deleteById(id);
        return ResponseBean.success();
    }

    /**
     * 更新用户
     *
     * @param id
     * @param userEditVO
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "更新用户失败", operation = "更新用户")
    @ApiOperation(value = "更新用户", notes = "更新用户信息")
    @RequiresPermissions({"user:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable String id, @RequestBody @Validated UserEditVO userEditVO) throws SystemException {
        userService.updateById(id, userEditVO);
        return ResponseBean.success();
    }

    /**
     * 编辑用户
     * @param id
     * @return
     */
    @ApiOperation(value = "编辑用户", notes = "获取用户的详情，编辑用户信息")
    @RequiresPermissions({"user:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean<UserEditVO> editUser(@PathVariable String id) throws SystemException {
        UserEditVO userVO = userService.editUser(id);
        return ResponseBean.success(userVO);
    }

    /**
     * 分配角色
     *
     * @param id
     * @param rids
     * @return
     */
    @ControllerEndpoint(exceptionMessage = "分配角色失败", operation = "分配角色")
    @ApiOperation(value = "分配角色", notes = "角色分配给用户")
    @RequiresPermissions({"user:assign"})
    @PostMapping("/{id}/assignRoles")
    public ResponseBean assignRoles(@PathVariable String id, @RequestBody String[] rids) throws SystemException {
        userService.assignRoles(id, rids);
        return ResponseBean.success();
    }

    /**
     * 用户角色信息
     * @param id
     * @return
     */
    @ApiOperation(value = "已有角色", notes = "根据用户id，获取用户已经拥有的角色")
    @GetMapping("/{id}/roles")
    public ResponseBean<Map<String, Object>> roles(@PathVariable String id) throws SystemException {
        List<String> values = userService.roles(id);
        List<Role> list = roleService.findAll();
        //转成前端需要的角色Item
        List<RoleTransferItemVO> items = RoleConverter.converterToRoleTransferItem(list);
        Map<String, Object> map = new HashMap<>();
        map.put("roles", items);
        map.put("values", values);
        return ResponseBean.success(map);
    }

    /**
     * 导出excel
     * @param response
     */
    @ApiOperation(value = "导出excel", notes = "导出所有用户的excel表格")
    @PostMapping("/excel")
    @RequiresPermissions("user:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败",operation = "导出用户excel")
    public void export(HttpServletResponse response) {
        List<User> users = userService.findAll();
        ExcelKit.$Export(User.class, response).downXlsx(users, false);
    }
}


