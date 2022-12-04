package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.StatKeyboard;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.StatText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;

/**
 * @author Anton Komrachkov
 * @since 0.1
 */

@Component
public class StatCommand extends CommandControllerImpl {
    @Autowired
    public StatCommand(TelegramUserService telegramUserService, StatText messageText, StatKeyboard keyboard) {
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
        int activeUserCount = telegramUserService.retrieveAllActiveUsers().size();
        StatText statText = (StatText) messageText;
        long chatId = UpdateParameter.getChatId(update);
        this.text = statText.setChatId(chatId).setActiveUserCount(activeUserCount).getText();
        this.inlineKeyboardMarkup = keyboard.getKeyboard();

        ToDoList toDoList = new ToDoList();
        toDoList.addToDo(executeMode, update, text, inlineKeyboardMarkup);
        return toDoList;
    }
}
