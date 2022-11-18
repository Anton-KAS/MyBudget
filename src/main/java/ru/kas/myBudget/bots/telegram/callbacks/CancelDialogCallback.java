package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandControllerImpl;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

public class CancelDialogCallback extends CommandControllerImpl {
    private final DialogsMap dialogsMap;

    public CancelDialogCallback(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard,
                                DialogsMap dialogsMap) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
        this.dialogsMap = dialogsMap;
    }

    @Override
    protected void executeData(Update update, ExecuteMode executeMode) {
        super.executeData(update, executeMode);
        long chatId = UpdateParameter.getChatId(update);
        ResponseWaitingMap.remove(chatId);
        dialogsMap.remove(chatId);
    }
}
