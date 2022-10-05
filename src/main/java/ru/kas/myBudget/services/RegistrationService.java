package ru.kas.myBudget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kas.myBudget.models.WebUser;
import ru.kas.myBudget.repositories.WebUserRepository;

@Service
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
        webUserRepository.save(webUser);
    }
}
