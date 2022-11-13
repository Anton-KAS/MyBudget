package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.*;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.bot.TelegramBot.COMMAND_PREFIX;
import static ru.kas.myBudget.bots.telegram.commands.CommandIndex.COMMAND;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogPattern.EDIT_NUM;

public class AddAccountDialog implements Dialog, CommandController {
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
        long chatId = UpdateParameter.getChatId(update);
        Map<String, String> dialogSteps = dialogsMap.get(chatId);
        Integer currentStep;
        int lastStep;
        String[] callbackData = UpdateParameter.getCallbackData(update);
        String messageText = UpdateParameter.getMessageText(update);

        if (dialogSteps == null || dialogSteps.isEmpty() || (callbackData != null &&
                callbackData[CALLBACK_STEP_INDEX.getIndex()].equals(START.getName()))) {
            currentStep = FIRST_STEP_INDEX.getIndex();
            lastStep = FIRST_STEP_INDEX.getIndex();
        } else {
            currentStep = Integer.parseInt(dialogSteps.get(CURRENT_DIALOG_STEP.getId()));
            lastStep = Integer.parseInt(dialogSteps.get(LAST_STEP.getId()));
        }

        if (!update.hasCallbackQuery() && messageText.startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = messageText.split(" ")[COMMAND.getIndex()].toLowerCase();
            if (commandIdentifier.matches(EDIT_NUM.getRegex())) {
                currentStep = null;
                assert dialogSteps != null;
                dialogSteps.replace(LAST_STEP.getId(), String.valueOf(lastStep - 1));
                lastStep = Integer.parseInt(commandIdentifier.substring(1)) + 1;
            }
        }

        if (currentStep != null) {
            if (update.hasCallbackQuery() && callbackData != null &&
                    callbackData.length > CALLBACK_OPERATION_DATA_INDEX.getIndex() &&
                    callbackData[CALLBACK_OPERATION_DATA_INDEX.getIndex()].equals(NEXT.getId())) {
                addAccountContainer.retrieve(AddAccountNames.getDialogNameByOrder(currentStep)).skip(update);
                lastStep = getNextStepNum(lastStep);
            } else if (addAccountContainer.retrieve(AddAccountNames.getDialogNameByOrder(currentStep)).commit(update)) {
                dialogSteps = dialogsMap.get(chatId);
                lastStep = getNextStepNum(lastStep);
            }
        }

        if (dialogSteps != null && (dialogSteps.get(TYPE.getName()) == null
                || dialogSteps.get(TYPE.getName()).equals(CASH_ID.getId()))
                && AddAccountNames.getDialogNameByOrder(lastStep).equals(BANK.getName())) {
            lastStep = getNextStepNum(lastStep);
        }

        assert dialogSteps != null;
        dialogSteps.replace(CURRENT_DIALOG_STEP.getId(), String.valueOf(lastStep));
        if (lastStep > Integer.parseInt(dialogSteps.get(LAST_STEP.getId()))) {
            dialogSteps.replace(LAST_STEP.getId(), String.valueOf(lastStep));
        }
        addAccountContainer.retrieve(AddAccountNames.getDialogNameByOrder(lastStep)).execute(update);
    }

    @Override
    public boolean commit(Update update) {
        return true;
    }

    @Override
    public void skip(Update update) {

    }

    private int getNextStepNum(int lastStep) {
        return lastStep + 1 < AddAccountNames.values().length ? lastStep + 1 : lastStep;
    }
}
