package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

public class CloseCallback implements CommandController {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;

    public CloseCallback(BotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.botMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        botMessageService.deleteMessage(UpdateParameter.getChatId(update), UpdateParameter.getMessageId(update));
        botMessageService.updateUser(telegramUserService, update);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }
}
