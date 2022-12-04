package komrachkov.anton.mybudget.bots.telegram.dialogs.util;

import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface Dialog extends CommandController {

    ToDoList commit(Update update);

    ToDoList skip(Update update);

    void addToDialogMap(long userId, CommandDialogNames name, String stringId, String text);

    ExecuteMode getExecuteMode(Update update, Integer dialogStep);

    Dialog setCurrentDialogName(String dialogName);
}
