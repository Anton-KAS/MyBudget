package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.util.*;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.CancelKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.CancelText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class CancelCommand extends CommandControllerImpl {

    @Autowired
    public CancelCommand(TelegramUserService telegramUserService, CancelText messageText, CancelKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (04.12.2022)
     */
    @Override
    public void setDefaultExecuteMode() {
        this.defaultExecuteMode = ExecuteMode.getCommandExecuteMode();
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        ToDoList toDoList = super.execute(update, executeMode);
        long chatId = UpdateParameter.getChatId(update);
        ResponseWaitingMap.remove(chatId);
        DialogsState.removeAllDialogs(chatId);
        return toDoList;
    }
}
