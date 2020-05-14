package org.cgic.commons.service.impl;



import org.cgic.commons.dto.SysRole;
import org.cgic.commons.mapper.SysUserRoleMapper;
import org.cgic.commons.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 用户角色关系表服务实现类
 *
 * @author charleyZZZZ
 * @date 2019-07-02 14:09:54
 */
@Service
public class SysUserRoleServiceImpl  implements ISysUserRoleService {


    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        Assert.notNull(userId,"userId is not allow null...");
        return sysUserRoleMapper.selectRolesByUserId(userId);
    }
}
