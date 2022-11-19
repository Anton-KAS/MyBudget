package ru.kas.myBudget.bots.telegram.keyboards.addAccountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.DialogKeyboardImpl;
import ru.kas.myBudget.bots.telegram.keyboards.InlineKeyboardBuilder;
import ru.kas.myBudget.models.AccountType;
import ru.kas.myBudget.services.AccountTypeService;

import static ru.kas.myBudget.bots.telegram.dialogs.addAccount.AddAccountNames.TYPE;

public class TypeKeyboard extends DialogKeyboardImpl {
    private AccountTypeService accountTypeService;
    private final static String TEXT_BUTTON_PATTERN = "%s";

    public TypeKeyboard(String currentDialogName) {
        super(currentDialogName);
        this.callbackPattern = String.format(callbackPattern, TYPE.getName(), "%s");
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (AccountType account : accountTypeService.findAll()) {
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, account.getTitleRu()),
                            String.format(callbackPattern, account.getId()));
        }
        return inlineKeyboardBuilder.build();
    }

    public TypeKeyboard setAccountTypeService(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
        return this;
    }
}
