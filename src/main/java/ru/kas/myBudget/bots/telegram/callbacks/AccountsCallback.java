package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.AccountsKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AccountsText;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

public class AccountsCallback implements CommandController {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final ExecuteMode defaultExecuteMode;

    public AccountsCallback(BotMessageService BotMessageService, TelegramUserService telegramUserService,
                            ExecuteMode defaultExecuteMode) {
        this.botMessageService = BotMessageService;
        this.telegramUserService = telegramUserService;
        this.defaultExecuteMode = defaultExecuteMode;
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
        String text = new AccountsText(telegramUserService).setUserId(UpdateParameter.getUserId(update)).getText();

        botMessageService.executeAndUpdateUser(telegramUserService, update, executeMode, text, inlineKeyboardMarkup);
    }
}
