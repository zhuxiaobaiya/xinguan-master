package com.hl.xinguanservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.common.enums.system.RoleStatusEnum;
import com.hl.common.error.SystemCodeEnum;
import com.hl.common.error.SystemException;
import com.hl.common.vo.system.PageVO;
import com.hl.common.vo.system.RoleVO;
import com.hl.xinguanservice.converter.RoleConverter;
import com.hl.xinguanservice.entity.system.Menu;
import com.hl.xinguanservice.entity.system.Role;
import com.hl.xinguanservice.entity.system.RoleMenu;
import com.hl.xinguanservice.entity.system.User;
import com.hl.xinguanservice.mapper.MenuMapper;
import com.hl.xinguanservice.mapper.RoleMapper;
import com.hl.xinguanservice.mapper.RoleMenuMapper;
import com.hl.xinguanservice.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;



    /**
     * 查询所有的角色
     * @return
     */
    @Override
    public List<Role> findAll() {
        return baseMapper.selectList(null);
    }

    /**
     * 角色列表
     * @param pageNum
     * @param pageSize
     * @param roleVO
     * @return
     */
    @Override
    public PageVO<RoleVO> findRoleList(Integer pageNum, Integer pageSize, RoleVO roleVO) {
        //创建page对象
        Page<Role> rolePage = new Page<>(pageNum,pageSize);
        //构建条件
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        String roleName = roleVO.getRoleName();
        if (roleName!=null&&!"".equals(roleName)){
            wrapper.like("role_name",roleName);
        }

        baseMapper.selectPage(rolePage,wrapper);
        List<Role> roles = rolePage.getRecords();
        List<RoleVO> roleVOS= RoleConverter.converterToRoleVOList(roles);
        return new PageVO<>(rolePage.getTotal(),roleVOS);
    }

    /**
     * 添加角色
     * @param roleVO
     */
    @Override
    public void addRole(RoleVO roleVO) throws SystemException {
        @NotBlank(message = "角色名必填") String roleName = roleVO.getRoleName();
        int i = baseMapper.selectCount(new QueryWrapper<Role>().eq("role_name",roleName));
        if(i!=0){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该角色名已被占用");
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleVO,role);
        role.setCreateTime(new Date());
        role.setModifiedTime(new Date());
        role.setStatus(RoleStatusEnum.AVAILABLE.getStatusCode());//默认启用添加的角色
        baseMapper.insert(role);
    }

    /**
     * 删除角色
     * @param id
     */
    @Transactional
    @Override
    public void deleteById(String id) throws SystemException {
        Role role = baseMapper.selectById(id);
        if(role==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要删除的角色不存在");
        }
        baseMapper.deleteById(id);
        //删除对应的[角色-菜单]记录
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id",id);
        roleMenuMapper.delete(roleMenuQueryWrapper);
    }

    /**
     * 编辑角色信息
     * @param id
     * @return
     */
    @Override
    public RoleVO editRole(String id) throws SystemException {
        Role role = baseMapper.selectById(id);
        if(role==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"编辑的角色不存在");
        }
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(role,roleVO);
        return roleVO;
    }

    /**
     * 更新角色信息
     * @param id
     * @param roleVO
     */
    @Override
    public void updateRole(String id, RoleVO roleVO) throws SystemException {
        @NotBlank(message = "角色名必填") String roleName = roleVO.getRoleName();
        Role dbRole = baseMapper.selectById(id);
        if(dbRole==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要更新的角色不存在");
        }
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("role_name",roleName);
        List<Role> roles = baseMapper.selectList(wrapper);
        if(!CollectionUtils.isEmpty(roles)){
            Role role = roles.get(0);
            if(!role.getId().equals(id)){
                throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该角色名已被占用");
            }
        }
        Role role = new Role();
        BeanUtils.copyProperties(roleVO,role);
        role.setModifiedTime(new Date());
        baseMapper.updateById(role);
    }

    /**
     * 角色状态
     * @param id
     * @param status
     */
    @Override
    public void updateRoleStatus(String id, Boolean status) throws SystemException {
        Role role = baseMapper.selectById(id);
        if(role==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"角色不存在");
        }
        Role t = new Role();
        t.setId(id);
        t.setStatus(status ? RoleStatusEnum.DISABLE.getStatusCode()
                           : RoleStatusEnum.AVAILABLE.getStatusCode());
        baseMapper.updateById(t);
    }

    /**
     * 角色授权
     * @param id
     * @param mids
     */
    @Transactional
    @Override
    public void authority(String id,String[] mids) throws SystemException {
        Role role = baseMapper.selectById(id);
        if(role==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该角色不存在");
        }
        //先删除原来的权限
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id",id);
        roleMenuMapper.delete(roleMenuQueryWrapper);
        //增加现在分配的角色
        if(mids.length>0){
            for (String mid : mids) {
                Menu menu = menuMapper.selectById(mid);
                if(menu==null){
                    throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"menuId="+mid+",菜单权限不存在");
                }else {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(id);
                    roleMenu.setMenuId(mid);
                    roleMenuMapper.insert(roleMenu);
                }
            }
        }
    }

    /**
     * 获取角色已有权限id
     * @param id
     * @return
     */
    @Override
    public List<String> findMenuIdsByRoleId(String id) throws SystemException {
        Role role = baseMapper.selectById(id);
        if(role==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该角色已不存在");
        }
        List<String> ids=new ArrayList<>();
        QueryWrapper<RoleMenu> roleMenuQueryWrapper = new QueryWrapper<>();
        roleMenuQueryWrapper.eq("role_id",id);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(roleMenuQueryWrapper);
        if(!CollectionUtils.isEmpty(roleMenus)){
            for (RoleMenu roleMenu : roleMenus) {
                ids.add(roleMenu.getMenuId());
            }
        }
        return ids;
    }
}
