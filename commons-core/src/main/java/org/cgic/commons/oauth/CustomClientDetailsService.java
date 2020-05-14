package org.cgic.commons.oauth;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;

/**
 * @Description
 * @Author: charleyZZZZ
 * @Date: 2019/10/12 16:45
 * @Version 1.0
 */
public class CustomClientDetailsService implements ClientDetailsService {


    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        return null;
    }
}
