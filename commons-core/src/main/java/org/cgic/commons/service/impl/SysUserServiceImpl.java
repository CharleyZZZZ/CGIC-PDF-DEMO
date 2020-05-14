package org.cgic.commons.service.impl;



import org.cgic.commons.dto.SysUser;
import org.cgic.commons.mapper.SysUserMapper;
import org.cgic.commons.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/7/1 16:15
 * @Version 1.0
 */
@Service
public class SysUserServiceImpl implements ISysUserService {


    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser selectUserByUserName(String s) {
        return sysUserMapper.selectByUserName(s);
    }
}
