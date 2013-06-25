package org.motechproject.carereporting.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import java.util.ArrayList;
import java.util.List;

public class CareReportingAuthenticationProvider implements AuthenticationProvider {

    private String testUserName = "test";
    private String testPassword = "test";

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (testUserName.equals(authentication.getPrincipal()) && testPassword.equals(authentication.getCredentials())) {
            GrantedAuthority grantedAuthority = new GrantedAuthorityImpl("ROLE_USER");
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>(1);
            grantedAuthorities.add(grantedAuthority);
            return new UsernamePasswordAuthenticationToken(testUserName, testPassword, grantedAuthorities);
        }
        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
