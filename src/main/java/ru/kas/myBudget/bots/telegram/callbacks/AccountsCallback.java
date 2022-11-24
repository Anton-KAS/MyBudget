package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.CommandControllerImpl;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNT;

/**
 * Отправка в Telegram Bot меню с информацией о всех счетах пользователя.
 * <br>Тригеры:
 * <br>- CallbackData содержит ACCOUNTS из списка {@link CallbackNamesImpl}
 * <br>- Текстовая команда ACCOUNTS из списка {@link ru.kas.myBudget.bots.telegram.commands.CommandNamesImpl}
 * @author Anton Komrachkov
 * @version 1.0
 */

public class AccountsCallback extends CommandControllerImpl {

    public AccountsCallback(BotMessageService botMessageService, TelegramUserService telegramUserService,
                            ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
        super.executeByOrder(update, executeMode);
        ResponseWaitingMap.put(UpdateParameter.getChatId(update), ACCOUNT);
    }
}
