package komrachkov.anton.mybudget.bots.telegram.callbacks;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogPattern;
import komrachkov.anton.mybudget.bots.telegram.keyboards.callback.AccountKeyboard;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.AccountText;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.models.TelegramUser;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.keyboards.callback.NoKeyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.NoText;
import komrachkov.anton.mybudget.bots.telegram.util.CommandControllerImpl;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.services.AccountService;

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

public class AccountCallback extends CommandControllerImpl {
    private final AccountText accountText = (AccountText) messageText;
    private final AccountService accountService;
    private final AccountKeyboard accountKeyboard = (AccountKeyboard) keyboard;

    public AccountCallback(BotMessageService botMessageService, TelegramUserService telegramUserService,
                           ExecuteMode defaultExecuteMode, MessageText messageText, Keyboard keyboard,
                           AccountService accountService) {
        super(botMessageService, telegramUserService, defaultExecuteMode, messageText, keyboard);
        this.accountService = accountService;
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
        ResponseWaitingMap.remove(UpdateParameter.getChatId(update));
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
            accountText.setAccountService(accountService).setAccountId(accountId);
            super.executeByOrder(update, executeMode);
        } else {
            new UnknownCallback(botMessageService, telegramUserService, executeMode, new NoText(), new NoKeyboard());
        }
    }
}
