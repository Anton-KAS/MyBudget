package ru.kas.myBudget.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.kas.myBudget.services.WebUserDetailsService;

import java.util.Collections;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final WebUserDetailsService webUserDetailsService;

    @Autowired
    public AuthProviderImpl(WebUserDetailsService webUserDetailsService) {
        this.webUserDetailsService = webUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails webUserDetails = webUserDetailsService.loadUserByUsername(username);
        String password = authentication.getCredentials().toString();

        if (!password.equals(webUserDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect username or password");
        }
        return new UsernamePasswordAuthenticationToken(webUserDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
