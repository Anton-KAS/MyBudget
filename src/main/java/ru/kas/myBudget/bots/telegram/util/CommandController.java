package ru.kas.myBudget.bots.telegram.util;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public interface CommandController {
    void execute(Update update);

    void execute(Update update, ExecuteMode executeMode);
}
