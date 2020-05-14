package org.cgic.platform.menu.controllers;

import org.cgic.commons.oauth.DetailsHelper;
import org.cgic.platform.menu.exception.SysMenuException;
import org.cgic.platform.menu.service.SysMenuService;
import org.cgic.commons.dto.BaseResponseDTO;
import org.cgic.commons.web.BaseController;
import org.cgic.platform.menu.service.SysMenuTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/10/11 16:25
 * @Version 1.0
 */
@Controller
@RequestMapping("/api/menu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysMenuTService sysMenuTService;

    @GetMapping("/list")
    @ResponseBody
    public BaseResponseDTO query(@RequestParam(defaultValue = DEFAULT_PAGE) int page,
                                 @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        return new BaseResponseDTO(sysMenuService.getMenuList(page,pageSize));
    }


    /**
     * 获取登录用户菜单信息
     * @return
     * @throws SysMenuException
     */
    @GetMapping("/info")
    @ResponseBody
    public BaseResponseDTO queryMenuInfoByUserId() throws SysMenuException {
        Long userId = DetailsHelper.getUserDetails().getUserId();
        if (userId == null) {
            return new BaseResponseDTO(null);
        }
        return new BaseResponseDTO(sysMenuTService.getMenuInfoByUserId(userId));
    }

}
