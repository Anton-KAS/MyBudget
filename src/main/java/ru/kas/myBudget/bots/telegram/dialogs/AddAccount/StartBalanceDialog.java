package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.AddAccount.StartBalanceKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountText;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.services.CurrencyService;
import ru.kas.myBudget.services.TelegramUserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogPattern.CURRENCY_AMOUNT;
import static ru.kas.myBudget.bots.telegram.util.UpdateParameter.getUserId;

public class StartBalanceDialog implements Dialog, CommandController {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final CurrencyService currencyService;
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static String ASK_TEXT = "Введите текущий баланс счета:";
    private final static String VERIFY_EXCEPTION_TEXT = "Введите только одно число";
    private final static String DEFAULT_BALANCE_TEXT = "0.0";

    public StartBalanceDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                              CurrencyService currencyService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.currencyService = currencyService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        long userId = getUserId(update);
        int dialogStep = Integer.parseInt(dialogsMap.get(userId).get(CURRENT_DIALOG_STEP.getDialogId()));

        ExecuteMode executeMode = getExecuteMode(update, dialogStep);
        String text = new AddAccountText().setUserId(userId).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = new StartBalanceKeyboard().getKeyboard();

        botMessageService.executeAndUpdateUser(telegramUserService, update, executeMode,
                String.format(text, ASK_TEXT), inlineKeyboardMarkup);
    }

    @Override
    public boolean commit(Update update) {
        String text = UpdateParameter.getMessageText(update);

        if (!text.matches(CURRENCY_AMOUNT.getRegex())) {
            botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                    VERIFY_EXCEPTION_TEXT, null);
            return false;
        }

        addStartBalance(text, getUserId(update));

        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }

    @Override
    public void skip(Update update) {
        addStartBalance(DEFAULT_BALANCE_TEXT, getUserId(update));
    }

    private void addStartBalance(String text, long userId) {
        Map<String, String> dialogSteps = dialogsMap.get(userId);

        Optional<Currency> currency = currencyService.findById(Integer.parseInt(dialogSteps.get(CURRENCY.getDialogId())));
        int numberToBAsic = currency.map(Currency::getNumberToBasic).orElse(1);

        text = text.replace(",", ".");
        BigDecimal startBalance = new BigDecimal(text);
        startBalance = startBalance.setScale(String.valueOf(numberToBAsic).length() - 1, RoundingMode.HALF_UP);

        dialogSteps.put(START_BALANCE.getDialogId(), startBalance.toString());

        dialogSteps.put(START_BALANCE.getDialogIdText(),
                String.format(START_BALANCE.getDialogTextPattern(), "%s", startBalance));
    }
}
