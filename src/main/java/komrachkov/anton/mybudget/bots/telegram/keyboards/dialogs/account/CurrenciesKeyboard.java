package komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.DialogKeyboardImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.InlineKeyboardBuilder;
import komrachkov.anton.mybudget.models.Account;
import komrachkov.anton.mybudget.models.Currency;
import komrachkov.anton.mybudget.models.TelegramUser;
import komrachkov.anton.mybudget.services.CurrencyService;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;

import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.CURRENCY;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.DialogNamesImpl.*;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class CurrenciesKeyboard extends DialogKeyboardImpl {
    private CurrencyService currencyService;
    private TelegramUserService telegramUserService;
    private int page;
    private final static int NUM_IN_PAGE = 5;
    private final static String TEXT_BUTTON_PATTERN = "%s - %s";

    public CurrenciesKeyboard(String currentDialogName) {
        super(currentDialogName);
        this.callbackPattern = String.format(callbackPattern, CURRENCY.getName(), "%s");
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        List<komrachkov.anton.mybudget.models.Currency> currencies = getCurrenciesByOrder();
        InlineKeyboardBuilder inlineKeyboardBuilder = new InlineKeyboardBuilder();

        page = Math.max(page, 1);

        for (int i = NUM_IN_PAGE * (page - 1); i < NUM_IN_PAGE * page; i++) {
            if (i >= currencies.size()) break;
            komrachkov.anton.mybudget.models.Currency currency = currencies.get(i);
            inlineKeyboardBuilder.addRow()
                    .addButton(String.format(TEXT_BUTTON_PATTERN, currency.getSymbol(), currency.getCurrencyRu()),
                            String.format(callbackPattern, currency.getId()));
        }
        if (page > 1 || currencies.size() > NUM_IN_PAGE * page) inlineKeyboardBuilder.addRow();
        if (page > 1) inlineKeyboardBuilder.addPreviousPageButton(
                ADD_ACCOUNT.getName(), CURRENCY.getName(), page - 1);
        if (currencies.size() > NUM_IN_PAGE * page) inlineKeyboardBuilder.addNextPageButton(
                ADD_ACCOUNT.getName(), CURRENCY.getName(), page + 1);

        return inlineKeyboardBuilder.build();
    }

    private List<komrachkov.anton.mybudget.models.Currency> getCurrenciesByOrder() {
        /* Order:
        1. Used currencies
        2. National currency
        3. Reserve currencies
        4. Other currencies
         */

        List<komrachkov.anton.mybudget.models.Currency> currencies = new ArrayList<>();

        // 1
        TelegramUser telegramUser = telegramUserService.findById(userId).orElse(null);
        List<Account> accounts = null;

        if (telegramUser != null) accounts = telegramUser.getAccounts();
        if (accounts != null) {
            for (Account account : accounts) {
                komrachkov.anton.mybudget.models.Currency usedCurrency = account.getCurrency();
                if (currencies.contains(usedCurrency)) continue;
                currencies.add(usedCurrency);
                if (currencies.size() > page * NUM_IN_PAGE) break;
            }
            Collections.sort(currencies);
            if (currencies.size() > page * NUM_IN_PAGE) return currencies;
        }
        // 2 TODO: ADD national currency
        // 3
        List<komrachkov.anton.mybudget.models.Currency> reserveCurrencies = currencyService.getReserveCurrencies();
        addCurrenciesToList(currencies, reserveCurrencies);
        // 4
        List<komrachkov.anton.mybudget.models.Currency> otherCurrencies = currencyService.findAll();
        addCurrenciesToList(currencies, otherCurrencies);
        return currencies;
    }

    private void addCurrenciesToList(List<komrachkov.anton.mybudget.models.Currency> currencies, List<komrachkov.anton.mybudget.models.Currency> addCurrencies) {
        Collections.sort(addCurrencies);
        for (Currency addCurrency : addCurrencies) {
            if (currencies.contains(addCurrency)) continue;
            currencies.add(addCurrency);
            if (currencies.size() > page * NUM_IN_PAGE) return;
        }
    }

    public void setCurrencyService(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public void setTelegramUserService(TelegramUserService telegramUserService) {
        this.telegramUserService = telegramUserService;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public CurrenciesKeyboard setUserId(long userId) {
        this.userId = userId;
        return this;
    }
}
