package com.hl.xinguanservice.service;

import com.hl.common.error.SystemException;
import com.hl.common.vo.system.PageVO;
import com.hl.common.vo.system.RoleVO;
import com.hl.xinguanservice.entity.system.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
public interface RoleService extends IService<Role> {
    /**
     * 查询所有的角色
     * @return
     */
    List<Role> findAll();

    /**
     * 角色列表
     * @param pageNum
     * @param pageSize
     * @param roleVO
     * @return
     */
     PageVO<RoleVO> findRoleList(Integer pageNum, Integer pageSize, RoleVO roleVO);

    /**
     * 添加角色
     * @param roleVO
     */
    void addRole(RoleVO roleVO) throws SystemException;

    /**
     * 删除角色
     * @param id
     */
    void deleteById(String id) throws SystemException;

    /**
     * 编辑角色
     * @param id
     * @return
     */
    RoleVO editRole(String id) throws SystemException;

    /**
     * 更新角色
     * @param id
     * @param roleVO
     */
    void updateRole(String id, RoleVO roleVO) throws SystemException;

    /**
     * 更新角色状态
     * @param id
     * @param status
     */
    void updateRoleStatus(String id, Boolean status) throws SystemException;

    /**
     * 角色授权
     * @param mids
     */
    void authority(String id,String[] mids) throws SystemException;

    /**
     * 查询角色拥有的菜单权限id
     * @param id
     * @return
     */
    List<String> findMenuIdsByRoleId(String id) throws SystemException;
}
