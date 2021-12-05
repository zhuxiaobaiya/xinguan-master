package com.hl.xinguanservice.service;

import com.hl.common.error.SystemException;
import com.hl.common.vo.system.*;
import com.hl.xinguanservice.entity.system.*;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     * @return
     */
    User findUserByName(String name);


    /**
     * 用户登入
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password) throws SystemException;


    /**
     * 查询用户角色
     *
     * @param id 用户id
     * @return
     */
    List<Role> findRolesById(String id) throws  SystemException;

    /**
     * 根据用户角色查询用户的菜单
     * 菜单: menu+button
     *
     * @param roles 用户的角色
     * @return
     */
    List<Menu> findMenuByRoles(List<Role> roles);

    /**
     * 用户详情
     *
     * @return
     */
    UserInfoVO info() throws SystemException;

    /**
     * 加载菜单
     *
     * @return
     */
    List<MenuNodeVO> findMenu();

    /**
     * 用户列表
     * @param userVO
     * @return
     */
    PageVO<UserVO> findUserList(Integer pageNum, Integer pageSize, UserVO userVO);


    /**
     * 添加用户
     * @param userVO
     */
    void addUser(UserVO userVO) throws SystemException;

    /**
     * 删除用户
     *
     * @param id
     */
    void deleteById(String id) throws SystemException;

    /**
     * 更新用户
     *
     * @param id
     * @param userVO
     */
    void updateById(String id, UserEditVO userVO) throws SystemException;

    /**
     * 编辑用户
     *
     * @param id
     * @return
     */
    UserEditVO editUser(String id) throws SystemException;

    /**
     * 分配角色
     *
     * @param id
     * @param rids
     */
    void assignRoles(String id, String[] rids) throws SystemException;


    /**
     * 已拥有的角色ids
     *
     * @param id 用户id
     * @return
     */
    List<String> roles(String id) throws SystemException;

    /**
     * 所有用户
     *
     * @return
     */
    List<User> findAll();
}
