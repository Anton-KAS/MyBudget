package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.util.UpdateExtraction;

public interface Command extends UpdateExtraction {
    default String getMessageText(Update update) {
        return update.getMessage().getText().trim();
    }
}
