package ru.kas.myBudget.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @since 0.1
 * @author Anton Komrachkov
 */

@Controller
public class LogController {
    @GetMapping("/log")
    public String logPage() {
        return "log";
    }
}
