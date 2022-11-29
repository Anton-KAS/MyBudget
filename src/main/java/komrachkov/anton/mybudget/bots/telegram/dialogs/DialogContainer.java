package komrachkov.anton.mybudget.bots.telegram.dialogs;

import com.google.common.collect.ImmutableMap;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.add.AddContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit.EditContainer;
import komrachkov.anton.mybudget.bots.telegram.util.CommandController;
import komrachkov.anton.mybudget.bots.telegram.util.Container;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.services.*;
import komrachkov.anton.mybudget.bots.telegram.keyboards.callback.NoKeyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.NoText;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class DialogContainer implements Container {
    private final ImmutableMap<String, CommandController> dialogMap;
    private final CommandController unknownDialog;

    public DialogContainer(BotMessageService botMessageService, TelegramUserService telegramUserService,
                           CallbackContainer callbackContainer, AccountTypeService accountTypeService,
                           CurrencyService currencyService, BankService bankService, AccountService accountService) {

        AddContainer addContainer = new AddContainer(botMessageService, telegramUserService,
                callbackContainer, accountTypeService, currencyService, bankService, accountService);

        EditContainer editContainer = new EditContainer(botMessageService, telegramUserService,
                callbackContainer, accountTypeService, currencyService, bankService, accountService);

        dialogMap = ImmutableMap.<String, CommandController>builder()
                .put(DialogNamesImpl.ADD_ACCOUNT.getName(),
                        new AccountDialog(botMessageService, telegramUserService, addContainer))
                .put(DialogNamesImpl.EDIT_ACCOUNT.getName(),
                        new AccountDialog(botMessageService, telegramUserService, editContainer))
                .put(DialogNamesImpl.DELETE_CONFIRM.getName(),
                        new DeleteConfirmDialog(botMessageService, telegramUserService))
                .put(DialogNamesImpl.DELETE_EXECUTE.getName(),
                        new DeleteExecuteDialog(botMessageService, telegramUserService, callbackContainer, accountService))
                .build();

        unknownDialog = new UnknownDialog(botMessageService, telegramUserService, ExecuteMode.SEND,
                new NoText(), new NoKeyboard());
        //TODO: Execute command подумать, может задавать автоматически
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
