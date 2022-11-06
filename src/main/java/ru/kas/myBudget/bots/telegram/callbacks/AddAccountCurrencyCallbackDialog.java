package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.AccountTypeKeyboard;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountTypeText;
import ru.kas.myBudget.services.AccountTypeService;
import ru.kas.myBudget.services.CurrencyService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.ADD_ACCOUNT_TYPE;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.CURRENT_DIALOG_STEP;

public class AddAccountCurrencyCallbackDialog implements Callback {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final CurrencyService currencyService;
    private final AccountTypeService accountTypeService;
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static int CURRENCY_ID_INDEX = 2;

    public AddAccountCurrencyCallbackDialog(SendBotMessageService sendBotMessageService,
                                            TelegramUserService telegramUserService, CurrencyService currencyService,
                                            AccountTypeService accountTypeService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.currencyService = currencyService;
        this.accountTypeService = accountTypeService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        long chatId = getChatId(update);

        Map<String, String> dialogSteps = dialogsMap.get(chatId);
        dialogSteps.put(dialogSteps.get(CURRENT_DIALOG_STEP.getDialogName()), getCallbackData(update)[CURRENCY_ID_INDEX]);
        dialogSteps.replace(CURRENT_DIALOG_STEP.getDialogName(), ADD_ACCOUNT_TYPE.getCallbackName());

        String text = new AddAccountTypeText(currencyService, getUserId(update)).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = new AccountTypeKeyboard(accountTypeService).getKeyboard();

        sendBotMessageService.editMessageWithInlineKeyboard(chatId, getMessageId(update), text, inlineKeyboardMarkup);

        updateUserLastActiveInDb(telegramUserService, update);

    }
}
