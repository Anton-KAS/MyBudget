package komrachkov.anton.mybudget.bots.telegram.dialogs.account.add;

import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.SaveKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.models.*;
import komrachkov.anton.mybudget.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.SaveDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;

import java.math.BigDecimal;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
public class AddSaveDialog extends SaveDialog {

    @Autowired
    public AddSaveDialog(TelegramUserService telegramUserService, AccountDialogText messageText, SaveKeyboard keyboard,
                         CallbackContainer callbackContainer, AccountTypeService accountTypeService,
                         CurrencyService currencyService, BankService bankService, AccountService accountService) {
        super(telegramUserService, messageText, keyboard, callbackContainer, accountTypeService,
                currencyService, bankService, accountService);
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        long chatId = UpdateParameter.getChatId(update);

        String title = DialogsState.getByStepId(chatId, TITLE.getName()).orElse(null);
        String description = DialogsState.getByStepId(chatId, DESCRIPTION.getName()).orElse(null);
        BigDecimal startBalance = getStartBalance(chatId);
        TelegramUser telegramUser = telegramUserService.findById(UpdateParameter.getUserId(update)).orElse(null);

        int currencyId = Integer.parseInt(DialogsState.getByStepId(chatId, CURRENCY.getName()).orElse("-1"));
        Currency currency = currencyService.findById(currencyId).orElse(null);

        int accountTypeId = Integer.parseInt(DialogsState.getByStepId(chatId, TYPE.getName()).orElse("-1"));
        AccountType accountType = accountTypeService.findById(accountTypeId).orElse(null);

        Bank bank = getBank(chatId);

        Account account = new Account(title, description, startBalance, startBalance, telegramUser, currency, accountType, bank);
        account.setCurrentBalanceWithScale(startBalance);
        account.setStartBalanceWithScale(startBalance);
        accountService.save(account);

        return super.execute(update, executeMode);
    }
}
