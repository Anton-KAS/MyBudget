package ru.kas.myBudget.bots.telegram.keyboards.AddAccount;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.keyboards.InlineKeyboardBuilder;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.models.Account;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.CurrencyService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.*;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackType.DIALOG;
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.CURRENCY;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.*;

public class CurrenciesKeyboard implements Keyboard {
    private final CurrencyService currencyService;
    private final TelegramUserService telegramUserService;
    private final long userId;
    private final static String TEXT_BUTTON_PATTERN = "%s - %s";
    public final String CALLBACK_BUTTON_PATTERN = String.format("%s_%s_%s_%s_%s",
            DIALOG.getId(), ADD_ACCOUNT.getDialogName(), ADD_ACCOUNT.getDialogName(), CURRENCY.getDialogId(), "%s");

    public CurrenciesKeyboard(CurrencyService currencyService, TelegramUserService telegramUserService, long userId) {
        this.currencyService = currencyService;
        this.telegramUserService = telegramUserService;
        this.userId = userId;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        List<Currency> currencies = getCurrenciesByOrder();
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (Currency currency : currencies) {
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, currency.getSymbol(), currency.getCurrencyRu()),
                            String.format(CALLBACK_BUTTON_PATTERN, currency.getId()));
        }
        return inlineKeyboardBuilder.build();
    }

    private List<Currency> getCurrenciesByOrder() {
        /* Order:
        1. Used currencies
        2. National currency
        3. Reserve currencies
        4. Other currencies
         */

        List<Currency> currencies = new ArrayList<>();

        // 1
        Optional<TelegramUser> optionalTelegramUser = telegramUserService.findById(userId);
        TelegramUser telegramUser = null;
        List<Account> accounts = null;
        if (optionalTelegramUser.isPresent()) {
            telegramUser = optionalTelegramUser.get();
        }
        if (telegramUser != null) {
            accounts = telegramUser.getAccounts();
        }
        if (accounts != null) {
            for (Account account : accounts) {
                Currency usedCurrency = account.getCurrency();
                if (currencies.contains(usedCurrency)) continue;
                currencies.add(usedCurrency);
            }
            Collections.sort(currencies);
        }
        // 2 TODO: ADD national currency
        // 3
        List<Currency> reserveCurrencies = currencyService.getReserveCurrencies();
        Collections.sort(reserveCurrencies);
        for (Currency reserveCurrency : reserveCurrencies) {
            if (currencies.contains(reserveCurrency)) continue;
            currencies.add(reserveCurrency);
        }
        // 4
        List<Currency> otherCurrencies = currencyService.findAll();
        Collections.sort(otherCurrencies);
        for (Currency otherCurrency : otherCurrencies) {
            if (currencies.contains(otherCurrency)) continue;
            currencies.add(otherCurrency);
        }
        return currencies;
    }
}
