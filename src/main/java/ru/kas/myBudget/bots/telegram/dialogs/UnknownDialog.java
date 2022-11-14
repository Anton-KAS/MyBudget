package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandControllerImpl;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.TelegramUserService;

public class UnknownDialog extends CommandControllerImpl implements Dialog {

    public UnknownDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                         ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
    }

    @Override
    public boolean commit(Update update) {
        return false;
    }

    @Override
    public void skip(Update update) {

    }

    @Override
    public ExecuteMode getExecuteMode(Update update, Integer dialogStep) {
        return null;
    }
}
