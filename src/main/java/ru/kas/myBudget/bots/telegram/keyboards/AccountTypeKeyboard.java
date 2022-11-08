package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName;
import ru.kas.myBudget.bots.telegram.dialogs.DialogName;
import ru.kas.myBudget.models.AccountType;
import ru.kas.myBudget.services.AccountTypeService;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.DIALOG;

public class AccountTypeKeyboard implements Keyboard {
    private final AccountTypeService accountTypeService;
    private final static String TEXT_BUTTON_PATTERN = "%s";
    private final static String CALLBACK_BUTTON_PATTERN = String.format("%s_%s_%s_%s_%s",
            DIALOG.getId(),
            DialogName.ADD_ACCOUNT.getDialogName(),
            DialogName.ADD_ACCOUNT.getDialogName(),
            AddAccountName.TYPE.getDialogId(),
            "%s");

    public AccountTypeKeyboard(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (AccountType account : accountTypeService.findAll()) {
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, account.getTitleRu()),
                            String.format(CALLBACK_BUTTON_PATTERN, account.getId()));
        }
        return inlineKeyboardBuilder.build();
    }
}
