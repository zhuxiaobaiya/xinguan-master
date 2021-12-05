package com.hl.xinguanservice.service;

import com.hl.common.error.SystemException;
import com.hl.common.vo.system.MenuNodeVO;
import com.hl.common.vo.system.MenuVO;
import com.hl.xinguanservice.entity.system.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author huangliang
 * @since 2021-11-15
 */
public interface MenuService extends IService<Menu> {

    /**
     * 获取菜单树
     * @return
     */
    List<MenuNodeVO> findMenuTree();

    /**
     * 添加菜单
     * @param menuVO
     */
    Menu addMenu(MenuVO menuVO);

    /**
     * 删除节点
     * @param id
     */
    void deleteMenu(Long id) throws SystemException;

    /**
     * 所有展开菜单的ID
     * @return
     */
    List<Long> findOpenIds();

    /**
     * 编辑节点
     * @param id
     * @return
     */
    MenuVO editMenu(Long id) throws SystemException;

    /**
     * 更新节点
     * @param id
     */
    void updateMenu(Long id, MenuVO menuVO) throws SystemException;

    /**
     * 获取所有菜单
     * @return
     */
    List<Menu> findAll();
}
