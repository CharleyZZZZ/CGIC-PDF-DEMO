package org.cgic.platform.menu.service.impl;

import com.github.pagehelper.PageHelper;
import org.cgic.commons.exception.CommonException;
import org.cgic.commons.utils.CommonConvertor;
import org.cgic.platform.menu.dto.SysMenu;
import org.cgic.platform.menu.dto.SysMenuDTO;
import org.cgic.platform.menu.exception.SysMenuException;
import org.cgic.platform.menu.mapper.SysMenuMapper;
import org.cgic.platform.menu.service.SysMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/10/11 16:30
 * @Version 1.0
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

    public static final Logger LOG = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getMenuList(int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        return sysMenuMapper.selectMenuList();
    }

    @Override
    public SysMenu getMenu(Long menuId) {
        return sysMenuMapper.selectByPrimaryKey(new SysMenu(menuId));
    }



    @Override
    public List<SysMenuDTO> getMenuInfoByUserId(Long userId) throws SysMenuException {
        List<SysMenu> sysMenuList = sysMenuMapper.selectMenuInfoByUserId(userId);
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return null;
        }
        List<SysMenuDTO> sysMenuDTOList = getSysMenuDTOList(sysMenuList);

        Map<Long, List<SysMenuDTO>> collect = sysMenuDTOList.stream()
                .collect(Collectors.groupingBy(SysMenuDTO::getParentId));

        List<SysMenuDTO> topMenu = collect.get(9999L);
        if(CollectionUtils.isEmpty(topMenu)){
            throw new SysMenuException(SysMenuException.SysMenuError.GET_TOP_MENU_ERROR.getCode(),
                    SysMenuException.SysMenuError.GET_TOP_MENU_ERROR.getMessage());
        }
        SysMenuDTO sysMenuDTO = topMenu.get(0);
        listConvertTree(collect,sysMenuDTO);

        return sysMenuDTO.getChildren();
    }


    /**
     * 集合 转换成 树结构
     * @param collect
     * @param sysMenuDTO
     */
    private static void listConvertTree(Map<Long, List<SysMenuDTO>> collect, SysMenuDTO sysMenuDTO) {
        List<SysMenuDTO> sysMenuDTOs = collect.get(sysMenuDTO.getMenuId());
        if (collect.get(sysMenuDTO.getMenuId()) != null) {
            //排序
            sysMenuDTOs.sort((u1, u2) -> u1.getMenuOrder().compareTo(u2.getMenuOrder()));
            sysMenuDTOs.stream().sorted(Comparator.comparing(SysMenuDTO::getMenuOrder)).collect(Collectors.toList());
            sysMenuDTO.setChildren(sysMenuDTOs);
            sysMenuDTO.getChildren().forEach(t -> {
                listConvertTree(collect, t);
            });
        }
    }


    /**
     * 将 数据库对象集合 转为 DTO对象集合
     *
     * @param sysMenuList
     * @return
     */
    private List<SysMenuDTO> getSysMenuDTOList(List<SysMenu> sysMenuList) {
        List<SysMenuDTO> sysMenuDTOList = new ArrayList<>(sysMenuList.size());
        sysMenuList.forEach(sysMenu -> {
            try {
                SysMenuDTO sysMenuDTO = CommonConvertor.beanConvert(SysMenuDTO.class, sysMenu);
                sysMenuDTOList.add(sysMenuDTO);
            } catch (CommonException e) {
                LOG.error("menuId: {} convert bean failure .", sysMenu.getMenuId().toString());
            }
        });
        return sysMenuDTOList;
    }
}
