package org.cgic.commons.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/12/2 14:32
 * @Version 1.0
 */
public class CustomTokenConverter extends DefaultAccessTokenConverter {


    private static final String USER_ID = "userId";
    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";
    private static final String DISPLAY_NAME = "displayName";
    private static final String EMPLOYEE_ID = "employeeId";
    private static final String ROLE_IDS = "roleIds";

    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;
    @Autowired
    private CustomClientDetailsService clientDetailsService;


    public CustomTokenConverter() {
    }


    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Map<String, Object> map = (Map<String, Object>) super.convertAccessToken(token, authentication);
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailImpl) {
            UserDetailImpl userDetail = this.userDetailsService.loadUserByUsername(((UserDetailImpl) principal).getUsername());
            map.put(USER_ID, userDetail.getUserId().toString());
            map.put(USER_NAME, userDetail.getUsername());
            map.put(PASSWORD, userDetail.getPassword());
            map.put(EMPLOYEE_ID, userDetail.getEmployeeId());
            map.put(DISPLAY_NAME, userDetail.getDisplayName());
            map.put(ROLE_IDS, userDetail.getRoleIds());
        } else if (principal instanceof String) {

        }
        return map;

    }


    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        if (map.get("principal") != null) {
            map = (Map) map.get("principal");
        }
        OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);
        if (map.containsKey(USER_ID)) {
            UserDetailImpl userDetail = new UserDetailImpl(oAuth2Authentication.getName(), "unknow", oAuth2Authentication.getAuthorities());
            if (map.get(USER_ID) != null) {
                userDetail.setUserId(((Integer) map.get(USER_ID)).longValue());
                userDetail.setDisplayName((String) map.get(DISPLAY_NAME));
                userDetail.setEmployeeId(((Integer) map.get(EMPLOYEE_ID)).longValue());
                if (map.get(EMAIL) != null) {
                    userDetail.setEmail((String) map.get(EMAIL));
                }
                if (map.get(PHONE) != null) {
                    userDetail.setPhone((String) map.get(PHONE));
                }

                List roleIdList;
                if (map.get(ROLE_IDS) != null) {
                    Object roleIds = map.get(ROLE_IDS);
                    if (roleIds instanceof List) {
                        roleIdList = (List) roleIds;
                        userDetail.setRoleIds((List) roleIdList.stream().map((item) -> {
                            return Long.valueOf(String.valueOf(item));
                        }).collect(Collectors.toList()));
                    }
                }
            }

            oAuth2Authentication.setDetails(userDetail);
        } else {
            ClientDetailImpl client = new ClientDetailImpl();
            client.setClientId(oAuth2Authentication.getName());
            client.setAuthorities(oAuth2Authentication.getAuthorities());
            oAuth2Authentication.setDetails(client);
        }

        return oAuth2Authentication;
    }
}
