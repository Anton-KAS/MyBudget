package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.dialogs.MainDialogImpl;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.*;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.bot.TelegramBot.COMMAND_PREFIX;
import static ru.kas.myBudget.bots.telegram.commands.CommandIndex.COMMAND;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogPattern.EDIT_NUM;

public class AddAccountDialog extends MainDialogImpl {
    private final AddAccountContainer addAccountContainer;
    private Map<String, String> dialogMap;
    private long chatId;

    public AddAccountDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                            DialogsMap dialogsMap, CallbackContainer callbackContainer,
                            AccountTypeService accountTypeService, CurrencyService currencyService,
                            BankService bankService, AccountService accountService) {
        super(botMessageService, telegramUserService, dialogsMap);
        this.addAccountContainer = new AddAccountContainer(botMessageService, telegramUserService, callbackContainer,
                accountTypeService, currencyService, bankService, accountService);
    }

    @Override
    public void execute(Update update) {
        this.chatId = UpdateParameter.getChatId(update);
        this.dialogMap = dialogsMap.getDialogMapById(chatId);

        System.out.println("DIALOG MAP FOR " + chatId + " START : " + DialogsMap.getDialogMap(chatId));

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
        System.out.println("1 LAST STEP : " + lastStep);

        dialogMap = dialogsMap.getDialogMapById(chatId);
        System.out.println("DIALOG MAP FOR " + chatId + " MIDDLE : " + DialogsMap.getDialogMap(chatId));

        if (!update.hasCallbackQuery() && messageText.startsWith(COMMAND_PREFIX)) {
            String commandIdentifier = messageText.split(" ")[COMMAND.getIndex()].toLowerCase();
            if (commandIdentifier.matches(EDIT_NUM.getRegex())) {
                currentStep = null;
                assert dialogMap != null;
                dialogMap.replace(LAST_STEP.getId(), String.valueOf(lastStep - 1));
                lastStep = Integer.parseInt(commandIdentifier.substring(1));
            }
        }

        System.out.println("2 LAST STEP : " + lastStep);

        lastStep = skipOrCommit(update, currentStep, lastStep);
        System.out.println("3 LAST STEP : " + lastStep);
        System.out.println("DIALOG MAP FOR " + chatId + " AFTER COMMIT : " + DialogsMap.getDialogMap(chatId));
        lastStep = skipStep(lastStep);
        System.out.println("4 LAST STEP : " + lastStep);
        updateStepsInDialogMap(lastStep);
        System.out.println("5 LAST STEP : " + lastStep);

        System.out.println("EXECUTE: " + AddAccountNames.getDialogNameByOrder(lastStep));
        addAccountContainer.retrieve(AddAccountNames.getDialogNameByOrder(lastStep)).execute(update);

        System.out.println("DIALOG MAP FOR " + chatId + " END : " + DialogsMap.getDialogMap(chatId));
        System.out.println("LAST STEP FOR " + chatId + lastStep);
    }


    private int skipOrCommit(Update update, Integer currentStep, int lastStep) {
        if (currentStep == null) return lastStep;

        String[] callbackData = UpdateParameter.getCallbackData(update);
        if (update.hasCallbackQuery() && callbackData != null &&
                callbackData.length > CALLBACK_OPERATION_DATA_INDEX.getIndex() &&
                callbackData[CALLBACK_OPERATION_DATA_INDEX.getIndex()].equals(NEXT.getId())) {

            addAccountContainer.retrieve(AddAccountNames.getDialogNameByOrder(currentStep)).skip(update);

            System.out.println("skipOrCommit : SKIP");

            return getNextStepNum(lastStep);

        } else {
            boolean result = addAccountContainer.retrieve(AddAccountNames.getDialogNameByOrder(currentStep)).commit(update);
            System.out.println("Commit RESULT : " + result);
            if (result) {
                dialogMap = dialogsMap.getDialogMapById(chatId);
                System.out.println("skipOrCommit : COMMIT");
                return getNextStepNum(lastStep);
            }
        }
        return lastStep;
    }

    private int getNextStepNum(int lastStep) {
        return lastStep + 1 < AddAccountNames.values().length ? lastStep + 1 : lastStep;
    }

    private int skipStep(int lastStep) {
        if ((dialogMap.get(TYPE.getName()) == null || dialogMap.get(TYPE.getName()).equals(CASH_ID.getId()))
                && AddAccountNames.getDialogNameByOrder(lastStep).equals(BANK.getName())) {

            System.out.println("skipStep : SKIP");

            return getNextStepNum(lastStep);
        }
        System.out.println("skipStep : NOT SKIP");
        return lastStep;
    }

    private void updateStepsInDialogMap(int lastStep) {
        dialogsMap.replaceById(chatId, CURRENT_DIALOG_STEP.getId(), String.valueOf(lastStep));
        if (lastStep > Integer.parseInt(dialogMap.get(LAST_STEP.getId()))) {
            dialogsMap.replaceById(chatId, LAST_STEP.getId(), String.valueOf(lastStep));
        }
    }
}
