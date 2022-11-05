package ru.kas.myBudget.bots.telegram.texts;

import ru.kas.myBudget.bots.telegram.callbacks.CallbackName;
import ru.kas.myBudget.bots.telegram.dialogs.DialogName;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.models.AccountType;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.services.AccountTypeService;
import ru.kas.myBudget.services.CurrencyService;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class AddAccountBankText implements MessageText {
    private final CurrencyService currencyService;
    private final AccountTypeService accountTypeService;
    private final Long userId;

    public AddAccountBankText(CurrencyService currencyService, AccountTypeService accountTypeService, Long userId) {
        this.currencyService = currencyService;
        this.accountTypeService = accountTypeService;
        this.userId = userId;
    }

    @Override
    public String getText() {
        Map<String, String> dialogMap = DialogsMap.getDialogMap(userId);
        assert dialogMap != null;
        String title = Objects.requireNonNull(dialogMap.get(DialogName.ADD_ACCOUNT_TITLE.getDialogName()));
        String description = Objects.requireNonNull(dialogMap.get(DialogName.ADD_ACCOUNT_DESCRIPTION.getDialogName()));
        int currencyId = Integer.parseInt(dialogMap.get(CallbackName.ADD_ACCOUNT_CURRENCY.getCallbackName()));
        Optional<Currency> currency = currencyService.findById(currencyId);
        assert currency.isPresent();

        int accountTypeId = Integer.parseInt(dialogMap.get(CallbackName.ADD_ACCOUNT_TYPE.getCallbackName()));
        Optional<AccountType> accountType = accountTypeService.findById(accountTypeId);
        assert accountType.isPresent();

        return "<b>Добавление счета</b>\n\n" +
                "1. Название: " + title + "\n" +
                "2. Описание: " + description + "\n" +
                "3. Валюта: " + currency.get().getSymbol() + " - " + currency.get().getCurrencyRu() + "\n" +
                "4. Tип счета:" + accountType.get().getTitleRu() + "\n" +
                "5. <b>Выберете банк:</b>\n" +
                "<i>(для отмены введите /back)</i>";
    }
}
