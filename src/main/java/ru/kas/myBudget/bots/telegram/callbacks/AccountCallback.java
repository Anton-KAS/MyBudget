package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.keyboards.callback.AccountKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.callback.NoKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.texts.callback.AccountText;
import ru.kas.myBudget.bots.telegram.texts.callback.NoText;
import ru.kas.myBudget.bots.telegram.util.CommandControllerImpl;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.TelegramUserService;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackIndex.OPERATION_DATA;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogPattern.EDIT_NUM;

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
                accountId = Integer.parseInt(callbackData[OPERATION_DATA.getIndex()]);
            }
        } else {
            String textData = UpdateParameter.getMessageText(update);
            if (textData.matches(EDIT_NUM.getRegex())) {
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
