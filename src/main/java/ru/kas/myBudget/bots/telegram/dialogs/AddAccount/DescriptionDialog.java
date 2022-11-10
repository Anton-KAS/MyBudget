package ru.kas.myBudget.bots.telegram.dialogs.AddAccount;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.kas.myBudget.bots.telegram.commands.Command;
import ru.kas.myBudget.bots.telegram.dialogs.Dialog;
import ru.kas.myBudget.bots.telegram.dialogs.DialogsMap;
import ru.kas.myBudget.bots.telegram.keyboards.AddAccount.DescriptionKeyboard;
import ru.kas.myBudget.bots.telegram.services.BotMessageService;
import ru.kas.myBudget.bots.telegram.texts.AddAccountText;
import ru.kas.myBudget.bots.telegram.util.ExecuteMode;
import ru.kas.myBudget.models.TelegramUser;
import ru.kas.myBudget.services.TelegramUserService;

import java.util.Map;
import java.util.Optional;

import static ru.kas.myBudget.bots.telegram.dialogs.AddAccount.AddAccountName.*;

public class DescriptionDialog implements Dialog, Command {

    private final BotMessageService botMessageService;
    private final TelegramUserService telegramUserService;
    private final Map<Long, Map<String, String>> dialogsMap;
    private final static int MAX_DESCRIPTION_LENGTH = 100;
    private final static String ASK_TEXT = "Введите описание счета:";
    private final static String VERIFY_EXCEPTION_TEXT = "Описание должно быть до %s символов";

    public DescriptionDialog(BotMessageService botMessageService, TelegramUserService telegramUserService) {
        this.botMessageService = botMessageService;
        this.telegramUserService = telegramUserService;
        this.dialogsMap = DialogsMap.getDialogsMap();
    }

    @Override
    public void execute(Update update) {
        int dialogStep = Integer.parseInt(dialogsMap.get(getUserId(update)).get(CURRENT_DIALOG_STEP.getDialogId()));
        long userId = getUserId(update);

        ExecuteMode executeMode = getExecuteMode(update, dialogStep);
        String text = new AddAccountText(userId).getText();
        InlineKeyboardMarkup inlineKeyboardMarkup = new DescriptionKeyboard().getKeyboard();

        sendAndUpdateUser(telegramUserService, botMessageService, update, executeMode, String.format(text, ASK_TEXT),
                inlineKeyboardMarkup);
    }

    @Override
    public boolean commit(Update update) {
        String text = getMessageText(update);

        if (text.length() > MAX_DESCRIPTION_LENGTH) {
            botMessageService.executeSendMessage(
                    getChatId(update), String.format(VERIFY_EXCEPTION_TEXT, MAX_DESCRIPTION_LENGTH));
            return false;
        }

        Map<String, String> dialogSteps = dialogsMap.get(getChatId(update));
        dialogSteps.put(DESCRIPTION.getDialogId(), text);

        dialogSteps.put(DESCRIPTION.getDialogIdText(),
                String.format(DESCRIPTION.getDialogTextPattern(), "%s", text));

        checkUserInDb(telegramUserService, update);
        return true;
    }
}
