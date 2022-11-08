package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

public class CloseCallback implements Callback {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;

    public CloseCallback(BotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.botMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        botMessageService.executeDeleteMessage(getChatId(update), getMessageId(update));
        checkUserInDb(telegramUserService, update);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
