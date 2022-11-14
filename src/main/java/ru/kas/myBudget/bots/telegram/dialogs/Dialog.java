package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.CommandController;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.FIRST_STEP_INDEX;

public interface Dialog extends CommandController {

    boolean commit(Update update);

    void skip(Update update);

    ExecuteMode getExecuteMode(Update update, Integer dialogStep);

}
