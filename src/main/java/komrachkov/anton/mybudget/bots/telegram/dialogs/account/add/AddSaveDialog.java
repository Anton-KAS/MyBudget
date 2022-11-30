package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.models.*;
import komrachkov.anton.mybudget.services.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.SaveDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;

import java.math.BigDecimal;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class AddSaveDialog extends SaveDialog {
    public AddSaveDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                         MessageText messageText, Keyboard keyboard, CallbackContainer callbackContainer,
                         AccountTypeService accountTypeService, CurrencyService currencyService,
                         BankService bankService, AccountService accountService) {
        super(botMessageService, telegramUserService, messageText, keyboard, callbackContainer, accountTypeService,
                currencyService, bankService, accountService);
    }

    @Override
    public void setData(Update update) {
        super.setData(update);
        if (dialogMap == null) return;

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
