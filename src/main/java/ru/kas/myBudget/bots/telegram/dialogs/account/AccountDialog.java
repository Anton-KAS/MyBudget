package ru.kas.myBudget.bots.telegram.dialogs.account;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.util.CallbackOperator;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogStepsContainer;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.dialogs.util.MainDialogImpl;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.*;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.bot.TelegramBot.COMMAND_PREFIX;
import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackIndex.OPERATION_DATA;
import static ru.kas.myBudget.bots.telegram.commands.CommandIndex.COMMAND;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogIndex.*;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogPattern.EDIT_NUM;

public class AccountDialog extends MainDialogImpl {
    private final DialogStepsContainer dialogContainer;
    private long chatId;
    private Integer currentStep;
    private int lastStep;

    public AccountDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                         DialogStepsContainer dialogContainer) {
        super(botMessageService, telegramUserService);
        this.dialogContainer = dialogContainer;
    }

    @Override
    public void execute(Update update) {
        this.chatId = UpdateParameter.getChatId(update);

        System.out.println("DIALOG MAP: " + DialogsMap.getDialogMapById(chatId)); // TODO: Add project logger

        getStepNums(update);
        checkDialogCommand(update);
        skipOrCommitStep(update);
        skipNextStep(update);
        updateStepsInDialogMap();

        dialogContainer.retrieve(AccountNames.values()[lastStep].getName()).execute(update);
    }

    private void getStepNums(Update update) {
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        Map<String, String> dialogMap = DialogsMap.getDialogMap(chatId);
        if (dialogMap == null || (callbackData != null &&
                callbackData[OPERATION_DATA.ordinal()].equals(AccountNames.START.getName()))) {
            currentStep = FIRST_STEP_INDEX.ordinal();
            lastStep = FIRST_STEP_INDEX.ordinal();
        } else {
            currentStep = Integer.parseInt(dialogMap.get(CURRENT_DIALOG_STEP.getId()));
            lastStep = Integer.parseInt(dialogMap.get(LAST_STEP.getId()));
        }

    }

    private void checkDialogCommand(Update update) {
        String messageText = UpdateParameter.getMessageText(update);
        if (update.hasCallbackQuery() && !messageText.startsWith(COMMAND_PREFIX)) return;

        String commandIdentifier = messageText.split(" ")[COMMAND.getIndex()].toLowerCase();
        if (commandIdentifier.matches(EDIT_NUM.getRegex())) {
            currentStep = null;
            DialogsMap.replaceById(chatId, LAST_STEP.getId(), String.valueOf(lastStep));
            int newLastStep = Integer.parseInt(commandIdentifier.substring(1));
            if (lastStep > newLastStep) lastStep = newLastStep;
        }
    }

    private void skipOrCommitStep(Update update) {
        if (currentStep == null) return;

        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        if (update.hasCallbackQuery() && callbackData != null &&
                callbackData.length > OPERATION_DATA.ordinal() &&
                callbackData[OPERATION_DATA.ordinal()].equals(NEXT.getId())) {

            dialogContainer.retrieve(AccountNames.values()[currentStep].getName()).skip(update);
            getNextStepNum(update);
            return;
        }
        boolean result = dialogContainer.retrieve(AccountNames.values()[currentStep].getName()).commit(update);
        if (result) {
            lastStep = Integer.parseInt(DialogsMap.getDialogMap(chatId).get(LAST_STEP.getId()));
            getNextStepNum(update);
        }
    }

    private void skipNextStep(Update update) {
        Map<String, String> dialogMap = DialogsMap.getDialogMap(chatId);
        if (dialogMap == null) return;
        if ((dialogMap.get(AccountNames.TYPE.getName()) == null || dialogMap.get(AccountNames.TYPE.getName()).equals(CASH_ID.getId()))
                && AccountNames.values()[lastStep].getName().equals(AccountNames.BANK.getName())) {
            getNextStepNum(update);
        }
    }

    private void updateStepsInDialogMap() {
        DialogsMap.replaceById(chatId, CURRENT_DIALOG_STEP.getId(), String.valueOf(lastStep));
        System.out.println("DIALOG MAP UPDATE STEPS: " + DialogsMap.getDialogMap(chatId)); // TODO: Add project logger
        if (DialogsMap.getDialogMap(chatId) != null
                && lastStep > Integer.parseInt(DialogsMap.getDialogMap(chatId).get(LAST_STEP.getId()))) {
            DialogsMap.replaceById(chatId, LAST_STEP.getId(), String.valueOf(lastStep));
        }
    }

    private void getNextStepNum(Update update) {
        lastStep = lastStep + 1 < AccountNames.values().length ? lastStep + 1 : lastStep;
        if (lastStep == AccountNames.SAVE.ordinal()) {
            String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
            if (callbackData == null || callbackData.length < OPERATION_DATA.ordinal()
                    || !callbackData[OPERATION_DATA.ordinal()].equals(CallbackOperator.SAVE.getId())) {
                lastStep = AccountNames.CONFIRM.ordinal();
            }
        }
    }
}
