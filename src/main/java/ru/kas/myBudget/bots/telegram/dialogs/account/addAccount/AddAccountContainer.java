package ru.kas.myBudget.bots.telegram.dialogs.account.addAccount;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.*;
import ru.kas.myBudget.bots.telegram.dialogs.account.*;
import ru.kas.myBudget.bots.telegram.dialogs.util.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogStepsContainer;
import ru.kas.myBudget.bots.telegram.keyboards.accountDialog.*;
import ru.kas.myBudget.bots.telegram.keyboards.accountDialog.addAccountDialog.AddAccountConfirmKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.callback.NoKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.accountDialog.AccountText;
import ru.kas.myBudget.bots.telegram.texts.accountDialog.SaveText;
import ru.kas.myBudget.bots.telegram.texts.callback.NoText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.*;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;
import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class AddAccountContainer implements DialogStepsContainer {
    private final ImmutableMap<String, Dialog> dialogMap;
    private final Dialog unknownDialog;

    public AddAccountContainer(BotMessageService botMessageService, TelegramUserService telegramUserService,
                               CallbackContainer callbackContainer,
                               AccountTypeService accountTypeService, CurrencyService currencyService,
                               BankService bankService, AccountService accountService) {

        String currentDialogName = ADD_ACCOUNT.getName();

        dialogMap = ImmutableMap.<String, Dialog>builder()
                .put(START.getName(), new AddAccountStartDialog(botMessageService, telegramUserService, new AccountText(),
                        null, ADD_ACCOUNT))
                .put(TYPE.getName(),
                        new TypeDialog(botMessageService, telegramUserService, new AccountText(),
                                new TypeKeyboard(currentDialogName), accountTypeService))
                .put(TITLE.getName(),
                        new TitleDialog(botMessageService, telegramUserService, new AccountText(),
                                new TitleKeyboard(currentDialogName)))
                .put(DESCRIPTION.getName(),
                        new DescriptionDialog(botMessageService, telegramUserService, new AccountText(),
                                new DescriptionKeyboard(currentDialogName)))
                .put(CURRENCY.getName(),
                        new CurrencyDialog(botMessageService, telegramUserService, new AccountText(),
                                new CurrenciesKeyboard(currentDialogName), currencyService))
                .put(BANK.getName(),
                        new BankDialog(botMessageService, telegramUserService, new AccountText(),
                                new BanksKeyboard(currentDialogName), bankService))
                .put(START_BALANCE.getName(),
                        new StartBalanceDialog(botMessageService, telegramUserService, new AccountText(),
                                new StartBalanceKeyboard(currentDialogName), currencyService))
                .put(CONFIRM.getName(),
                        new AddAccountConfirmDialog(botMessageService, telegramUserService, new AccountText(),
                                new AddAccountConfirmKeyboard(currentDialogName)))
                .put(SAVE.getName(),
                        new AddAccountSaveDialog(botMessageService, telegramUserService, new SaveText(),
                                new SaveKeyboard(currentDialogName), callbackContainer, accountTypeService, currencyService,
                                bankService, accountService))
                .build();

        unknownDialog = new UnknownDialog(botMessageService, telegramUserService, ExecuteMode.SEND,
                new NoText(), new NoKeyboard());
    }

    @Override
    public Dialog retrieve(String identifier) {
        return dialogMap.getOrDefault(identifier, unknownDialog);
    }

    @Override
    public boolean contains(String commandNames) {
        return dialogMap.containsKey(commandNames);
    }
}
