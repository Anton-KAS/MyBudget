package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.models.Account;
import komrachkov.anton.mybudget.models.AccountType;
import komrachkov.anton.mybudget.models.Bank;
import komrachkov.anton.mybudget.models.Currency;
import komrachkov.anton.mybudget.services.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.SaveDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsMap;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;

import java.math.BigDecimal;

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
        account.setCurrency(currency);
        account.setStartBalanceWithScale(startBalance);
        account.setCurrentBalanceWithScale(startBalance); // TODO : Something not good...
        account.setAccountType(accountType);
        account.setBank(bank);
        accountService.save(account);
    }
}
