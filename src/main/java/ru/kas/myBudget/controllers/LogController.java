package ru.kas.myBudget.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Controller
public class LogController {
    @GetMapping("/log")
    public String logPage() {
        return "log";
    }
}
