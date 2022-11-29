package komrachkov.anton.mybudget.bots.telegram.dialogs.account;

import komrachkov.anton.mybudget.bots.telegram.dialogs.DialogImpl;
import komrachkov.anton.mybudget.bots.telegram.keyboards.util.Keyboard;
import komrachkov.anton.mybudget.bots.telegram.texts.MessageText;
import komrachkov.anton.mybudget.bots.telegram.util.UpdateParameter;
import komrachkov.anton.mybudget.models.Currency;
import komrachkov.anton.mybudget.services.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;
import komrachkov.anton.mybudget.bots.telegram.keyboards.dialogs.account.CurrenciesKeyboard;
import komrachkov.anton.mybudget.bots.telegram.services.BotMessageService;
import komrachkov.anton.mybudget.bots.telegram.util.ExecuteMode;
import komrachkov.anton.mybudget.services.CurrencyService;

import java.util.Optional;

import static komrachkov.anton.mybudget.bots.telegram.callbacks.util.CallbackIndex.OPERATION_DATA;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.account.AccountNames.*;
import static komrachkov.anton.mybudget.bots.telegram.dialogs.util.DialogMapDefaultName.PAGE;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class CurrencyDialog extends DialogImpl {
    private final CurrencyService currencyService;
    private final static int PAGE_INDEX = 5;
    private final static String ASK_TEXT = "Выберете валюту счета:";
    private final CurrenciesKeyboard currenciesKeyboard = (CurrenciesKeyboard) keyboard;

    public CurrencyDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                          MessageText messageText, Keyboard keyboard,
                          CurrencyService currencyService) {
        super(botMessageService, telegramUserService, messageText, keyboard, ASK_TEXT);
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
        this.chatId = UpdateParameter.getUserId(update);
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);

        if (callbackData == null ||
                (update.hasCallbackQuery() &&
                        callbackData.length > PAGE_INDEX &&
                        callbackData[PAGE_INDEX - 1].equals(PAGE.getId())))
            return false;

        int currencyId;
        if (callbackData.length > OPERATION_DATA.ordinal())
            currencyId = Integer.parseInt(callbackData[OPERATION_DATA.ordinal()]);
        else return false;

        Optional<Currency> currency = currencyService.findById(currencyId);
        if (currency.isEmpty()) return false;

        String text = String.format(CURRENCY.getStepTextPattern(),
                "%s", currency.get().getSymbol() + " - " + currency.get().getCurrencyRu());

        addToDialogMap(chatId, CURRENCY, String.valueOf(currencyId), text);
        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }

    private void setKeyboardPage(Update update) {
        String[] callbackData = UpdateParameter.getCallbackData(update).orElse(null);
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
