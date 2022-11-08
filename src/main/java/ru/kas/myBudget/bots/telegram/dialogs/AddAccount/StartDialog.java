package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.bot.TelegramBot;
import ru.kas.myBudget.bots.telegram.callbacks.Callback;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogName;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;

import java.util.HashMap;
import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.CURRENT_DIALOG_STEP;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.START_FROM_ID;

public class StartDialog implements Dialog, Callback {
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static int FIRST_STEP_INDEX = 1;

    public StartDialog() {
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
    }

    @Override
    public boolean commit(Update update) {
        dialogsMap.remove(getChatId(update));
        Map<String, String>  dialogSteps = new HashMap<>();
        dialogSteps.put(TelegramBot.DIALOG_ID, DialogName.ADD_ACCOUNT.getDialogName());
        dialogSteps.put(START_FROM_ID.getId(), getCallbackData(update)[TelegramBot.CALLBACK_FROM_INDEX]);
        dialogSteps.put(CURRENT_DIALOG_STEP.getDialogId(), String.valueOf(FIRST_STEP_INDEX));
        dialogsMap.put(getChatId(update), dialogSteps);
        return true;
    }
}
