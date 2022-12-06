package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.bot.TelegramBot;
import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex;
import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackOperator;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.*;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogStepsContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.dialogs.MainDialogImpl;

import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.commands.util.CommandIndex.COMMAND;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.TYPE;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.CURRENT_DIALOG_STEP;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.LAST_STEP;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public abstract class AccountDialog extends MainDialogImpl {
    private final DialogStepsContainer dialogContainer;
    private long chatId;
    private Integer currentStep;
    private int lastStep;

    public AccountDialog(TelegramUserService telegramUserService, DialogStepsContainer dialogContainer) {
        super(telegramUserService);
        this.dialogContainer = dialogContainer;
    }

    @Override
    public ToDoList execute(Update update, ExecuteMode executeMode) {
        this.chatId = UpdateParameter.getChatId(update);

        System.out.println("DIALOG MAP: " + DialogsState.stateToString(chatId)); // TODO: Add project logger

        getStepNums(update);
        checkDialogCommand(update);
        ToDoList toDoList = skipOrCommitStep(update);
        skipNextStep(update);
        updateStepsInDialogMap();

        System.out.println("EXECUTE: lastStep: " + lastStep + " | " + AccountNames.values()[lastStep].getName());
        return toDoList.addAll(dialogContainer.retrieve(AccountNames.values()[lastStep].getName()).execute(update));
    }

    private void getStepNums(Update update) {
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        if (!DialogsState.hasDialogs(chatId) || (callbackData != null &&
                callbackData[CallbackIndex.OPERATION_DATA.ordinal()].equals(AccountNames.START.getName()))) {
            currentStep = DialogIndex.FIRST_STEP_INDEX.ordinal();
            lastStep = DialogIndex.FIRST_STEP_INDEX.ordinal();
        } else {
            Optional<String> currentStepOpt = DialogsState.getDialogStepById(chatId, CURRENT_DIALOG_STEP.getId());
            Optional<String> lastStepOpt = DialogsState.getDialogStepById(chatId, LAST_STEP.getId());
            if (currentStepOpt.isEmpty() || lastStepOpt.isEmpty()) return;

            currentStep = Integer.parseInt(currentStepOpt.get());
            lastStep = Integer.parseInt(lastStepOpt.get());
        }

    }

    private void checkDialogCommand(Update update) {
        String messageText = UpdateParameter.getMessageText(update);
        if (update.hasCallbackQuery() && !messageText.startsWith(TelegramBot.COMMAND_PREFIX)) return;

        String commandIdentifier = messageText.split(" ")[COMMAND.getIndex()].toLowerCase();
        if (commandIdentifier.matches(DialogPattern.EDIT_NUM.getRegex())) {
            currentStep = null;
            DialogsState.replaceById(chatId, LAST_STEP.getId(), String.valueOf(lastStep));
            int newLastStep = Integer.parseInt(commandIdentifier.substring(1));
            if (lastStep > newLastStep) lastStep = newLastStep;
        }
    }

    private ToDoList skipOrCommitStep(Update update) {
        if (currentStep == null) return new ToDoList();

        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
        if (update.hasCallbackQuery() && callbackData != null &&
                callbackData.length > CallbackIndex.OPERATION_DATA.ordinal() &&
                callbackData[CallbackIndex.OPERATION_DATA.ordinal()].equals(DialogMapDefaultName.NEXT.getId())) {

            getNextStepNum(update);
            return dialogContainer.retrieve(AccountNames.values()[currentStep].getName()).skip(update);
        }

        ToDoList toDoList = dialogContainer.retrieve(AccountNames.values()[currentStep].getName()).commit(update);
        System.out.println("COMMIT: currentStep: " + currentStep + " | " + AccountNames.values()[currentStep].getName());
        if (toDoList.isResultCommit()) {
            Optional<String> lastStepOpt = DialogsState.getByStepId(chatId, LAST_STEP.getId());
            if (lastStepOpt.isEmpty()) return toDoList;
            lastStep = Integer.parseInt(lastStepOpt.get());
            getNextStepNum(update);
        } else {
            lastStep = currentStep;
        }
        return toDoList;
    }

    private void skipNextStep(Update update) {
        Optional<String> accountTypeOpt = DialogsState.getDialogStepById(chatId, TYPE.getName());

        if ((accountTypeOpt.isEmpty() || accountTypeOpt.get().equals(DialogMapDefaultName.CASH_ID.getId()))
                && AccountNames.values()[lastStep].getName().equals(AccountNames.BANK.getName())) {
            getNextStepNum(update);
        }
    }

    private void updateStepsInDialogMap() {
        DialogsState.replaceById(chatId, CURRENT_DIALOG_STEP.getId(), String.valueOf(lastStep));

        System.out.println("DIALOG MAP UPDATE STEPS: " + DialogsState.stateToString(chatId)); // TODO: Add project logger

        Optional<String> lastStepOpt = DialogsState.getByStepId(chatId, LAST_STEP.getId());
        if (lastStepOpt.isPresent() && lastStep > Integer.parseInt(lastStepOpt.get())) {
            DialogsState.replaceById(chatId, LAST_STEP.getId(), String.valueOf(lastStep));
        }
    }

    private void getNextStepNum(Update update) {
        lastStep = lastStep + 1 < AccountNames.values().length ? lastStep + 1 : lastStep;
        if (lastStep == AccountNames.SAVE.ordinal()) {
            String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
            if (callbackData == null || callbackData.length < CallbackIndex.OPERATION_DATA.ordinal()
                    || !callbackData[CallbackIndex.OPERATION_DATA.ordinal()].equals(CallbackOperator.SAVE.getId())) {
                lastStep = AccountNames.CONFIRM.ordinal();
            }
        }
    }
}
