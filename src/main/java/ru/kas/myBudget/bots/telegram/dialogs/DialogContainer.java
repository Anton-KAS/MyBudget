package ru.kas.myBudget.bots.telegram.dialogs;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountContainer;
import ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountDialog;
import ru.kas.myBudget.bots.telegram.dialogs.editAccount.EditAccountContainer;
import ru.kas.myBudget.bots.telegram.dialogs.editAccount.EditAccountDialog;
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

        AddAccountContainer addAccountContainer = new AddAccountContainer(botMessageService, telegramUserService,
                callbackContainer, accountTypeService, currencyService, bankService, accountService);

        EditAccountContainer editAccountContainer = new EditAccountContainer(botMessageService, telegramUserService,
                callbackContainer, accountTypeService, currencyService, bankService, accountService);

        dialogMap = ImmutableMap.<String, CommandController>builder()
                .put(ADD_ACCOUNT.getName(),
                        new AddAccountDialog(botMessageService, telegramUserService, addAccountContainer))
                .put(EDIT_ACCOUNT.getName(),
                        new EditAccountDialog(botMessageService, telegramUserService, editAccountContainer))
                .put(DELETE_CONFIRM.getName(),
                        new DeleteConfirmDialog(botMessageService, telegramUserService))
                .put(DELETE_EXECUTE.getName(),
                        new DeleteExecuteDialog(botMessageService, telegramUserService, callbackContainer, accountService))
                .build();

        unknownDialog = new UnknownDialog(botMessageService, telegramUserService, ExecuteMode.SEND,
                new NoText(), new NoKeyboard());
        // TODO: Execute command подумать, может задавать автоматически
    }

    @Override
    public CommandController retrieve(String identifier) {
        return dialogMap.getOrDefault(identifier, unknownDialog);
    }

    @Override
    public boolean contains(String commandNames) {
        return dialogMap.containsKey(commandNames);
    }
}
