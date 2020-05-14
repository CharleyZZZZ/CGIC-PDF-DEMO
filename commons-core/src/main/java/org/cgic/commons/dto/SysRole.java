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
 * @Date: 2019/7/1 16:53
 * @Version 1.0
 */

@Getter
@Setter
@Table(name = "sys_role")
@ApiModel("角色表")
public class SysRole extends BaseDTO {

    /**
     * 角色ID
     */
    @Id
    private Long roleId;

    /**
     * 角色编码
     */
    @NotNull
    private String roleCode;

    /**
     * 角色名称
     */
    @NotNull
    private String roleName;

}
