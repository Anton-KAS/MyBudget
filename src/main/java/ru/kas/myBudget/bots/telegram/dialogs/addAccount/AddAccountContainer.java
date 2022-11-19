package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.*;
import ru.kas.myBudget.bots.telegram.keyboards.addAccountDialog.*;
import ru.kas.myBudget.bots.telegram.keyboards.callback.NoKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.addAccountDialog.AddAccountText;
import ru.kas.myBudget.bots.telegram.texts.addAccountDialog.SaveDialogText;
import ru.kas.myBudget.bots.telegram.texts.callback.NoText;
import ru.kas.myBudget.bots.telegram.util.Container;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.*;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;
import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.*;

public class AddAccountContainer implements Container {
    private final ImmutableMap<String, Dialog> dialogMap;
    private final Dialog unknownDialog;

    public AddAccountContainer(BotMessageService botMessageService, TelegramUserService telegramUserService,
                               CallbackContainer callbackContainer,
                               AccountTypeService accountTypeService, CurrencyService currencyService,
                               BankService bankService, AccountService accountService) {

        String currentDialogName = ADD_ACCOUNT.getName();

        dialogMap = ImmutableMap.<String, Dialog>builder()
                .put(START.getName(), new AddAccountStartDialog(botMessageService, telegramUserService, new AddAccountText(),
                        null, ADD_ACCOUNT))
                .put(TYPE.getName(),
                        new TypeDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new TypeKeyboard(currentDialogName), accountTypeService))
                .put(TITLE.getName(),
                        new TitleDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new TitleKeyboard(currentDialogName)))
                .put(DESCRIPTION.getName(),
                        new DescriptionDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new DescriptionKeyboard(currentDialogName)))
                .put(CURRENCY.getName(),
                        new CurrencyDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new CurrenciesKeyboard(currentDialogName), currencyService))
                .put(BANK.getName(),
                        new BankDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new BanksKeyboard(currentDialogName), bankService))
                .put(START_BALANCE.getName(),
                        new StartBalanceDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new StartBalanceKeyboard(currentDialogName), currencyService))
                .put(CONFIRM.getName(),
                        new AddAccountConfirmDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new AddAccountConfirmKeyboard(currentDialogName)))
                .put(SAVE.getName(),
                        new AddAccountSaveDialog(botMessageService, telegramUserService, new SaveDialogText(),
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
