package ru.kas.myBudget.bots.telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandControllerImpl;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class CancelCommand extends CommandControllerImpl {

    public CancelCommand(BotMessageService botMessageService, TelegramUserService telegramUserService,
                         ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
    }

    @Override
    public void execute(Update update) {
        super.execute(update);
        long chatId = UpdateParameter.getChatId(update);
        ResponseWaitingMap.remove(chatId);
        DialogsMap.remove(chatId);
    }
}
