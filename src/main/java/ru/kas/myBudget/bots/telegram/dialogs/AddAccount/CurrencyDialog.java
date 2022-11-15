package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.keyboards.addAccountDialog.CurrenciesKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.services.CurrencyService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.CALLBACK_OPERATION_DATA_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.PAGE;

public class CurrencyDialog extends DialogImpl {
    private final CurrencyService currencyService;
    private final static int PAGE_INDEX = 5;
    private final static String ASK_TEXT = "Выберете валюту счета:";
    private final CurrenciesKeyboard currenciesKeyboard = (CurrenciesKeyboard) keyboard;

    public CurrencyDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                          MessageText messageText, Keyboard keyboard, DialogsMap dialogsMap,
                          CurrencyService currencyService) {
        super(botMessageService, telegramUserService, messageText, keyboard, dialogsMap, ASK_TEXT);
        this.currencyService = currencyService;
    }

    @Override
    public void executeByOrder(Update update, ExecuteMode executeMode) {
        this.userId = UpdateParameter.getUserId(update);
        setKeyboardPage(update);
        setKeyboardServices();

        super.executeByOrder(update, executeMode);
    }

    @Override
    public boolean commit(Update update) {
        this.userId = UpdateParameter.getUserId(update);
        String[] callbackData = UpdateParameter.getCallbackData(update);

        if (callbackData == null ||
                (update.hasCallbackQuery() &&
                        callbackData.length > PAGE_INDEX &&
                        callbackData[PAGE_INDEX - 1].equals(PAGE.getId())))
            return false;

        int currencyId;
        if (callbackData.length > CALLBACK_OPERATION_DATA_INDEX.getIndex())
            currencyId = Integer.parseInt(callbackData[CALLBACK_OPERATION_DATA_INDEX.getIndex()]);
        else return false;

        Optional<Currency> currency = currencyService.findById(currencyId);
        if (currency.isEmpty()) return false;

        String text = String.format(CURRENCY.getDialogTextPattern(),
                "%s", currency.get().getSymbol() + " - " + currency.get().getCurrencyRu());

        addToDialogMap(userId, CURRENCY, String.valueOf(currencyId), text);
        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }

    private void setKeyboardPage(Update update) {
        String[] callbackData = UpdateParameter.getCallbackData(update);
        int page;
        if (update.hasCallbackQuery() && callbackData != null && callbackData.length > PAGE_INDEX
                && callbackData[PAGE_INDEX - 1].equals(PAGE.getId())) {
            page = Integer.parseInt(callbackData[PAGE_INDEX]);
        } else {
            page = 1;
        }
        currenciesKeyboard.setPage(page);
    }

    private void setKeyboardServices() {
        currenciesKeyboard.setUserId(userId);
        currenciesKeyboard.setTelegramUserService(telegramUserService);
        currenciesKeyboard.setCurrencyService(currencyService);
    }
}
