package komrachkov.anton.mybudget.bots.telegram.util;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public interface CommandController {
    ToDoList execute(Update update);

    ToDoList execute(Update update, ExecuteMode executeMode);

    void setDefaultExecuteMode(Update update);
}
