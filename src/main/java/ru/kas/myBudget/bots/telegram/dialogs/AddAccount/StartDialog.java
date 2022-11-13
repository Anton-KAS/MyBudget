package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;

import java.util.HashMap;
import java.util.Map;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackIndex.FROM;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.*;

public class StartDialog implements Dialog, CommandController {
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
        String[] callbackData = UpdateParameter.getCallbackData(update);
        dialogsMap.remove(UpdateParameter.getChatId(update));

        if (callbackData == null) return false;
        Map<String, String> dialogSteps = new HashMap<>();
        dialogSteps.put(DIALOG_ID.getId(), DialogNamesImpl.ADD_ACCOUNT.getName());
        dialogSteps.put(START_FROM_ID.getId(), callbackData[FROM.getIndex()]);
        dialogSteps.put(CURRENT_DIALOG_STEP.getId(), String.valueOf(FIRST_STEP_INDEX));
        dialogSteps.put(LAST_STEP.getId(), String.valueOf(FIRST_STEP_INDEX));
        dialogsMap.put(UpdateParameter.getChatId(update), dialogSteps);
        return true;
    }

    @Override
    public void skip(Update update) {

    }
}
