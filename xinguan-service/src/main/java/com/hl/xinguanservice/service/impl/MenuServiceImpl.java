package com.hl.xinguanservice.service.impl;

import com.hl.common.error.SystemCodeEnum;
import com.hl.common.error.SystemException;
import com.hl.common.utils.MenuTreeBuilder;
import com.hl.common.vo.system.MenuNodeVO;
import com.hl.common.vo.system.MenuVO;
import com.hl.xinguanservice.converter.MenuConverter;
import com.hl.xinguanservice.entity.system.Menu;
import com.hl.xinguanservice.mapper.MenuMapper;
import com.hl.xinguanservice.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {


    /**
     * 加载菜单树（按钮和菜单）
     *
     * @return
     */
    @Override
    public List<MenuNodeVO> findMenuTree() {
        List<Menu> menus = baseMapper.selectList(null);
        List<MenuNodeVO> menuNodeVOS = MenuConverter.converterToALLMenuNodeVO(menus);
        return MenuTreeBuilder.build(menuNodeVOS);
    }

    /**
     * 添加菜单
     *
     * @param menuVO
     */
    @Override
    public Menu addMenu(MenuVO menuVO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVO,menu);
        menu.setCreateTime(new Date());
        menu.setModifiedTime(new Date());
        menu.setAvailable(menuVO.isDisabled()?0:1);
        baseMapper.insert(menu);
        return menu;
    }

    /**
     * 删除菜单
     * @param id
     */
    @Override
    public void deleteMenu(Long id) throws SystemException {
        Menu menu = baseMapper.selectById(id);
        if(menu==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要删除的菜单不存在");
        }
        baseMapper.deleteById(id);
    }




    /**
     * 获取展开项
     *
     * @return
     */
    @Override
    public List<Long> findOpenIds() {
        List<Long> ids=new ArrayList<>();
        List<Menu> menus = baseMapper.selectList(null);
        if(!CollectionUtils.isEmpty(menus)){
            for (Menu menu : menus) {
                if(menu.getOpen()==1){
                    ids.add(menu.getId());
                }
            }
        }
        return ids;
    }

    /**
     * 编辑菜单
     * @param id
     * @return
     */
    @Override
    public MenuVO editMenu(Long id) throws SystemException {
        Menu menu = baseMapper.selectById(id);
        if(menu==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"该编辑的菜单不存在");
        }
        return MenuConverter.converterToMenuVO(menu);
    }

    /**
     * 更新菜单
     * @param id
     * @param menuVO
     */
    @Override
    public void updateMenu(Long id, MenuVO menuVO) throws SystemException {
        Menu dbMenu = baseMapper.selectById(id);
        if(dbMenu==null){
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR,"要更新的菜单不存在");
        }
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVO,menu);
        menu.setId(id);
        menu.setAvailable(menuVO.isDisabled()?0:1);
        menu.setModifiedTime(new Date());
        baseMapper.updateById(menu);
    }

    /**
     * 获取所有菜单
     * @return
     */
    @Override
    public List<Menu> findAll() {
        return baseMapper.selectList(null);
    }


}
