package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.services.BankService;

import java.util.List;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.DIALOG;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.BANK;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.ADD_ACCOUNT;

public class AccountBanksKeyboard implements Keyboard {
    private final BankService bankService;
    private final static String TEXT_BUTTON_PATTERN = "%s (%s)";
    public final String CALLBACK_BUTTON_PATTERN = String.format("%s_%s_%s_%s_%s",
            DIALOG.getId(), ADD_ACCOUNT.getDialogName(), ADD_ACCOUNT.getDialogName(), BANK.getDialogId(), "%s");

    public AccountBanksKeyboard(BankService bankService) {
        this.bankService = bankService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        List<Bank> banks = bankService.findAll();
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (Bank bank : banks) {
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, bank.getTitleRu(), bank.getCountry().getTitleRu()),
                            String.format(CALLBACK_BUTTON_PATTERN, bank.getId()));
        }
        inlineKeyboardBuilder.addRow().addButton(getNextButton(ADD_ACCOUNT.getDialogName(), BANK.getDialogId()));
        return inlineKeyboardBuilder.build();
    }
}
