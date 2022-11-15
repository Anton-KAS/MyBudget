package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.Keyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.MessageText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.services.CurrencyService;
import ru.kas.myBudget.services.TelegramUserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogPattern.CURRENCY_AMOUNT;

public class StartBalanceDialog extends DialogImpl {
    private final CurrencyService currencyService;
    private final static String ASK_TEXT = "Введите текущий баланс счета:";
    public final static String VERIFY_EXCEPTION_TEXT = "Введите только одно число";
    private final static String DEFAULT_BALANCE_TEXT = "0.0";

    public StartBalanceDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                              MessageText messageText, Keyboard keyboard, DialogsMap dialogsMap,
                              CurrencyService currencyService) {
        super(botMessageService, telegramUserService, messageText, keyboard, dialogsMap, ASK_TEXT);
        this.currencyService = currencyService;
    }

    @Override
    public boolean commit(Update update) {
        long chatId = UpdateParameter.getChatId(update);
        String receivedText = UpdateParameter.getMessageText(update);

        if (update.hasCallbackQuery() || !receivedText.matches(CURRENCY_AMOUNT.getRegex())) {
            botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                    VERIFY_EXCEPTION_TEXT, null);
            return false;
        }

        BigDecimal startBalance = getStartBalance(receivedText, chatId);

        addToDialogMap(chatId, START_BALANCE, startBalance.toString(),
                String.format(START_BALANCE.getDialogTextPattern(), "%s", startBalance));

        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }

    @Override
    public void skip(Update update) {
        long chatId = UpdateParameter.getChatId(update);
        BigDecimal startBalance = getStartBalance(DEFAULT_BALANCE_TEXT, chatId);
        addToDialogMap(chatId, START_BALANCE, startBalance.toString(),
                String.format(START_BALANCE.getDialogTextPattern(), "%s", startBalance));
        telegramUserService.checkUser(telegramUserService, update);
    }

    private BigDecimal getStartBalance(String text, long chatId) {
        Map<String, String> dialogMap = dialogsMap.getDialogMapById(chatId);

        Optional<Currency> currency = currencyService.findById(Integer.parseInt(dialogMap.get(CURRENCY.getName())));
        int numberToBAsic = currency.map(Currency::getNumberToBasic).orElse(1);

        text = text.replace(",", ".");
        BigDecimal startBalance = new BigDecimal(text);

        return startBalance.setScale(String.valueOf(numberToBAsic).length() - 1, RoundingMode.HALF_UP);
    }
}
