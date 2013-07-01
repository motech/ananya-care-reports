package org.motechproject.carereporting.auth;

import org.motechproject.carereporting.domain.UserEntity;
import org.motechproject.carereporting.exception.UserException;
import org.motechproject.carereporting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class CareReportingAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        try {
            UserEntity user = userService.login((String) authentication.getPrincipal(),
                    (String) authentication.getCredentials());
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        } catch (UserException e) {
            throw new BadCredentialsException("Bad credentials", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
