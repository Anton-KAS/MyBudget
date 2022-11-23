package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.util.CommandDialogNames;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
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
    public void executeByOrder(Update update, ExecuteMode executeMode) {

    }

    @Override
    public void setData(Update update) {

    }

    @Override
    public void executeData(Update update, ExecuteMode executeMode) {

    }

    @Override
    public void addToDialogMap(long userId, CommandDialogNames name, String stringId, String text) {

    }

    @Override
    public ExecuteMode getExecuteMode(Update update, Integer dialogStep) {
        return null;
    }
}
