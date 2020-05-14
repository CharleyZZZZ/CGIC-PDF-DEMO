package org.cgic.commons.oauth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2020/1/9 11:07
 * @Version 1.0
 */
public class DetailsHelper {

  
        private DetailsHelper() {
        }

        public static UserDetailImpl getUserDetails() {
            if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal instanceof UserDetailImpl) {
                    return (UserDetailImpl)principal;
                } else {
                    Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
                    if (details instanceof OAuth2AuthenticationDetails) {
                        Object decodedDetails = ((OAuth2AuthenticationDetails)details).getDecodedDetails();
                        if (decodedDetails instanceof UserDetailImpl) {
                            return (UserDetailImpl)decodedDetails;
                        }
                    }

                    return null;
                }
            } else {
                return null;
            }
        }

        public static ClientDetailImpl getClientDetails() {
            if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
                Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                if (principal instanceof ClientDetailImpl) {
                    return (ClientDetailImpl)principal;
                } else {
                    Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
                    if (details instanceof OAuth2AuthenticationDetails) {
                        Object decodedDetails = ((OAuth2AuthenticationDetails)details).getDecodedDetails();
                        if (decodedDetails instanceof ClientDetailImpl) {
                            return (ClientDetailImpl)decodedDetails;
                        }
                    }

                    return null;
                }
            } else {
                return null;
            }
        }

}
