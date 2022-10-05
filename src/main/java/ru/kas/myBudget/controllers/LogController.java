package ru.kas.myBudget.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogController {
    @GetMapping("/log")
    public String logPage() {
        return "log";
    }
}
