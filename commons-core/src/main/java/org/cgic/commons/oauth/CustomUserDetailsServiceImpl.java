package org.cgic.commons.oauth;

import org.cgic.commons.dto.SysRole;
import org.cgic.commons.dto.SysUser;
import org.cgic.commons.service.ISysUserRoleService;
import org.cgic.commons.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Description 用户信息实现类
 * @Author: charleyZZZZ
 * @Date: 2019/10/12 16:52
 * @Version 1.0
 */
@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;




    @Override
    public UserDetailImpl loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = userService.selectUserByUserName(s);
        if (null == sysUser) {
            throw new UsernameNotFoundException("hr: " + s + "not found in DB .");
        }
        UserDetailImpl userDetail = new UserDetailImpl(sysUser.getUsername(), sysUser.getPassword(), Collections.emptySet());
        userDetail.setDisplayName(sysUser.getDisplayName());
        userDetail.setEmployeeId(sysUser.getEmployeeId());
        userDetail.setUserId(sysUser.getUserId());
        if (!StringUtils.isEmpty(sysUser.getEmail())) {
            userDetail.setEmail(sysUser.getEmail());
        }
        if (!StringUtils.isEmpty(sysUser.getPhone())) {
            userDetail.setPhone(sysUser.getPhone());
        }
        return userDetail;
    }
}
