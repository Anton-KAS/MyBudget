package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.Callback;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.services.*;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.START_FROM_ID;

public class SaveDialog implements Dialog, Callback {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final CallbackContainer callbackContainer;
    private final CurrencyService currencyService;
    private final AccountTypeService accountTypeService;
    private final BankService bankService;
    private final AccountService accountService;
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static String CONFIRM_TEXT = "всё сохранил";
    private final static String EXCEPTION_TEXT = "что-то пошло не так =(";

    public SaveDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                      CallbackContainer callbackContainer, AccountTypeService accountTypeService,
                      CurrencyService currencyService, BankService bankService, AccountService accountService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.callbackContainer = callbackContainer;
        this.currencyService = currencyService;
        this.accountTypeService = accountTypeService;
        this.bankService = bankService;
        this.accountService = accountService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        long chatId = getChatId(update);
        long userId = getUserId(update);

        Map<String, String> dialogMap = dialogsMap.get(userId);
        try {
            Bank bank;
            if (dialogMap.get(BANK.getDialogId()) != null &&
                    bankService.findById(Integer.parseInt(dialogMap.get(BANK.getDialogId()))).isPresent()) {
                bank = bankService.findById(Integer.parseInt(dialogMap.get(BANK.getDialogId()))).get();
            } else {
                bank = null;
            }

            Account account = new Account(
                    dialogMap.get(TITLE.getDialogId()),
                    dialogMap.get(DESCRIPTION.getDialogId()),
                    telegramUserService.findById(userId).get(),
                    currencyService.findById(Integer.parseInt(dialogMap.get(CURRENCY.getDialogId()))).get(),
                    accountTypeService.findById(Integer.parseInt(dialogMap.get(TYPE.getDialogId()))).get(),
                    bank
            );

            accountService.save(account);
            removeInlineKeyboard(telegramUserService, botMessageService, update, ExecuteMode.SEND);
            botMessageService.executeSendMessage(chatId, CONFIRM_TEXT);
        } catch (Exception ignore) {
            //TODO Add project Logger
            botMessageService.executeSendMessage(chatId, EXCEPTION_TEXT);
        }
        String fromStartId = dialogMap.get(START_FROM_ID.getId());
        if (fromStartId != null) {
            callbackContainer.retrieve(fromStartId).execute(update, ExecuteMode.SEND);
        }
        dialogsMap.remove(userId);
    }

    @Override
    public boolean commit(Update update) {
        return true;
    }
}
