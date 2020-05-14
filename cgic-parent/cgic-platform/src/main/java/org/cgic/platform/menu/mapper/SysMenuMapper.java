package org.cgic.platform.menu.mapper;

import org.cgic.platform.menu.dto.SysMenu;
import org.cgic.commons.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description 菜单Mapper
 * @Author: charleyZZZZ
 * @Date: 2019/10/10 15:18
 * @Version 1.0
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu>{

    List<SysMenu> selectMenuInfoByUserId(Long userId);

    List<SysMenu> selectMenuList();
}
