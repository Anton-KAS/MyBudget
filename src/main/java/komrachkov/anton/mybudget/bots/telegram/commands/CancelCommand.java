package komrachkov.anton.mybudget.bots.telegram.commands;

import komrachkov.anton.mybudget.bots.telegram.commands.util.CommandControllerTestImpl;
import komrachkov.anton.mybudget.bots.telegram.commands.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.keyboards.commands.CancelKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.commands.CancelText;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class CancelCommand extends CommandControllerTestImpl {

    @Autowired
    public CancelCommand(TelegramUserService telegramUserService, CancelText messageText, CancelKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
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
