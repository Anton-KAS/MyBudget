package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.addAccount.CurrenciesKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountText;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.models.Currency;
import ru.kas.myBudget.services.CurrencyService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.CALLBACK_OPERATION_DATA_INDEX;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogMapDefaultName.PAGE;

public class CurrencyDialog implements Dialog, CommandController {
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
        long userId = UpdateParameter.getUserId(update);
        int dialogStep = Integer.parseInt(dialogsMap.get(userId).get(CURRENT_DIALOG_STEP.getDialogId()));
        String[] callbackData = UpdateParameter.getCallbackData(update);

        int page;
        if (update.hasCallbackQuery() && callbackData != null && callbackData.length > PAGE_INDEX
                && callbackData[PAGE_INDEX - 1].equals(PAGE.getId())) {
            page = Integer.parseInt(callbackData[PAGE_INDEX]);
        } else {
            page = 1;
        }

        ExecuteMode executeMode = getExecuteMode(update, dialogStep);
        String text = new AddAccountText().setUserId(userId).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = new CurrenciesKeyboard(currencyService, telegramUserService,
                userId, page).getKeyboard();

        botMessageService.executeAndUpdateUser(telegramUserService, update, executeMode,
                String.format(text, ASK_TEXT), inlineKeyboardMarkup);
    }

    @Override
    public boolean commit(Update update) {
        String[] callbackData = UpdateParameter.getCallbackData(update);
        if (callbackData == null) return false;

        if (update.hasCallbackQuery() && callbackData.length > PAGE_INDEX
                && callbackData[PAGE_INDEX - 1].equals(PAGE.getId())) return false;

        int currencyId = Integer.parseInt(callbackData[CALLBACK_OPERATION_DATA_INDEX.getIndex()]);

        Map<String, String> dialogSteps = dialogsMap.get(UpdateParameter.getChatId(update));
        dialogSteps.put(CURRENCY.getDialogId(), String.valueOf(currencyId));

        Optional<Currency> currency = currencyService.findById(currencyId);
        assert currency.isPresent();

        dialogSteps.put(CURRENCY.getDialogIdText(), String.format(CURRENCY.getDialogTextPattern(), "%s",
                currency.get().getSymbol() + " - " + currency.get().getCurrencyRu()));

        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }

    @Override
    public void skip(Update update) {

    }
}
