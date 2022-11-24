package ru.kas.myBudget.bots.telegram.dialogs.account;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.HashMap;

import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackIndex.FROM;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public abstract class StartDialog extends DialogImpl {
    protected final DialogNamesImpl dialogName;
    protected String[] callbackData;

    public StartDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                       MessageText messageText, Keyboard keyboard, DialogNamesImpl dialogName) {
        super(botMessageService, telegramUserService, messageText, keyboard, null);
        this.dialogName = dialogName;
    }

    @Override
    public void execute(Update update) {
    }

    @Override
    public void execute(Update update, ExecuteMode executeMode) {
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
    public boolean commit(Update update) {
        this.chatId = UpdateParameter.getChatId(update);
        DialogsMap.remove(chatId);
        this.callbackData = UpdateParameter.getCallbackData(update).orElse(null);

        if (callbackData == null) return false;
        var dialogSteps = new HashMap<String, String>();
        if (callbackData.length <= FROM.ordinal()) return false;

        dialogSteps.put(DIALOG_ID.getId(), dialogName.getName());
        dialogSteps.put(START_FROM_ID.getId(), callbackData[FROM.ordinal()]);

        DialogsMap.putDialogMap(chatId, dialogSteps);
        ResponseWaitingMap.put(chatId, ADD_ACCOUNT);
        return true;
    }
}
