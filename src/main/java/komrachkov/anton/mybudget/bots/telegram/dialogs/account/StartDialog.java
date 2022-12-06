package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
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
    protected String[] callbackData;

    public StartDialog(TelegramUserService telegramUserService, AccountDialogText messageText) {
        super(telegramUserService, messageText, null, null);
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        return new ToDoList();
    }


    @Override
    public ToDoList commit(Update update) {
        long chatId = UpdateParameter.getChatId(update);
        DialogsState.removeAllDialogs(chatId);
        this.callbackData = UpdateParameter.getCallbackData(update).orElse(null);

        ToDoList toDoList = new ToDoList();
        if (callbackData == null) return toDoList;

        var dialogSteps = new HashMap<String, String>();
        if (callbackData.length <= CallbackIndex.FROM.ordinal()) return toDoList;

        if (dialogName == null) return toDoList;
        dialogSteps.put(DialogMapDefaultName.DIALOG_ID.getId(), dialogName);
        dialogSteps.put(DialogMapDefaultName.START_FROM_ID.getId(), callbackData[CallbackIndex.FROM.ordinal()]);

        DialogsState.putDialogStateMap(chatId, ACCOUNT, dialogSteps);
        ResponseWaitingMap.put(chatId, DialogNamesImpl.ADD_ACCOUNT);

        toDoList.setResultCommit(true);
        return toDoList;
    }
}
