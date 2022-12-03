package komrachkov.anton.mybudget.bots.telegram.commands.util;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

/**
 * @author Anton Komrachkov
 * @since (03.12.2022)
 */

public abstract class CommandControllerTestImpl implements CommandControllerTest{
    protected final TelegramUserService telegramUserService;
    protected final ExecuteMode defaultExecuteMode;
    protected final MessageText messageText;
    protected final Keyboard keyboard;

    protected String text;
    protected InlineKeyboardMarkup inlineKeyboardMarkup;

    protected CommandControllerTestImpl(TelegramUserService telegramUserService, MessageText messageText, Keyboard keyboard) {
        this.telegramUserService = telegramUserService;
        this.defaultExecuteMode = CommandExecuteMode.getExecuteMode();
        this.messageText = messageText;
        this.keyboard = keyboard;
    }

    @Override
    public ToDoList execute(Update update) {
        return execute(update, defaultExecuteMode);
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        ResponseWaitingMap.remove(UpdateParameter.getChatId(update));
        setData(update);
        ToDoList toDoList = new ToDoList();
        toDoList.addToDo(executeMode, update, text, inlineKeyboardMarkup);
        return toDoList;
    }

    protected void setData(Update update) {
        if (messageText != null) this.text = messageText.setChatId(UpdateParameter.getChatId(update)).getText();
        if (keyboard != null) this.inlineKeyboardMarkup = keyboard.getKeyboard();
    }
}
