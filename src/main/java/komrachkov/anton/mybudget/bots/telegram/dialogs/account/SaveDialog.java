package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.DialogKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.*;
import komrachkov.anton.mybudget.models.Bank;
import komrachkov.anton.mybudget.services.*;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;
import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.BANK;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.START_BALANCE;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.START_FROM_ID;

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

    public SaveDialog(TelegramUserService telegramUserService, AccountDialogText messageText, DialogKeyboard keyboard,
                      CallbackContainer callbackContainer, AccountTypeService accountTypeService,
                      CurrencyService currencyService, BankService bankService, AccountService accountService) {
        super(telegramUserService, messageText, keyboard, null);
        this.callbackContainer = callbackContainer;
        this.currencyService = currencyService;
        this.accountTypeService = accountTypeService;
        this.bankService = bankService;
        this.accountService = accountService;
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        ToDoList toDoList = new ToDoList();
        toDoList.addToDo(ExecuteMode.POPUP, update, messageText.setChatId(UpdateParameter.getChatId(update)).getText());

        long chatId = UpdateParameter.getChatId(update);
        Optional<String> stepId = DialogsState.getDialogStepById(chatId, DialogMapDefaultName.START_FROM_CALLBACK.getId());
        stepId.ifPresent(s -> update.getCallbackQuery().setData(s));

        Optional<String> fromStartIdOpt = DialogsState.getByStepId(chatId, START_FROM_ID.getId());

        DialogsState.removeDialog(chatId);
        ResponseWaitingMap.remove(chatId);

        Optional<CommandNames> dialogCommandName = DialogsState.getCommandName(chatId);
        if (dialogCommandName.isPresent()) {
            ResponseWaitingMap.put(chatId, dialogCommandName.get());
            Optional<CommandNames> commandNameOpt = DialogsState.getCommandName(chatId);
            commandNameOpt.ifPresent(s -> toDoList.addAll(callbackContainer.retrieve(s.getName()).execute(update, ExecuteMode.SEND)));
        } else {
            fromStartIdOpt.ifPresent(s -> toDoList.addAll(callbackContainer.retrieve(s).execute(update, ExecuteMode.SEND)));
        }
        return toDoList;
    }

    protected Bank getBank(long chatId) {
        Optional<String> bankOpt = DialogsState.getByStepId(chatId, BANK.getName());
        if (bankOpt.isPresent() && bankService.findById(Integer.parseInt(bankOpt.get())).isPresent())
            return bankService.findById(Integer.parseInt(bankOpt.get())).get();
        else return null;
    }

    public BigDecimal getStartBalance(long chatId) {
        Optional<String> startBalanceOpt = DialogsState.getByStepId(chatId, START_BALANCE.getName());
        if (startBalanceOpt.isEmpty()) return new BigDecimal(0);
        return new BigDecimal(startBalanceOpt.get());
    }
}
