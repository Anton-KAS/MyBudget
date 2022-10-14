package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    void execute(Update update);

    default long getUserId(Update update) {
        return update.getMessage().getFrom().getId();
    }

    default long getChatId(Update update) {
        return update.getMessage().getChatId();
    }
}
