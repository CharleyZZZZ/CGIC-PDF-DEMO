package org.cgic.platform.menu.dto;

import org.cgic.commons.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Description 菜单DTO
 * @Author: charleyZZZZ
 * @Date: 2019/10/10 14:50
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("菜单表")
@Table(name = "sys_menu")
public class SysMenu extends BaseDTO{

    public static final String FIELD_MENU_ID = "menuId";
    public static final String FIELD_MENU_CODE = "menuCode";
    public static final String FIELD_MENU_NAME = "menuName";
    public static final String FIELD_MENU_ICON = "menuIcon";
    public static final String FIELD_DESCRIPTION = "description";
    public static final String FIELD_RESOURCE_ID = "resourceId";
    public static final String FIELD_PARENT_ID = "parentId";
    public static final String FIELD_MENU_ORDER = "menuOrder";
    public static final String FIELD_IS_ENABLED = "isEnabled";


    public SysMenu() {
    }

    public SysMenu(Long menuId) {
        this.menuId = menuId;
    }

    @Id
    private Long menuId;

    /**
     * 菜单编码
     */
    @Column
    @NotEmpty
    private String menuCode;

    /**
     * 菜单名称
     */
    @Column
    @NotEmpty
    private String menuName;

    /**
     * 菜单图标
     */
    @Column
    @NotEmpty
    private String menuIcon;

    /**
     * 菜单描述
     */
    @Column
    private String description;

    /**
     * 父级菜单ID
     */
    @Column
    private Long parentId;

    /**
     * 菜单排序（该目录类，由小到大）
     */
    @Column
    private Long menuOrder;

    /**
     * 是否可用
     */
    @Column
    @NotEmpty
    private String isEnabled;

    /**
     * 绑定资源ID
     */
    @Column
    private Long resourceId;

    /**
     * 拓展字段
     * 资源url
     */
    @Transient
    private String resourceUrl;

    /**
     * 父级菜单名称
     */
    @Transient
    private String parentMenuName;
}
