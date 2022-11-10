package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.commands.Command;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.AddAccount.DescriptionKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.AddAccount.StartBalanceKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.services.CurrencyService;
import ru.kas.myBudget.services.TelegramUserService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogPattern.CURRENCY_AMOUNT;

public class StartBalanceDialog implements Dialog, Command {

    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final CurrencyService currencyService;
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static String ASK_TEXT = "Введите текущий баланс счета:";
    private final static String VERIFY_EXCEPTION_TEXT = "Введите только одно число";

    public StartBalanceDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                              CurrencyService currencyService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.currencyService = currencyService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        int dialogStep = Integer.parseInt(dialogsMap.get(getUserId(update)).get(CURRENT_DIALOG_STEP.getDialogId()));
        long userId = getUserId(update);

        ExecuteMode executeMode = getExecuteMode(update, dialogStep);
        String text = new AddAccountText(userId).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = new StartBalanceKeyboard().getKeyboard();

        sendAndUpdateUser(telegramUserService, botMessageService, update, executeMode, String.format(text, ASK_TEXT),
                inlineKeyboardMarkup);
    }

    @Override
    public boolean commit(Update update) {
        String text = getMessageText(update);

        if (!text.matches(CURRENCY_AMOUNT.getRegex())) {
            botMessageService.executeSendMessage(
                    getChatId(update), VERIFY_EXCEPTION_TEXT);
            return false;
        }

        Map<String, String> dialogSteps = dialogsMap.get(getChatId(update));

        Optional<Currency> currency = currencyService.findById(Integer.parseInt(dialogSteps.get(CURRENCY.getDialogId())));
        int numberToBAsic = currency.map(Currency::getNumberToBasic).orElse(1);

        text = text.replace(",", ".");
        BigDecimal startBalance = new BigDecimal(text);
        startBalance = startBalance.setScale(String.valueOf(numberToBAsic).length() - 1, RoundingMode.HALF_UP);

        dialogSteps.put(START_BALANCE.getDialogId(), startBalance.toString());

        dialogSteps.put(START_BALANCE.getDialogIdText(),
                String.format(START_BALANCE.getDialogTextPattern(), "%s", startBalance));

        checkUserInDb(telegramUserService, update);
        return true;
    }
}
