package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.models.*;
import komrachkov.anton.mybudget.services.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.SaveDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;

import java.math.BigDecimal;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;

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

        String title = DialogsState.getByStepId(chatId, TITLE.getName()).orElse(null);
        String description = DialogsState.getByStepId(chatId, DESCRIPTION.getName()).orElse(null);
        BigDecimal startBalance = getStartBalance();
        TelegramUser telegramUser = telegramUserService.findById(userId).orElse(null);

        int currencyId = Integer.parseInt(DialogsState.getByStepId(chatId, CURRENCY.getName()).orElse("-1"));
        Currency currency = currencyService.findById(currencyId).orElse(null);

        int accountTypeId = Integer.parseInt(DialogsState.getByStepId(chatId, TYPE.getName()).orElse("-1"));
        AccountType accountType = accountTypeService.findById(accountTypeId).orElse(null);

        Bank bank = getBank();

        Account account = new Account(title, description, startBalance, startBalance, telegramUser, currency, accountType, bank);
        account.setCurrentBalanceWithScale(startBalance);
        account.setStartBalanceWithScale(startBalance);
        accountService.save(account);
    }
}
