package ru.kas.myBudget.bots.telegram.dialogs.editAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.dialogs.MainDialogImpl;
import ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountContainer;
import ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.bot.TelegramBot.COMMAND_PREFIX;
import static ru.kas.myBudget.bots.telegram.commands.CommandIndex.COMMAND;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogPattern.EDIT_NUM;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.*;

public class EditAccountDialog extends MainDialogImpl {
    private final EditAccountContainer editAccountContainer;
    private Map<String, String> dialogMap;
    private long chatId;

    public EditAccountDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                             DialogsMap dialogsMap, EditAccountContainer editAccountContainer) {
        super(botMessageService, telegramUserService, dialogsMap);
        this.editAccountContainer = editAccountContainer;
    }

    @Override
    public void execute(Update update) {
        this.chatId = UpdateParameter.getChatId(update);
        this.dialogMap = dialogsMap.getDialogMapById(chatId);
        ResponseWaitingMap.remove(chatId);

        Integer currentStep;
        int lastStep;
        String[] callbackData = UpdateParameter.getCallbackData(update);
        String messageText = UpdateParameter.getMessageText(update);

        if (dialogMap == null || dialogMap.isEmpty() || (callbackData != null &&
                callbackData[CALLBACK_STEP_INDEX.getIndex()].equals(START.getName()))) {
            currentStep = FIRST_STEP_INDEX.getIndex();
            lastStep = FIRST_STEP_INDEX.getIndex();
        } else {
            currentStep = Integer.parseInt(dialogMap.get(CURRENT_DIALOG_STEP.getId()));
            lastStep = Integer.parseInt(dialogMap.get(LAST_STEP.getId()));
        }

        dialogMap = dialogsMap.getDialogMapById(chatId);

        if (!update.hasCallbackQuery() && messageText.startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = messageText.split(" ")[COMMAND.getIndex()].toLowerCase();
            if (commandIdentifier.matches(EDIT_NUM.getRegex())) {
                currentStep = null;
                assert dialogMap != null;
                dialogMap.replace(LAST_STEP.getId(), String.valueOf(lastStep - 1));
                lastStep = Integer.parseInt(commandIdentifier.substring(1));
            }
        }

        lastStep = skipOrCommit(update, currentStep, lastStep);
        lastStep = skipStep(lastStep);
        updateStepsInDialogMap(lastStep);

        editAccountContainer.retrieve(AddAccountNames.values()[lastStep].getName()).execute(update);
    }


    private int skipOrCommit(Update update, Integer currentStep, int lastStep) {
        if (currentStep == null) return lastStep;

        String[] callbackData = UpdateParameter.getCallbackData(update);
        if (update.hasCallbackQuery() && callbackData != null &&
                callbackData.length > CALLBACK_OPERATION_DATA_INDEX.getIndex() &&
                callbackData[CALLBACK_OPERATION_DATA_INDEX.getIndex()].equals(NEXT.getId())) {

            editAccountContainer.retrieve(AddAccountNames.values()[currentStep].getName()).skip(update);

            return getNextStepNum(lastStep);

        } else {
            boolean result = editAccountContainer.retrieve(AddAccountNames.values()[currentStep].getName()).commit(update);
            if (result) {
                dialogMap = dialogsMap.getDialogMapById(chatId);
                return getNextStepNum(lastStep);
            }
        }
        return lastStep;
    }

    private int getNextStepNum(int lastStep) {
        return lastStep + 1 < AddAccountNames.values().length ? lastStep + 1 : lastStep;
    }

    private int skipStep(int lastStep) {
        if (dialogMap != null && (dialogMap.get(TYPE.getName()) == null
                || dialogMap.get(TYPE.getName()).equals(CASH_ID.getId()))
                && AddAccountNames.values()[lastStep].getName().equals(BANK.getName())) {

            return getNextStepNum(lastStep);
        }
        return lastStep;
    }

    private void updateStepsInDialogMap(int lastStep) {
        dialogsMap.replaceById(chatId, CURRENT_DIALOG_STEP.getId(), String.valueOf(lastStep));
        if (dialogMap != null && lastStep > Integer.parseInt(dialogMap.get(LAST_STEP.getId()))) {
            dialogsMap.replaceById(chatId, LAST_STEP.getId(), String.valueOf(lastStep));
        }
    }
}
