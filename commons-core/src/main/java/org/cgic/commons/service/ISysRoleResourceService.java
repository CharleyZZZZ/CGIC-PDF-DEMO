package org.cgic.commons.service;



import org.cgic.commons.dto.SysRole;

import java.util.List;

/**
 * 角色资源关系表服务接口
 *
 * @author charleyZZZZ
 * @date 2019-07-02 14:09:54
 */
public interface ISysRoleResourceService {

    /**
     * @Description: 根据资源url获取角色列表
     * @param requestUrl
     * @return java.util.List<com.putech.oauth.entity.SysRole>
     *
     * Modification History
     * Date            Author           Description
     * ----------------------------------------------------
     * 2019/7/2      charleyZZZZ      create/modify
     */
    List<SysRole> selectRolesByResourceUrl(String requestUrl);
}

