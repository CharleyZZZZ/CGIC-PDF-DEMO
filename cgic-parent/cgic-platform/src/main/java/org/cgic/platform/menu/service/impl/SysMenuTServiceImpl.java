package org.cgic.platform.menu.service.impl;

import org.cgic.commons.exception.CommonException;
import org.cgic.commons.utils.CommonConvertor;
import org.cgic.platform.menu.dto.SysMenuMeta;
import org.cgic.platform.menu.dto.SysMenuT;
import org.cgic.platform.menu.dto.SysMenuTDTO;
import org.cgic.platform.menu.exception.SysMenuException;
import org.cgic.platform.menu.mapper.SysMenuTMapper;
import org.cgic.platform.menu.service.SysMenuTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/10/11 16:30
 * @Version 1.0
 */
@Service
public class SysMenuTServiceImpl implements SysMenuTService {

    public static final Logger LOG = LoggerFactory.getLogger(SysMenuTServiceImpl.class);

    @Autowired
    private SysMenuTMapper sysMenuTMapper;

    @Override
    public List<SysMenuTDTO> getMenuInfoByUserId(Long userId) throws SysMenuException {
        List<SysMenuT> sysMenuTS = sysMenuTMapper.selectMenuInfoByUserId(userId);
        if (CollectionUtils.isEmpty(sysMenuTS)) {
            LOG.info("GET User:userId: {} menu list is empty ...", userId.toString());
            return new ArrayList<>();
        }

        sysMenuTS.forEach(menu->{
            menu.setMeta(new SysMenuMeta(menu.getIcon(),menu.getTitle()));
        });

        List<SysMenuTDTO> sysMenuDTOList = getSysMenuDTOList(sysMenuTS);

        Map<Long, List<SysMenuTDTO>> listMap = sysMenuDTOList.stream()
                .collect(Collectors.groupingBy(SysMenuTDTO::getParentId));

        // 获取顶层菜单
        List<SysMenuTDTO> topMenuList = listMap.get(9999L);
        if (CollectionUtils.isEmpty(topMenuList)) {
            throw new SysMenuException(SysMenuException.SysMenuError.GET_TOP_MENU_ERROR.getCode(),
                    SysMenuException.SysMenuError.GET_TOP_MENU_ERROR.getMessage());
        }
        SysMenuTDTO topMenu = topMenuList.get(0);
        // 转换成树结构
        listConvertTree(listMap, topMenuList.get(0));
        return  topMenu.getChildren();
    }


    private void listConvertTree(Map<Long, List<SysMenuTDTO>> listMap, SysMenuTDTO topMenu) {
        List<SysMenuTDTO> sysMenuTList = listMap.get(topMenu.getId());
        if (listMap.get(topMenu.getId()) != null) {
            //排序
            sysMenuTList.sort((u1, u2) -> u1.getOrder().compareTo(u2.getOrder()));
            sysMenuTList.stream().sorted(Comparator.comparing(SysMenuTDTO::getOrder)).collect(Collectors.toList());
            topMenu.setChildren(sysMenuTList);
            topMenu.getChildren().forEach(t -> {
                listConvertTree(listMap, t);
            });
        }
    }

    /**
     * 将 数据库对象集合 转为 DTO对象集合
     *
     * @param sysMenuList
     * @return
     */
    private List<SysMenuTDTO> getSysMenuDTOList(List<SysMenuT> sysMenuList) {
        List<SysMenuTDTO> sysMenuDTOList = new ArrayList<>(sysMenuList.size());
        sysMenuList.forEach(sysMenu -> {
            try {
                SysMenuTDTO sysMenuDTO = CommonConvertor.beanConvert(SysMenuTDTO.class, sysMenu);
                sysMenuDTOList.add(sysMenuDTO);
            } catch (CommonException e) {
                LOG.error("menuId: {} convert bean failure .", sysMenu.getId().toString());
            }
        });
        return sysMenuDTOList;
    }

}
