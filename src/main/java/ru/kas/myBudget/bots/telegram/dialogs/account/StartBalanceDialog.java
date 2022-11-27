package ru.kas.myBudget.bots.telegram.dialogs.account;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogImpl;
import ru.kas.myBudget.bots.telegram.dialogs.util.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.util.Keyboard;
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

import static ru.kas.myBudget.bots.telegram.dialogs.account.AccountNames.*;
import static ru.kas.myBudget.bots.telegram.dialogs.util.DialogPattern.CURRENCY_AMOUNT;

/**
 * @author Anton Komrachkov
 * @since 0.2
 */

public class StartBalanceDialog extends DialogImpl {
    protected final CurrencyService currencyService;
    protected final static String ASK_TEXT = "Введите текущий баланс счета:";
    public final static String VERIFY_EXCEPTION_TEXT = "Введите только одно число";
    protected final static String DEFAULT_BALANCE_TEXT = "0.0";

    public StartBalanceDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                              MessageText messageText, Keyboard keyboard, CurrencyService currencyService) {
        super(botMessageService, telegramUserService, messageText, keyboard, ASK_TEXT);
        this.currencyService = currencyService;
    }

    @Override
    public boolean commit(Update update) {
        this.userId = UpdateParameter.getUserId(update);
        this.chatId = UpdateParameter.getChatId(update);
        String receivedText = UpdateParameter.getMessageText(update);

        if (update.hasCallbackQuery() || !receivedText.matches(CURRENCY_AMOUNT.getRegex())) {
            botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                    VERIFY_EXCEPTION_TEXT, null);
            return false;
        }

        BigDecimal startBalance = getStartBalance(receivedText, chatId);

        addToDialogMap(chatId, START_BALANCE, startBalance.toString(),
                String.format(START_BALANCE.getStepTextPattern(), "%s", startBalance));

        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }

    @Override
    public void skip(Update update) {
        this.userId = UpdateParameter.getUserId(update);
        this.chatId = UpdateParameter.getChatId(update);
        BigDecimal startBalance = getStartBalance(DEFAULT_BALANCE_TEXT, chatId);
        if (!DialogsMap.getDialogMap(chatId).containsKey(START_BALANCE.getName())) {
            addToDialogMap(chatId, START_BALANCE, startBalance.toString(),
                    String.format(START_BALANCE.getStepTextPattern(), "%s", startBalance));
        }
        telegramUserService.checkUser(telegramUserService, update);
    }

    private BigDecimal getStartBalance(String text, long chatId) {
        Map<String, String> dialogMap = DialogsMap.getDialogMapById(chatId);

        Optional<Currency> currency = currencyService.findById(Integer.parseInt(dialogMap.get(CURRENCY.getName())));
        int numberToBAsic = currency.map(Currency::getNumberToBasic).orElse(1);

        text = text.replace(",", ".");
        BigDecimal startBalance = new BigDecimal(text);

        return startBalance.setScale(String.valueOf(numberToBAsic).length() - 1, RoundingMode.HALF_UP);
    }
}
