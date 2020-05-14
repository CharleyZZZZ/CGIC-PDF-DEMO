package org.cgic.oauth.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2020/4/23 18:49
 * @Version 1.0
 */
@Getter
@Setter
public class SysMenuTDTO {


    public SysMenuTDTO() {
    }


    private Long id;

    /**
     * 菜单名称
     */
    private String name;


    /**
     * 视图路径
     */
    private String path;

    /**
     * 视图路径
     */
    private String component;

    /**
     * 父级菜单ID
     */
    private Long parentId;

    /**
     * 菜单排序（该目录类，由小到大）
     */
    private Long order;


    /**
     * 菜单元数据
     */
    private SysMenuMeta meta;

    /**
     * 子菜单
     */
    private List<SysMenuTDTO> children;

}
