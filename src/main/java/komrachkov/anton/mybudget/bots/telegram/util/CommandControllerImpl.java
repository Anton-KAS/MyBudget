package komrachkov.anton.mybudget.bots.telegram.util;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class CommandControllerImpl implements CommandController {
    protected final TelegramUserService telegramUserService;
    protected ExecuteMode defaultExecuteMode;
    protected final MessageText messageText;
    protected final Keyboard keyboard;

    protected String text;
    protected InlineKeyboardMarkup inlineKeyboardMarkup;

    protected CommandControllerImpl(TelegramUserService telegramUserService, MessageText messageText, Keyboard keyboard) {
        this.telegramUserService = telegramUserService;
        this.messageText = messageText;
        this.keyboard = keyboard;
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (04.12.2022)
     */
    @Override
    public void setDefaultExecuteMode(Update update) {
        this.defaultExecuteMode = autoDefineExecuteMode(update);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (04.12.2022)
     */
    protected ExecuteMode autoDefineExecuteMode(Update update) {
        if (update.hasCallbackQuery()) return ExecuteMode.EDIT;
        return ExecuteMode.SEND;
    }

    @Override
    public ToDoList execute(Update update) {
        setDefaultExecuteMode(update);
        return execute(update, defaultExecuteMode);
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        long chatId = UpdateParameter.getChatId(update);
        ResponseWaitingMap.remove(chatId);
        if (messageText != null) this.text = messageText.setChatId(chatId).getText();
        if (keyboard != null) this.inlineKeyboardMarkup = keyboard.getKeyboard();

        ToDoList toDoList = new ToDoList();
        toDoList.addToDo(executeMode, update, text, inlineKeyboardMarkup);
        return toDoList;
    }
}
