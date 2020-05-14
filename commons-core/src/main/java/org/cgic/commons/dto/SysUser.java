package org.cgic.commons.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/7/1 16:07
 * @Version 1.0
 */
@Getter
@Setter
@Table(name = "sys_user")
@ApiModel("用户表")
@AllArgsConstructor
public class SysUser extends BaseDTO {

    /**
     * 用户ID
     */
    @Id
    private Long userId;

    /**
     * 用户名
     */
    @NotNull
    private String username;

    /**
     * 显示名称
     */
    private String displayName;

    /**
     * 密码
     */
    @NotNull
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    @NotNull
    private String phone;

    /**
     * 员工ID
     */
    private Long employeeId;

}
