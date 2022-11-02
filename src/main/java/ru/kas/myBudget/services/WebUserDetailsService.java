package ru.kas.myBudget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kas.myBudget.models.WebUser;
import ru.kas.myBudget.repositories.WebUserRepository;
import ru.kas.myBudget.security.WebUserDetails;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class WebUserDetailsService implements UserDetailsService {
    private final WebUserRepository webUserRepository;

    @Autowired
    public WebUserDetailsService(WebUserRepository webUserRepository) {
        this.webUserRepository = webUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<WebUser> webUser = webUserRepository.findByUsername(username);
        if (webUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new WebUserDetails(webUser.get());
    }
}
