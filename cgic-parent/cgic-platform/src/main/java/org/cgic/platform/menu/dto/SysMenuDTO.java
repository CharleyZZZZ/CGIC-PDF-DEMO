package org.cgic.platform.menu.dto;

import lombok.Getter;
import lombok.Setter;
import org.cgic.commons.dto.BaseDTO;

import java.util.List;

/**
 * @Description 对外封装DTO
 * @Author: charleyZZZZ
 * @Date: 2020/4/13 10:43
 * @Version 1.0
 */
@Getter
@Setter
public class SysMenuDTO extends BaseDTO{


    private Long menuId;

    /**
     * 菜单编码
     */
    private String menuCode;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单描述
     */
    private String description;

    /**
     * 父级菜单ID
     */
    private Long parentId;

    /**
     * 菜单排序（该目录类，由小到大）
     */
    private Long menuOrder;

    /**
     * 是否可用
     */
    private String isEnabled;

    /**
     * 绑定资源ID
     */
    private Long resourceId;

    /**
     * 拓展字段
     * 资源url
     */
    private String resourceUrl;

    /**
     * 子菜单
     */
    private List<SysMenuDTO> children;
}
