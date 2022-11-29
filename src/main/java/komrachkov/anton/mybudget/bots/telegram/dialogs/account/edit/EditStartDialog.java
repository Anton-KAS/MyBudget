package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.models.Account;
import komrachkov.anton.mybudget.models.Bank;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.StartDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsMap;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.services.AccountService;

import java.util.Map;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNT;
import static komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackNamesImpl.ACCOUNTS;
import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackType.NORMAL;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.*;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class EditStartDialog extends StartDialog {
    private final AccountService accountService;

    public EditStartDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
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

        Map<String, String> dialogMap = DialogsMap.getDialogMap(chatId);

        if (callbackData.length <= CallbackIndex.OPERATION_DATA.ordinal()) return false;
        int accountId = Integer.parseInt(callbackData[CallbackIndex.OPERATION_DATA.ordinal()]);

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
