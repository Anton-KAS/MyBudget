package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.services.CurrencyService;

import java.util.List;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.DIALOG;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.CURRENCY;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.*;

public class CurrenciesKeyboard implements Keyboard {
    private final CurrencyService currencyService;
    private final static String TEXT_BUTTON_PATTERN = "%s - %s";
    public final String CALLBACK_BUTTON_PATTERN = String.format("%s_%s_%s_%s_%s",
            DIALOG.getId(), ADD_ACCOUNT.getDialogName(), ADD_ACCOUNT.getDialogName(), CURRENCY.getDialogId(), "%s");

    public CurrenciesKeyboard(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        List<Currency> currencies = currencyService.findAll();
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (Currency currency : currencies) {
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, currency.getSymbol(), currency.getCurrencyRu()),
                            String.format(CALLBACK_BUTTON_PATTERN, currency.getId()));
        }
        return inlineKeyboardBuilder.build();
    }
}
