package org.cgic.commons.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 角色资源关系表
 *
 * @author charleyZZZZ 2019-07-02 14:09:54
 */
@ApiModel("角色资源关系表")
@Table(name = "sys_role_resource")
@Getter
@Setter
public class SysRoleResource extends BaseDTO {

    public static final String FIELD_ROLE_RESOURCE_ID = "roleResourceId";
    public static final String FIELD_ROLE_ID = "roleId";
    public static final String FIELD_RESOURCE_ID = "resourceId";

    @Id
    private Long roleResourceId;
    @NotNull
    private Long roleId;
    @NotNull
    private Long resourceId;

}
