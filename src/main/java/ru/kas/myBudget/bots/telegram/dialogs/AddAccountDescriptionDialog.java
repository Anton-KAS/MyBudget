package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.commands.Command;
import ru.kas.myBudget.bots.telegram.keyboards.CurrenciesKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.MenuKeyboard;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountCurrencyText;
import ru.kas.myBudget.bots.telegram.texts.AddAccountDescriptionText;
import ru.kas.myBudget.services.CurrencyService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.*;

public class AddAccountDescriptionDialog implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final CurrencyService currencyService;
    private final Map<Long, Map<String, String>> dialogsMap;

    public AddAccountDescriptionDialog(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, CurrencyService currencyService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.currencyService = currencyService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        long chatId = getChatId(update);

        Map<String, String> dialogSteps = dialogsMap.get(chatId);
        dialogSteps.put(dialogSteps.get(CURRENT_DIALOG_STEP.getDialogName()), getMessageText(update));
        dialogSteps.replace(CURRENT_DIALOG_STEP.getDialogName(), ADD_ACCOUNT_CURRENCY.getDialogName());

        String text = new AddAccountCurrencyText(getUserId(update)).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = new CurrenciesKeyboard(currencyService).getKeyboard();

        sendBotMessageService.sendMessageWithInlineKeyboard(chatId, text, inlineKeyboardMarkup);

        checkUserInDb(telegramUserService, update);

    }
}
