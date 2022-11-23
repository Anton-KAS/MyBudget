package ru.kas.myBudget.bots.telegram.keyboards.AccountDialog;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.DialogKeyboardImpl;
import ru.kas.myBudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.services.BankService;

import java.util.List;

import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.BANK;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.ADD_ACCOUNT;

public class BanksKeyboard extends DialogKeyboardImpl {
    private BankService bankService;
    private final static String TEXT_BUTTON_PATTERN = "%s (%s)";

    public BanksKeyboard(String currentDialogName) {
        super(currentDialogName);
        this.callbackPattern = String.format(callbackPattern, BANK.getName(), "%s");
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        List<Bank> banks = bankService.findAll();
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (Bank bank : banks) {
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, bank.getTitleRu(), bank.getCountry().getTitleRu()),
                            String.format(callbackPattern, bank.getId()));
        }
        inlineKeyboardBuilder.addRow().addNextButton(ADD_ACCOUNT.getName(), BANK.getName());
        return inlineKeyboardBuilder.build();
    }

    public BanksKeyboard setBankService(BankService bankService) {
        this.bankService = bankService;
        return this;
    }

    public BanksKeyboard setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
