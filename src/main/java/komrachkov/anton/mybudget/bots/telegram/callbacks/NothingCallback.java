package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Do nothing
 * @author Anton Komrachkov
 * @since 0.4
 */

@Component
public class NothingCallback extends CommandControllerImpl {

    public NothingCallback() {
        super(null, null, null);
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        return new ToDoList();
    }
}
