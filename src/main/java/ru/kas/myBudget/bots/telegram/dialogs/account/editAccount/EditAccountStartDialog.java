package ru.kas.myBudget.bots.telegram.dialogs.account.editAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl;
import ru.kas.myBudget.bots.telegram.dialogs.account.StartDialog;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.ResponseWaitingMap;
import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackIndex.OPERATION_DATA;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNT;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNTS;
import static ru.kas.myBudget.bots.telegram.callbacks.util.CallbackType.NORMAL;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;
import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class EditAccountStartDialog extends StartDialog {
    private final AccountService accountService;

    public EditAccountStartDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                  MessageText messageText, Keyboard keyboard, DialogNamesImpl dialogName,
                                  AccountService accountService) {
        super(botMessageService, telegramUserService, messageText, keyboard, dialogName);
        this.accountService = accountService;
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
    }

    @Override
    public boolean commit(Update update) {
        if (!super.commit(update)) return false;

        Map<String, String> dialogMap = DialogsMap.getDialogMapById(chatId);

        if (callbackData.length <= OPERATION_DATA.ordinal()) return false;
        int accountId = Integer.parseInt(callbackData[OPERATION_DATA.ordinal()]);

        Account account = accountService.findById(accountId).orElse(null);
        if (account == null) return false;

        dialogMap.put(START_FROM_CALLBACK.getId(),
                String.format("%s_%s_%s_%s_%s", NORMAL.getId(), ACCOUNTS.getName(), ACCOUNT.getName(), "show", accountId));
        dialogMap.put(CURRENT_DIALOG_STEP.getId(), String.valueOf(CONFIRM.ordinal() - 1));
        dialogMap.put(LAST_STEP.getId(), String.valueOf(CONFIRM.ordinal() - 1));
        dialogMap.put(CAN_SAVE.getId(), "true");
        dialogMap.put(EDIT_ID.getId(), String.valueOf(accountId));

        Bank bank = account.getBank();
        String bankText = "";
        String bankId = "";
        if (bank != null) {
            bankText = bank.displayToUser();
            bankId = String.valueOf(bank.getId());
        }

        String description;
        if (account.getDescription() == null) description = "";
        else description = account.getDescription();

        fillDataToDialogMap(account, description, bankId, bankText);

        for (int i = START.ordinal() + 1; i < CONFIRM.ordinal(); i++) {
            String stepIdText = AccountNames.values()[i].getStepIdText();
            dialogMap.replace(stepIdText, String.format(dialogMap.get(stepIdText), i));
        }
        ResponseWaitingMap.put(chatId, EDIT_ACCOUNT);
        return true;
    }

    private void fillDataToDialogMap(Account account, String description, String bankId, String bankText) {
        addToDialogMap(chatId, TYPE, String.valueOf(account.getAccountType().getId()),
                String.format(TYPE.getStepTextPattern(), "%s", account.getAccountType().getTitleRu()));

        addToDialogMap(chatId, TITLE, String.valueOf(account.getTitle()),
                String.format(TITLE.getStepTextPattern(), "%s", account.getTitle()));

        addToDialogMap(chatId, DESCRIPTION, String.valueOf(description),
                String.format(DESCRIPTION.getStepTextPattern(), "%s", description));

        addToDialogMap(chatId, CURRENCY, String.valueOf(account.getCurrency().getId()),
                String.format(CURRENCY.getStepTextPattern(), "%s", account.getCurrency().displayToUser()));

        addToDialogMap(chatId, BANK, bankId,
                String.format(BANK.getStepTextPattern(), "%s", bankText));

        addToDialogMap(chatId, START_BALANCE, String.valueOf(account.getStartBalance()),
                String.format(START_BALANCE.getStepTextPattern(), "%s", account.getStartBalanceWithScale()));
    }
}
