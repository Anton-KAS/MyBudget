package ru.kas.myBudget.bots.telegram.dialogs;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountDialog;
import ru.kas.myBudget.bots.telegram.keyboards.callback.NoKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.callback.NoText;
import ru.kas.myBudget.bots.telegram.util.Container;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.*;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.*;

public class DialogContainer implements Container {
    private final ImmutableMap<String, CommandController> dialogMap;
    private final CommandController unknownDialog;

    public DialogContainer(BotMessageService botMessageService, TelegramUserService telegramUserService,
                           CallbackContainer callbackContainer, AccountTypeService accountTypeService,
                           CurrencyService currencyService, BankService bankService, AccountService accountService) {
        dialogMap = ImmutableMap.<String, CommandController>builder()
                .put(ADD_ACCOUNT.getName(),
                        new AddAccountDialog(botMessageService, telegramUserService, DialogsMap.getDialogsMapClass(),
                                callbackContainer, accountTypeService, currencyService, bankService, accountService))
                .build();

        unknownDialog = new UnknownDialog(botMessageService, telegramUserService, ExecuteMode.SEND,
                new NoText(), new NoKeyboard());
        // TODO: Execute command подумать, может задавать автоматически
    }

    public CommandController retrieve(String identifier) {
        return dialogMap.getOrDefault(identifier, unknownDialog);
    }
}
