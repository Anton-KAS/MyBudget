package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.models.AccountType;
import ru.kas.myBudget.services.AccountTypeService;

import java.util.List;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.*;

public class AccountTypeKeyboard implements Keyboard {
    private final AccountTypeService accountTypeService;
    private final static String TEXT_BUTTON_PATTERN = "%s";
    private final static String CALLBACK_BUTTON_PATTERN =
            ADD_ACCOUNT_CURRENCY.getCallbackName() + "_" + ADD_ACCOUNT_TYPE.getCallbackName() + "_%s";

    public AccountTypeKeyboard(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        List<AccountType> accountTypes = accountTypeService.findAll();
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (AccountType account : accountTypes) {
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, account.getTitleRu()),
                            String.format(CALLBACK_BUTTON_PATTERN, account.getId()));
        }
        return inlineKeyboardBuilder.build();
    }
}
