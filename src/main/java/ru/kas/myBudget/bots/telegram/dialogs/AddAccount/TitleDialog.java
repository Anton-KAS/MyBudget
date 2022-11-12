package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountText;
import ru.kas.myBudget.bots.telegram.util.CommandController;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.bots.telegram.util.UpdateParameter;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;

public class TitleDialog implements Dialog, CommandController {
    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static int MIN_TITLE_LENGTH = 2;
    private final static int MAX_TITLE_LENGTH = 30;
    private final static String ASK_TEXT = "Введите название счета:";
    private final static String VERIFY_EXCEPTION_TEXT = "Название должно быть от %s и до %s символов";

    public TitleDialog(BotMessageService botMessageService, TelegramUserService telegramUserService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        long userId = UpdateParameter.getUserId(update);
        int dialogStep = Integer.parseInt(dialogsMap.get(userId).get(CURRENT_DIALOG_STEP.getDialogId()));

        ExecuteMode executeMode = getExecuteMode(update, dialogStep);
        String text = new AddAccountText().setUserId(userId).getText();

        botMessageService.executeAndUpdateUser(telegramUserService, update, executeMode,
                String.format(text, ASK_TEXT), null);
    }

    @Override
    public boolean commit(Update update) {
        String text = UpdateParameter.getMessageText(update);
        if (text == null) return false;

        if (text.length() < MIN_TITLE_LENGTH || text.length() > MAX_TITLE_LENGTH) {
            botMessageService.executeAndUpdateUser(telegramUserService, update, ExecuteMode.SEND,
                    String.format(VERIFY_EXCEPTION_TEXT, MIN_TITLE_LENGTH, MAX_TITLE_LENGTH), null);
            return false;
        }

        Map<String, String> dialogSteps = dialogsMap.get(UpdateParameter.getChatId(update));
        dialogSteps.put(TITLE.getDialogId(), text);

        dialogSteps.put(TITLE.getDialogIdText(),
                String.format(TITLE.getDialogTextPattern(), "%s", text));

        telegramUserService.checkUser(telegramUserService, update);
        return true;
    }
}
