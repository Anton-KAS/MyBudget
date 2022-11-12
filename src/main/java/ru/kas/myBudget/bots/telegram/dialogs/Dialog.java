package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.CommandController;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.FIRST_STEP_INDEX;

public interface Dialog extends CommandController {
    boolean commit(Update update);

    @Override
    default void execute(Update update, ExecuteMode executeMode) {
        execute(update);
    }

    default ExecuteMode getExecuteMode(Update update, Integer dialogStep) {
        if (update.hasCallbackQuery() && dialogStep == null) {
            return ExecuteMode.EDIT;
        } else if (update.hasCallbackQuery() && dialogStep != null) {
            if (dialogStep > FIRST_STEP_INDEX.getIndex() + 1) {
                return ExecuteMode.EDIT;
            }
        }
        return ExecuteMode.SEND;
    }
}
