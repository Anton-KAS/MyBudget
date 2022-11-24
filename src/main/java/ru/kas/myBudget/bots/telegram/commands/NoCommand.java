package ru.kas.myBudget.bots.telegram.commands;

import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandControllerImpl;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

/**
 * @since 0.1
 * @author Anton Komrachkov
 */

public class NoCommand extends CommandControllerImpl {

    public NoCommand(BotMessageService botMessageService, TelegramUserService telegramUserService,
                     ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
    }
}
