package org.cgic.commons.service;


import org.cgic.commons.dto.SysUser;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/7/1 16:14
 * @Version 1.0
 */
public interface ISysUserService {

    /**
     * 根据用户名查询用户信息
     * @param s
     * @return
     */
    SysUser selectUserByUserName(String s);

}
