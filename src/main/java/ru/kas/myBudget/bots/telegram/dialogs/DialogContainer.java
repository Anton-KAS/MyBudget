package ru.kas.myBudget.bots.telegram.dialogs;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountDialog;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.util.Container;
import ru.kas.myBudget.bots.telegram.util.UpdateExtraction;
import ru.kas.myBudget.services.*;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.*;

public class DialogContainer implements Container {

    private final ImmutableMap<String, Dialog> dialogMap;
    private final Dialog unknownDialog;

    public DialogContainer(BotMessageService botMessageService, TelegramUserService telegramUserService,
                           CallbackContainer callbackContainer,
                           AccountTypeService accountTypeService, CurrencyService currencyService,
                           BankService bankService, AccountService accountService) {
        dialogMap = ImmutableMap.<String, Dialog>builder()
                .put(ADD_ACCOUNT.getDialogName(),
                        new AddAccountDialog(botMessageService, telegramUserService, callbackContainer,
                                accountTypeService, currencyService, bankService, accountService))
                .build();

        unknownDialog = new UnknownDialog(botMessageService);
    }

    public UpdateExtraction retrieve(String identifier) {
        return dialogMap.getOrDefault(identifier, unknownDialog);
    }
}
