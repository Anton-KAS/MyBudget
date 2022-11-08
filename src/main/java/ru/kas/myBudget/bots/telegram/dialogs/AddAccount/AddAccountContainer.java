package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.UnknownDialog;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.services.*;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;

public class AddAccountContainer {
    private final ImmutableMap<String, Dialog> dialogMap;
    private final Dialog unknownDialog;

    public AddAccountContainer(BotMessageService botMessageService, TelegramUserService telegramUserService,
                               CallbackContainer callbackContainer,
                               AccountTypeService accountTypeService, CurrencyService currencyService,
                               BankService bankService, AccountService accountService) {
        dialogMap = ImmutableMap.<String, Dialog>builder()
                .put(START.getDialogId(), new StartDialog())
                .put(TYPE.getDialogId(),
                        new AddAccountTypeDialog(botMessageService, telegramUserService, accountTypeService))
                .put(TITLE.getDialogId(),
                        new AddAccountTitleDialog(botMessageService, telegramUserService))
                .put(DESCRIPTION.getDialogId(),
                        new AddAccountDescriptionDialog(botMessageService, telegramUserService))
                .put(CURRENCY.getDialogId(),
                        new AddAccountCurrencyDialog(botMessageService, telegramUserService, currencyService))
                .put(BANK.getDialogId(),
                        new AddAccountBankDialog(botMessageService, telegramUserService, bankService))
                .put(CONFIRM.getDialogId(),
                        new AddAccountConfirmDialog(botMessageService, telegramUserService))
                .put(SAVE.getDialogId(),
                        new AddAccountSaveDialog(botMessageService, telegramUserService, callbackContainer,
                                accountTypeService, currencyService, bankService, accountService))
                .build();

        unknownDialog = new UnknownDialog(botMessageService);
    }

    public Dialog retrieve(String identifier) {
        return dialogMap.getOrDefault(identifier, unknownDialog);
    }
}
