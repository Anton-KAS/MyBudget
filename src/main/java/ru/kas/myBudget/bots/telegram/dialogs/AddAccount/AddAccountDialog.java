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

import static ru.kas.myBudget.bots.telegram.bot.TelegramBot.COMMAND_PREFIX;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.*;

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
        Integer currentStep;
        int lastStep;
        if (dialogSteps == null || dialogSteps.isEmpty() ||
                (update.hasCallbackQuery() &&
                        getCallbackData(update)[CALLBACK_STEP_INDEX.getIndex()].equals(START.getDialogId()))) {
            currentStep = FIRST_STEP_INDEX.getIndex();
            lastStep = FIRST_STEP_INDEX.getIndex();
        } else {
            currentStep = Integer.parseInt(dialogSteps.get(CURRENT_DIALOG_STEP.getDialogId()));
            lastStep = Integer.parseInt(dialogSteps.get(LAST_STEP.getId()));
        }

        if (!update.hasCallbackQuery() && getMessageText(update).startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = getMessageText(update).split(" ")[0].toLowerCase();
            if (commandIdentifier.matches("/\\d+")) {
                currentStep = null;
                assert dialogSteps != null;
                dialogSteps.replace(LAST_STEP.getId(), String.valueOf(lastStep - 1));
                lastStep = Integer.parseInt(commandIdentifier.substring(1)) + 1;
            }
        }

        if (currentStep != null) {
            if (update.hasCallbackQuery() && getCallbackData(update).length > CALLBACK_OPERATION_DATA_INDEX.getIndex() &&
                    getCallbackData(update)[CALLBACK_OPERATION_DATA_INDEX.getIndex()].equals(NEXT.getId())) {
                lastStep = getNextStepNum(lastStep);
            } else if (addAccountContainer.retrieve(AddAccountName.getDialogNameByOrder(currentStep)).commit(update)) {
                dialogSteps = dialogsMap.get(chatId);
                lastStep = getNextStepNum(lastStep);
            }
        }

        if (dialogSteps != null && (dialogSteps.get(TYPE.getDialogId()) == null
                || dialogSteps.get(TYPE.getDialogId()).equals(CASH_ID.getId()))
                && AddAccountName.getDialogNameByOrder(lastStep).equals(BANK.getDialogId())) {
            lastStep = getNextStepNum(lastStep);
        }

        assert dialogSteps != null;
        dialogSteps.replace(CURRENT_DIALOG_STEP.getDialogId(), String.valueOf(lastStep));
        if (lastStep > Integer.parseInt(dialogSteps.get(LAST_STEP.getId()))) {
            dialogSteps.replace(LAST_STEP.getId(), String.valueOf(lastStep));
        }
        addAccountContainer.retrieve(AddAccountName.getDialogNameByOrder(lastStep)).execute(update);
    }

    @Override
    public boolean commit(Update update) {
        return true;
    }

    private int getNextStepNum(int lastStep) {
        return lastStep + 1 < AddAccountName.values().length - 1 ? lastStep + 1 : lastStep;
    }
}
