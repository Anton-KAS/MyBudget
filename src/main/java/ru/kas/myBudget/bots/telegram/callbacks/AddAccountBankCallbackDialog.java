package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.AccountBanksKeyboard;
import ru.kas.myBudget.bots.telegram.keyboards.AddAccountEndKeyboard;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountBankText;
import ru.kas.myBudget.bots.telegram.texts.AddAccountEndText;
import ru.kas.myBudget.services.AccountTypeService;
import ru.kas.myBudget.services.BankService;
import ru.kas.myBudget.services.CurrencyService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.callbacks.CallbackName.ADD_ACCOUNT_END;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.CURRENT_DIALOG_STEP;

public class AddAccountBankCallbackDialog implements Callback {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final CurrencyService currencyService;
    private final AccountTypeService accountTypeService;
    private final BankService bankService;
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static int BANK_TYPE_ID_INDEX = 2;

    public AddAccountBankCallbackDialog(SendBotMessageService sendBotMessageService,
                                        TelegramUserService telegramUserService, CurrencyService currencyService,
                                        AccountTypeService accountTypeService, BankService bankService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.currencyService = currencyService;
        this.accountTypeService = accountTypeService;
        this.bankService = bankService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        long chatId = getChatId(update);

        Map<String, String> dialogSteps = dialogsMap.get(chatId);
        dialogSteps.put(dialogSteps.get(CURRENT_DIALOG_STEP.getDialogName()), getCallbackData(update)[BANK_TYPE_ID_INDEX]);
        dialogSteps.replace(CURRENT_DIALOG_STEP.getDialogName(), ADD_ACCOUNT_END.getCallbackName());

        String text = new AddAccountEndText(currencyService, accountTypeService, bankService, getUserId(update)).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = new AddAccountEndKeyboard().getKeyboard();

        sendBotMessageService.editMessageWithInlineKeyboard(chatId, getMessageId(update), text, inlineKeyboardMarkup);

        updateUserLastActiveInDb(telegramUserService, update);

    }
}
