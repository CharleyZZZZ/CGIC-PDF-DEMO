package org.cgic.platform.menu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.cgic.commons.mapper.BaseMapper;
import org.cgic.platform.menu.dto.SysMenu;
import org.cgic.platform.menu.dto.SysMenuT;

import java.util.List;

/**
 * @Description 菜单Mapper
 * @Author: charleyZZZZ
 * @Date: 2019/10/10 15:18
 * @Version 1.0
 */
@Mapper
public interface SysMenuTMapper extends BaseMapper<SysMenuT>{

    List<SysMenuT> selectMenuInfoByUserId(Long userId);

}
