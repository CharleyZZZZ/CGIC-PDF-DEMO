package org.cgic.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/7/1 16:58
 * @Version 1.0
 */
@Setter
@Getter
@AllArgsConstructor
public class SysUserRoles {

    /**
     * 用户
     */
    private SysUser user;

    /**
     * 角色集
     */
    private List<SysRole> roles;
}
