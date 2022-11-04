package ru.kas.myBudget.bots.telegram.callbacks;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountTitleText;
import ru.kas.myBudget.services.AccountService;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.HashMap;
import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.*;

public class AddAccountCallback implements Callback {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final AccountService accountService;
    private final Map<Long, Map<String, String>> dialogsMap;

    public AddAccountCallback(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, AccountService accountService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.accountService = accountService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        String text = new AddAccountTitleText().getText();
        long chatId = getChatId(update);

        sendBotMessageService.sendMessage(chatId, text);
        updateUserLastActiveInDb(telegramUserService, update);

        dialogsMap.remove(chatId);
        Map<String, String> dialogSteps = new HashMap<>();
        dialogSteps.put(CURRENT_DIALOG_STEP.getDialogName(), ADD_ACCOUNT_TITLE.getDialogName());
        dialogsMap.put(getChatId(update), dialogSteps);
    }
}
