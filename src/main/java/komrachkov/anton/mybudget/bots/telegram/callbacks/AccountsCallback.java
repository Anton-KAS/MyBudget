package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.commands.CommandNamesImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

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
