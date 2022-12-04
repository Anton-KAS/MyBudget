package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DialogKeyboardImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import komrachkov.anton.mybudget.models.Account;
import komrachkov.anton.mybudget.models.Currency;
import komrachkov.anton.mybudget.models.TelegramUser;
import komrachkov.anton.mybudget.services.CurrencyService;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.CURRENCY;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

@Component
@Scope("prototype")
public class CurrenciesKeyboard extends DialogKeyboardImpl {
    private final CurrencyService currencyService;
    private final TelegramUserService telegramUserService;
    private int page;
    private final static int NUM_IN_PAGE = 5;
    private final static String TEXT_BUTTON_PATTERN = "%s - %s";

    public CurrenciesKeyboard(TelegramUserService telegramUserService, CurrencyService currencyService) {
        super();
        this.telegramUserService = telegramUserService;
        this.currencyService = currencyService;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        List<Currency> currencies = getCurrenciesByOrder(currencyService.findAll());
        return addNavigationByPages(getKeyboardForPage(currencies), currencies.size(), false).build();
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (2.12.2022)
     */
    public InlineKeyboardMarkup getKeyboard(String searchWord) {
        List<Currency> currencies = getCurrenciesByOrder(currencyService.getCurrenciesBySearchWord(searchWord));
        return addNavigationByPages(getKeyboardForPage(currencies), currencies.size(), true).build();
    }

    private List<komrachkov.anton.mybudget.models.Currency> getCurrenciesByOrder(List<Currency> currencies) {
        /* Order:
        1. Used currencies
        2. National currency
        3. Reserve currencies
        4. Other currencies
         */

        List<Currency> sortedCurrencies = new ArrayList<>();

        // 1
        TelegramUser telegramUser = telegramUserService.findById(userId).orElse(null);
        List<Account> accounts = null;

        if (telegramUser != null) accounts = telegramUser.getAccounts();
        if (accounts != null) {
            for (Account account : accounts) {
                Currency usedCurrency = account.getCurrency();
                if (sortedCurrencies.contains(usedCurrency)) continue;
                if (!currencies.contains(usedCurrency)) continue;
                sortedCurrencies.add(usedCurrency);
                if (sortedCurrencies.size() > page * NUM_IN_PAGE) break;
            }
            Collections.sort(sortedCurrencies);
            if (sortedCurrencies.size() > page * NUM_IN_PAGE) return sortedCurrencies;
        }
        // 2 TODO: ADD national currency
        // 3
        List<Currency> reserveCurrencies = currencyService.getReserveCurrencies();
        addCurrenciesToList(currencies, sortedCurrencies, reserveCurrencies);
        // 4
        addCurrenciesToList(currencies, sortedCurrencies, currencies);
        return sortedCurrencies;
    }

    private void addCurrenciesToList(List<Currency> currencies, List<Currency> sortedCurrencies, List<Currency> addCurrencies) {
        Collections.sort(addCurrencies);
        for (Currency addCurrency : addCurrencies) {
            if (sortedCurrencies.contains(addCurrency)) continue;
            if (!currencies.contains(addCurrency)) continue;
            sortedCurrencies.add(addCurrency);
            if (sortedCurrencies.size() > page * NUM_IN_PAGE) return;
        }
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (2.12.2022)
     */
    private InlineKeyboardBuilder getKeyboardForPage(List<Currency> currencies) {
        String callbackFormat = String.format(CALLBACK_FORMAT, currentDialogName, currentDialogName, CURRENCY.getName(), "%s");

        page = Math.max(page, 1);
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        for (int i = NUM_IN_PAGE * (page - 1); i < NUM_IN_PAGE * page; i++) {
            if (i >= currencies.size()) break;
            Currency currency = currencies.get(i);
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, currency.getSymbol(), currency.getCurrencyRu()),
                            String.format(callbackFormat, currency.getId()));
        }
        return inlineKeyboardBuilder;
    }

    /**
     * @author Anton Komrachkov
     * @since 0.4 (2.12.2022)
     */
    private InlineKeyboardBuilder addNavigationByPages(InlineKeyboardBuilder inlineKeyboardBuilder, int currenciesSize, boolean allButton) {
        if (page > 1 || currenciesSize > NUM_IN_PAGE * page || allButton) inlineKeyboardBuilder.addRow();

        if (page > 1) inlineKeyboardBuilder.addPreviousPageButton(
                ADD_ACCOUNT.getName(), CURRENCY.getName(), page - 1);
        else if (currenciesSize > NUM_IN_PAGE * page) inlineKeyboardBuilder.addEmptyButton();

        if (allButton) inlineKeyboardBuilder.addReturnToAllButton(ADD_ACCOUNT.getName(), CURRENCY.getName(), 1);

        if (currenciesSize > NUM_IN_PAGE * page) inlineKeyboardBuilder.addNextPageButton(
                ADD_ACCOUNT.getName(), CURRENCY.getName(), page + 1);
        else if (page > 1) inlineKeyboardBuilder.addEmptyButton();

        return inlineKeyboardBuilder;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public CurrenciesKeyboard setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
