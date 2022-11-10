package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.callbacks.Callback;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.AddAccount.BanksKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.models.Bank;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.BankService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;
import static ru.kas.myBudget.bots.telegram.dialogs.DialogIndex.CALLBACK_OPERATION_DATA_INDEX;

public class BankDialog implements Dialog, Callback {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final BankService bankService;
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static String ASK_TEXT = "Выберете банк счета:";

    public BankDialog(BotMessageService botMessageService, TelegramUserService telegramUserService,
                      BankService bankService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.bankService = bankService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        int dialogStep = Integer.parseInt(dialogsMap.get(getUserId(update)).get(CURRENT_DIALOG_STEP.getDialogId()));
        long userId = getUserId(update);

        ExecuteMode executeMode = getExecuteMode(update, dialogStep);
        String text = new AddAccountText(userId).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = new BanksKeyboard(bankService).getKeyboard();

        sendAndUpdateUser(telegramUserService, botMessageService, update, executeMode, String.format(text, ASK_TEXT),
                inlineKeyboardMarkup);
    }

    @Override
    public boolean commit(Update update) {
        int bankId = Integer.parseInt(getCallbackData(update)[CALLBACK_OPERATION_DATA_INDEX.getIndex()]);

        Map<String, String> dialogSteps = dialogsMap.get(getChatId(update));
        dialogSteps.put(BANK.getDialogId(), String.valueOf(bankId));

        Optional<Bank> bank = bankService.findById(bankId);
        assert bank.isPresent();

        dialogSteps.put(BANK.getDialogIdText(), String.format(BANK.getDialogTextPattern(), "%s",
                bank.get().getTitleRu() + " (" + bank.get().getCountry().getTitleRu() + ")"));

        checkUserInDb(telegramUserService, update);
        return true;
    }
}
