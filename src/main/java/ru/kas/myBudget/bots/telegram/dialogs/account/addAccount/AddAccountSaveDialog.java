package ru.kas.myBudget.bots.telegram.dialogs.account.addAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames;
import ru.kas.myBudget.bots.telegram.dialogs.account.SaveDialog;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.*;
import ru.kas.myBudget.services.*;

import java.math.BigDecimal;

public class AddAccountSaveDialog extends SaveDialog {
    public AddAccountSaveDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                MessageText messageText, Keyboard keyboard, CallbackContainer callbackContainer,
                                AccountTypeService accountTypeService, CurrencyService currencyService,
                                BankService bankService, AccountService accountService) {
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
        TelegramUser telegramUser = telegramUserService.findById(userId).orElse(null);
        Currency currency = currencyService.findById(Integer.parseInt(dialogMap.get(AccountNames.CURRENCY.getName()))).orElse(null);
        AccountType accountType = accountTypeService.findById(Integer.parseInt(dialogMap.get(AccountNames.TYPE.getName()))).orElse(null);

        Account account = new Account(dialogMap.get(AccountNames.TITLE.getName()), dialogMap.get(AccountNames.DESCRIPTION.getName()),
                startBalance, startBalance, telegramUser, currency, accountType, bank);
        account.setCurrentBalanceWithScale(startBalance);
        account.setStartBalanceWithScale(startBalance);
        accountService.save(account);
    }
}
