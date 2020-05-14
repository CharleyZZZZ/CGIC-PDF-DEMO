package org.cgic.commons.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/7/1 16:55
 * @Version 1.0
 */
@Getter
@Setter
@Table(name = "sys_user_role")
@ApiModel("用户角色关系表")
public class SysUserRole extends BaseDTO {



    /**
     * 用户角色ID
     */
    @Id
    private Long userRoleId;

    /**
     * 用户ID
     */
    @NotNull
    private Long userId;

    /**
     * 角色ID
     */
    @NotNull
    private Long roleId;
}
