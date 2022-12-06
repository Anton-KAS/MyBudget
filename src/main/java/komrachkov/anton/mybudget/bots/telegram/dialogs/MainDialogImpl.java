package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.dialogs.util.MainDialog;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class MainDialogImpl implements MainDialog {
    protected final TelegramUserService telegramUserService;
    protected ExecuteMode defaultExecuteMode;

    public MainDialogImpl(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    @Override
    public abstract ToDoList execute(Update update, ExecuteMode executeMode);

    @Override
    public ToDoList execute(Update update) {
        setDefaultExecuteMode(update);
        return execute(update, defaultExecuteMode);
    }

    @Override
    public void setDefaultExecuteMode(Update update) {
        defaultExecuteMode = ExecuteMode.SEND;
    }
}
