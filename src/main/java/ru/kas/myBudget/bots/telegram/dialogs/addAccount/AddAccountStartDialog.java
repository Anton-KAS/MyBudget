package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.HashMap;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackIndex.FROM;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.MENU;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.NORMAL;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.FIRST_STEP_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

public class AddAccountStartDialog extends DialogImpl {
    protected final DialogNamesImpl dialogName;
    protected String[] callbackData;

    public AddAccountStartDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                 MessageText messageText, Keyboard keyboard,
                                 DialogNamesImpl dialogName) {
        super(botMessageService, telegramUserService, messageText, keyboard, null);
        this.dialogName = dialogName;
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
    }

    @Override
    public boolean commit(Update update) {
        this.chatId = UpdateParameter.getChatId(update);
        this.callbackData = UpdateParameter.getCallbackData(update);
        DialogsMap.remove(chatId);

        if (callbackData == null) return false;
        var dialogSteps = new HashMap<String, String>();

        if (callbackData.length <= FROM.getIndex()) return false;

        dialogSteps.put(DIALOG_ID.getId(), dialogName.getName());
        dialogSteps.put(START_FROM_ID.getId(), callbackData[FROM.getIndex()]);
        dialogSteps.put(START_FROM_DATA.getId(), String.format("%s_%s_%s", NORMAL.getId(), MENU.getName(), callbackData[FROM.getIndex()]));
        dialogSteps.put(CURRENT_DIALOG_STEP.getId(), String.valueOf(FIRST_STEP_INDEX.getIndex()));
        dialogSteps.put(LAST_STEP.getId(), String.valueOf(FIRST_STEP_INDEX.getIndex()));

        DialogsMap.putDialogMap(chatId, dialogSteps);
        ResponseWaitingMap.put(chatId, ADD_ACCOUNT);
        return true;
    }
}
