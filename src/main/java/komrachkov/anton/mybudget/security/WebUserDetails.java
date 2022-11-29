package komrachkov.anton.mybudget.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import komrachkov.anton.mybudget.models.WebUser;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */
public class WebUserDetails implements UserDetails {
    private final WebUser webUser;

    public WebUserDetails(WebUser webUser) {
        this.webUser = webUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(webUser.getRole()));
    }

    @Override
    public String getPassword() {
        return this.webUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public WebUser getWebUser() {
        return this.webUser;
    }
}
