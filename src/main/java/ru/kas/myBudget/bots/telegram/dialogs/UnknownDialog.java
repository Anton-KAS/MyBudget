package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.services.TelegramUserService;

public class UnknownDialog implements Dialog, CommandController {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;

    public final static String NO_MESSAGE = "Что-то пошло не так =(";

    public UnknownDialog(BotMessageService botMessageService, TelegramUserService telegramUserService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        botMessageService.executeAndUpdateUser(telegramUserService, update, getExecuteMode(update, null),
                NO_MESSAGE, null);
    }

    @Override
    public boolean commit(Update update) {
        return true;
    }
}
