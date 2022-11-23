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

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.MENU;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.START_FROM_CALLBACK;

public class CancelDialogCallback extends CommandControllerImpl {
    private final CallbackContainer callbackContainer;

    public CancelDialogCallback(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard,
                                CallbackContainer callbackContainer) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
        this.callbackContainer = callbackContainer;
    }

    @Override
    protected void executeData(Update update, ExecuteMode executeMode) {
        super.executeData(update, executeMode);

        long chatId = UpdateParameter.getChatId(update);
        update.getCallbackQuery().setData(DialogsMap.getDialogStepById(chatId, START_FROM_CALLBACK.getId()));
        callbackContainer.retrieve(MENU.getName()).execute(update);
        ResponseWaitingMap.remove(chatId);
        DialogsMap.remove(chatId);
    }
}
