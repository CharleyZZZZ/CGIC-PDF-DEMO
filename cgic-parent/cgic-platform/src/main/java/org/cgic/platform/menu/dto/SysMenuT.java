package org.cgic.platform.menu.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.cgic.commons.dto.BaseDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2020/4/23 18:49
 * @Version 1.0
 */
@Getter
@Setter
@ApiModel("菜单表")
@Table(name = "sys_menu_t")
public class SysMenuT extends BaseDTO {

    public static final String FIELD_ID = "id";
    public static final String FIELD_CODE = "code";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ICON = "icon";
    public static final String FIELD_PATH = "path";
    public static final String FIELD_COMPONENT = "component";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_PARENT_ID = "parentId";
    public static final String FIELD_ORDER = "order";
    public static final String FIELD_IS_ENABLED = "isEnabled";


    public SysMenuT() {
    }



    @Id
    private Long id;

    /**
     * 菜单编码
     */
    @Column
    @NotEmpty
    private String code;

    /**
     * 菜单名称
     */
    @Column
    @NotEmpty
    private String name;

    /**
     * 菜单图标
     */
    @Column
    @NotEmpty
    private String icon;

    /**
     * 菜单标题
     */
    @Column
    @NotEmpty
    private String title;


    /**
     * 视图路径
     */
    @Column
    private String path;

    /**
     * 视图路径
     */
    @Column
    private String component;

    /**
     * 父级菜单ID
     */
    @Column
    private Long parentId;

    /**
     * 菜单排序（该目录类，由小到大）
     */
    @Column
    private Long order;

    /**
     * 是否可用
     */
    @Column
    @NotEmpty
    private String isEnabled;


    /**
     * 父级菜单名称
     */
    @Transient
    private String parentMenuName;

    /**
     * 菜单元数据
     */
    @Transient
    private SysMenuMeta meta;

    /**
     * 子菜单
     */
    @Transient
    private List<SysMenuT> children;

}
