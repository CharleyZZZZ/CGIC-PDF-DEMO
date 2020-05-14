package org.cgic.commons.service;



import org.cgic.commons.dto.SysRole;

import java.util.List;

/**
 * 用户角色关系表服务接口
 *
 * @author charleyZZZZ
 * @date 2019-07-02 14:09:54
 */
public interface ISysUserRoleService {


    /**
     * @Description: 根据用户id 查询角色列表
     * @param userId
     * @return java.util.List<com.putech.oauth.entity.SysRole>
     *
     * Modification History
     * Date            Author           Description
     * ----------------------------------------------------
     * 2019/7/4      charleyZZZZ      create/modify
     */
    List<SysRole> selectRolesByUserId(Long userId);
}

