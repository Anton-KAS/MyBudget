package ru.kas.myBudget.bots.telegram.keyboards.addAccountDialog;

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
import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.CURRENCY;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogNamesImpl.*;

public class CurrenciesKeyboard implements Keyboard {
    private CurrencyService currencyService;
    private TelegramUserService telegramUserService;
    private long userId;
    private int page;
    private final static int NUM_IN_PAGE = 5;
    private final static String TEXT_BUTTON_PATTERN = "%s - %s";
    public final String CALLBACK_BUTTON_PATTERN = String.format("%s_%s_%s_%s_%s",
            DIALOG.getId(), ADD_ACCOUNT.getName(), ADD_ACCOUNT.getName(), CURRENCY.getName(), "%s");

    public CurrenciesKeyboard() {
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        List<Currency> currencies = getCurrenciesByOrder();
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        page = Math.max(page, 1);

        for (int i = NUM_IN_PAGE * (page - 1); i < NUM_IN_PAGE * page; i++) {
            if (i >= currencies.size()) break;
            Currency currency = currencies.get(i);
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, currency.getSymbol(), currency.getCurrencyRu()),
                            String.format(CALLBACK_BUTTON_PATTERN, currency.getId()));
        }
        if (page > 1 || currencies.size() > NUM_IN_PAGE * page) inlineKeyboardBuilder.addRow();
        if (page > 1) inlineKeyboardBuilder.addButton(getPreviousPageButton(
                ADD_ACCOUNT.getName(), CURRENCY.getName(), page - 1));
        if (currencies.size() > NUM_IN_PAGE * page) inlineKeyboardBuilder.addButton(getNextPageButton(
                ADD_ACCOUNT.getName(), CURRENCY.getName(), page + 1));

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
        TelegramUser telegramUser = telegramUserService.findById(userId).orElse(null);
        List<Account> accounts = null;

        if (telegramUser != null) accounts = telegramUser.getAccounts();
        if (accounts != null) {
            for (Account account : accounts) {
                Currency usedCurrency = account.getCurrency();
                if (currencies.contains(usedCurrency)) continue;
                currencies.add(usedCurrency);
                if (currencies.size() > page * NUM_IN_PAGE) break;
            }
            Collections.sort(currencies);
            if (currencies.size() > page * NUM_IN_PAGE) return currencies;
        }
        // 2 TODO: ADD national currency
        // 3
        List<Currency> reserveCurrencies = currencyService.getReserveCurrencies();
        addCurrenciesToList(currencies, reserveCurrencies);
        // 4
        List<Currency> otherCurrencies = currencyService.findAll();
        addCurrenciesToList(currencies, otherCurrencies);
        return currencies;
    }

    private void addCurrenciesToList(List<Currency> currencies, List<Currency> addCurrencies) {
        Collections.sort(addCurrencies);
        for (Currency addCurrency : addCurrencies) {
            if (currencies.contains(addCurrency)) continue;
            currencies.add(addCurrency);
            if (currencies.size() > page * NUM_IN_PAGE) return;
        }
    }

    public CurrenciesKeyboard setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
        return this;
    }

    public CurrenciesKeyboard setTelegramUserService(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
        return this;
    }

    public CurrenciesKeyboard setPage(int page) {
        this.page = page;
        return this;
    }

    public CurrenciesKeyboard setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
