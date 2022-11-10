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
    private final int page;
    private final static int NUM_IN_PAGE = 5;
    private final static String TEXT_BUTTON_PATTERN = "%s - %s";
    public final String CALLBACK_BUTTON_PATTERN = String.format("%s_%s_%s_%s_%s",
            DIALOG.getId(), ADD_ACCOUNT.getDialogName(), ADD_ACCOUNT.getDialogName(), CURRENCY.getDialogId(), "%s");

    public CurrenciesKeyboard(CurrencyService currencyService, TelegramUserService telegramUserService,
                              long userId, int page) {
        this.currencyService = currencyService;
        this.telegramUserService = telegramUserService;
        this.userId = userId;
        this.page = page;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        List<Currency> currencies = getCurrenciesByOrder();
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (int i = NUM_IN_PAGE * (page - 1); i < NUM_IN_PAGE * page; i++) {
            if (i >= currencies.size()) break;
            Currency currency = currencies.get(i);
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, currency.getSymbol(), currency.getCurrencyRu()),
                            String.format(CALLBACK_BUTTON_PATTERN, currency.getId()));
        }
        if (page > 1 || currencies.size() > NUM_IN_PAGE * page) inlineKeyboardBuilder.addRow();
        if (page > 1) inlineKeyboardBuilder.addButton(getPreviousPageButton(
                ADD_ACCOUNT.getDialogName(), CURRENCY.getDialogId(), page - 1));
        if (currencies.size() > NUM_IN_PAGE * page) inlineKeyboardBuilder.addButton(getNextPageButton(
                ADD_ACCOUNT.getDialogName(), CURRENCY.getDialogId(), page + 1));

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
        if (optionalTelegramUser.isPresent()) telegramUser = optionalTelegramUser.get();

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
}
