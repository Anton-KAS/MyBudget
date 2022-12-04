package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.callback.AccountsKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.AccountsText;
import komrachkov.anton.mybudget.bots.telegram.util.*;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNT;

/**
 * Отправка в Telegram Bot меню с информацией о всех счетах пользователя.
 * <br>Тригеры:
 * <br>- CallbackData содержит ACCOUNTS из списка {@link CallbackNamesImpl}
 * <br>- Текстовая команда ACCOUNTS из списка {@link CommandNamesImpl}
 *
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class AccountsCallback extends CommandControllerImpl {

    @Autowired
    public AccountsCallback(TelegramUserService telegramUserService, AccountsText messageText, AccountsKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (04.12.2022)
     */
    @Override
    public void setDefaultExecuteMode() {
        this.defaultExecuteMode = ExecuteMode.getCallbackExecuteMode();
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        ToDoList toDoList = super.execute(update, executeMode);
        ResponseWaitingMap.put(UpdateParameter.getChatId(update), ACCOUNT);
        return toDoList;
    }
}
