package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.commands.AccountsCommand;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.bots.telegram.texts.SaveDialogText;
import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.services.*;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.*;

public class AddAccountSaveCallbackDialog implements Callback {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final CurrencyService currencyService;
    private final AccountTypeService accountTypeService;
    private final BankService bankService;
    private final AccountService accountService;
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static int BANK_TYPE_ID_INDEX = 2;

    public AddAccountSaveCallbackDialog(SendBotMessageService sendBotMessageService,
                                        TelegramUserService telegramUserService, CurrencyService currencyService,
                                        AccountTypeService accountTypeService, BankService bankService,
                                        AccountService accountService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.currencyService = currencyService;
        this.accountTypeService = accountTypeService;
        this.bankService = bankService;
        this.accountService = accountService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        String text = new SaveDialogText().getText();
        long chatId = getChatId(update);
        long userId = getUserId(update);

        sendBotMessageService.sendMessage(chatId, text);

        Map<String, String> dialogMap = dialogsMap.get(userId);

        Account account = new Account(
                dialogMap.get(ADD_ACCOUNT_TITLE.getDialogName()),
                dialogMap.get(ADD_ACCOUNT_DESCRIPTION.getDialogName()),
                telegramUserService.findById(userId).get(),
                currencyService.findById(Integer.parseInt(dialogMap.get(ADD_ACCOUNT_CURRENCY.getCallbackName()))).get(),
                accountTypeService.findById(Integer.parseInt(dialogMap.get(ADD_ACCOUNT_TYPE.getCallbackName()))).get(),
                bankService.findById(Integer.parseInt(dialogMap.get(ADD_ACCOUNT_BANK.getCallbackName()))).get()
                );

        accountService.save(account);

        new AccountsCommand(sendBotMessageService, telegramUserService).execute(update);
    }
}
