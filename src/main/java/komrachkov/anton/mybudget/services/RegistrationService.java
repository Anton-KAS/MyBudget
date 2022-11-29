package komrachkov.anton.mybudget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import komrachkov.anton.mybudget.models.WebUser;
import komrachkov.anton.mybudget.repositories.WebUserRepository;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Service
@Transactional(readOnly = true)
public class RegistrationService {

    public final WebUserRepository webUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(WebUserRepository webUserRepository, PasswordEncoder passwordEncoder) {
        this.webUserRepository = webUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(WebUser webUser) {
        webUser.setPassword(passwordEncoder.encode(webUser.getPassword()));
        webUser.setRole("ROLE_USER");
        webUserRepository.save(webUser);
    }
}
