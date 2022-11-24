package ru.kas.myBudget.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kas.myBudget.models.WebUser;
import ru.kas.myBudget.services.RegistrationService;
import ru.kas.myBudget.util.WebUserValidator;

import javax.validation.Valid;

/**
 * @since 0.1
 * @author Anton Komrachkov
 */

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final WebUserValidator webUserValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(WebUserValidator webUserValidator, RegistrationService registrationService) {
        this.webUserValidator = webUserValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("webUser") WebUser webUser) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("webUser") @Valid WebUser webUser, BindingResult bindingResult) {
        webUserValidator.validate(webUser, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }

        registrationService.register(webUser);
        return "redirect:/auth/login";
    }
}
