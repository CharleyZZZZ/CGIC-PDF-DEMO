package org.cgic.oauth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2020/4/23 23:12
 * @Version 1.0
 */
@Getter
@Setter
public class UserInfoDTO {

    private Long userId;
    private String username;
    private String displayName;
    private String email;
    private String phone;
    private Long employeeId;
    private List<SysMenuTDTO> routers;
}
