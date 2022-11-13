package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import com.google.common.collect.ImmutableMap;
import ru.kas.myBudget.bots.telegram.callbacks.CallbackContainer;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.UnknownDialog;
import ru.kas.myBudget.bots.telegram.keyboards.callback.NoKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.callback.NoText;
import ru.kas.myBudget.bots.telegram.util.Container;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.services.*;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.*;

public class AddAccountContainer implements Container {
    private final ImmutableMap<String, Dialog> dialogMap;
    private final Dialog unknownDialog;

    public AddAccountContainer(BotMessageService botMessageService, TelegramUserService telegramUserService,
                               CallbackContainer callbackContainer,
                               AccountTypeService accountTypeService, CurrencyService currencyService,
                               BankService bankService, AccountService accountService) {
        dialogMap = ImmutableMap.<String, Dialog>builder()
                .put(START.getName(), new StartDialog())
                .put(TYPE.getName(),
                        new TypeDialog(botMessageService, telegramUserService, accountTypeService))
                .put(TITLE.getName(),
                        new TitleDialog(botMessageService, telegramUserService))
                .put(DESCRIPTION.getName(),
                        new DescriptionDialog(botMessageService, telegramUserService))
                .put(CURRENCY.getName(),
                        new CurrencyDialog(botMessageService, telegramUserService, currencyService))
                .put(BANK.getName(),
                        new BankDialog(botMessageService, telegramUserService, bankService))
                .put(START_BALANCE.getName(),
                        new StartBalanceDialog(botMessageService, telegramUserService, currencyService))
                .put(CONFIRM.getName(),
                        new ConfirmDialog(botMessageService, telegramUserService))
                .put(SAVE.getName(),
                        new SaveDialog(botMessageService, telegramUserService, callbackContainer,
                                accountTypeService, currencyService, bankService, accountService))
                .build();

        unknownDialog = new UnknownDialog(botMessageService, telegramUserService, ExecuteMode.SEND,
                new NoText(), new NoKeyboard());
    }

    public Dialog retrieve(String identifier) {
        return dialogMap.getOrDefault(identifier, unknownDialog);
    }
}
