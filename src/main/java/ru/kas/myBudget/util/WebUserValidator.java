package ru.kas.myBudget.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kas.myBudget.models.WebUser;
import ru.kas.myBudget.services.WebUserService;

import java.util.Optional;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class WebUserValidator implements Validator {

    private final WebUserService webUserService;

    @Autowired
    public WebUserValidator(WebUserService webUserService) {
        this.webUserService = webUserService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return WebUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WebUser webUser = (WebUser) target;
        Optional<WebUser> webUserInDB = webUserService.loadUserByUsername(webUser.getUsername());
        if (!webUserInDB.isEmpty()) {
            errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует");
        }
    }
}
