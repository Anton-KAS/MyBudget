package ru.kas.myBudget.bots.telegram.dialogs.editAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames;
import ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountStartDialog;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.TelegramUserService;

import java.math.RoundingMode;
import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.CALLBACK_OPERATION_DATA_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.CURRENT_DIALOG_STEP;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.LAST_STEP;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.*;

public class EditAccountStartDialog extends AddAccountStartDialog {
    private final AccountService accountService;

    public EditAccountStartDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                                  MessageText messageText, Keyboard keyboard, DialogsMap dialogsMap,
                                  DialogNamesImpl dialogName, AccountService accountService) {
        super(botMessageService, telegramUserService, messageText, keyboard, dialogsMap, dialogName);
        this.accountService = accountService;
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
    }

    @Override
    public boolean commit(Update update) {
        if (!super.commit(update)) return false;

        Map<String, String> dialogMap = dialogsMap.getDialogMapById(chatId);

        if (callbackData.length <= CALLBACK_OPERATION_DATA_INDEX.getIndex()) return false;
        int accountId = Integer.parseInt(callbackData[CALLBACK_OPERATION_DATA_INDEX.getIndex()]);

        Account account = accountService.findById(accountId).orElse(null);
        if (account == null) return false;

        dialogMap.replace(CURRENT_DIALOG_STEP.getId(), String.valueOf(CONFIRM.ordinal() - 1));
        dialogMap.replace(LAST_STEP.getId(), String.valueOf(CONFIRM.ordinal() - 1));

        Bank bank = account.getBank();
        String bankText = "";
        if (bank != null) bankText = bank.displayToUser();

        String description;
        if (account.getDescription() == null) description = "";
        else description = account.getDescription();

        addToDialogMap(chatId, TYPE, String.valueOf(account.getAccountType().getId()),
                String.format(TYPE.getStepTextPattern(), "%s", account.getAccountType().getTitleRu()));
        addToDialogMap(chatId, TITLE, String.valueOf(account.getAccountType().getId()),
                String.format(TITLE.getStepTextPattern(), "%s", account.getTitle()));
        addToDialogMap(chatId, DESCRIPTION, String.valueOf(account.getAccountType().getId()),
                String.format(DESCRIPTION.getStepTextPattern(), "%s", description));
        addToDialogMap(chatId, CURRENCY, String.valueOf(account.getAccountType().getId()),
                String.format(CURRENCY.getStepTextPattern(), "%s", account.getCurrency().displayToUser()));
        addToDialogMap(chatId, BANK, String.valueOf(account.getAccountType().getId()),
                String.format(BANK.getStepTextPattern(), "%s", bankText));
        addToDialogMap(chatId, START_BALANCE, String.valueOf(account.getAccountType().getId()),
                String.format(START_BALANCE.getStepTextPattern(), "%s", account.getStartBalance().setScale(
                        String.valueOf(account.getCurrency().getNumberToBasic()).length() - 1,
                        RoundingMode.HALF_UP)));

        for (int i = START.ordinal() + 1; i < CONFIRM.ordinal(); i++) {
            String stepIdText = AddAccountNames.values()[i].getStepIdText();
            dialogMap.replace(stepIdText, String.format(dialogMap.get(stepIdText), i));
        }

        return true;
    }
}
