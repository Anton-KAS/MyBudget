package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogPattern;
import komrachkov.anton.mybudget.bots.telegram.keyboards.callback.AccountKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.AccountText;
import komrachkov.anton.mybudget.bots.telegram.util.*;
import komrachkov.anton.mybudget.models.TelegramUser;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Отправка в Telegram Bot меню с информацией о счете пользователя.
 * <br>При вызове метода {@code execute(Update update)} должно выполняться одно из условий:
 * <p> -  Update содержит CallbackQuery c CallbackData порядковым номером счета пользователя в списке выдачи БД
 * в OPERATOR_DATA (см. {@link CallbackIndex})
 * <p> -  Update содержит текстовую комманду "/n", где n - порядковый номер счета пользователя в списке выдачи БД
 *
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class AccountCallback extends CommandControllerImpl {
    private final AccountKeyboard accountKeyboard;

    @Autowired
    public AccountCallback(TelegramUserService telegramUserService, AccountText messageText, AccountKeyboard keyboard) {
        super(telegramUserService, messageText, keyboard);
        this.accountKeyboard = keyboard;
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
        ToDoList toDoList = null;

        Integer accountId = null;
        if (update.hasCallbackQuery()) {
            String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
            if (callbackData != null) {
                accountId = Integer.parseInt(callbackData[CallbackIndex.OPERATION_DATA.ordinal()]);
            }

        } else {
            String textData = UpdateParameter.getMessageText(update);
            if (textData.matches(DialogPattern.EDIT_NUM.getRegex())) {
                int accountNum = Integer.parseInt(textData.replace("/", "").trim()) - 1;
                TelegramUser telegramUser = telegramUserService.findById(UpdateParameter.getUserId(update)).orElse(null);
                if (telegramUser != null) {
                    accountId = telegramUser.getAccounts().get(accountNum).getId();
                    executeMode = ExecuteMode.SEND;
                }
            }
        }
        if (accountId != null) {
            accountKeyboard.setAccountId(accountId);
            toDoList = super.execute(update, executeMode);
        }
        return toDoList;
    }
}
