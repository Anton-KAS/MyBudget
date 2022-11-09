package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.callbacks.Callback;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.AddAccount.CurrenciesKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountText;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.services.CurrencyService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.CALLBACK_OPERATION_DATA_INDEX;

public class CurrencyDialog implements Dialog, Callback {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final CurrencyService currencyService;
    private final Map<Long, Map<String, String>> dialogsMap;
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

        String text = new AddAccountText(getUserId(update)).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = new CurrenciesKeyboard(currencyService, telegramUserService,
                getUserId(update)).getKeyboard();

        botMessageService.executeMessage(getExecuteMode(update, dialogStep), getChatId(update), getMessageId(update),
                String.format(text, ASK_TEXT), inlineKeyboardMarkup);

        checkUserInDb(telegramUserService, update);
    }

    @Override
    public boolean commit(Update update) {
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
