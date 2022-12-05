package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.dialogs.util.CommandDialogNames;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.keyboards.UnknownKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.UnknownText;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class UnknownDialog extends CommandControllerImpl implements Dialog {

    @Autowired
    public UnknownDialog(TelegramUserService telegramUserService, UnknownText messageText, UnknownKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
    }

    @Override
    public ToDoList commit(Update update) {
        ToDoList toDoList = new ToDoList();
        toDoList.setResultCommit(false);
        return toDoList;
    }

    @Override
    public ToDoList skip(Update update) {
        return new ToDoList();
    }

    @Override
    public void addToDialogMap(long userId, CommandDialogNames name, String stringId, String text) {
    }

    @Override
    public ExecuteMode autoDefineExecuteMode(Update update, Integer dialogStep) {
        return ExecuteMode.SEND;
    }

    @Override
    public Dialog setCurrentDialogName(String dialogName) {
        return this;
    }

    @Override
    public void setDefaultExecuteMode(Update update) {
        defaultExecuteMode = ExecuteMode.SEND;
    }
}
