package ru.kas.myBudget.bots.telegram.dialogs.account.editAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.account.SaveDialog;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.*;
import ru.kas.myBudget.services.*;

import java.math.BigDecimal;

import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.*;

public class EditAccountSaveDialog extends SaveDialog {

    public EditAccountSaveDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                 MessageText messageText, Keyboard keyboard,
                                 CallbackContainer callbackContainer, AccountTypeService accountTypeService,
                                 CurrencyService currencyService, BankService bankService, AccountService accountService) {
        super(botMessageService, telegramUserService, messageText, keyboard, callbackContainer, accountTypeService,
                currencyService, bankService, accountService);
    }

    @Override
    public void setData(Update update) {
        this.userId = UpdateParameter.getUserId(update);
        this.chatId = UpdateParameter.getChatId(update);
        this.dialogMap = DialogsMap.getDialogMap(chatId);

        Bank bank = getBank();
        BigDecimal startBalance = getStartBalance();
        Currency currency = currencyService.findById(Integer.parseInt(dialogMap.get(CURRENCY.getName()))).orElse(null);
        AccountType accountType = accountTypeService.findById(Integer.parseInt(dialogMap.get(TYPE.getName()))).orElse(null);

        String accountIdString = DialogsMap.getDialogStepById(chatId, EDIT_ID.getId());
        if (accountIdString == null) return;

        int accountId = Integer.parseInt(accountIdString);
        Account account = accountService.findById(accountId).orElse(new Account());
        account.setTitle(dialogMap.get(TITLE.getName()));
        account.setDescription(dialogMap.get(DESCRIPTION.getName()));
        account.setStartBalanceWithScale(startBalance);
        account.setCurrentBalanceWithScale(startBalance); // TODO : Something not good...
        account.setCurrency(currency);
        account.setAccountType(accountType);
        account.setBank(bank);
        accountService.save(account);
    }
}
