package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.*;
import ru.kas.myBudget.services.*;

import java.math.BigDecimal;
import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.START_FROM_CALLBACK;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.START_FROM_ID;

public class AddAccountSaveDialog extends DialogImpl {
    protected final CallbackContainer callbackContainer;
    protected final CurrencyService currencyService;
    protected final AccountTypeService accountTypeService;
    protected final BankService bankService;
    protected final AccountService accountService;
    protected Map<String, String> dialogMap;

    public AddAccountSaveDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                MessageText messageText, Keyboard keyboard,
                                CallbackContainer callbackContainer, AccountTypeService accountTypeService,
                                CurrencyService currencyService, BankService bankService, AccountService accountService) {
        super(botMessageService, telegramUserService, messageText, keyboard, null);
        this.callbackContainer = callbackContainer;
        this.currencyService = currencyService;
        this.accountTypeService = accountTypeService;
        this.bankService = bankService;
        this.accountService = accountService;
    }

    @Override
    public void setData(Update update) {
        this.userId = UpdateParameter.getUserId(update);
        this.chatId = UpdateParameter.getChatId(update);
        this.dialogMap = DialogsMap.getDialogMap(chatId);

        Bank bank = getBank();
        BigDecimal startBalance = getStartBalance();
        TelegramUser telegramUser = telegramUserService.findById(userId).orElse(null);
        Currency currency = currencyService.findById(Integer.parseInt(dialogMap.get(CURRENCY.getName()))).orElse(null);
        AccountType accountType = accountTypeService.findById(Integer.parseInt(dialogMap.get(TYPE.getName()))).orElse(null);

        Account account = new Account(dialogMap.get(TITLE.getName()), dialogMap.get(DESCRIPTION.getName()),
                startBalance, startBalance, telegramUser, currency, accountType, bank);
        account.setCurrentBalanceWithScale(startBalance);
        account.setStartBalanceWithScale(startBalance);
        accountService.save(account);
    }

    @Override
    public void executeData(Update update, ExecuteMode executeMode) {
        botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                messageText.setUserId(userId).getText(), keyboard.getKeyboard());
        String fromStartId = dialogMap.get(START_FROM_ID.getId());
        update.getCallbackQuery().setData(DialogsMap.getDialogStepById(chatId, START_FROM_CALLBACK.getId()));
        DialogsMap.remove(chatId);
        ResponseWaitingMap.remove(chatId);
        if (fromStartId != null) callbackContainer.retrieve(fromStartId).execute(update, ExecuteMode.SEND);
    }

    protected Bank getBank() {
        if (dialogMap.get(BANK.getName()) != null &&
                bankService.findById(Integer.parseInt(dialogMap.get(BANK.getName()))).isPresent())
            return bankService.findById(Integer.parseInt(dialogMap.get(BANK.getName()))).get();

        else return null;
    }

    public BigDecimal getStartBalance() {
        String startBalanceString = dialogMap.get(START_BALANCE.getName());
        if (startBalanceString == null) return new BigDecimal(0);

        else return new BigDecimal(startBalanceString);
    }

}
