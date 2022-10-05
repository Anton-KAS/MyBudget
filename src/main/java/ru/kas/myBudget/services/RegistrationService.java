package ru.kas.myBudget.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kas.myBudget.models.WebUser;
import ru.kas.myBudget.repositories.WebUserRepository;

@Service
public class RegistrationService {

    public final WebUserRepository webUserRepository;

    @Autowired
    public RegistrationService(WebUserRepository webUserRepository) {
        this.webUserRepository = webUserRepository;
    }

    @Transactional
    public void register(WebUser webUser) {
        webUserRepository.save(webUser);
    }
}
