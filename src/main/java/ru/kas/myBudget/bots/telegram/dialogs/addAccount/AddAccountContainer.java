package ru.kas.myBudget.bots.telegram.dialogs.addAccount;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.dialogs.UnknownDialog;
import ru.kas.myBudget.bots.telegram.keyboards.addAccountDialog.*;
import ru.kas.myBudget.bots.telegram.keyboards.callback.NoKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.addAccountDialog.AddAccountText;
import ru.kas.myBudget.bots.telegram.texts.addAccountDialog.SaveDialogText;
import ru.kas.myBudget.bots.telegram.texts.callback.NoText;
import ru.kas.myBudget.bots.telegram.util.Container;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.*;

import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.*;

public class AddAccountContainer implements Container {
    private final ImmutableMap<String, Dialog> dialogMap;
    private final Dialog unknownDialog;

    public AddAccountContainer(BotMessageService botMessageService, TelegramUserService telegramUserService,
                               CallbackContainer callbackContainer,
                               AccountTypeService accountTypeService, CurrencyService currencyService,
                               BankService bankService, AccountService accountService) {
        dialogMap = ImmutableMap.<String, Dialog>builder()
                .put(START.getName(), new StartDialog(botMessageService, telegramUserService, new AddAccountText(),
                        null, DialogsMap.getDialogsMapClass()))
                .put(TYPE.getName(),
                        new TypeDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new TypeKeyboard(), DialogsMap.getDialogsMapClass(), accountTypeService))
                .put(TITLE.getName(),
                        new TitleDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new TitleKeyboard(), DialogsMap.getDialogsMapClass()))
                .put(DESCRIPTION.getName(),
                        new DescriptionDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new DescriptionKeyboard(), DialogsMap.getDialogsMapClass()))
                .put(CURRENCY.getName(),
                        new CurrencyDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new CurrenciesKeyboard(), DialogsMap.getDialogsMapClass(), currencyService))
                .put(BANK.getName(),
                        new BankDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new BanksKeyboard(), DialogsMap.getDialogsMapClass(), bankService))
                .put(START_BALANCE.getName(),
                        new StartBalanceDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new StartBalanceKeyboard(), DialogsMap.getDialogsMapClass(), currencyService))
                .put(CONFIRM.getName(),
                        new ConfirmDialog(botMessageService, telegramUserService, new AddAccountText(),
                                new ConfirmKeyboard(), DialogsMap.getDialogsMapClass()))
                .put(SAVE.getName(),
                        new SaveDialog(botMessageService, telegramUserService, new SaveDialogText(),
                                new SaveKeyboard(), DialogsMap.getDialogsMapClass(),
                                callbackContainer, accountTypeService, currencyService, bankService, accountService))
                .build();

        unknownDialog = new UnknownDialog(botMessageService, telegramUserService, ExecuteMode.SEND,
                new NoText(), new NoKeyboard());
    }

    public Dialog retrieve(String identifier) {
        return dialogMap.getOrDefault(identifier, unknownDialog);
    }
}
