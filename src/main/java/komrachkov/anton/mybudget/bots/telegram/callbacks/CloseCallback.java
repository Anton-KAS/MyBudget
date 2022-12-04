package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.util.*;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Закрытие текущего окна меню (удаление сообщения с меню)
 *
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class CloseCallback extends CommandControllerImpl {

    public CloseCallback(TelegramUserService telegramUserService) {
        super(telegramUserService, null, null);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (04.12.2022)
     */
    @Override
    public void setDefaultExecuteMode() {
        this.defaultExecuteMode = ExecuteMode.getCallbackExecuteMode();
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        long chatId = UpdateParameter.getChatId(update);
        ResponseWaitingMap.remove(chatId);

        ToDoList toDoList = new ToDoList();
        toDoList.addToDo(ExecuteMode.DELETE, update);
        return toDoList;
    }
}
