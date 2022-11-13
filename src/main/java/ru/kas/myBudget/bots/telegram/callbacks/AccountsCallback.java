package ru.kas.myBudget.bots.telegram.callbacks;

import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandControllerImpl;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

public class AccountsCallback extends CommandControllerImpl {

    public AccountsCallback(BotMessageService botMessageService, TelegramUserService telegramUserService,
                            ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
    }
}
