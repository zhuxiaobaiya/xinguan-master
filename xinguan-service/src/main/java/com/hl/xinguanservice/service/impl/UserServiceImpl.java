package com.hl.xinguanservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hl.common.enums.system.UserStatusEnum;
import com.hl.common.enums.system.UserTypeEnum;
import com.hl.common.error.SystemCodeEnum;
import com.hl.common.error.SystemException;
import com.hl.common.utils.JwtUtils;
import com.hl.common.utils.MD5Utils;
import com.hl.common.utils.MenuTreeBuilder;
import com.hl.common.vo.system.*;
import com.hl.xinguanservice.converter.MenuConverter;
import com.hl.xinguanservice.converter.UserConverter;
import com.hl.xinguanservice.entity.respBean.ActiveUser;
import com.hl.xinguanservice.entity.system.*;
import com.hl.xinguanservice.mapper.*;
import com.hl.xinguanservice.service.DepartmentService;
import com.hl.xinguanservice.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hl.xinguanservice.shiro.JwtToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private DepartmentService departmentService;











    /**
     * 查询用户
     * @param name 用户名
     * @return
     */
    @Override
    public User findUserByName(String name) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",name);
        return baseMapper.selectOne(userQueryWrapper);
    }

    @Override
    public String login(String username, String password) throws SystemException {
        System.out.println(username);
        System.out.println(password);
        String token = null;
        User user = findUserByName(username);
        if (user != null) {
            String salt = user.getSalt();
            //秘钥为盐
            String target = MD5Utils.md5Encryption(password, salt);
            //生成Token
            token = JwtUtils.sign(username, target);
            JwtToken jwtToken = new JwtToken(token);
            try {
                SecurityUtils.getSubject().login(jwtToken);
            } catch (AuthenticationException e) {
                throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,e.getMessage());
            }
        } else {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"用户不存在");
        }
        return token;
    }

    /**
     * 查询用户角色
     * @param id 用户ID
     * @return
     */
    @Override
    public List<Role> findRolesById(String id) throws SystemException {
        User dbUser = baseMapper.selectById(id);
        if(dbUser==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该用户不存在");
        }
        List<Role> roles=new ArrayList<>();
        List<UserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id",dbUser.getId()));
        List<String> rids=new ArrayList<>();
        if(!CollectionUtils.isEmpty(userRoleList)){
            for (UserRole userRole : userRoleList) {
                rids.add(userRole.getRoleId());
            }
            if(!CollectionUtils.isEmpty(rids)){
                for (String rid : rids) {
                    Role role = roleMapper.selectById(rid);
                    if(role!=null){
                        roles.add(role);
                    }
                }
            }
        }
        return roles;
    }

    /**
     * 查询权限
     * @param roles 用户的角色
     * @return
     */
    @Override
    public List<Menu> findMenuByRoles(List<Role> roles) {
        List<Menu> menus=new ArrayList<>();
        if(!CollectionUtils.isEmpty(roles)){
            Set<String> menuIds=new HashSet<>();//存放用户的菜单id
            List<RoleMenu> roleMenus;
            for (Role role : roles) {
                //根据角色ID查询权限ID
                roleMenus= roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().eq("role_id",role.getId()));
                if(!CollectionUtils.isEmpty(roleMenus)){
                    for (RoleMenu roleMenu : roleMenus) {
                        menuIds.add(roleMenu.getMenuId());
                    }
                }
            }
            if(!CollectionUtils.isEmpty(menuIds)){
                for (String menuId : menuIds) {
                    //该用户所有的菜单
                    Menu menu = menuMapper.selectById(menuId);
                    if(menu!=null){
                        menus.add(menu);
                    }
                }
            }
        }
        return menus;
    }


    /**
     * 用户列表
     * @param userVO
     * @return
     */
    @Override
    public PageVO<UserVO> findUserList(Integer pageNum, Integer pageSize, UserVO userVO) {
        //创建page对象
        Page<User> userPage = new Page<>(pageNum,pageSize);
        //构建条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        String username = userVO.getUsername();
        String nickname = userVO.getNickname();
        String departmentId = userVO.getDepartmentId();
        Integer sex = userVO.getSex();
        String email = userVO.getEmail();
        Boolean isOk = userVO.getStatus();
        if(username!=null && !"".equals(username)){
            wrapper.like("username",username);
        }
        if(nickname!=null && !"".equals(nickname)){
            wrapper.like("nickname",nickname);
        }
        if(email!=null && !"".equals(email)){
            wrapper.like("email",email);
        }
        if(sex!=null){
            wrapper.eq("sex",sex);
        }
        if(departmentId!=null && !"".equals(departmentId)){
            wrapper.eq("department_id",departmentId);
        }
        if (isOk != null){
            if (isOk){
                wrapper.eq("status",0);
            }else {
                wrapper.eq("status",1);
            }
        }

        wrapper.ne("type",0);
        baseMapper.selectPage(userPage, wrapper);
        List<User> userList = userPage.getRecords();
        List<UserVO> userVOS = userConverter.converterToUserVOList(userList);
        return new PageVO<>(userPage.getTotal(),userVOS);
    }

    /**
     * 用户详情
     *
     * @return
     */
    @Override
    public UserInfoVO info() throws SystemException {
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        UserInfoVO userInfo = new UserInfoVO();
        userInfo.setAvatar(activeUser.getUser().getAvatar());
        userInfo.setUsername(activeUser.getUser().getUsername());
        userInfo.setUrl(activeUser.getUrls());
        userInfo.setNickname(activeUser.getUser().getNickname());
        List<String> roleNames = activeUser.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
        userInfo.setRoles(roleNames);
        userInfo.setPerms(activeUser.getPermissions());
        userInfo.setIsAdmin(activeUser.getUser().getType()== UserTypeEnum.SYSTEM_ADMIN.getTypeCode());
        DepartmentVO dept = departmentService.editDept(activeUser.getUser().getDepartmentId());
        if(dept!=null){
            userInfo.setDepartment(dept.getName());
        }
        return userInfo;
    }

    /**
     *获取菜单
     * @return
     */
    @Override
    public List<MenuNodeVO> findMenu() {
        List<Menu> menus=null;
        List<MenuNodeVO> menuNodeVOS=new ArrayList<>();
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        if(activeUser.getUser().getType()== UserTypeEnum.SYSTEM_ADMIN.getTypeCode()){
            //超级管理员
            menus=menuMapper.selectList(null);
        }else if(activeUser.getUser().getType()== UserTypeEnum.SYSTEM_USER.getTypeCode()){
            //普通系统用户
            menus= activeUser.getMenus();
        }
        if(!CollectionUtils.isEmpty(menus)){
            menuNodeVOS= MenuConverter.converterToMenuNodeVO(menus);
        }
        //构建树形菜单
        return MenuTreeBuilder.build(menuNodeVOS);
    }


    /**
     * 添加用户
     * @param userVO
     */
    @Transactional
    @Override
    public void addUser(UserVO userVO) throws SystemException {
        @NotBlank(message = "用户名不能为空") String username = userVO.getUsername();
        @NotNull(message = "部门id不能为空") String departmentId = userVO.getDepartmentId();
        Integer i = baseMapper.selectCount(new QueryWrapper<User>().eq("username", username));
        if(i!=0){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该用户名已被占用");
        }
        Department department = departmentMapper.selectById(departmentId);
        if(department==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该部门不存在");
        }
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        String salt=UUID.randomUUID().toString().substring(0,32);
        user.setPassword(MD5Utils.md5Encryption(user.getPassword(), salt));
        user.setModifiedTime(new Date());
        user.setCreateTime(new Date());
        user.setSalt(salt);
        user.setType(UserTypeEnum.SYSTEM_USER.getTypeCode());//添加的用户默认是普通用户
        user.setStatus(UserStatusEnum.AVAILABLE.getStatusCode());//添加的用户默认启用
        user.setAvatar("http://badidol.com/uploads/images/avatars/201910/24/18_1571921832_HG9E55x9NY.jpg");
        baseMapper.insert(user);
    }

    /**
     * 删除用户
     * @param id 用户ID
     */
    @Transactional
    @Override
    public void deleteById(String id) throws SystemException {
        User user = baseMapper.selectById(id);
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();

        if(user==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要删除的用户不存在");
        }

        if(user.getId().equals(activeUser.getUser().getId())){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"不能删除当前登入用户");
        }

        baseMapper.deleteById(id);
        //删除对应[用户-角色]记录
        userRoleMapper.deleteById(id);
    }

    /**
     * 更新
     * @param id
     * @param userVO
     */
    @Transactional
    @Override
    public void updateById(String id, UserEditVO userVO) throws SystemException {
        User dbUser = baseMapper.selectById(id);
        @NotBlank(message = "用户名不能为空") String username = userVO.getUsername();
        @NotNull(message = "部门不能为空") String departmentId = userVO.getDepartmentId();
        if(dbUser==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要删除的用户不存在");
        }
        Department department = departmentMapper.selectById(departmentId);
        if(department==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该部门不存在");
        }
        List<User> users = baseMapper.selectList(new QueryWrapper<User>().eq("username",username));
        if(!CollectionUtils.isEmpty(users)){
            if(!users.get(0).getId().equals(id)){
                throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该用户名已被占用");
            }
        }
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        user.setModifiedTime(new Date());
        user.setId(dbUser.getId());
        baseMapper.updateById(user);
    }

    /**
     * 编辑
     * @param id
     * @return
     */
    @Transactional
    @Override
    public UserEditVO editUser(String id) throws SystemException {
        User user = baseMapper.selectById(id);
        if(user==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要编辑的用户不存在");
        }
        UserEditVO userEditVO = new UserEditVO();
        BeanUtils.copyProperties(user,userEditVO);
        Department department = departmentMapper.selectById(user.getDepartmentId());
        if(department!=null){
            userEditVO.setDepartmentId(department.getId());
        }
        return userEditVO;
    }

    /**
     * 分配角色
     * @param id 用户id
     * @param rids 角色数组
     */
    @Override
    @Transactional
    public void assignRoles(String id, String[] rids) throws SystemException {
        //删除之前用户的所有角色
        User user = baseMapper.selectById(id);
        if(user==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"用户不存在");
        }
        //删除之前分配的
        userRoleMapper.deleteById(user.getId());
        //增加现在分配的
        if(rids.length>0){
            for (String rid : rids) {
                Role role = roleMapper.selectById(rid);
                if(role==null){
                    throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"roleId="+rid+",该角色不存在");
                }
                //判断角色状态
                if(role.getStatus()==0){
                    throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"roleName="+role.getRoleName()+",该角色已禁用");
                }
                UserRole userRole = new UserRole();
                userRole.setUserId(id);
                userRole.setRoleId(rid);
                userRoleMapper.insert(userRole);
            }
        }
    }

    /**
     * 用户拥有的角色ID
     * @param id 用户id
     * @return
     */
    @Transactional
    @Override
    public List<String> roles(String id) throws SystemException {
        User user = baseMapper.selectById(id);
        if(user==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该用户不存在");
        }
        List<UserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id",user.getId()));
        List<String> roleIds=new ArrayList<>();
        if(!org.springframework.util.CollectionUtils.isEmpty(userRoleList)){
            for (UserRole userRole : userRoleList) {
                Role role = roleMapper.selectById(userRole.getRoleId());
                if(role!=null){
                    roleIds.add(role.getId());
                }
            }
        }
        return roleIds;
    }

    @Override
    public List<User> findAll() {
        return baseMapper.selectList(null);
    }
}
