package komrachkov.anton.mybudget.services;

import komrachkov.anton.mybudget.repositories.WebUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import komrachkov.anton.mybudget.models.WebUser;

import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.1
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
