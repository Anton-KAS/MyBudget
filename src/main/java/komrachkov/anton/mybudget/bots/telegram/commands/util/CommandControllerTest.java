package komrachkov.anton.mybudget.bots.telegram.commands.util;

import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Anton Komrachkov
 * @since (03.12.2022)
 */

public interface CommandControllerTest {
    ToDoList execute(Update update);

    ToDoList execute(Update update, ExecuteMode executeMode);
}
