package ru.kas.myBudget.bots.telegram.dialogs.util;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.CommandController;

/**
 * @since 0.2
 * @author Anton Komrachkov
 */

public interface Dialog extends CommandController {

    boolean commit(Update update);

    void skip(Update update);

    void executeByOrder(Update update, ExecuteMode executeMode);

    void setData(Update update);

    void executeData(Update update, ExecuteMode executeMode);

    void addToDialogMap(long userId, CommandDialogNames name, String stringId, String text);

    ExecuteMode getExecuteMode(Update update, Integer dialogStep);

}
