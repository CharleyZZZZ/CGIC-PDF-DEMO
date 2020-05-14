package org.cgic.oauth.controller;

import com.alibaba.fastjson.JSONObject;
import org.cgic.commons.dto.BaseResponseDTO;
import org.cgic.commons.dto.SysUser;
import org.cgic.commons.oauth.CustomUserDetailsServiceImpl;
import org.cgic.commons.oauth.UserDetailImpl;
import org.cgic.commons.service.ISysUserService;
import org.cgic.commons.utils.CommonConvertor;
import org.cgic.commons.web.BaseController;
import org.cgic.oauth.dto.SysMenuTDTO;
import org.cgic.oauth.dto.UserInfoDTO;
import org.cgic.oauth.service.ISysMenuTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/10/17 15:29
 * @Version 1.0
 */
@Controller
@RequestMapping("/api")
public class OauthController extends BaseController {


    final BASE64Encoder encoder = new BASE64Encoder();

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysMenuTService sysMenuTService;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @GetMapping("/user/info")
    @ResponseBody
    public BaseResponseDTO getUserInfo() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailImpl) {
            UserDetailImpl userDetail = (UserDetailImpl) principal;
            SysUser sysUser = sysUserService.selectUserByUserName(userDetail.getUsername());
            if (sysUser == null) {
                throw new Exception("获取用户信息失败！");
            }
            UserInfoDTO userInfoDTO = CommonConvertor.beanConvert(UserInfoDTO.class, sysUser);
            List<SysMenuTDTO> menuTDTOS = sysMenuTService.getMenuInfoByUserId(sysUser.getUserId());
            userInfoDTO.setRouters(menuTDTOS);
            return new BaseResponseDTO(userInfoDTO);
        }
        return BaseResponseDTO.unAuthticated();
    }


    @GetMapping("/jwt_token")
    @ResponseBody
    public BaseResponseDTO getJwtToken() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailImpl) {
            UserDetailImpl userDetail = (UserDetailImpl) principal;
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userDetail.getUsername());
            return new BaseResponseDTO(userDetails);
        }
        // 客户端模式
        UserDetailImpl clientUser = new UserDetailImpl("client_user", "unknow", Collections.emptyList());
        return new BaseResponseDTO(clientUser);
    }


    private BaseResponseDTO getBaseResponseDTO(BaseResponseDTO baseResponseDTO, UserDetailImpl userDetail) throws UnsupportedEncodingException {
        String jwtToken;
        String json = JSONObject.toJSONString(userDetail);
        jwtToken = encoder.encode(json.getBytes("UTF-8"));
        baseResponseDTO.setMessage(jwtToken);
        return baseResponseDTO;
    }


}
