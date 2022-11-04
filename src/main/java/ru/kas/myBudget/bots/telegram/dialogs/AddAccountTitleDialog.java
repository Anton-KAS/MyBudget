package ru.kas.myBudget.bots.telegram.dialogs;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.commands.Command;
import ru.kas.myBudget.bots.telegram.services.SendBotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountDescriptionText;
import ru.kas.myBudget.bots.telegram.texts.AddAccountTitleText;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.DialogName.*;

public class AddAccountTitleDialog implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final Map<Long, Map<String, String>> dialogsMap;

    public AddAccountTitleDialog(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        long chatId = getChatId(update);

        Map<String, String> dialogSteps = dialogsMap.get(chatId);
        dialogSteps.put(dialogSteps.get(CURRENT_DIALOG_STEP.getDialogName()), getMessageText(update));
        dialogSteps.replace(CURRENT_DIALOG_STEP.getDialogName(), ADD_ACCOUNT_DESCRIPTION.getDialogName());

        String text = new AddAccountDescriptionText().getText(telegramUserService, getUserId(update));

        sendBotMessageService.sendMessage(chatId, text);

        checkUserInDb(telegramUserService, update);

    }
}
