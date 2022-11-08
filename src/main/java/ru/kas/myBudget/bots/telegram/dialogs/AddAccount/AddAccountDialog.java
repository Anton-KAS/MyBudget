package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.Callback;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.commands.Command;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.services.*;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.CALLBACK_STEP_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.FIRST_STEP_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.CASH_ID;

public class AddAccountDialog implements Dialog, Callback, Command {
    private final AddAccountContainer addAccountContainer;
    private final Map<Long, Map<String, String>> dialogsMap;

    public AddAccountDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                            CallbackContainer callbackContainer,
                            AccountTypeService accountTypeService, CurrencyService currencyService,
                            BankService bankService, AccountService accountService) {
        this.addAccountContainer = new AddAccountContainer(botMessageService, telegramUserService, callbackContainer,
                accountTypeService, currencyService, bankService, accountService);
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        long chatId = getChatId(update);
        Map<String, String> dialogSteps = dialogsMap.get(chatId);
        int currentStep;
        if (dialogSteps == null || dialogSteps.isEmpty() ||
                (update.hasCallbackQuery() &&
                        getCallbackData(update)[CALLBACK_STEP_INDEX.getIndex()].equals(START.getDialogId()))) {
            currentStep = FIRST_STEP_INDEX.getIndex();
        } else {
            currentStep = Integer.parseInt(dialogSteps.get(CURRENT_DIALOG_STEP.getDialogId()));
        }

        if (addAccountContainer.retrieve(AddAccountName.getDialogNameByOrder(currentStep)).commit(update)) {
            dialogSteps = dialogsMap.get(chatId);
            currentStep++;
        }
        if (dialogSteps != null && (dialogSteps.get(TYPE.getDialogId()) == null
                || dialogSteps.get(TYPE.getDialogId()).equals(CASH_ID.getId()))
                && AddAccountName.getDialogNameByOrder(currentStep).equals(BANK.getDialogId())) {
            currentStep++;
        }

        assert dialogSteps != null;
        dialogSteps.replace(CURRENT_DIALOG_STEP.getDialogId(), String.valueOf(currentStep));
        addAccountContainer.retrieve(AddAccountName.getDialogNameByOrder(currentStep)).execute(update);
    }

    @Override
    public boolean commit(Update update) {
        return true;
    }
}
