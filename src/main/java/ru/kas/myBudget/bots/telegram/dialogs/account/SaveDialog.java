package ru.kas.myBudget.bots.telegram.dialogs.account;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.services.*;

import java.math.BigDecimal;
import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.START_FROM_CALLBACK;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.START_FROM_ID;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class SaveDialog extends DialogImpl {
    protected final CallbackContainer callbackContainer;
    protected final CurrencyService currencyService;
    protected final AccountTypeService accountTypeService;
    protected final BankService bankService;
    protected final AccountService accountService;
    protected Map<String, String> dialogMap;

    public SaveDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
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

    abstract public void setData(Update update);

    @Override
    public void executeData(Update update, ExecuteMode executeMode) {
        botMessageService.sendPopup(UpdateParameter.getCallbackQueryId(update).orElse(null),
                messageText.setUserId(userId).getText());
        String fromStartId = dialogMap.get(START_FROM_ID.getId());
        update.getCallbackQuery().setData(DialogsMap.getDialogStepById(chatId, START_FROM_CALLBACK.getId()));
        DialogsMap.remove(chatId);
        ResponseWaitingMap.remove(chatId);
        if (fromStartId != null) callbackContainer.retrieve(fromStartId).execute(update, ExecuteMode.SEND);
    }

    protected Bank getBank() {
        if (dialogMap.get(AccountNames.BANK.getName()) != null &&
                bankService.findById(Integer.parseInt(dialogMap.get(AccountNames.BANK.getName()))).isPresent())
            return bankService.findById(Integer.parseInt(dialogMap.get(AccountNames.BANK.getName()))).get();

        else return null;
    }

    public BigDecimal getStartBalance() {
        String startBalanceString = dialogMap.get(AccountNames.START_BALANCE.getName());
        if (startBalanceString == null) return new BigDecimal(0);

        else return new BigDecimal(startBalanceString);
    }
}
