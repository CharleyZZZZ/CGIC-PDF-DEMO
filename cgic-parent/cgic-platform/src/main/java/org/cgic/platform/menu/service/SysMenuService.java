package org.cgic.platform.menu.service;

import org.cgic.platform.menu.dto.SysMenu;
import org.cgic.platform.menu.dto.SysMenuDTO;
import org.cgic.platform.menu.exception.SysMenuException;

import java.util.List;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/10/11 16:29
 * @Version 1.0
 */
public interface SysMenuService {

    /**
     * 获取菜单信息
     * @param menuId
     * @return
     */
    SysMenu getMenu(Long menuId);

    /**
     * 根据用户id（userId）
     * 获取权限下的菜单信息
     * @param userId
     * @return
     */
    List<SysMenuDTO> getMenuInfoByUserId(Long userId) throws SysMenuException;

    /**
     * 分页获取菜单数据
     * @return
     * @param page
     * @param pageSize
     */
    List<SysMenu> getMenuList(int page, int pageSize);
}
