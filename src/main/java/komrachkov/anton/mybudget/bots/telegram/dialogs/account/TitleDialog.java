package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.TitleKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
@Scope("prototype")
public class TitleDialog extends DialogImpl {
    public final static int MIN_TITLE_LENGTH = 2;
    public final static int MAX_TITLE_LENGTH = 30;
    private final static String ASK_TEXT = "Введите название счета:";
    public final static String VERIFY_EXCEPTION_TEXT = "Название должно быть от %s и до %s символов";

    @Autowired
    public TitleDialog(TelegramUserService telegramUserService, AccountDialogText messageText, TitleKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard, ASK_TEXT);
    }

    @Override
    public ToDoList commit(Update update) {
        ToDoList toDoList = new ToDoList();
        if (update.hasCallbackQuery()) return toDoList;

        String text = UpdateParameter.getMessageText(update);

        if (text.length() < MIN_TITLE_LENGTH || text.length() > MAX_TITLE_LENGTH) {
            toDoList.addToDo(ExecuteMode.SEND, update, String.format(VERIFY_EXCEPTION_TEXT, MIN_TITLE_LENGTH, MAX_TITLE_LENGTH));
            return toDoList;
        }

        long chatId = UpdateParameter.getUserId(update);
        addToDialogMap(chatId, AccountNames.TITLE, text, String.format(AccountNames.TITLE.getStepTextPattern(), "%s", text));
        toDoList.setResultCommit(true);
        return toDoList;
    }
}
