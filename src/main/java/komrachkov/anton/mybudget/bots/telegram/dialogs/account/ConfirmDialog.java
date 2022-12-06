package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.DialogKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class ConfirmDialog extends DialogImpl {
    protected final static String ASK_TEXT = "Всё готово! Сохранить?";
    protected final static String VERIFY_EXCEPTION_TEXT = "В этом диалоге такая комманда не поддерживается";
    protected String[] callbackData;

    public ConfirmDialog(TelegramUserService telegramUserService, MessageText messageText, DialogKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard, ASK_TEXT);
    }

    @Override
    public ToDoList commit(Update update) {
        ToDoList toDoList = new ToDoList();

        this.callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        if (callbackData == null || callbackData.length <= CallbackIndex.OPERATION_DATA.ordinal()
                || !callbackData[CallbackIndex.OPERATION_DATA.ordinal()].equals("save")) {
            toDoList.addToDo(ExecuteMode.SEND, update, VERIFY_EXCEPTION_TEXT);
            return toDoList;
        }
        toDoList.setResultCommit(true);
        return toDoList;
    }
}
