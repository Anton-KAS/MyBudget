package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNT;

/**
 * @author Anton Komrachkov
 * @since 0.2
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
        DialogsState.removeAllDialogs(chatId);
        this.callbackData = UpdateParameter.getCallbackData(update).orElse(null);

        if (callbackData == null) return false;
        var dialogSteps = new HashMap<String, String>();
        if (callbackData.length <= CallbackIndex.FROM.ordinal()) return false;

        dialogSteps.put(DialogMapDefaultName.DIALOG_ID.getId(), dialogName.getName());
        dialogSteps.put(DialogMapDefaultName.START_FROM_ID.getId(), callbackData[CallbackIndex.FROM.ordinal()]);

        DialogsState.putDialogStateMap(chatId, ACCOUNT, dialogSteps);
        ResponseWaitingMap.put(chatId, DialogNamesImpl.ADD_ACCOUNT);
        return true;
    }
}
