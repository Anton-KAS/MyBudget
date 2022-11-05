package ru.kas.myBudget.bots.telegram.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.services.CurrencyService;

import java.util.List;

public class CurrenciesKeyboard implements Keyboard{
    private final CurrencyService currencyService;
    private final static String TEXT_BUTTON_PATTERN = "%s - %s";
    private final static String CALLBACK_BUTTON_PATTERN = "addAccountDialog_addCurrency_%s";

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
