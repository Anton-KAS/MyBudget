package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.AccountsKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AccountsText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

public class AccountsCallback implements Callback {

    private final ExecuteMode defaultExecuteMode;
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;

    public AccountsCallback(ExecuteMode defaultExecuteMode, BotMessageService BotMessageService,
                            TelegramUserService telegramUserService) {
        this.defaultExecuteMode = defaultExecuteMode;
        this.botMessageService = BotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        executeMessageService(update, defaultExecuteMode);
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
        executeMessageService(update, executeMode);
    }

    private void executeMessageService(Update update, ExecuteMode executeMode) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new AccountsKeyboard().getKeyboard();
        String text = new AccountsText(telegramUserService, getUserId(update)).getText();

        botMessageService.executeMessage(executeMode, getChatId(update), getMessageId(update), text, inlineKeyboardMarkup);
        checkUserInDb(telegramUserService, update);
    }
}
