package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.dialogs.MainDialogImpl;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.*;

import static ru.kas.myBudget.bots.telegram.bot.TelegramBot.COMMAND_PREFIX;
import static ru.kas.myBudget.bots.telegram.commands.CommandIndex.COMMAND;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogPattern.EDIT_NUM;

public class AddAccountDialog extends MainDialogImpl {
    private final AddAccountContainer addAccountContainer;
    private long chatId;

    public AddAccountDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                            AddAccountContainer addAccountContainer) {
        super(botMessageService, telegramUserService);
        this.addAccountContainer = addAccountContainer;
    }

    @Override
    public void execute(Update update) {
        this.chatId = UpdateParameter.getChatId(update);

        System.out.println("DIALOG MAP: " + DialogsMap.getDialogMapById(chatId));

        Integer currentStep;
        int lastStep;
        String[] callbackData = UpdateParameter.getCallbackData(update);
        String messageText = UpdateParameter.getMessageText(update);

        if (DialogsMap.getDialogMap(chatId) == null || (callbackData != null &&
                callbackData[CALLBACK_STEP_INDEX.getIndex()].equals(START.getName()))) {
            currentStep = FIRST_STEP_INDEX.getIndex();
            lastStep = FIRST_STEP_INDEX.getIndex();
        } else {
            currentStep = Integer.parseInt(DialogsMap.getDialogMap(chatId).get(CURRENT_DIALOG_STEP.getId()));
            lastStep = Integer.parseInt(DialogsMap.getDialogMap(chatId).get(LAST_STEP.getId()));
        }

        if (!update.hasCallbackQuery() && messageText.startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = messageText.split(" ")[COMMAND.getIndex()].toLowerCase();
            if (commandIdentifier.matches(EDIT_NUM.getRegex())) {
                currentStep = null;
                DialogsMap.replaceById(chatId, LAST_STEP.getId(), String.valueOf(lastStep - 1));
                int newLastStep = Integer.parseInt(commandIdentifier.substring(1));
                if (lastStep > newLastStep) lastStep = newLastStep;
            }
        }

        lastStep = skipOrCommit(update, currentStep, lastStep);
        lastStep = skipStep(lastStep);
        updateStepsInDialogMap(lastStep);

        addAccountContainer.retrieve(AddAccountNames.values()[lastStep].getName()).execute(update);
    }


    private int skipOrCommit(Update update, Integer currentStep, int lastStep) {
        if (currentStep == null) return lastStep;

        String[] callbackData = UpdateParameter.getCallbackData(update);
        if (update.hasCallbackQuery() && callbackData != null &&
                callbackData.length > CALLBACK_OPERATION_DATA_INDEX.getIndex() &&
                callbackData[CALLBACK_OPERATION_DATA_INDEX.getIndex()].equals(NEXT.getId())) {

            addAccountContainer.retrieve(AddAccountNames.values()[currentStep].getName()).skip(update);

            return getNextStepNum(lastStep);

        } else {
            boolean result = addAccountContainer.retrieve(AddAccountNames.values()[currentStep].getName()).commit(update);
            if (result) {
                return getNextStepNum(lastStep);
            }
        }
        return lastStep;
    }

    private int getNextStepNum(int lastStep) {
        return lastStep + 1 < AddAccountNames.values().length ? lastStep + 1 : lastStep;
    }

    private int skipStep(int lastStep) {
        if (DialogsMap.getDialogMap(chatId) != null && (DialogsMap.getDialogMap(chatId).get(TYPE.getName()) == null
                || DialogsMap.getDialogMap(chatId).get(TYPE.getName()).equals(CASH_ID.getId()))
                && AddAccountNames.values()[lastStep].getName().equals(BANK.getName())) {

            return getNextStepNum(lastStep);
        }
        return lastStep;
    }

    private void updateStepsInDialogMap(int lastStep) {
        DialogsMap.replaceById(chatId, CURRENT_DIALOG_STEP.getId(), String.valueOf(lastStep));
        System.out.println("DIALOG MAP UPDATE STEPS: " + DialogsMap.getDialogMap(chatId));
        if (DialogsMap.getDialogMap(chatId) != null
                && lastStep > Integer.parseInt(DialogsMap.getDialogMap(chatId).get(LAST_STEP.getId()))) {
            DialogsMap.replaceById(chatId, LAST_STEP.getId(), String.valueOf(lastStep));
        }
    }
}
