package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.DescriptionKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
@Scope("prototype")
public class DescriptionDialog extends DialogImpl {
    public final static int MAX_DESCRIPTION_LENGTH = 100;
    private final static String ASK_TEXT = "Введите описание счета:";
    public final static String VERIFY_EXCEPTION_TEXT = "Описание должно быть до %s символов";

    @Autowired
    public DescriptionDialog(TelegramUserService telegramUserService, AccountDialogText messageText, DescriptionKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard, ASK_TEXT);
    }

    @Override
    public ToDoList commit(Update update) {
        ToDoList toDoList = new ToDoList();

        if (update.hasCallbackQuery()) return toDoList;

        long chatId = UpdateParameter.getChatId(update);
        String text = UpdateParameter.getMessageText(update);

        if (text.length() > MAX_DESCRIPTION_LENGTH) {
            toDoList.addToDo(ExecuteMode.SEND, update, String.format(VERIFY_EXCEPTION_TEXT, MAX_DESCRIPTION_LENGTH));
            return toDoList;
        }

        addToDialogMap(chatId, AccountNames.DESCRIPTION, text, String.format(AccountNames.DESCRIPTION.getStepTextPattern(), "%s", text));
        toDoList.setResultCommit(true);
        return toDoList;
    }
}
