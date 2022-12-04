package komrachkov.anton.mybudget.bots.telegram.dialogs;

import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DeleteConfirmDialogKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.DeleteConfirmDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class DeleteConfirmDialog extends MainDialogImpl {
    private final DeleteConfirmDialogKeyboard keyboard;
    private final DeleteConfirmDialogText messageText;

    @Autowired
    public DeleteConfirmDialog(TelegramUserService telegramUserService, DeleteConfirmDialogText messageText,
                               DeleteConfirmDialogKeyboard keyboard) {
        super(telegramUserService);
        this.messageText = messageText;
        this.keyboard = keyboard;
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        String text = messageText.setChatId(UpdateParameter.getChatId(update)).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = keyboard.setUpdate(update).getKeyboard();

        ToDoList toDoList = new ToDoList();
        toDoList.addToDo(executeMode, update, text, inlineKeyboardMarkup);
        return toDoList;
    }
}
