package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.services.BankService;

import java.util.List;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.ADD_ACCOUNT_CURRENCY;
import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.ADD_ACCOUNT_TYPE;

public class AccountBanksKeyboard implements Keyboard {
    private final BankService bankService;
    private final static String TEXT_BUTTON_PATTERN = "%s";
    private final static String CALLBACK_BUTTON_PATTERN =
            ADD_ACCOUNT_CURRENCY.getCallbackName() + "_" + ADD_ACCOUNT_TYPE.getCallbackName() + "_%s";

    public AccountBanksKeyboard(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        List<Bank> banks = bankService.findAll();
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (Bank bank : banks) {
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, bank.getTitleRu()),
                            String.format(CALLBACK_BUTTON_PATTERN, bank.getId()));
        }
        return inlineKeyboardBuilder.build();
    }
}
