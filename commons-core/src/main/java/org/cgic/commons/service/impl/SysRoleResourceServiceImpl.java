package org.cgic.commons.service.impl;


import org.cgic.commons.dto.SysRole;
import org.cgic.commons.mapper.SysRoleResourceMapper;
import org.cgic.commons.service.ISysRoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色资源关系表服务实现类
 *
 * @author charleyZZZZ
 * @date 2019-07-02 14:09:54
 */
@Service
public class SysRoleResourceServiceImpl implements ISysRoleResourceService {

    @Autowired
    private SysRoleResourceMapper roleResourceMapper;

    @Override
    public List<SysRole> selectRolesByResourceUrl(String requestUrl) {
        return roleResourceMapper.selectRolesByResourceUrl(requestUrl);
    }
}
