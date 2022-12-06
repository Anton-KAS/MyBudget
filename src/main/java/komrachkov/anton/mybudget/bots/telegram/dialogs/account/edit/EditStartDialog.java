package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountDialogText;
import komrachkov.anton.mybudget.bots.telegram.util.ResponseWaitingMap;
import komrachkov.anton.mybudget.bots.telegram.util.ToDoList;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.models.Account;
import komrachkov.anton.mybudget.models.Bank;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.StartDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogsState;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames;
import komrachkov.anton.mybudget.services.AccountService;

import java.util.Optional;

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

@Component
public class EditStartDialog extends StartDialog {
    private final AccountService accountService;

    @Autowired
    public EditStartDialog(TelegramUserService telegramUserService, AccountDialogText messageText,
                           AccountService accountService) {
        super(telegramUserService, messageText);
        this.accountService = accountService;
    }

    @Override
    public ToDoList commit(Update update) {
        ToDoList toDoList = super.commit(update);
        if (!toDoList.isResultCommit()) return toDoList;
        toDoList.setResultCommit(false);

        long chatId = UpdateParameter.getChatId(update);
        if (!DialogsState.hasDialogs(chatId)) return toDoList;

        if (callbackData.length <= CallbackIndex.OPERATION_DATA.ordinal()) return toDoList;

        int accountId = Integer.parseInt(callbackData[CallbackIndex.OPERATION_DATA.ordinal()]);
        Account account = accountService.findById(accountId).orElse(null);
        if (account == null) return toDoList;

        DialogsState.put(chatId, START_FROM_CALLBACK.getId(),
                String.format("%s_%s_%s_%s_%s", NORMAL.getId(), ACCOUNTS.getName(), ACCOUNT.getName(), "show", accountId));
        DialogsState.put(chatId, CURRENT_DIALOG_STEP.getId(), String.valueOf(CONFIRM.ordinal() - 1));
        DialogsState.put(chatId, LAST_STEP.getId(), String.valueOf(CONFIRM.ordinal() - 1));
        DialogsState.put(chatId, CAN_SAVE.getId(), "true");
        DialogsState.put(chatId, EDIT_ID.getId(), String.valueOf(accountId));

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

        fillDataToDialogMap(chatId, account, description, bankId, bankText);

        for (int i = START.ordinal() + 1; i < CONFIRM.ordinal(); i++) {
            String stepIdText = AccountNames.values()[i].getStepIdText();
            Optional<String> stepTextOpt = DialogsState.getByStepId(chatId, stepIdText);
            if (stepTextOpt.isPresent()) {
                DialogsState.replaceById(chatId, stepIdText, String.format(stepTextOpt.get(), i));
            }
        }
        ResponseWaitingMap.put(chatId, EDIT_ACCOUNT);

        toDoList.setResultCommit(true);
        return toDoList;
    }

    private void fillDataToDialogMap(long chatId, Account account, String description, String bankId, String bankText) {
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
