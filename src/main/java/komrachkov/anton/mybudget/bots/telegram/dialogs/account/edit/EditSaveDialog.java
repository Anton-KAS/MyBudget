package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.models.Account;
import komrachkov.anton.mybudget.models.AccountType;
import komrachkov.anton.mybudget.models.Bank;
import komrachkov.anton.mybudget.models.Currency;
import komrachkov.anton.mybudget.services.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.SaveDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;

import java.math.BigDecimal;
import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class EditSaveDialog extends SaveDialog {

    public EditSaveDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                          MessageText messageText, Keyboard keyboard,
                          CallbackContainer callbackContainer, AccountTypeService accountTypeService,
                          CurrencyService currencyService, BankService bankService, AccountService accountService) {
        super(botMessageService, telegramUserService, messageText, keyboard, callbackContainer, accountTypeService,
                currencyService, bankService, accountService);
    }

    @Override
    public void setData(Update update) {
        super.setData(update);

        Optional<String> accountIdString = DialogsState.getDialogStepById(chatId, EDIT_ID.getId());
        if (accountIdString.isEmpty()) return;

        int accountId = Integer.parseInt(accountIdString.get());
        Account account = accountService.findById(accountId).orElse(new Account());

        String title = DialogsState.getByStepId(chatId, TITLE.getName()).orElse(null);
        account.setTitle(title);

        String description = DialogsState.getByStepId(chatId, DESCRIPTION.getName()).orElse(null);
        account.setDescription(description);

        int currencyId = Integer.parseInt(DialogsState.getByStepId(chatId, CURRENCY.getName()).orElse("-1"));
        Currency currency = currencyService.findById(currencyId).orElse(null);
        account.setCurrency(currency);

        BigDecimal startBalance = getStartBalance();
        account.setStartBalanceWithScale(startBalance);
        account.setCurrentBalanceWithScale(startBalance); // TODO : Something not good...

        int accountTypeId = Integer.parseInt(DialogsState.getByStepId(chatId, TYPE.getName()).orElse("-1"));
        AccountType accountType = accountTypeService.findById(accountTypeId).orElse(null);
        account.setAccountType(accountType);

        Bank bank = getBank();
        account.setBank(bank);
        accountService.save(account);
    }
}
