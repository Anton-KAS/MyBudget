package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

public class UnknownCallback implements CommandController {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final ExecuteMode executeMode;

    public final static String NO_MESSAGE = "Что-то пошло не так =(";

    public UnknownCallback(BotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                           ExecuteMode executeMode) {
        this.botMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.executeMode = executeMode;
    }

    @Override
    public void execute(Update update) {
        botMessageService.executeAndUpdateUser(telegramUserService, update, executeMode, NO_MESSAGE, null);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
