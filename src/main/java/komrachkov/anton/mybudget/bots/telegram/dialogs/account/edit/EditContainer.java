package komrachkov.anton.mybudget.bots.telegram.dialogs.account.edit;

import com.google.common.collect.ImmutableMap;
import komrachkov.anton.mybudget.bots.telegram.dialogs.UnknownDialog;
import komrachkov.anton.mybudget.bots.telegram.dialogs.account.*;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogStepsContainer;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.*;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.edit.EditConfirmKeyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.SaveText;
import komrachkov.anton.mybudget.services.*;
import komrachkov.anton.mybudget.bots.telegram.callbacks.CallbackContainer;
import komrachkov.anton.mybudget.bots.telegram.dialogs.util.Dialog;
import komrachkov.anton.mybudget.bots.telegram.keyboards.callback.NoKeyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.texts.dialogs.account.AccountText;
import komrachkov.anton.mybudget.bots.telegram.texts.callback.NoText;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.EDIT_ACCOUNT;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class EditContainer implements DialogStepsContainer {
    private final ImmutableMap<String, Dialog> dialogMap;
    private final Dialog unknownDialog;

    public EditContainer(BotMessageService botMessageService, TelegramUserService telegramUserService,
                         CallbackContainer callbackContainer,
                         AccountTypeService accountTypeService, CurrencyService currencyService,
                         BankService bankService, AccountService accountService) {

        String currentDialogName = EDIT_ACCOUNT.getName();

        dialogMap = ImmutableMap.<String, Dialog>builder()
                .put(START.getName(), new EditStartDialog(botMessageService, telegramUserService, new AccountText(),
                        null, EDIT_ACCOUNT, accountService))
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
                        new EditConfirmDialog(botMessageService, telegramUserService, new AccountText(),
                                new EditConfirmKeyboard(currentDialogName)))
                .put(SAVE.getName(),
                        new EditSaveDialog(botMessageService, telegramUserService, new SaveText(),
                                new SaveKeyboard(currentDialogName), callbackContainer, accountTypeService, currencyService, bankService,
                                accountService))
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
