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
