package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.callbacks.Callback;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.AddAccount.CurrenciesKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.CurrencyService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.CALLBACK_OPERATION_DATA_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.PAGE;

public class CurrencyDialog implements Dialog, Callback {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final CurrencyService currencyService;
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static int PAGE_INDEX = 5;
    private final static String ASK_TEXT = "Выберете валюту счета:";

    public CurrencyDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
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

        int page;
        if (update.hasCallbackQuery() && getCallbackData(update).length > PAGE_INDEX
                && getCallbackData(update)[PAGE_INDEX-1].equals(PAGE.getId())) {
            page = Integer.parseInt(getCallbackData(update)[PAGE_INDEX]);
        } else {
            page = 1;
        }

        ExecuteMode executeMode = getExecuteMode(update, dialogStep);
        String text = new AddAccountText().setUserId(userId).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = new CurrenciesKeyboard(currencyService, telegramUserService,
                getUserId(update), page).getKeyboard();

        sendAndUpdateUser(telegramUserService, botMessageService, update, executeMode, String.format(text, ASK_TEXT),
                inlineKeyboardMarkup);
    }

    @Override
    public boolean commit(Update update) {
        if (update.hasCallbackQuery() && getCallbackData(update).length > PAGE_INDEX
                && getCallbackData(update)[PAGE_INDEX-1].equals(PAGE.getId())) {
            return false;
        }

        int currencyId = Integer.parseInt(getCallbackData(update)[CALLBACK_OPERATION_DATA_INDEX.getIndex()]);

        Map<String, String> dialogSteps = dialogsMap.get(getChatId(update));
        dialogSteps.put(CURRENCY.getDialogId(), String.valueOf(currencyId));

        Optional<Currency> currency = currencyService.findById(currencyId);
        assert currency.isPresent();

        dialogSteps.put(CURRENCY.getDialogIdText(), String.format(CURRENCY.getDialogTextPattern(), "%s",
                currency.get().getSymbol() + " - " + currency.get().getCurrencyRu()));

        checkUserInDb(telegramUserService, update);
        return true;
    }
}
