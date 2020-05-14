package org.cgic.platform.menu.service;

import org.cgic.platform.menu.dto.SysMenuTDTO;
import org.cgic.platform.menu.exception.SysMenuException;

import java.util.List;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/10/11 16:29
 * @Version 1.0
 */
public interface SysMenuTService {

    /**
     * 通过userId获取权限菜单
     *
     * @param userId
     * @return
     * @throws SysMenuException
     */
    List<SysMenuTDTO> getMenuInfoByUserId(Long userId) throws SysMenuException;

}
