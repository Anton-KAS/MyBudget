package ru.kas.myBudget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kas.myBudget.models.WebUser;
import ru.kas.myBudget.repositories.WebUserRepository;
import ru.kas.myBudget.security.WebUserDetails;

import java.util.Optional;

/**
 * @since 0.1
 * @author Anton Komrachkov
 */

@Service
@Transactional(readOnly = true)
public class WebUserService {
    private final WebUserRepository webUserRepository;

    @Autowired
    public WebUserService(WebUserRepository webUserRepository) {
        this.webUserRepository = webUserRepository;
    }

    public Optional<WebUser> loadUserByUsername(String username) throws UsernameNotFoundException {
        return webUserRepository.findByUsername(username);
    }
}
