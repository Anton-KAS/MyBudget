package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.CommandNames;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.models.Bank;
import komrachkov.anton.mybudget.services.*;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

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

    public void setData(Update update) {
        this.userId = UpdateParameter.getUserId(update);
        this.chatId = UpdateParameter.getChatId(update);
        this.dialogMap = DialogsState.getDialogStateMap(chatId).orElse(null);
    }

    @Override
    public void executeData(Update update, ExecuteMode executeMode) {
        botMessageService.sendPopup(UpdateParameter.getCallbackQueryId(update).orElse(null),
                messageText.setUserId(userId).getText());
        String fromStartId = dialogMap.get(DialogMapDefaultName.START_FROM_ID.getId());

        Optional<String> stepId = DialogsState.getDialogStepById(chatId, DialogMapDefaultName.START_FROM_CALLBACK.getId());
        stepId.ifPresent(s -> update.getCallbackQuery().setData(s));

        DialogsState.removeDialog(chatId);
        ResponseWaitingMap.remove(chatId);

        Optional<CommandNames> dialogCommandName = DialogsState.getCommandName(chatId);
        if (dialogCommandName.isPresent()) {
            ResponseWaitingMap.put(chatId, dialogCommandName.get());
            // TODO: Add return back point to previous Dialog
        } else {
            if (fromStartId == null) return;
            callbackContainer.retrieve(fromStartId).execute(update, ExecuteMode.SEND);
        }

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
