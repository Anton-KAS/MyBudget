package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.services.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.START_FROM_ID;

public class SaveDialog implements Dialog, CommandController {
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
        long userId = UpdateParameter.getUserId(update);

        Map<String, String> dialogMap = dialogsMap.get(userId);
        try {
            Bank bank;
            if (dialogMap.get(BANK.getDialogId()) != null &&
                    bankService.findById(Integer.parseInt(dialogMap.get(BANK.getDialogId()))).isPresent()) {
                bank = bankService.findById(Integer.parseInt(dialogMap.get(BANK.getDialogId()))).get();
            } else {
                bank = null;
            }

            Optional<Currency> currency = currencyService.findById(Integer.parseInt(dialogMap.get(CURRENCY.getDialogId())));
            int numberToBAsic = currency.map(Currency::getNumberToBasic).orElse(1);

            String startBalanceString = dialogMap.get(START_BALANCE.getDialogId());
            BigDecimal startBalance;
            if (startBalanceString == null) startBalance = new BigDecimal(0);
            else startBalance = new BigDecimal(startBalanceString).multiply(new BigDecimal(numberToBAsic))
                    .setScale(String.valueOf(numberToBAsic).length() - 1, RoundingMode.HALF_UP);

            Account account = new Account(
                    dialogMap.get(TITLE.getDialogId()),
                    dialogMap.get(DESCRIPTION.getDialogId()),
                    startBalance,
                    startBalance,
                    telegramUserService.findById(userId).get(),
                    currencyService.findById(Integer.parseInt(dialogMap.get(CURRENCY.getDialogId()))).get(),
                    accountTypeService.findById(Integer.parseInt(dialogMap.get(TYPE.getDialogId()))).get(),
                    bank
            );

            accountService.save(account);

            botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                    CONFIRM_TEXT, null);
        } catch (Exception ignore) {
            //TODO Add project Logger
            botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                    EXCEPTION_TEXT, null);
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

    @Override
    public void skip(Update update) {

    }
}
